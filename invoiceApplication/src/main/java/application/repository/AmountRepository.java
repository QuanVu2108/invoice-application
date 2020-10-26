package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.Amount;

public interface AmountRepository extends JpaRepository<Amount, Long>{

}
