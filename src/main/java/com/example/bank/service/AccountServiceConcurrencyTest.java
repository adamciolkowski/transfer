package com.example.bank.service;

import com.example.bank.domain.Account;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.inmemory.InMemoryAccountRepository;
import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.IntResult2;

import java.math.BigDecimal;

@SuppressWarnings("RedundantStringConstructorCall")
@JCStressTest
@Description("Concurrent transfer of the same amount from a to b and from b to a")
@Outcome(id = "100, 100", expect = Expect.ACCEPTABLE, desc = "Both accounts have same balance as before")
@Outcome(id = "90, 100", expect = Expect.FORBIDDEN, desc = "Transfer atomicity failure")
@Outcome(id = "100, 90", expect = Expect.FORBIDDEN, desc = "Transfer atomicity failure")
@Outcome(id = "100, 110", expect = Expect.FORBIDDEN, desc = "Transfer atomicity failure")
@Outcome(id = "110, 100", expect = Expect.FORBIDDEN, desc = "Transfer atomicity failure")
@Outcome(id = "110, 110", expect = Expect.FORBIDDEN, desc = "Transfer atomicity failure")
@Outcome(id = "90, 90", expect = Expect.FORBIDDEN, desc = "Transfer atomicity failure")
@State
public class AccountServiceConcurrencyTest {

    AccountRepository repository = newRepository();

    AccountService accountService = new AccountService(repository);

    private AccountRepository newRepository() {
        AccountRepository r = new InMemoryAccountRepository();
        r.save(new Account("a", BigDecimal.valueOf(100)));
        r.save(new Account("b", BigDecimal.valueOf(100)));
        return r;
    }

    @Actor
    public void actor1() {
        accountService.transfer(new String("a"), new String("b"), BigDecimal.TEN);
    }

    @Actor
    public void actor2() {
        accountService.transfer(new String("b"), new String("a"), BigDecimal.TEN);
    }

    @Arbiter
    public void afterAllActors(IntResult2 result) {
        result.r1 = repository.findById("a").getBalance().intValue();
        result.r2 = repository.findById("b").getBalance().intValue();
    }
}
