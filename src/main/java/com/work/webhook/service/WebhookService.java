package com.work.webhook.service;

import com.work.webhook.dto.ApplicationDTO;
import com.work.webhook.model.MessageDO;
import java.util.List;

public interface WebhookService {

    long register(String name, String url);

    List<ApplicationDTO> list();

    void delete(long id);

    MessageDO send(String body, String contentType, long id);
}
