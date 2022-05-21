package com.learn.axon.events;

public class AccountActivatedEvent extends BaseEvent<String> {

    public final String status;

    public AccountActivatedEvent(String id, String status) {
        super(id);
        this.status = status;
    }
}
