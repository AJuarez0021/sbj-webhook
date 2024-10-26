package com.work.webhook.service;

import lombok.extern.slf4j.Slf4j;
import com.work.webhook.events.MessageReceivedEvent;
import com.work.webhook.model.ApplicationDO;
import com.work.webhook.model.MessageDO;
import com.work.webhook.repository.ApplicationRepository;
import com.work.webhook.repository.MessageRepository;
import java.time.LocalDate;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.work.webhook.client.ClientService;
import java.util.UUID;

@Service
@Slf4j
public class MessageProcessorImpl implements MessageProcessor {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ApplicationRepository destinationRepository;

    @Autowired
    private ClientService client;

    /**
     * Async EventListener for MessageReceivedEvent
     *
     * @param messageReceivedEvent
     */
    @Async
    @EventListener
    @Override
    public void messageReceivedListener(MessageReceivedEvent messageReceivedEvent) {
        MessageDO message = messageReceivedEvent.getMessage();

        log.debug("Listening Event for Message {}", message.getId());

        processMessagesForDestination(message.getApplication());
    }

    @Scheduled(cron = "${expression.message}")
    @Override
    public void scheduledMessagesProcessor() {
        log.debug("Executing scheduled message processor at {}", LocalDate.now());

        destinationRepository.findAll().forEach(destination -> processMessagesForDestination(destination));
    }

    private void processMessagesForDestination(ApplicationDO destination) {
        try {
            log.debug("Processing messages for Application {}", destination.getUrl());

            destinationRepository.setDestinationOnline(destination.getId());

            List<MessageDO> messages = messageRepository.findAllByApplicationOrderByIdAsc(destination);
            for (MessageDO message : messages) {
                if (message.isMessageTimeout()) {
                    deleteMessage(message);
                } else {
                    sendMessage(message);
                }
            }
        } catch (MessageProcessorException ex) {
            log.info("processMessagesForDestination caught an exception: {}", ex.getMessage());
        }
    }

    private void sendMessage(MessageDO message) throws MessageProcessorException {
        try {

            Map<String, Object> headers = new HashMap<>();
            headers.put(HttpHeaders.CONTENT_TYPE, message.getContentType());
            headers.put("transaction_id", "12345");
            Thread.sleep(100);
            log.debug("Sending Message {} to Application {}", message.getId(), message.getDestinationUrl());

            log.debug("Request: {}", message.getMessageBody());

            ResponseEntity<String> entity = client.send(UUID.randomUUID().toString(),
                    String.valueOf(message.getContentType()),
                    message.getMessageBody(),
                    message.getDestinationUrl());
            if (entity.getStatusCode().equals(HttpStatus.OK)) {
                onSendMessageSuccess(message);
            } else {
                throw new MessageProcessorException("Non 200 HTTP response code!");
            }
        } catch (Exception ex) {
            log.info("sendMessage caught an exception: {}", ex.getMessage());

            onSendMessageError(message);
            throw new MessageProcessorException(ex.getMessage());
        }
    }

    private void onSendMessageSuccess(MessageDO message) {
        log.debug("Sent Message {}", message.getId());

        deleteMessage(message);
    }

    private void onSendMessageError(MessageDO message) {
        log.debug("Unsent Message {}", message.getId());

        destinationRepository.setDestinationOffline(message.getDestinationId());
    }

    private void deleteMessage(MessageDO message) {
        messageRepository.delete(message);

        log.debug("Deleted Message {}", message.getId());
    }

}
