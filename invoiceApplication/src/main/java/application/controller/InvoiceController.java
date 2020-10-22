package application.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import application.dto.InvoiceDTO;
import application.dto.ProductDataDTO;
import application.exception.InvoiceException;
import application.model.Invoice;
import application.repository.InvoiceRepository;
import application.service.InvoiceService;
import application.service.impl.InvoiceServiceImpl;
import application.utils.GeneratePdfReport;

@RestController
@RequestMapping("/invoices")
@Api(tags = "invoices")
public class InvoiceController {

	@Autowired
	private InvoiceService invoiceService;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@PostMapping("/create")
	public String create(//
			@ApiParam("invoiceCode") @RequestParam String invoiceCode,
			@ApiParam("customerName") @RequestParam String customerName,
			@ApiParam("customerEmail") @RequestParam String customerEmail,
			@ApiParam("productList") @RequestBody List<ProductDataDTO> productList,
			@ApiParam("subTotal") @RequestParam Long subTotal, 
			@ApiParam("tax") @RequestParam Long tax,
			@ApiParam("discount") @RequestParam Long discount,
			@ApiParam("total") @RequestParam Long total) {
		Optional<Invoice> invoiceOptional = invoiceRepository.findByInvoiceCode(invoiceCode);
		if(invoiceOptional.isPresent()) throw new InvoiceException("invoiceCode supplied is exits", HttpStatus.UNPROCESSABLE_ENTITY);
		return invoiceService.create(invoiceCode, customerName, customerEmail, productList, subTotal, tax, discount,
				total);
	}

	@PostMapping("/update")
	public String update(//
			@ApiParam("invoiceCode") @RequestParam String invoiceCode,
			@ApiParam("customerName") @RequestParam String customerName,
			@ApiParam("customerEmail") @RequestParam String customerEmail,
			@ApiParam("productList") @RequestBody List<ProductDataDTO> productList,
			@ApiParam("subTotal") @RequestParam Long subTotal, 
			@ApiParam("tax") @RequestParam Long tax,
			@ApiParam("discount") @RequestParam Long discount,
			@ApiParam("total") @RequestParam Long total) {
		return invoiceService.update(invoiceCode, customerName, customerEmail, productList, subTotal, tax, discount,
				total);
	}

	@GetMapping("/get-all")
	public List<InvoiceDTO> getAll(//
			@ApiParam("invoiceCode") @RequestParam(name = "invoiceCode", required = false, defaultValue = "") String invoiceCode,
			@ApiParam("customerName") @RequestParam(name = "customerName", required = false, defaultValue = "") String customerName,
			@ApiParam("customerEmail") @RequestParam(name = "customerEmail", required = false, defaultValue = "") String customerEmail
			) {
		return invoiceService.getAll(invoiceCode, customerName, customerEmail);
	}

	@RequestMapping(value = "/export-pdf", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<InputStreamResource> invoiceReport(
			@ApiParam("invoiceId") @RequestParam(name = "invoiceId", required = true) Long invoiceId
			) throws IOException {

		Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
		if(!invoiceOptional.isPresent())  throw new InvoiceException("invoice is not exits", HttpStatus.UNPROCESSABLE_ENTITY);
		Invoice invoice = invoiceOptional.get();

		ByteArrayInputStream bis = GeneratePdfReport.invoiceReport(invoice);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}
}
