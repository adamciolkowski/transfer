package com.example.bank.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class AccountTest {

    @Test
    public void shouldCloneAccount() {
        Account account = new Account("a123", new BigDecimal("10.00"));

        Account clone = account.clone();
        assertThat(clone.getId()).isEqualTo("a123");
        assertThat(clone.getBalance()).isEqualTo(new BigDecimal("10.00"));
        assertThat(clone).isNotSameAs(account);
    }
}