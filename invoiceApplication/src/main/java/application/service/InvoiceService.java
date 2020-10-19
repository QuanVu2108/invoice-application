package application.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import application.dto.ProductDataDTO;
import application.model.Invoice;
import application.model.Product;
import application.repository.InvoiceRepository;
import application.repository.ProductRepository;

@Service
public class InvoiceService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ProductRepository productRepository;

	public String create(String invoiceCode, String customerName, String customerEmail,
			List<ProductDataDTO> productDataDTOList, Long subTotal, Long tax, Long discount, Long total) {
		Invoice invoice = new Invoice();
		List<Product> productList = new ArrayList<Product>();
		if(productDataDTOList.size() != 0) {
			for(ProductDataDTO productDataDTO: productDataDTOList) {
				Product product = new Product();
				product = modelMapper.map(productDataDTO, Product.class);
				product.setInvoice(invoice);
				productRepository.save(product);
				productList.add(product);
			}
		}
		invoice.setInvoiceCode(invoiceCode);
		invoice.setCustomerName(customerName);
		invoice.setCustomerEmail(customerEmail);
		invoice.setProductList(productList);
		invoice.setSubTotal(subTotal);
		invoice.setTax(tax);
		invoice.setDiscount(discount);
		invoice.setTotal(total);
		invoiceRepository.save(invoice);
		return null;
	}

}
