package net.anas.ebankbackend.web;

import lombok.AllArgsConstructor;
import net.anas.ebankbackend.dtos.CustomerDTO;
import net.anas.ebankbackend.exceptions.CustomerAlreadyExistException;
import net.anas.ebankbackend.exceptions.CustomerNotFoundException;
import net.anas.ebankbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerRestContoller {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    @PreAuthorize("hasRole('SCOPE_USER')")
    public List<CustomerDTO> getCustomers() {
        return bankAccountService.getCustomers();
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasRole('SCOPE_USER')")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerId);
    }

    @GetMapping("customers/search")
    @PreAuthorize("hasRole('SCOPE_USER')")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "kw",defaultValue = "") String kw) {
        return bankAccountService.searchCustomers(kw);
    }

    @PostMapping("customers")
    @PreAuthorize("hasRole('SCOPE_ADMIN')")
    // les données de customerDTO en va les recupérer à partir de corps de la requete en format JSON
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) throws CustomerAlreadyExistException {
        return bankAccountService.createCustomer(customerDTO);
    }

    @PutMapping("customers/{customerId}")
    @PreAuthorize("hasRole('SCOPE_ADMIN')")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO) throws CustomerAlreadyExistException {
        customerDTO.setId(customerId);
        return bankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("customers/{customerId}")
    @PreAuthorize("hasRole('SCOPE_ADMIN')")
    public void deleteCustomer(@PathVariable Long customerId){
        bankAccountService.deleteCustomer(customerId);
    }



}
