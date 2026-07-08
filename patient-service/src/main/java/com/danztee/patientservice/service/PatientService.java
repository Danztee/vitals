package com.danztee.patientservice.service;

import com.danztee.patientservice.dto.PatientRequestDTO;
import com.danztee.patientservice.dto.PatientResponseDTO;
import com.danztee.patientservice.exception.EmailAlreadyExistsException;
import com.danztee.patientservice.exception.PatientNotFoundException;
import com.danztee.patientservice.mapper.PatientMapper;
import com.danztee.patientservice.model.Patient;
import com.danztee.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toDTO).toList();

    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {

        if (patientRepository.existsByEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Patient newPatient = patientRepository
                .save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }


    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO) {
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
    }
}
