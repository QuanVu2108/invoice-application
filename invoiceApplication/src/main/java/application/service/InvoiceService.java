package application.service;

import java.util.List;

import org.springframework.data.domain.Page;

import application.dto.InvoiceDTO;
import application.dto.ProductDataDTO;

public interface InvoiceService {

	String create(String invoiceCode, String customerName, String customerEmail, List<ProductDataDTO> productList,
			Long subTotal, Long tax, Long discount, Long total);

	String update(String invoiceCode, String customerName, String customerEmail, List<ProductDataDTO> productList,
			Long subTotal, Long tax, Long discount, Long total);

	List<InvoiceDTO> getAll(String invoiceCode, String customerName, String customerEmail);

}
