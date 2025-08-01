package net.anas.ebankbackend.entities;

import jakarta.persistence.*;
import lombok.*;
import net.anas.ebankbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

//JPA
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
// discriminatorType par defaut de type string
// length par defaut est le max, ON CHOISIS AU MAX 4 CARACTER
@DiscriminatorColumn(name = "type", length=4, discriminatorType=DiscriminatorType.STRING)
//Lombok
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne //REPRESENTER DANS LES CARDINALITER PAR 1
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount")
    private List<AccountOperation> accountOperations;
}
