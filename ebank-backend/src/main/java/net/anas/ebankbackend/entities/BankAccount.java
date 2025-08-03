package net.anas.ebankbackend.entities;

import jakarta.persistence.*;
import lombok.*;
import net.anas.ebankbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;

///JPA Annotation
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// discriminatorType par defaut de type string
// length par defaut est le max, ON A CHOISIS 4 CARACTER AU MAX
@DiscriminatorColumn(name = "type", length=4, discriminatorType=DiscriminatorType.STRING)
///Lombok Annotation
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date createdAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToOne // Représenter dans les cardinaliter par 1
    private Customer customer;

    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    // LAZY : charger à la demande
    // non dans le main se fais dans la couche service avec @transaction
    private List<AccountOperation> accountOperations;
}
