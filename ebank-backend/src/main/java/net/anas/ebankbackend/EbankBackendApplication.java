package net.anas.ebankbackend;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import net.anas.ebankbackend.entities.*;
import net.anas.ebankbackend.enums.AccountStatus;
import net.anas.ebankbackend.enums.OperationType;
import net.anas.ebankbackend.repositories.AccountOperationRepo;
import net.anas.ebankbackend.repositories.BanckAccountRepo;
import net.anas.ebankbackend.repositories.CustomerRepo;
import org.hibernate.Hibernate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackendApplication.class, args);
    }


    //@Bean
    CommandLineRunner start(CustomerRepo customerRepo,//injecter les repo jpa
                            BanckAccountRepo banckAccountRepo,
                            AccountOperationRepo accountOperationRepo) {
        return args -> {
            ///initialze customer table
            Stream.of("walid","anas","karim").forEach(name -> {
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepo.save(customer);
            });
            ///create foreach customer tow bankAcount
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

                ///initiaze foreach account five operation
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
            });


        };
    }

    @Bean
    CommandLineRunner commandLineRunner(BanckAccountRepo banckAccountRepo){
        return args -> {
            /// show account infos with owner and operation infos
            //1er methode=>create exception folder============================================
            //BankAccount bankAccount1 = banckAccountRepo.findById("35992493-49cf-40f6-9c2f-c11bb56eb3ba").orElseThrow(()->new EntityNotFoundException("Bank Account not Found!"));
            //System.out.println(bankAccount1.getId());

            //2eme methode=>create exception folder===========================================
            //BankAccount bankAccount1 = banckAccountRepo.findById("35992493-49cf-40f6-9c2f-c11bb56eb3ba").orElse(null);
            //      if(bankAccount1!=null){
            //          System.out.println(bankAccount1.getId());
            //      }else throw new EntityNotFoundException();

            //3 eme methode====================================================================
            Optional<BankAccount> bankAccountOptional = banckAccountRepo.findById("35992493-49cf-40f6-9c2f-c11bb56eb3ba");
            if (bankAccountOptional.isPresent()) {
                BankAccount bankAccount1 = bankAccountOptional.get();
                System.out.println("==============debut_bankAccount_info=========");
                System.out.println(bankAccount1.getId());
                System.out.println(bankAccount1.getBalance());
                System.out.println(bankAccount1.getStatus());
                System.out.println(bankAccount1.getCreatedAt());
                System.out.println(bankAccount1.getCustomer().getName());
                //afficher le nom de la class de ce compte
                System.out.println(bankAccount1.getClass().getSimpleName());
                if(bankAccount1 instanceof SavingAcount) {
                    // fais une sous casting vers SavingAcount
                    System.out.println(((SavingAcount)bankAccount1).getInterestRate());
                }else if(bankAccount1 instanceof CurrentAcount) {
                    System.out.println(((CurrentAcount)bankAccount1).getOverDraft());
                }
                // it give only the attribut that exist in class not parent class
                System.out.println(bankAccount1.toString());
                System.out.println("==============bankAccount_operations==========");
                bankAccount1.getAccountOperations().forEach(operation -> {
                    System.out.println(operation.getOperationDate() + ": " + operation.getAmount() + ",\t " + operation.getType());
                });
                System.out.println("==============fin_bankAccount_info=========");
            } else {
                System.out.println("BankAccount not found");
            }


            /// say hi buddy
            System.out.println("Hello Mr. Engineer!");
        };
    }
}
