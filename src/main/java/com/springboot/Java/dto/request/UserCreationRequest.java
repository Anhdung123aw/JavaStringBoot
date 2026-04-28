package com.springboot.Java.dto.request;

import com.springboot.Java.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size( min =4,message = "INVALID_USER")
    String username;
    @Size(min = 8 , message = "INVALID_PASSWORD")
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min=16,message = "INVALID_DOB")
    LocalDate dob;
    private Set<String> roles;
}
