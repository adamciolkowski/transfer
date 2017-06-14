package com.example.bank;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.util.function.Function.identity;

public class AccountService {

    private final AccountRepository accountRepository;

    private final ConcurrentMap<String, Object> locks = new ConcurrentHashMap<>();

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void transfer(String payerId, String payeeId, BigDecimal amount) {
        Object payerLock = locks.computeIfAbsent(payerId, identity());
        Object payeeLock = locks.computeIfAbsent(payeeId, identity());
        Object first;
        Object second;
        if (payerId.compareTo(payeeId) < 0) {
            first = payerLock;
            second = payeeLock;
        } else {
            first = payeeLock;
            second = payerLock;
        }

        synchronized (first) {
            synchronized (second) {
                doTransfer(payerId, payeeId, amount);
            }
        }
    }

    private void doTransfer(String payerId, String payeeId, BigDecimal amount) {
        Account payer = accountRepository.findById(payerId);
        Account payee = accountRepository.findById(payeeId);
        payer.transferTo(payee, amount);
        accountRepository.save(payer);
        accountRepository.save(payee);
    }
}
