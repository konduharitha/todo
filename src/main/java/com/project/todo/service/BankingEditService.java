package com.project.todo.service;

import com.project.todo.model.BankCreditRequest;
import com.project.todo.model.BankDebitRequest;
import com.project.todo.model.ResponseStatus;

public interface BankingEditService {

   ResponseStatus saveDebitPurchase(BankDebitRequest request);

   ResponseStatus saveCreditOperation(BankCreditRequest request);

}
