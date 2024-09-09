package com.project.todo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankCreditRequest {

    private Long userId;

    private Double creditAmount;

    private String transactionDetails;

}
