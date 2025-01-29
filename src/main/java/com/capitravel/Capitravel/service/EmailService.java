package com.capitravel.Capitravel.service;

import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(String to, String subject, String body, boolean isHtml, String imagePath, String imageId) throws MessagingException;

    void sendConfirmationEmail(String email, String name, String lastName);

    void sendReservationConfirmationEmail(String email, String name, String lastName, String experienceTitle);
}
