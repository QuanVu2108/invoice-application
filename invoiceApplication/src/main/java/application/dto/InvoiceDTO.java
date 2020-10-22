package application.dto;

import java.util.List;

import application.model.Product;

public class InvoiceDTO {
	private Long id;
	private String customerName;
	private String customerEmail;
	private List<ProductDataDTO> productList;
	private Long subTotal;
	private Long tax;
	private Long discount;
	private Long total;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public List<ProductDataDTO> getProductList() {
		return productList;
	}

	public void setProductList(List<ProductDataDTO> productList) {
		this.productList = productList;
	}

	public Long getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(Long subTotal) {
		this.subTotal = subTotal;
	}

	public Long getTax() {
		return tax;
	}

	public void setTax(Long tax) {
		this.tax = tax;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

}
