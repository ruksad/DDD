package com.learn.axon.aggregates;

import com.learn.axon.commands.CreateAccountCommand;
import com.learn.axon.commands.CreditMoneyCommand;
import com.learn.axon.commands.DebitMoneyCommand;
import com.learn.axon.events.*;
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
        AggregateLifecycle.apply(
                new AccountCreatedEvent(cAC.id, cAC.accountBalance, cAC.currency));
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

    @CommandHandler
    protected void on(CreditMoneyCommand cMC) {
        AggregateLifecycle.apply(
                new MoneyCreditedEvent(cMC.id, cMC.creditAmount, cMC.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyCreditedEvent mCE) {
        if (this.accountBalance < 0 && (this.accountBalance + mCE.creditAmount) > 0) {
            AggregateLifecycle.apply(new AccountActivatedEvent(this.id, "ACTIVATED"));
        }
        this.accountBalance = this.accountBalance + mCE.creditAmount;
    }

    @CommandHandler
    protected void on(DebitMoneyCommand mDE) {

        AggregateLifecycle.apply(
                new MoneyDebitedEvent(mDE.id, mDE.debitAmount, mDE.currency));
    }

    @EventSourcingHandler
    protected void on(MoneyDebitedEvent mDE) {
        if (this.accountBalance >= 0 && (this.accountBalance - mDE.debitAmount) < 0) {
            AggregateLifecycle.apply(new AccountHeldEvent(mDE.id, "HOLD"));
        }
        this.accountBalance -= mDE.debitAmount;
    }
}
