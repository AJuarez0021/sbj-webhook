package com.work.webhook.service;

import com.work.webhook.events.MessageReceivedEvent;

public interface MessageProcessor {

    void messageReceivedListener(MessageReceivedEvent messageReceivedEvent);

    void scheduledMessagesProcessor();
}
