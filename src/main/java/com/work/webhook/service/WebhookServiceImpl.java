package com.work.webhook.service;

import com.work.webhook.dto.ApplicationDTO;
import com.work.webhook.model.ApplicationDO;
import com.work.webhook.model.MessageDO;
import com.work.webhook.repository.ApplicationRepository;
import com.work.webhook.repository.MessageRepository;
import com.work.webhook.transformer.ApplicationTransformer;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class WebhookServiceImpl implements WebhookService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private Validator validator;

    @Autowired
    private ApplicationTransformer appTransformer;

    @Override
    public long register(String name, String url) {
        ApplicationDO applicationRequest = ApplicationDO.builder()
                .name(name)
                .url(url)
                .online(true)
                .build();
        ApplicationDO application = applicationRepository.save(applicationRequest);

        log.debug("Received Application {}", application.getUrl());

        return application.getId();
    }

    @Override
    public List<ApplicationDTO> list() {
        log.debug("Listing applications");

        return appTransformer.transform(applicationRepository.findAll());

    }

    @Override
    public void delete(long id) {
        ApplicationDO application = getApplication(id);

        applicationRepository.delete(application);

        log.debug("Deleted Application {}", application.getUrl());
    }

    @Override
    public MessageDO send(String body, String contentType, long id) {
        //final String contentType = "application/json";

        validator.validNotNull(String.valueOf(id), "id");
        validator.validNotNull(body, "body");

        if (!validator.isValidJSON(body)) {
            validator.throwException(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "Json not valid");
        }

        ApplicationDO application = getApplication(id);

        MessageDO message = messageRepository.save(MessageDO.builder()
                .contentType(contentType)
                .application(application)
                .messageBody(body).build());

        log.debug("Received Message {} for Application {}", message.getId(), message.getApplication());

        return message;

    }

    private ApplicationDO getApplication(Long id) {
        ApplicationDO application = applicationRepository.findById(id).get();
        if (application == null) {
            validator.throwException(HttpStatus.INTERNAL_SERVER_ERROR, 5001, "Does not exist application with ID " + id);
        }
        return application;
    }

}
