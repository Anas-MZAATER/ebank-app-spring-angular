package net.anas.ebankbackend.entities;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("CA")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString
public class CurrentAcount extends BankAccount{
    private double overDraft;
}
