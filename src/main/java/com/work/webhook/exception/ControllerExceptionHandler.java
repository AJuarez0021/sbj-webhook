package com.work.webhook.exception;

import com.work.webhook.dto.ServicieErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class ControllerExceptionHandler.
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * The exception factory.
     */
    @Autowired
    private ExceptionFactory exceptionFactory;

    /**
     * Handle service exception.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    protected ResponseEntity<Object> handleServiceException(final ServiceException ex) {
        ServicieErrorDTO errorResult = exceptionFactory.service(ex);

        return new ResponseEntity<>(errorResult, ex.getStatus());
    }

}
