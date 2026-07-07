package com.danztee.patientservice.mapper;

import com.danztee.patientservice.dto.PatientRequestDTO;
import com.danztee.patientservice.dto.PatientResponseDTO;
import com.danztee.patientservice.model.Patient;

public class PatientMapper {
    public static PatientResponseDTO toDTO(Patient patient) {
//        PatientResponseDTO patientDTO = new PatientResponseDTO();
//        patientResponseDTO.setFirstName(patient.getFirstName());
//        patientResponseDTO.setLastName(patient.getLastName());
//        patientResponseDTO.setEmail(patient.getEmail());
//        patientResponseDTO.setAddress(patient.getAddress());
//        patientResponseDTO.setPhoneNumber(patient.getPhoneNumber());
//        return patientResponseDTO;

        return PatientResponseDTO.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .address(patient.getAddress())
                .phoneNumber(patient.getPhoneNumber())
                .build();
    }


    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        Patient patient = new Patient();

        patient = Patient.builder()
                .firstName(patientRequestDTO.getFirstName())
                .lastName(patientRequestDTO.getLastName())
                .email(patientRequestDTO.getEmail())
                .address(patientRequestDTO.getAddress())
                .phoneNumber(patientRequestDTO.getPhoneNumber())
                .dateOfBirth(patientRequestDTO.getDateOfBirth())
                .registrationDate(patientRequestDTO.getRegistrationDate())
                .build();

        return patient;
    }
}
