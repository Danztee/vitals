package com.danztee.patientservice.service;

import com.danztee.patientservice.dto.PatientRequestDTO;
import com.danztee.patientservice.dto.PatientResponseDTO;
import com.danztee.patientservice.exception.EmailAlreadyExistsException;
import com.danztee.patientservice.exception.PatientNotFoundException;
import com.danztee.patientservice.grpc.BillingServiceGrpcClient;
import com.danztee.patientservice.kafka.KafkaProducer;
import com.danztee.patientservice.mapper.PatientMapper;
import com.danztee.patientservice.model.Patient;
import com.danztee.patientservice.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final BillingServiceGrpcClient billingServiceGrpcClient;
    private final KafkaProducer kafkaProducer;

    public PatientService(PatientRepository patientRepository,
                          BillingServiceGrpcClient billingServiceGrpcClient,
                          KafkaProducer kafkaProducer) {
        this.patientRepository = patientRepository;
        this.billingServiceGrpcClient = billingServiceGrpcClient;
        this.kafkaProducer = kafkaProducer;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDTO).toList();

    }

    public PatientResponseDTO createPatient(@NonNull PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Patient newPatient = patientRepository
                .save(PatientMapper.toModel(patientRequestDTO));

        billingServiceGrpcClient.createBillingAccount(newPatient.getId().toString(),
                newPatient.getFirstName(), newPatient.getLastName(),
                newPatient.getEmail(), newPatient.getPhoneNumber());

        kafkaProducer.sendEvent(newPatient);

        return PatientMapper.toDTO(newPatient);
    }


    public PatientResponseDTO updatePatient(UUID id, @NonNull PatientRequestDTO patientRequestDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        if (patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Patient.builder().firstName(patientRequestDTO.getFirstName())
                .lastName(patientRequestDTO.getLastName())
                .email(patientRequestDTO.getEmail())
                .address(patientRequestDTO.getAddress())
                .phoneNumber(patientRequestDTO.getPhoneNumber())
                .dateOfBirth(patientRequestDTO.getDateOfBirth())
                .registrationDate(patientRequestDTO.getRegistrationDate())
                .build();

        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toDTO(updatedPatient);
    }

    public void deletePatient(UUID id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found"));

        patientRepository.deleteById(id);

        log.info("Deleted patient with id {}", id);
    }
}
