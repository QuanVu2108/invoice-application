package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
