package net.anas.ebankbackend.web;

import lombok.AllArgsConstructor;
import net.anas.ebankbackend.dtos.AccountOperationDTO;
import net.anas.ebankbackend.dtos.BankAccountDTO;
import net.anas.ebankbackend.exceptions.BankAccountNotFoundException;
import net.anas.ebankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;


@RestController
@AllArgsConstructor
public class BankAccountRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> getBankAccounts() {
        return bankAccountService.getBankAccounts();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getAccountHistory(@PathVariable String accountId) {
        return bankAccountService.getAccountHistory(accountId);
    }
}
