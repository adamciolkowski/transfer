package com.example.bank;

import java.math.BigDecimal;

public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transfer(String payerId, String payeeId, BigDecimal amount) {
        Account payer = accountRepository.findById(payerId);
        Account payee = accountRepository.findById(payeeId);
        payer.transferTo(payee, amount);
        accountRepository.save(payer);
        accountRepository.save(payee);
    }
}
