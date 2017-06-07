package com.example.bank;

import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceTest {

    AccountRepository accountRepository = mock(AccountRepository.class);

    AccountService accountService = new AccountService(accountRepository);

    @Test
    public void shouldTransferAmountFromOneAccountToAnother() {
        when(accountRepository.findById("a123"))
                .thenReturn(new Account("a123", new BigDecimal("1000.00")));
        when(accountRepository.findById("a456"))
                .thenReturn(new Account("a456", new BigDecimal("200.00")));

        accountService.transfer("a123", "a456", new BigDecimal("100.00"));

        verify(accountRepository).save(new Account("a123", new BigDecimal("900.00")));
        verify(accountRepository).save(new Account("a456", new BigDecimal("300.00")));
    }

    @Test
    public void shouldThrowWhenPayerHasInsufficientFunds() {
        when(accountRepository.findById("a123"))
                .thenReturn(new Account("a123", new BigDecimal("1000.00")));
        when(accountRepository.findById("a456"))
                .thenReturn(new Account("a456", new BigDecimal("200.00")));

        assertThatThrownBy(() -> accountService.transfer("a123", "a456", new BigDecimal("1001.00")))
                .isInstanceOf(InsufficientFundsException.class);
    }
}