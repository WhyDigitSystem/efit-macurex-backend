package com.efitops.basesetup.service;



import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // 🔹 Simple Text Email
    @Override
    public void sendSimpleEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    // 🔹 HTML Email
    @Override
    public void sendHtmlEmail(String fromEmail, String toEmail, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(mimeMessage);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send HTML email", e);
        }
    }
    
    
    
    //forgot password
    
    // 🔹 Load HTML template
    public String loadHtmlTemplate() {
        try {
            InputStream is = new ClassPathResource("template/forgot_password.html").getInputStream();
            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Template load failed", e);
        }
    }

    // 🔹 Send OTP Email
    public void sendOtpEmail(String to, String name, String otp) {

        String html = loadHtmlTemplate();

        html = html.replace("{{name}}", name);
        html = html.replace("{{otp}}", otp);

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("support@whydigit.in");
            helper.setTo(to);
            helper.setSubject("Password Reset OTP");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Email sending failed", e);
        }
    }
}
