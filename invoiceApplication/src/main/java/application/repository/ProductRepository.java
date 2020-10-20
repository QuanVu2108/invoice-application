package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>  {

}
