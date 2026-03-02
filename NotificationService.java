package com.ecotrack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    @Value("${admin.email:admin@ecotrack.com}")
    private String adminEmail;

    public void sendPickupNotification(String wasteDetails) {
        if (mailSender != null) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(adminEmail);
                message.setSubject("Waste Pickup Scheduled");
                message.setText("A new waste pickup has been scheduled.\n\nDetails:\n" + wasteDetails);
                mailSender.send(message);
                System.out.println("Email notification sent to " + adminEmail);
            } catch (Exception e) {
                System.err.println("Failed to send email: " + e.getMessage());
            }
        } else {
            // Fallback for when SMTP isn't configured
            System.out.println("--- [MOCK EMAIL] ---");
            System.out.println("To: " + adminEmail);
            System.out.println("Subject: Waste Pickup Scheduled");
            System.out.println("Body: " + wasteDetails);
            System.out.println("--------------------");
        }
    }
}