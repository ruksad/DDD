package com.learn.axon.service;

import com.learn.axon.model.AccountCreateDTO;
import com.learn.axon.model.MoneyCreditDTO;
import com.learn.axon.model.MoneyDebitDTO;

import java.util.concurrent.CompletableFuture;

public interface AccountCommandService {
    CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO);

    CompletableFuture<String> creditMoneyToAccount(MoneyCreditDTO moneyCreditDTO, String to);

    CompletableFuture<String> debitMoneyFromAccount(MoneyDebitDTO moneyDebitDTO, String from);
}
