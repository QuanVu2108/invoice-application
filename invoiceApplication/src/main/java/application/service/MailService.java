package application.service;

import org.springframework.core.io.InputStreamSource;

public interface MailService {
	
	void sendMail(String customerEmail);

	void sendMailAttachment(String customerEmail, InputStreamSource file);

}
