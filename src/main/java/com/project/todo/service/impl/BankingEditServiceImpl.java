package com.project.todo.service.impl;

import com.project.todo.entity.Statement;
import com.project.todo.entity.UserBank;
import com.project.todo.model.BankCreditRequest;
import com.project.todo.model.BankDebitRequest;
import com.project.todo.model.ResponseStatus;
import com.project.todo.repository.UserBankRepository;
import com.project.todo.service.BankingEditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class BankingEditServiceImpl implements BankingEditService {


    private final UserBankRepository userBankRepository;


    public BankingEditServiceImpl(UserBankRepository userBankRepository) {
        this.userBankRepository = userBankRepository;
    }


    //Two types of spring transactions - declarative and programmatic tx
    //@Transaction is Declarative transaction. simple way to execute a transaction and automatically handles committing and rollbacks on exceptions.
    //This annotation is can only be applied to a public concrete implementation method.
    //This won't work for private methods.
    @Override
    @Transactional
    public ResponseStatus saveDebitPurchase(BankDebitRequest request) {

        //1. find the user
        //2. check if he has enough balance.
        //3. deduct the money and save the user amount.
        //4. save the new statement in statement table.
        //5. return the response.


        Optional<UserBank> userDetails = userBankRepository.findById(request.getUserId());

        if(userDetails.isPresent()){

            UserBank userBank = userDetails.get();

            if(userBank.getAmount() >= request.getDebitAmount()){

                Double newBalance = userBank.getAmount() - request.getDebitAmount();

                userBank.setAmount(newBalance);


                Statement newStatement = Statement.builder()
                        .debitAmount(request.getDebitAmount())
                        .description(request.getTransactionDetails())
                        .createdAt(LocalDateTime.now())
                        .build();

                userBank.getStatements().add(newStatement);


                UserBank updatedUserBank = userBankRepository.save(userBank);

                log.info(updatedUserBank.toString());

//                if( 1 == 1){
//                    throw  new RuntimeException("Some random failure.");
//                }


                return ResponseStatus
                        .builder()
                        .isSuccess(Boolean.TRUE)
                        .message("Transaction Success.")
                        .detailMessage("Successfully deducted the amount.")
                        .build();


            } else {

                return ResponseStatus
                        .builder()
                        .isSuccess(Boolean.FALSE)
                        .message("Transaction Failed.")
                        .detailMessage("Due to Insufficient funds")
                        .build();
            }

        }else {
            return ResponseStatus
                    .builder()
                    .isSuccess(Boolean.FALSE)
                    .message("Transaction Failed.")
                    .detailMessage("User Not Found")
                    .build();
        }

    }

    @Override
    @Transactional
    public ResponseStatus saveCreditOperation(BankCreditRequest request) {

        //1. find user
        //2. update user amount.
        //3. create a new statement
        //4. save the user credit operation.

        Optional<UserBank> userBankOptional = userBankRepository.findById(request.getUserId());

        if(userBankOptional.isPresent()){

            UserBank userBank = userBankOptional.get();

            Double newAmount = userBank.getAmount() + request.getCreditAmount();

            userBank.setAmount(newAmount);

            Statement statement = Statement
                    .builder()
                    .description(request.getTransactionDetails())
                    .creditAmount(request.getCreditAmount())
                    .createdAt(LocalDateTime.now())
                    .build();

            userBank.getStatements().add(statement);

            UserBank savedUserBankDetails = userBankRepository.save(userBank);

            if (1 == 1) {
                throw new RuntimeException("Some random failure.");
            }

            return ResponseStatus
                    .builder()
                    .isSuccess(Boolean.TRUE)
                    .message("Transaction success.")
                    .detailMessage("Successfully credited")
                    .build();

        } else {
            return ResponseStatus
                    .builder()
                    .isSuccess(Boolean.FALSE)
                    .message("Transaction Failed.")
                    .detailMessage("User Not Found")
                    .build();
        }

    }
}
