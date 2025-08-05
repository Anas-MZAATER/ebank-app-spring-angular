package net.anas.ebankbackend.mappers;

import net.anas.ebankbackend.dtos.CurrentAccountDTO;
import net.anas.ebankbackend.dtos.CustomerDTO;
import net.anas.ebankbackend.dtos.SavingAccountDTO;
import net.anas.ebankbackend.entities.CurrentAcount;
import net.anas.ebankbackend.entities.Customer;
import net.anas.ebankbackend.entities.SavingAcount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO toCustomerDTO(Customer customer) {
        if (customer == null) {
            return null; // Or handle the null case appropriately
        }
        CustomerDTO customerDTO = new CustomerDTO();
        /// first Solution
        // customerDTO.setId(customer.getId());
        // customerDTO.setName(customer.getName());
        // customerDTO.setEmail(customer.getEmail());
        /// secend Solution pour les attribut seulement
        BeanUtils.copyProperties(customer, customerDTO);

        return customerDTO;
    }

    public Customer toCustomer(CustomerDTO customerDTO) {
        if (customerDTO == null) {
            return null; // Or handle the null case appropriately
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }

    public SavingAcount toSavingAccount(SavingAccountDTO savingAccountDTO) {
        SavingAcount savingAcount = new SavingAcount();
        BeanUtils.copyProperties(savingAccountDTO, savingAcount);
        /// pour les objet en doit le fais manuel
        savingAcount.setCustomer(toCustomer(savingAccountDTO.getCustomerDTO()));
        return savingAcount;
    }

    public SavingAccountDTO toSavingAccountDTO(SavingAcount savingAcount) {
        SavingAccountDTO savingAccountDTO = new SavingAccountDTO();
        BeanUtils.copyProperties(savingAcount, savingAccountDTO);
        /// pour les objet en doit le fais manuel
        savingAccountDTO.setCustomerDTO(toCustomerDTO(savingAcount.getCustomer()));
        savingAccountDTO.setType(savingAcount.getClass().getSimpleName());
        return savingAccountDTO;
    }

    public CurrentAcount toCurrentAccount(CurrentAccountDTO currentAccountDTO) {
        CurrentAcount currentAcount = new CurrentAcount();
        BeanUtils.copyProperties(currentAccountDTO, currentAcount);
        /// pour les objet en doit le fais manuel
        currentAccountDTO.setCustomerDTO(toCustomerDTO(currentAcount.getCustomer()));
        return currentAcount;
    }

    public CurrentAccountDTO toCurrentAccountDTO(CurrentAcount currentAcount) {
        CurrentAccountDTO currentAccountDTO = new CurrentAccountDTO();
        BeanUtils.copyProperties(currentAcount, currentAccountDTO);
        /// pour les objet en doit le fais manuel
        currentAcount.setCustomer(toCustomer(currentAccountDTO.getCustomerDTO()));
        currentAccountDTO.setType(currentAcount.getClass().getSimpleName());
        return currentAccountDTO;
    }
}
