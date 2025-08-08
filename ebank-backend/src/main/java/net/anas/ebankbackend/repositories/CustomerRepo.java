package net.anas.ebankbackend.repositories;

import net.anas.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer,Long> {

    Customer findByEmail(String email); // Returns a list of customers

    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:kw%") //OR c.email LIKE %:kw%"
    List<Customer> searchCustomers(@Param("kw") String kw);
}