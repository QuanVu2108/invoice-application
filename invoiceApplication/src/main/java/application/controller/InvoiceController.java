package application.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import application.dto.InvoiceDTO;
import application.dto.InvoiceInputDTO;
import application.exception.InvoiceException;
import application.model.Invoice;
import application.repository.InvoiceRepository;
import application.service.InvoiceService;
import application.utils.GeneratePdfReport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/invoices")
@Api(tags = "invoices")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	public JavaMailSender emailSender;

	@PostMapping("/create")
	public ResponseEntity<InvoiceDTO> create(//
			@ApiParam("invoiceCode") @RequestBody InvoiceInputDTO invoiceInput) {
		Optional<Invoice> invoiceOptional = invoiceRepository.findByInvoiceCode(invoiceInput.getInvoiceCode());
		if (invoiceOptional.isPresent())
			throw new InvoiceException("invoiceCode supplied is exits", HttpStatus.UNPROCESSABLE_ENTITY);
		InvoiceDTO invoiceDTO = invoiceService.create(invoiceInput); 
		return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
	}

	@PostMapping("/update")
	public ResponseEntity<InvoiceDTO> update(//
			@ApiParam("invoiceCode") @RequestBody InvoiceInputDTO invoiceInput) {
		Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceInput.getId());
		if (!invoiceOptional.isPresent())
			throw new InvoiceException("invoiceCode supplied is not exits", HttpStatus.UNPROCESSABLE_ENTITY);
		InvoiceDTO invoiceDTO = invoiceService.update(invoiceInput); 
		return new ResponseEntity<>(invoiceDTO, HttpStatus.OK);
	}

	@GetMapping("/get-all")
	public List<InvoiceDTO> getAll(//
			@ApiParam("invoiceCode") @RequestParam(name = "invoiceCode", required = false, defaultValue = "") String invoiceCode) {
		return invoiceService.getAll(invoiceCode);
	}

	@GetMapping("/get-invoice-detail")
	public InvoiceDTO getInvoiceDetail(//
			@ApiParam("id") @RequestParam(name = "id", required = false) Long id) {
		InvoiceDTO invoiceDTO = invoiceService.getInvoiceDetail(id);
		return invoiceDTO;
	}
	
//	@RequestMapping(value = "/export-pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
//	public ResponseEntity<InputStreamResource> invoiceReport(
//			@ApiParam("invoiceId") @RequestParam(name = "invoiceId", required = true) Long invoiceId)
//			throws IOException {
//
//		Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
//		if (!invoiceOptional.isPresent())
//			throw new InvoiceException("invoice is not exits", HttpStatus.UNPROCESSABLE_ENTITY);
//		Invoice invoice = invoiceOptional.get();
//
//		ByteArrayInputStream bis = GeneratePdfReport.invoiceReport(invoice);
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
//
//		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
//				.body(new InputStreamResource(bis));
//	}

}
