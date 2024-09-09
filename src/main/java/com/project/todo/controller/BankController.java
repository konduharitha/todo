package com.project.todo.controller;

import com.project.todo.entity.UserBank;
import com.project.todo.model.BankCreditRequest;
import com.project.todo.model.BankDebitRequest;
import com.project.todo.model.ResponseStatus;
import com.project.todo.service.BankingEditService;
import com.project.todo.service.BankingReadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final BankingEditService bankingEditService;

    private final BankingReadService bankingReadService;

    public BankController(BankingEditService bankingEditService, BankingReadService bankingReadService) {
        this.bankingEditService = bankingEditService;
        this.bankingReadService = bankingReadService;
    }


    @GetMapping("/user/{id}")
    public UserBank getUserBankDetails(@PathVariable Long id){
        return bankingReadService.getUserBankDetails(id);
    }

    @PostMapping("/debit")
    public ResponseEntity<ResponseStatus> bankDebitTransaction(@RequestBody BankDebitRequest request){

        ResponseStatus response = bankingEditService.saveDebitPurchase(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/credit")
    public ResponseEntity<ResponseStatus> bankCreditTransaction(@RequestBody BankCreditRequest request){

        ResponseStatus response = bankingEditService.saveCreditOperation(request);

        return ResponseEntity.ok(response);
    }
}
