package com.learn.axon.service;

import com.learn.axon.commands.CreateAccountCommand;
import com.learn.axon.commands.CreditMoneyCommand;
import com.learn.axon.commands.DebitMoneyCommand;
import com.learn.axon.model.AccountCreateDTO;
import com.learn.axon.model.MoneyCreditDTO;
import com.learn.axon.model.MoneyDebitDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AccountCommandServiceImpl implements AccountCommandService {

    private final CommandGateway commandGateWay;

    public AccountCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateWay = commandGateway;
    }

    @Override
    public CompletableFuture<String> createAccount(AccountCreateDTO accountCreateDTO) {
        return commandGateWay.send(new CreateAccountCommand(UUID.randomUUID().toString(), accountCreateDTO.getStartingBalance(), accountCreateDTO.getCurrency()));
    }

    @Override
    public CompletableFuture<String> creditMoneyToAccount(MoneyCreditDTO moneyCreditDTO, String to) {
        return commandGateWay.send(new CreditMoneyCommand(to, moneyCreditDTO.getCreditAmount(), moneyCreditDTO.getCurrency()));
    }

    @Override
    public CompletableFuture<String> debitMoneyFromAccount(MoneyDebitDTO moneyDebitDTO, String from) {
        return commandGateWay.send(new DebitMoneyCommand(from, moneyDebitDTO.getDebitAmount(), moneyDebitDTO.getCurrency()));
    }
}
