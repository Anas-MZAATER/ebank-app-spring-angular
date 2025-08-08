package net.anas.ebankbackend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.anas.ebankbackend.dtos.*;
import net.anas.ebankbackend.entities.*;
import net.anas.ebankbackend.enums.OperationType;
import net.anas.ebankbackend.exceptions.BalanceNotSufficientException;
import net.anas.ebankbackend.exceptions.BankAccountNotFoundException;
import net.anas.ebankbackend.exceptions.CustomerAlreadyExistException;
import net.anas.ebankbackend.exceptions.CustomerNotFoundException;
import net.anas.ebankbackend.mappers.BankAccountMapperImpl;
import net.anas.ebankbackend.repositories.AccountOperationRepo;
import net.anas.ebankbackend.repositories.BanckAccountRepo;
import net.anas.ebankbackend.repositories.CustomerRepo;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
/// Injection des dépendance via constructeur version annotation lombok
@AllArgsConstructor
/// use la journalisation "log4j" via l'API "slf4j" version annotation lombok
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {
    ///@Autowired // Injection des dépendance via autowired
    private CustomerRepo customerRepo;
    private BanckAccountRepo banckAccountRepo;
    private AccountOperationRepo accountOperationRepo;
    private BankAccountMapperImpl dtoMapper;

    /// use la journalisation "log4j" via l'API "slf4j"
    /// l'objet avec laquel on peut logger dans notre application
    //Logger log = LoggerFactory.getLogger(this.getClass().getName());

    /// Injection des dépendance via constructeur
    //public BankAccountServiceImpl(CustomerRepo customerRepo, BanckAccountRepo banckAccountRepo, AccountOperationRepo accountOperationRepo) {
    //    this.customerRepo = customerRepo;
    //    this.banckAccountRepo = banckAccountRepo;
    //    this.accountOperationRepo = accountOperationRepo;
    //}


    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) throws CustomerAlreadyExistException {
        Customer customer = dtoMapper.toCustomer(customerDTO);
        Customer oldCustomers = customerRepo.findByEmail(customer.getEmail());
        if (oldCustomers != null) {
            // Handle the case where a customer already exists (e.g., update or throw an error)
            log.error("CustomerAlreadyExistException occurred: {}", customer.getEmail(), customer);
            throw new CustomerAlreadyExistException("Customer with email :" + customer.getEmail() + " already exists.");
        }

        /* //2 eme solution
        List<Customer> customers = customerRepo.findByEmail(customer.getEmail());
        if (!customers.isEmpty()) {
            // Handle the case where a customer already exists (e.g., update or throw an error)
            log.error("CustomerAlreadyExistException occurred: {}", customer.getEmail(), customer);
            throw new CustomerAlreadyExistException("Customer with email :" + customer.getEmail() + " already exists.");
        }*/

        /* //3 eme solution
        Optional<Customer> customers = customerRepo.findByEmail(customer.getEmail());
        if (customers.isPresent()) {
            // Handle the case where a customer already exists (e.g., update or throw an error)
            log.error("CustomerAlreadyExistException occurred: {}", customer.getEmail(), customer);
            throw new CustomerAlreadyExistException("Customer with email :" + customer.getEmail() + " already exists.");
        }*/

        Customer savedCustomer = customerRepo.save(customer);
        log.info("createCustomer with id:" + savedCustomer.getId() + " by X");
        return dtoMapper.toCustomerDTO(savedCustomer);

    }

    @Override
    public CurrentAccountDTO createCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        /// verifier l'existance du client
        //customerRepo.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer with id: "+customerId+" not found"));
        Customer customer = customerRepo.findById(customerId).orElse(null);
        if (customer == null) {
            log.error("CustomerNotFoundException occurred: {}", customerId, customerId);
            throw new CustomerNotFoundException("Customer with id: "+customerId+" not found");
        }

        /// créer le compte
        CurrentAcount currentAcount = new CurrentAcount();

        currentAcount.setId(UUID.randomUUID().toString());
        currentAcount.setCreatedAt(new Date());
        currentAcount.setBalance(initialBalance);
        currentAcount.setOverDraft(overDraft);
        currentAcount.setCustomer(customer);

        CurrentAcount savedCurrentAccount = banckAccountRepo.save(currentAcount);
        log.info("currentBankAccount id: "+savedCurrentAccount.getId()+" by: "+savedCurrentAccount.getCustomer().getId());
        return dtoMapper.toCurrentAccountDTO(savedCurrentAccount);
    }

    @Override
    public SavingAccountDTO createSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        /// verifier l'existance du client
