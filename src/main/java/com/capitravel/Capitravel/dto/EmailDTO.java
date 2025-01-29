package com.capitravel.Capitravel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 32, message = "Name must be between 3 and 32 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Name can only contain letters and spaces")
    private String name;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 32, message = "Last name must be between 3 and 32 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "Last name can only contain letters and spaces")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid and contain @ and .com")
    private String email;
}
