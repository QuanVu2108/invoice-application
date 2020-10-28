package application.service;

import java.util.List;

import javax.mail.internet.MimeMessage;

import application.dto.InvoiceDTO;
import application.dto.InvoiceInputDTO;

public interface InvoiceService {

	String create(InvoiceInputDTO invoiceInput);

	String update(InvoiceInputDTO invoiceInput);

	List<InvoiceDTO> getAll(String invoiceCode);

}
