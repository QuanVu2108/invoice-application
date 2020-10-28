package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import application.exception.InvoiceException;
import application.model.Customer;
import application.model.Invoice;
import application.repository.InvoiceRepository;
import application.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/mails")
@Api(tags = "mails")
public class MailController {

	@Autowired
	private MailService mailService;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@GetMapping("/send-mail")
	public String sendMail(//
			@ApiParam("invoiceId") @RequestParam(name = "invoiceId", required = false, defaultValue = "") Long invoiceId) {
		String customerEmail = getCustomerMail(invoiceId);
		try {
			mailService.sendMail(customerEmail);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "Congratulations! Your mail has been send to the user.";
	}

	@GetMapping("/send-mail-attachment")
	public String sendMailAttachment(
			@ApiParam("invoiceId") @RequestParam(name = "invoiceId", required = false, defaultValue = "") Long invoiceId,
			@ApiParam("file") @RequestParam(name = "file", required = false, defaultValue = "") InputStreamSource file) {
		String customerEmail = getCustomerMail(invoiceId);
		try {
			mailService.sendMailAttachment(customerEmail, file);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "Congratulations! Your mail has been send to the user.";
	}

	public String getCustomerMail(Long invoiceId) {
		Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
		if (!invoiceOptional.isPresent())
			throw new InvoiceException("invoice is not exits", HttpStatus.UNPROCESSABLE_ENTITY);
		Invoice invoice = invoiceOptional.get();
		Customer customer = invoice.getCustomer();
		String customerEmail = customer.getCustomerEmail();
		return customerEmail;
	}
}
