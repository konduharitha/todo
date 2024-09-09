package com.project.todo.service.impl;

import com.project.todo.entity.UserBank;
import com.project.todo.repository.UserBankRepository;
import com.project.todo.service.BankingReadService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BankingReadServiceImpl implements BankingReadService {

    private final UserBankRepository userBankRepository;

    public BankingReadServiceImpl(UserBankRepository userBankRepository) {
        this.userBankRepository = userBankRepository;
    }

    @Override
    public UserBank getUserBankDetails(Long id) {

        Optional<UserBank> userBankOptional = userBankRepository.findById(id);

       return userBankOptional.orElse(new UserBank());

    }
}
