package net.anas.ebankbackend.entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DebitDTO {
    private String accountId;
    private double amount;
    private String description;
}
