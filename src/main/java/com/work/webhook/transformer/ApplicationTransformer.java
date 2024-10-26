package com.work.webhook.transformer;

import com.work.webhook.dto.ApplicationDTO;
import com.work.webhook.model.ApplicationDO;
import org.springframework.stereotype.Component;

@Component
public class ApplicationTransformer implements Transformer<ApplicationDTO, ApplicationDO> {

    @Override
    public ApplicationDTO transform(ApplicationDO app) {
        return ApplicationDTO.builder()
                .id(app.getId())
                .name(app.getName())
                .online(app.getOnline())
                .url(app.getUrl())
                .build();
    }

}
