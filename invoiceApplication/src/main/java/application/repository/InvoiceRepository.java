package application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>  {

	Optional<Invoice> findByInvoiceCode(String invoiceCode);

}
