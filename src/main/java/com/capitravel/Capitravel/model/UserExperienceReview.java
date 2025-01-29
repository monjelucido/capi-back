package com.capitravel.Capitravel.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class UserExperienceReview {

    private String name;
    private String lastname;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Email(message = "Invalid email format")
    private String email;

    @ManyToOne
    @JoinColumn(name = "experience_id", nullable = false)
    private Experience experience;

    @NotNull
    private double rating;

    private String reviewMessage;
    private LocalDateTime createdAt = LocalDateTime.now();
}
