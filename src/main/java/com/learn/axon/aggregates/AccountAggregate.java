package com.learn.axon.aggregates;

import com.learn.axon.commands.CreateAccountCommand;
import com.learn.axon.events.AccountActivatedEvent;
import com.learn.axon.events.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String id;
    private double accountBalance;
    private String currency;
    private String status;

    public AccountAggregate() {
    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand cAC) {
        AggregateLifecycle.apply(new AccountCreatedEvent(cAC.id, cAC.accountBalance, cAC.currency));
    }

    @EventSourcingHandler
    protected void on(AccountCreatedEvent aCE) {
        this.id = aCE.id;
        this.currency = aCE.currency;
        this.accountBalance = aCE.accountBalance;
        this.status = "CREATED";
        AggregateLifecycle.apply(new AccountActivatedEvent(this.id, "ACTIVATED"));
    }

    @EventSourcingHandler
    protected void on(AccountActivatedEvent aAE) {
        this.status = aAE.status;
    }

}
