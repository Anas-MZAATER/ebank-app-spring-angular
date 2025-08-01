package net.anas.ebankbackend.entities;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("SA")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter @ToString
public class SavingAcount extends BankAccount{
    private double interestRate;
}
