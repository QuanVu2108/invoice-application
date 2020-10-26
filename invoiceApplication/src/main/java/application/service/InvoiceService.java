package application.service;

import java.util.List;

import org.springframework.data.domain.Page;

import application.dto.InvoiceDTO;
import application.dto.InvoiceInputDTO;
import application.dto.ProductDataDTO;

public interface InvoiceService {

	String create(InvoiceInputDTO invoiceInput);

	String update(InvoiceInputDTO invoiceInput);

	List<InvoiceDTO> getAll(String invoiceCode);

}
