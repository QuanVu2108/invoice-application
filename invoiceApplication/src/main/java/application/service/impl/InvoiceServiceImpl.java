package application.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import application.dto.InvoiceDTO;
import application.dto.InvoiceInputDTO;
import application.dto.ProductDataDTO;
import application.exception.InvoiceException;
import application.model.Amount;
import application.model.Customer;
import application.model.Invoice;
import application.model.Product;
import application.repository.AmountRepository;
import application.repository.CustomerRepository;
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
	private CustomerRepository customerRepository;

	@Autowired
	private AmountRepository amountRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public InvoiceDTO create(InvoiceInputDTO invoiceInput) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        Long date = zdt.toInstant().toEpochMilli();
        
        Customer customer = modelMapper.map(invoiceInput.getCustomer(), Customer.class);
        customer.setActive(true);
        customer = customerRepository.save(customer);
        
        Amount amount = modelMapper.map(invoiceInput.getAmount(), Amount.class);
        amount.setActive(true);
        amount = amountRepository.save(amount);
        
		Invoice invoice = new Invoice();
		invoice.setInvoiceCode(invoiceInput.getInvoiceCode());
		invoice.setCustomer(customer);
		invoice.setNote(invoiceInput.getNote());
		invoice.setAmount(amount);
		invoice.setCreatedDate(date);
		invoice.setIssueDate(invoiceInput.getIssueDate());;
		invoice.setDueDate(invoiceInput.getDueDate());
		invoiceRepository.save(invoice);

		List<ProductDataDTO> listProduct = invoiceInput.getProductList();
		if (listProduct.size() != 0) {
			for (ProductDataDTO productData : listProduct) {
				Product product = modelMapper.map(productData, Product.class);
				product.setInvoice(invoice);
				product.setActive(true);
				productRepository.save(product);
			}
		}
		InvoiceDTO invoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);
		return invoiceDTO;
	}

	@Override
	public InvoiceDTO update(InvoiceInputDTO invoiceInput) {
		Invoice invoice = invoiceRepository.findById(invoiceInput.getId()).get();
		//customer
		Customer customerOld = invoice.getCustomer();
        Customer customerInput = modelMapper.map(invoiceInput.getCustomer(), Customer.class);
        if(!customerOld.equals(customerInput)) {
        	customerOld.setActive(false);
        	customerRepository.save(customerOld);
        	customerInput.setActive(true);
        	customerRepository.save(customerInput);
            invoice.setCustomer(customerInput);
        }
      //amount
        Amount amountOld = invoice.getAmount();
        Amount amountInput = modelMapper.map(invoiceInput.getAmount(), Amount.class);
        if(!amountOld.equals(amountInput)) {
        	amountOld.setActive(false);
        	amountRepository.save(amountOld);
        	amountInput.setActive(true);
        	amountRepository.save(amountInput);
            invoice.setAmount(amountInput);
        }
        //product
		List<Product> productOldList = invoice.getProductList();
		for(Product product: productOldList) {
			product.setActive(false);
			productRepository.save(product);
		}
		List<Product> productInputList = new ArrayList<Product>();
		List<ProductDataDTO> listProductDTOinput = invoiceInput.getProductList();
		for(ProductDataDTO productDTO: listProductDTOinput) {
			productInputList.add(modelMapper.map(productDTO, Product.class));
		}
		for(Product product: productInputList) {
			product.setActive(true);
			product.setInvoice(invoice);
			productRepository.save(product);
		}
		invoice.setProductList(productInputList);

		invoice.setInvoiceCode(invoiceInput.getInvoiceCode());
		invoice.setNote(invoiceInput.getNote());
		invoice.setIssueDate(invoiceInput.getIssueDate());;
		invoice.setDueDate(invoiceInput.getDueDate());
		invoiceRepository.save(invoice);
		
		InvoiceDTO invoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);
		
		return invoiceDTO;
	}

	@Override
	public List<InvoiceDTO> getAll(String invoiceCode) {
		List<Invoice> invoices = invoiceRepository.findListByInvoiceCode(invoiceCode);
		List<InvoiceDTO> invoiceDTOList = new ArrayList<InvoiceDTO>(); 
		for(Invoice invoice: invoices) {
			List<Product> productList = productRepository.findByInvoiceId(invoice.getId());
			invoice.setProductList(productList);
			InvoiceDTO invoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);
			invoiceDTOList.add(invoiceDTO);
		}
		return invoiceDTOList;
	}

	@Override
	public InvoiceDTO getInvoiceDetail(Long id) {
		Optional<Invoice> invoiceOptional = invoiceRepository.findById(id);
		if(!invoiceOptional.isPresent()) throw new InvoiceException("invoiceId supplied is not exits", HttpStatus.UNPROCESSABLE_ENTITY);
		Invoice invoice = invoiceOptional.get();
		List<Product> productList = productRepository.findByInvoiceId(invoice.getId());
		invoice.setProductList(productList);
		InvoiceDTO invoiceDTO = modelMapper.map(invoice, InvoiceDTO.class);
		return invoiceDTO;
	}

}
