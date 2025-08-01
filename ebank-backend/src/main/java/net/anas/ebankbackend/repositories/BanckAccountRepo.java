package net.anas.ebankbackend.repositories;

import net.anas.ebankbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

//EN GERER L'ENTITE "BankAccount" et SON ID de type "String"
public interface BanckAccountRepo extends JpaRepository<BankAccount,String> {
}
