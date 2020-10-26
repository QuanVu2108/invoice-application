package application.dto;

public class AmountDTO {

	private Long subTotal;
	private Long tax;
	private Long discount;
	private Long total;
	private Long paidToDate;
	private Long balance;

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

	public Long getPaidToDate() {
		return paidToDate;
	}

	public void setPaidToDate(Long paidToDate) {
		this.paidToDate = paidToDate;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

}
