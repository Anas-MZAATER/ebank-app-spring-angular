package net.anas.ebankbackend.repositories;

import net.anas.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer,Long> {

    List<Customer> findByEmail(String email); // Returns a list of customers
}