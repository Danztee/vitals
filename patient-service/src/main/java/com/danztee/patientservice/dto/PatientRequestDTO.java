package com.danztee.patientservice.dto;

import com.danztee.patientservice.dto.validators.CreatePatientValidationGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PatientRequestDTO {
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 255, message = "First name must be between 2 and 255 characters")
    private String firstName;

    @NotBlank(message = "Last name cannot be blank")
    @Size(min = 2, max = 255, message = "Last name must be between 2 and 255 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Address cannot be blank")
    @Size(min = 2, max = 255, message = "Address must be between 2 and 255 characters")
    private String address;

    private String phoneNumber;

    @NotNull(message = "Date of birth cannot be blank")
    private LocalDate dateOfBirth;

    @NotNull(groups = CreatePatientValidationGroup.class, message = "Registration date cannot be blank")
    private LocalDateTime registrationDate;

}
