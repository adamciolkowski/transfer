package com.example.bank;

import java.math.BigDecimal;

public class Account {

    private final String id;
    private BigDecimal balance;

    public Account(String id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public void transferTo(Account payee, BigDecimal amount) {
        withdraw(amount);
        payee.deposit(amount);
    }

    private void withdraw(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

    private void deposit(BigDecimal amount) {
        balance = balance.add(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return id.equals(account.id) && balance.equals(account.balance);
    }

    @Override
    public int hashCode() {
        return 31 * id.hashCode() + balance.hashCode();
    }

    @Override
    public String toString() {
        return "Account{id='" + id + "', balance=" + balance + '}';
    }
}
