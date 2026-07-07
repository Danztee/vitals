package com.danztee.patientservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class PatientResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phoneNumber;
}
