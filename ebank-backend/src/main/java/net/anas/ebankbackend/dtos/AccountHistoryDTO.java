package net.anas.ebankbackend.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter @Setter
public class AccountHistoryDTO {
    private String accountId;
    private double balance;
    //pour la pagination
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<AccountOperationDTO> accountOperationDTOS;
}