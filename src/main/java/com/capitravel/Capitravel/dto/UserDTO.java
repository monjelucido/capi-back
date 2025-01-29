package com.capitravel.Capitravel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

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

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;
}
