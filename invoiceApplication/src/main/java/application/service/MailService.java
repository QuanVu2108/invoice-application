package application.service;

import org.springframework.web.multipart.MultipartFile;

public interface MailService {
	
	void sendMail(String invoiceCode, String customerEmail);

	void sendMailAttachment(String invoiceCode, String customerEmail, MultipartFile inputFile);

}
