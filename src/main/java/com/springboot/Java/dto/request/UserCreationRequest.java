package com.springboot.Java.dto.request;

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
    @Size( min =4,message = "Username must be at least 3 character")
    String username;
    @Size(min = 8 , message = "Password  must be at least 8 character!")
    String password;
    String firstName;
    String lastName;
    LocalDate dob;
    private Set<String> roles;
}
