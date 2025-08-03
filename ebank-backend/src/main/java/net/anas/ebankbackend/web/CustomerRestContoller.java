package net.anas.ebankbackend.web;

import lombok.AllArgsConstructor;
import net.anas.ebankbackend.dtos.CustomerDTO;
import net.anas.ebankbackend.services.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class CustomerRestContoller {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return bankAccountService.getCustomers();
    }
}
