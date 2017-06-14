package com.example.bank.repository;

import com.example.bank.domain.Account;

public interface AccountRepository {

    Account findById(String id);

    void save(Account account);
}
