package net.anas.ebankbackend.dtos;


import lombok.*;
import net.anas.ebankbackend.entities.BankAccount;
import net.anas.ebankbackend.enums.OperationType;

import java.util.Date;

@Getter @Setter
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType type;

}
