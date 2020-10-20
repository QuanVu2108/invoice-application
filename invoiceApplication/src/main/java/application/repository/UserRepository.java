package application.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import application.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUsername(String username);

  User findByUsername(String username);

  @Transactional
  void deleteByUsername(String username);

}
