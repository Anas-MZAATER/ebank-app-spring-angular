package net.anas.ebankbackend.dtos;

import lombok.Getter;
import lombok.Setter;
import net.anas.ebankbackend.enums.AccountStatus;

import java.util.Date;


@Getter @Setter
public class CurrentAccountDTO extends BankAccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    // ou bien seulement ce que j'ai besion
    // private CustomerDTO name;
    private double overdraft;
}
