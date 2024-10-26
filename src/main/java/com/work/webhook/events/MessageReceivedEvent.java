package com.work.webhook.events;

import com.work.webhook.model.MessageDO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class MessageReceivedEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    @Getter
    private final MessageDO message;

    public MessageReceivedEvent(Object source, MessageDO message) {
        super(source);
        this.message = message;
    }

}
