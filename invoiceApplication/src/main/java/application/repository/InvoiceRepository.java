package application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import application.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>  {

	Optional<Invoice> findByInvoiceCode(String invoiceCode);

	@Query(value = "SELECT * FROM invoice WHERE invoice_code LIKE %:invoiceCode% "
			+ "AND customer_name LIKE %:customerName% "
			+ "AND customer_email LIKE %:customerEmail% ", nativeQuery = true)
	List<Invoice> filterParam(@Param("invoiceCode") String invoiceCode, @Param("customerName") String customerName,@Param("customerEmail")  String customerEmail);

}
