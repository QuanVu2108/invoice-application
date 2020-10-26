package application.dto;

import java.util.List;

public class InvoiceInputDTO {
	private String invoiceCode;
	private CustomerDTO customer;
	private List<ProductDataDTO> productList;
	private String note;
	private String paymentTerm;
	private AmountDTO amount;
	private Long issueDate;
	private Long dueDate;

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

	public List<ProductDataDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDataDTO> productList) {
		this.productList = productList;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(String paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public AmountDTO getAmount() {
		return amount;
	}

	public void setAmount(AmountDTO amount) {
		this.amount = amount;
	}

	public Long getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Long issueDate) {
		this.issueDate = issueDate;
	}

	public Long getDueDate() {
		return dueDate;
	}

	public void setDueDate(Long dueDate) {
		this.dueDate = dueDate;
	}

}
