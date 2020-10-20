package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>  {

	List<Invoice> findByInvoiceCode(String invoiceCode);

}
