package com.learn.axon.controller;

import com.learn.axon.model.AccountCreateDTO;
import com.learn.axon.model.MoneyCreditDTO;
import com.learn.axon.model.MoneyDebitDTO;
import com.learn.axon.service.AccountCommandService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/bank-accounts")
@Api(value = "Account commands", description = "Accounts related endpoints", tags = "Account DDD")
public class AccountCommandController {

    private final AccountCommandService accountCommandService;

    AccountCommandController(AccountCommandService accountCommandService) {
        this.accountCommandService = accountCommandService;
    }

    @PostMapping(value = "/create")
    public CompletableFuture<String> createAccount(@RequestBody AccountCreateDTO accountCreateDTO) {
        CompletableFuture<String> account = accountCommandService.createAccount(accountCreateDTO);
        return account;
    }

    @PutMapping(value = "/credits/{accountNumber}")
    public CompletableFuture<String> creditMoneyToAccount(@PathVariable("accountNumber") String accountNumber, @RequestBody MoneyCreditDTO moneyCreditDTO) {
        CompletableFuture<String> stringCompletableFuture = accountCommandService.creditMoneyToAccount(moneyCreditDTO, accountNumber);
        return stringCompletableFuture;
    }

    @PutMapping(value = "/debits/{accountNumber}")
    public CompletableFuture<String> debitMoneyFromAccount(@PathVariable("accountNumber") String accountNumber, @RequestBody MoneyDebitDTO moneyDebitDTO) {
        return accountCommandService.debitMoneyFromAccount(moneyDebitDTO, accountNumber);
    }
}
