package net.anas.ebankbackend.services;

import jdk.dynalink.Operation;
import net.anas.ebankbackend.dtos.CustomerDTO;
import net.anas.ebankbackend.entities.BankAccount;
import net.anas.ebankbackend.entities.CurrentAcount;
import net.anas.ebankbackend.entities.Customer;
import net.anas.ebankbackend.entities.SavingAcount;
import net.anas.ebankbackend.exceptions.BalanceNotSufficientException;
import net.anas.ebankbackend.exceptions.BankAccountNotFoundException;
import net.anas.ebankbackend.exceptions.CustomerAlreadyExistException;
import net.anas.ebankbackend.exceptions.CustomerNotFoundException;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface BankAccountService {
    CustomerDTO createCustomer(CustomerDTO customerDTO) throws CustomerAlreadyExistException;

    CurrentAcount createCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;

    SavingAcount createSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;

    List<CustomerDTO> getCustomers();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerAlreadyExistException;

    void deleteCustomer(Long customerId);

    BankAccount getBankAccount(String accountId) throws AccountNotFoundException, BankAccountNotFoundException;

    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void virement(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

    List<BankAccount> getAllBankAccount();

}
