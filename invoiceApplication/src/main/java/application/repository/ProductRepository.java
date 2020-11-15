package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>  {

	@Query(value = "SELECT * FROM product WHERE invoice_id = :id AND active = true", nativeQuery = true)
	List<Product> findByInvoiceId(@Param("id") Long id);

}
