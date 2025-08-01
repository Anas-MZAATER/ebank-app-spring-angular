package net.anas.ebankbackend;

import net.anas.ebankbackend.entities.AccountOperation;
import net.anas.ebankbackend.entities.CurrentAcount;
import net.anas.ebankbackend.entities.Customer;
import net.anas.ebankbackend.entities.SavingAcount;
import net.anas.ebankbackend.enums.AccountStatus;
import net.anas.ebankbackend.enums.OperationType;
import net.anas.ebankbackend.repositories.AccountOperationRepo;
import net.anas.ebankbackend.repositories.BanckAccountRepo;
import net.anas.ebankbackend.repositories.CustomerRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackendApplication.class, args);
    }

    @Bean
    //injecter les repo jpa
    CommandLineRunner start(CustomerRepo customerRepo,
                            BanckAccountRepo banckAccountRepo,
                            AccountOperationRepo accountOperationRepo) {
        return args -> {
            Stream.of("walid","anas","karim").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepo.save(customer);
            });
            customerRepo.findAll().forEach(customer -> {
                CurrentAcount currentAcount = new CurrentAcount();
                currentAcount.setId(UUID.randomUUID().toString());
                currentAcount.setBalance((int)(Math.random()*90000));
                currentAcount.setCreatedAt(new Date());
                currentAcount.setStatus(AccountStatus.CREATED);
                currentAcount.setCustomer(customer);
                currentAcount.setOverDraft(9000);
                banckAccountRepo.save(currentAcount);

                SavingAcount savingAcount = new SavingAcount();
                savingAcount.setId(UUID.randomUUID().toString());
                savingAcount.setBalance((int)(Math.random()*90000));
                savingAcount.setCreatedAt(new Date());
                savingAcount.setStatus(AccountStatus.CREATED);
                savingAcount.setCustomer(customer);
                savingAcount.setInterestRate(5.5);
                banckAccountRepo.save(savingAcount);
            });

            banckAccountRepo.findAll().forEach(banckAccount -> {
                for(int i=0; i<=5; i++) {
                    AccountOperation accountOperation = new AccountOperation();
                    accountOperation.setOperationDate(new Date());
                    accountOperation.setAmount(100+(int)(Math.random()*10000));
                    accountOperation.setType(Math.random()>0.5? OperationType.CREDIT : OperationType.DEPOSIT);
                    accountOperation.setBankAccount(banckAccount);
                    accountOperationRepo.save(accountOperation);
                }
            });
            System.out.println("Hello Mr. Engineer!");
        };
    }

}