//        Customer customer =customerRepo.findById(customerId).orElseThrow(()-> new CustomerNotFoundException("Customer with id: "+customerId+" not found"));
         Customer customer = customerRepo.findById(customerId).orElse(null);
         if (customer == null) {
             log.error("CustomerNotFoundException occurred: {}", customerId, customerId);
            throw new CustomerNotFoundException("Customer with id: "+customerId+" not found");
         }

        /// créer le compte
        SavingAcount savingAcount = new SavingAcount();

        savingAcount.setId(UUID.randomUUID().toString());
        savingAcount.setCreatedAt(new Date());
        savingAcount.setBalance(initialBalance);
        savingAcount.setInterestRate(interestRate);
        savingAcount.setCustomer(customer);

        SavingAcount savedSavingAccount = banckAccountRepo.save(savingAcount);
        log.info("savingBankAccount id: "+savedSavingAccount.getId()+" by :"+savedSavingAccount.getCustomer().getId());
        return dtoMapper.toSavingAccountDTO(savedSavingAccount);
    }

    @Override
    public List<CustomerDTO> getCustomers() {
        List<Customer> customers = customerRepo.findAll();
        List<CustomerDTO> dtoCustomers = customers.stream()
                .map(customer -> dtoMapper.toCustomerDTO(customer))
                .collect(Collectors.toList());

        /* // Using la programmation interactif(classique)
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            CustomerDTO customerDTO = dtoMapper.toCustomerDTO(customer);
            customerDTOs.add(customerDTO);
        }*/

        return dtoCustomers;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepo.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer with id: "+customerId+"not found"));
        return dtoMapper.toCustomerDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) throws CustomerAlreadyExistException {
        Customer customer = dtoMapper.toCustomer(customerDTO);
        Customer oldCustomers = customerRepo.findByEmail(customer.getEmail());
        if (oldCustomers != null) {
            // Handle the case where a customer already exists (e.g., update or throw an error)
            log.error("CustomerAlreadyExistException occurred: {}", customer.getEmail(), customer);
            throw new CustomerAlreadyExistException("Customer with email :" + customer.getEmail() + " already exists.");
        }
        Customer savedCustomer = customerRepo.save(customer);
        log.info("updateCustomer with id:" + savedCustomer.getId() + " by X");
        return dtoMapper.toCustomerDTO(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId){
        try {
            customerRepo.deleteById(customerId);
            log.info("deleteCustomer with id:" + customerId + " by X");
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Cannot delete customer. Bank accounts are still associated.");
        }
    }


    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount = banckAccountRepo.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Account with id: "+accountId+" not found"));

        // log.info("bankAccount founded by X");
        if (bankAccount instanceof SavingAcount){
            SavingAcount savingAcount = (SavingAcount) bankAccount;
            return dtoMapper.toSavingAccountDTO(savingAcount);
        }else {
            CurrentAcount currentAcount = (CurrentAcount) bankAccount;
            return dtoMapper.toCurrentAccountDTO(currentAcount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = banckAccountRepo.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Account with id: "+accountId+" not found"));

        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance non sufficient");

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        banckAccountRepo.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException {
        BankAccount bankAccount = banckAccountRepo.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("Account with id: "+accountId+" not found"));

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setBankAccount(bankAccount);
        accountOperationRepo.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        banckAccountRepo.save(bankAccount);
    }

    @Override
    public void virement(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"transfer to : "+accountIdDestination);
        credit(accountIdDestination,amount,"transfer from : "+accountIdSource);
    }

    //pas interesant de returner tout les compted
    @Override
    public List<BankAccountDTO> getBankAccounts() {
        List<BankAccount> bankAccounts =banckAccountRepo.findAll();
        List<BankAccountDTO> bankAccountDTOS = bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAcount) {
                SavingAcount savingAcount = (SavingAcount) bankAccount;
                return dtoMapper.toSavingAccountDTO(savingAcount);
            } else {
                CurrentAcount currentAcount = (CurrentAcount) bankAccount;
                return dtoMapper.toCurrentAccountDTO(currentAcount);
            }
        }).toList();
        return bankAccountDTOS;
    }


    @Override
    public List<AccountOperationDTO> getAccountHistory(String accountId) {
        List<AccountOperation> accountOperations = accountOperationRepo.findByBankAccountId(accountId);
        return accountOperations.stream().map(accountOperation -> dtoMapper
                .toAccountOperationDTO(accountOperation)).toList();
    }

    @Override
    public AccountHistoryDTO getAccountHistoryByPage(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=banckAccountRepo.findById(accountId).orElse(null);
        if(bankAccount==null){
            throw new BankAccountNotFoundException("Account with id: "+accountId+" not found");
        }
        Page<AccountOperation> accountOperations = accountOperationRepo.findByBankAccountId(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO = new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> dtoMapper.toAccountOperationDTO(op)).toList();
        accountHistoryDTO.setAccountOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String kw) {
        List<Customer> customers = customerRepo.searchCustomers(kw);
        List<CustomerDTO> customersDTOS = customers.stream().map(Customer -> dtoMapper.toCustomerDTO(Customer)).toList();
        return customersDTOS;
    }

}
