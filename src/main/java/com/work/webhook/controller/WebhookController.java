package com.work.webhook.controller;

import com.work.webhook.dto.ApplicationDTO;
import com.work.webhook.dto.SucessDTO;
import com.work.webhook.events.MessageReceivedEvent;
import com.work.webhook.model.MessageDO;
import com.work.webhook.service.WebhookService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/webhook")
@Slf4j
public class WebhookController implements ApplicationEventPublisherAware {

    @Autowired
    private WebhookService service;

    private ApplicationEventPublisher applicationEventPublisher;

    @PostMapping(path = "/application", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public SucessDTO<Long> registerNewApplication(@RequestParam(required = true) String url,
            @RequestParam(required = true) String name) {

        SucessDTO<Long> response = new SucessDTO();
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setContent(service.register(name, url));
        return response;
    }

    @GetMapping(path = "/applications", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public SucessDTO<List<ApplicationDTO>> listApplications() {
        SucessDTO<List<ApplicationDTO>> response = new SucessDTO();
        response.setMessage(HttpStatus.OK.getReasonPhrase());
        response.setContent(service.list());
        return response;
    }

    @DeleteMapping(path = "/application/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteApplication(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @PostMapping("/application/{id}/message")
    public void sendMessageToApplication(@PathVariable("id") Long id,
            @RequestBody String body,
            @RequestHeader("Content-Type") String contentType) {
        MessageDO message = service.send(body, contentType, id);
        applicationEventPublisher.publishEvent(new MessageReceivedEvent(this, message));
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
