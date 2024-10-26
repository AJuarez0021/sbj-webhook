package com.work.webhook.exception;

import com.work.webhook.dto.ServicieErrorDTO;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * A factory for creating Exception objects.
 */
@Component
@Slf4j
public class ExceptionFactory {

    /**
     * The Constant PREFIX.
     */
    private static final String PREFIX = "message.error.";

    /**
     * The message source.
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Service.
     *
     * @param ex the ex
     * @return the servicie error DTO
     */
    public ServicieErrorDTO service(ServiceException ex) {
        return ServicieErrorDTO.builder()
                .status(ex.getStatus())
                .errors(ex.getListErrors())
                .message(ex.getMessage())
                .build();
    }

    /**
     * Error message.
     *
     * @param codeNumber the code number
     * @return the string
     */
    public String errorMessage(int codeNumber) {
        final Locale locale = new Locale("es", "MX");
        log.debug("locale={}", locale);
        return messageSource.getMessage(PREFIX + codeNumber, null, locale);
    }
}
