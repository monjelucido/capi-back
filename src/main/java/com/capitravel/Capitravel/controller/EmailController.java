package com.capitravel.Capitravel.controller;

import com.capitravel.Capitravel.dto.EmailDTO;
import com.capitravel.Capitravel.exception.ResourceNotFoundException;
import com.capitravel.Capitravel.model.User;
import com.capitravel.Capitravel.service.EmailService;
import com.capitravel.Capitravel.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emails")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public EmailController(EmailService emailService, UserService userService) {
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> resendEmail(@Valid @RequestBody EmailDTO emailDTO) {
        User existingUser = userService.getUser(emailDTO.getEmail());

        if (existingUser != null) {
            emailService.sendConfirmationEmail(emailDTO.getEmail(), emailDTO.getName(), emailDTO.getLastName());
            return ResponseEntity.noContent().build();
        }

        throw new ResourceNotFoundException("User for email: " + emailDTO.getEmail() + " not found");
    }
}
