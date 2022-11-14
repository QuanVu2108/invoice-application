package application.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import application.service.mapper.AmountMapper;
import application.service.mapper.CustomerMapper;
import application.service.mapper.InvoiceMapper;
import application.service.mapper.ProductMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import application.dto.InvoiceDTO;
import application.dto.InvoiceInputDTO;
import application.dto.ProductDTO;
import application.exception.InvoiceException;
import application.domain.Amount;
import application.domain.Customer;
import application.domain.Invoice;
import application.domain.Product;
import application.repository.AmountRepository;
import application.repository.CustomerRepository;
import application.repository.InvoiceRepository;
import application.repository.ProductRepository;
import application.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	private final CustomerMapper customerMapper;

	private final AmountMapper amountMapper;

	private final ProductMapper productMapper;

	private final InvoiceMapper invoiceMapper;

	private final InvoiceRepository invoiceRepository;

	private final CustomerRepository customerRepository;

	private final AmountRepository amountRepository;

	private final ProductRepository productRepository;

	public InvoiceServiceImpl(CustomerMapper customerMapper, AmountMapper amountMapper, ProductMapper productMapper, InvoiceMapper invoiceMapper, InvoiceRepository invoiceRepository, CustomerRepository customerRepository, AmountRepository amountRepository, ProductRepository productRepository) {
		this.customerMapper = customerMapper;
		this.amountMapper = amountMapper;
		this.productMapper = productMapper;
		this.invoiceMapper = invoiceMapper;
		this.invoiceRepository = invoiceRepository;
		this.customerRepository = customerRepository;
		this.amountRepository = amountRepository;
		this.productRepository = productRepository;
	}

	@Override
	public InvoiceDTO create(InvoiceInputDTO invoiceInput) {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
        Long date = zdt.toInstant().toEpochMilli();
        
        Customer customer = customerMapper.toSource(invoiceInput.getCustomer());
        customer.setActive(true);
        customer = customerRepository.save(customer);
        
        Amount amount = amountMapper.toSource(invoiceInput.getAmount());
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

		List<ProductDTO> listProduct = invoiceInput.getProductList();
		if (listProduct.size() != 0) {
			for (ProductDTO productData : listProduct) {
				Product product = productMapper.toSource(productData);
				product.setInvoice(invoice);
				product.setActive(true);
				productRepository.save(product);
			}
		}
		InvoiceDTO invoiceDTO = invoiceMapper.toTarget(invoice);
		return invoiceDTO;
	}

	@Override
	public InvoiceDTO update(InvoiceInputDTO invoiceInput) {
		Invoice invoice = invoiceRepository.findById(invoiceInput.getId()).get();
		//customer
		Customer customerOld = invoice.getCustomer();
        Customer customerInput = customerMapper.toSource(invoiceInput.getCustomer());
        if(!customerOld.equals(customerInput)) {
        	customerOld.setActive(false);
        	customerRepository.save(customerOld);
        	customerInput.setActive(true);
        	customerRepository.save(customerInput);
            invoice.setCustomer(customerInput);
        }
      //amount
        Amount amountOld = invoice.getAmount();
        Amount amountInput = amountMapper.toSource(invoiceInput.getAmount());
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
		List<Product> productInputList = productMapper.toSource(invoiceInput.getProductList());
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
		
		InvoiceDTO invoiceDTO = invoiceMapper.toTarget(invoice);
		
		return invoiceDTO;
	}

	@Override
	public List<InvoiceDTO> getAll(String invoiceCode) {
		List<Invoice> invoices = invoiceRepository.findListByInvoiceCode(invoiceCode);
		List<InvoiceDTO> invoiceDTOList = new ArrayList<InvoiceDTO>(); 
		for(Invoice invoice: invoices) {
			List<Product> productList = productRepository.findByInvoiceId(invoice.getId());
			invoice.setProductList(productList);
			InvoiceDTO invoiceDTO = invoiceMapper.toTarget(invoice);
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
		InvoiceDTO invoiceDTO = invoiceMapper.toTarget(invoice);
		return invoiceDTO;
	}

}
