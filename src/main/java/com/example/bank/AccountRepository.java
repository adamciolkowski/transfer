package com.example.bank;

public interface AccountRepository {

    Account findById(String id);

    void save(Account account);
}
