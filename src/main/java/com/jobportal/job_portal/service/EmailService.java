package com.jobportal.job_portal.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	private final JavaMailSender mailSender;

	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void sendShortListEmail(String toEmail,
									String candidateName,
									String jobTitle,
									String company
			) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message,true);
			
			helper.setTo(toEmail);
			helper.setSubject("Congratulations you have been shortlisted");
			helper.setText("""
                    Dear %s,

                    We are pleased to inform you that you have been shortlisted
                    for the position of %s at %s.

                    Our team will be in touch with you shortly regarding
                    the next steps in the selection process.

                    Best regards,
                    Job Portal Team
                    """.formatted(candidateName, jobTitle, company), false);
			
			mailSender.send(message);
		} catch(MessagingException e) {
			System.err.print("Failed to send email to " + toEmail + ": "+ e.getMessage());
			
		}
	}
	
}
