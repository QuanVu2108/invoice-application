package application.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "amount")
public class Amount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(mappedBy = "amount")
    private Invoice invoice;
	
	@Column(name = "sub_total")
	private Long subTotal;

	@Column(name = "tax")
	private Long tax;

	@Column(name = "discount")
	private Long discount;

	@Column(name = "total")
	private Long total;

	@Column(name = "paid_to_date")
	private Long paidToDate;

	@Column(name = "balance")
	private Long balance;

	@Column(name = "active")
	private boolean active;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
