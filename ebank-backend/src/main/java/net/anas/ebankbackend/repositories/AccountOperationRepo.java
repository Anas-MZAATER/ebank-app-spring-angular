package net.anas.ebankbackend.repositories;

import net.anas.ebankbackend.entities.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOperationRepo extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByBankAccountId(String accountId);
    /// 2EME METHODE
    //@Query("SELECT ao FROM AccountOperation ao WHERE ao.accountId = :accountId")
    //List<AccountOperation> findByAccountId(@Param("accountId") String accountId);
}
