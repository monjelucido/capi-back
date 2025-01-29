package com.capitravel.Capitravel.service.impl;

import com.capitravel.Capitravel.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ResourceLoader resourceLoader;

    public void sendEmail(String to, String subject, String body, boolean isHtml, String imagePath, String imageId) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, isHtml);

            if (imagePath != null && imageId != null) {
                Resource resource = resourceLoader.getResource(imagePath);
                helper.addInline(imageId, resource);
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Error while sending email", e);
        }
    }

    public void sendConfirmationEmail(String email, String name, String lastName) {
        String confirmationLink = "https://capitravelfe-production.up.railway.app/login";
        String body = "<div style='text-align: center; font-family: Arial, sans-serif; color: #333;'>"
                + "<div>"
                + "    <img src='cid:bannerImage' alt='Capi Travel' style='max-width: 100%; width: 600px; height: auto; display: block; margin: 0 auto;' />"
                + "</div>"
                + "<h2 style='color: #FF6B4A;'>Hi, " + name + " " + lastName + "!</h2>"
                + "<p>We are happy to have you here, you are just one step away to start enjoying new experiences.</p>"
                + "<p>Login with your email: <strong>" + email + "</strong></p>"
                + "<a href='" + confirmationLink + "' style='display: inline-block; width: 600px; padding: 10px 20px; background-color: #76d6c3; color: #333; font-weight: bold; text-decoration: none; border-radius: 5px; text-align: center;'>Login</a>"
                + "</div>";

        sendEmail(email, "Welcome from Capi", body, true, "classpath:static/images/banner_email.jpg", "bannerImage");
    }

    public void sendReservationConfirmationEmail(String email, String name, String lastName, String experienceTitle) {
        String reservationLink = "https://capitravelfe-production.up.railway.app/reservations";
        String body = "<div style='text-align: center; font-family: Arial, sans-serif; color: #333;'>"
                + "<div>"
                + "    <img src='cid:bannerImage' alt='Capi Travel' style='max-width: 100%; width: 600px; height: auto; display: block; margin: 0 auto;' />"
                + "</div>"
                + "<h2 style='color: #FF6B4A;'>Hi, " + name + " " + lastName + "!</h2>"
                + "<p>Thank you for booking with Capi Travel! Your reservation for <strong>" + experienceTitle + "</strong> has been confirmed.</p>"
                + "<p>You're almost ready to begin your adventure. You can view and manage your reservations below:</p>"
                + "<p>For the email address: <strong>" + email + "</strong></p>"
                + "<a href='" + reservationLink + "' style='display: inline-block; width: 600px; padding: 10px 20px; background-color: #76d6c3; color: #333; font-weight: bold; text-decoration: none; border-radius: 5px; text-align: center;'>Go to My Reservations</a>"
                + "</div>";

        sendEmail(email, "Your Reservation Confirmation from Capi Travel", body, true, "classpath:static/images/banner_email.jpg", "bannerImage");
    }
}
