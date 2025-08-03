package net.anas.ebankbackend.entities;


import jakarta.persistence.*;
import net.anas.ebankbackend.enums.OperationType;

import java.util.Date;

import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    // @Enumerated(EnumType.ORDINAL) // par defaut c'est ORDINAL
    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 10)
    private OperationType type;
    @ManyToOne
    private BankAccount bankAccount;

}
