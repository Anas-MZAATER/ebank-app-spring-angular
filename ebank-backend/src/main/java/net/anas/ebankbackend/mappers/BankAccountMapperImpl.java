package net.anas.ebankbackend.mappers;

import net.anas.ebankbackend.dtos.CustomerDTO;
import net.anas.ebankbackend.entities.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMapperImpl {
    public CustomerDTO toCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        /// first Solution
        // customerDTO.setId(customer.getId());
        // customerDTO.setName(customer.getName());
        // customerDTO.setEmail(customer.getEmail());
        /// secend Solution
        BeanUtils.copyProperties(customer, customerDTO);

        return customerDTO;
    }

    public Customer toCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        return customer;
    }
}
