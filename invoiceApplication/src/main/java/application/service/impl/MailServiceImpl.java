package application.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import application.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	private JavaMailSender javaMailSender;

    @Autowired
    public MailServiceImpl (JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
	@Override
	public void sendMail(String invoiceCode, String customerEmail) throws MailException {

		/*
		 * This JavaMailSender Interface is used to send Mail in Spring Boot. This
		 * JavaMailSender extends the MailSender Interface which contains send()
		 * function. SimpleMailMessage Object is required because send() function uses
		 * object of SimpleMailMessage as a Parameter
		 */

		SimpleMailMessage mail = new SimpleMailMessage();

		mail.setTo(customerEmail);
		mail.setSubject("Testing Mail API");
		mail.setText("Hurray ! You have done that dude...");

		/*
		 * This send() contains an Object of SimpleMailMessage as an Parameter
		 */
		javaMailSender.send(mail);
	}

	@Override
	public void sendMailAttachment(String invoiceCode, String customerEmail, MultipartFile inputFile) {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);
			helper.setTo(customerEmail);
			helper.setSubject("Invoice " + invoiceCode);
			helper.setText("Please find the attached document below.");
			helper.addAttachment(invoiceCode + ".pdf", inputFile);
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
