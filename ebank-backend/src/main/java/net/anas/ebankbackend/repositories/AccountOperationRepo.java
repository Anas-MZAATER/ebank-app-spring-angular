package net.anas.ebankbackend.repositories;

import net.anas.ebankbackend.entities.AccountOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepo extends JpaRepository<AccountOperation,Long> {
    /// SANS PAGINATION
    // 1ER METHODE
    List<AccountOperation> findByBankAccountId(String accountId);
    // 2EME METHODE
    //@Query("SELECT ao FROM AccountOperation ao WHERE ao.accountId = :accountId")
    //List<AccountOperation> findByAccountId(@Param("accountId") String accountId);

    /// AVEC PAGINATION
    Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}
