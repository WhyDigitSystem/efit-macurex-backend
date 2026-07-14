package com.efitops.basesetup.service;

import org.springframework.stereotype.Service;


public interface EmailService {

 void sendSimpleEmail(String toEmail, String subject, String body);

	

	/**
	 * Send an HTML formatted email
	 */
	void sendHtmlEmail(String fromEail, String toEmail, String subject, String htmlContent);

    void sendOtpEmail(String to, String name, String otp);

}
