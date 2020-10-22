package application.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import application.dto.InvoiceDTO;
import application.dto.ProductDataDTO;
import application.model.Invoice;
import application.model.Product;
import application.repository.InvoiceRepository;
import application.repository.ProductRepository;
import application.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public String create(String invoiceCode, String customerName, String customerEmail,
			List<ProductDataDTO> productDataDTOList, Long subTotal, Long tax, Long discount, Long total) {
		
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        Long date = zdt.toInstant().toEpochMilli();

		Invoice invoice = new Invoice();
		List<Product> productList = new ArrayList<Product>();
		if (productDataDTOList.size() != 0) {
			for (ProductDataDTO productDataDTO : productDataDTOList) {
				Product product = new Product();
				product = modelMapper.map(productDataDTO, Product.class);
				product.setInvoice(invoice);
				productList.add(product);
			}
		}
		invoice.setInvoiceCode(invoiceCode);
		invoice.setCustomerName(customerName);
		invoice.setCustomerEmail(customerEmail);
		invoice.setSubTotal(subTotal);
		invoice.setTax(tax);
		invoice.setDiscount(discount);
		invoice.setTotal(total);
		invoice.setCreatedDate(date);
		invoiceRepository.save(invoice);

		for (int i = 0; i < productList.size(); i++) {
			Product product = productList.get(i);
			product.setInvoice(invoice);
			productRepository.save(product);
		}

		return "OK";
	}

	@Override
	public String update(String invoiceCode, String customerName, String customerEmail,
			List<ProductDataDTO> productDataList, Long subTotal, Long tax, Long discount, Long total) {
		Invoice invoice = new Invoice();
		Optional<Invoice> invoiceOptional = invoiceRepository.findByInvoiceCode(invoiceCode);
		if (invoiceOptional.isPresent())
			invoice = invoiceOptional.get();

		List<Product> productList = new ArrayList<Product>();
		if (productDataList.size() != 0) {
			for (ProductDataDTO productDataDTO : productDataList) {
				Product product = new Product();
				product = modelMapper.map(productDataDTO, Product.class);
				product.setInvoice(invoice);
				productList.add(product);
			}
		}
		invoice.setInvoiceCode(invoiceCode);
		invoice.setCustomerName(customerName);
		invoice.setCustomerEmail(customerEmail);
		invoice.setSubTotal(subTotal);
		invoice.setTax(tax);
		invoice.setDiscount(discount);
		invoice.setTotal(total);
		invoiceRepository.save(invoice);

		for (int i = 0; i < productList.size(); i++) {
			Product product = productList.get(i);
			product.setInvoice(invoice);
			productRepository.save(product);
		}

		return "OK";
	}

	@Override
	public List<InvoiceDTO> getAll(String invoiceCode, String customerName, String customerEmail) {
		List<Invoice> invoices = invoiceRepository.filterParam(invoiceCode, customerName, customerEmail);
		List<InvoiceDTO> invoiceDTOList = new ArrayList<InvoiceDTO>(); 
		for(Invoice invoice: invoices) {
			InvoiceDTO invoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);
			invoiceDTOList.add(invoiceDTO);
		}
		return invoiceDTOList;
	}

}
