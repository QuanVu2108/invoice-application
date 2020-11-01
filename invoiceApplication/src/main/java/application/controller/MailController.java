package application.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import application.dto.MailInfo;
import application.exception.InvoiceException;
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

	@PostMapping("/send-mail")
	public ResponseEntity<String> sendMail(//
			@ApiParam("invoiceId") @RequestBody MailInfo mailInfo) {
		Long invoiceId = mailInfo.getInvoiceId();
		Invoice invoice = getInvoice(invoiceId);
		String customerEmail = invoice.getCustomer().getCustomerEmail();
		String invoiceCode = invoice.getInvoiceCode();
		try {
			mailService.sendMail(invoiceCode, customerEmail);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@PostMapping("/send-mail-attachment")
	public ResponseEntity<String> sendMailAttachment(
			@ApiParam("invoiceId") @RequestParam(name = "invoiceId", required = true) Long invoiceId,
			@ApiParam("inputFile") @RequestBody MultipartFile inputFile) {
		Invoice invoice = getInvoice(invoiceId);
		String customerEmail = invoice.getCustomer().getCustomerEmail();
		String invoiceCode = invoice.getInvoiceCode();
		try {
			mailService.sendMailAttachment(invoiceCode, customerEmail, inputFile);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	public Invoice getInvoice(Long invoiceId) {
		Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
		if (!invoiceOptional.isPresent())
			throw new InvoiceException("invoice is not exits", HttpStatus.UNPROCESSABLE_ENTITY);
		Invoice invoice = invoiceOptional.get();
		return invoice;
	}
}
