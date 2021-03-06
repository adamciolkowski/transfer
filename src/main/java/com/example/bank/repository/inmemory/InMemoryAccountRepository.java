package com.example.bank.repository.inmemory;

import com.example.bank.domain.Account;
import com.example.bank.repository.AccountRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryAccountRepository implements AccountRepository {

    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();

    @Override
    public Account findById(String id) {
        return accounts.get(id).clone();
    }

    @Override
    public void save(Account account) {
        accounts.put(account.getId(), account);
    }
}
