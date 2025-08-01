package net.anas.ebankbackend.repositories;

import net.anas.ebankbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountOperationRepo extends JpaRepository<AccountOperation,Long> {
}
