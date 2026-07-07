package com.danztee.patientservice.service;

import com.danztee.patientservice.dto.PatientResponseDTO;
import com.danztee.patientservice.mapper.PatientMapper;
import com.danztee.patientservice.model.Patient;
import com.danztee.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
