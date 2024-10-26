package com.work.webhook.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.work.webhook.dto.ErrorsDTO;

import lombok.Getter;
import lombok.ToString;

/**
 * The Class ServiceException.
 */
@Getter
@ToString
public class ServiceException extends RuntimeException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The list errors.
     */
    private final List<ErrorsDTO> listErrors;

    /**
     * The status.
     */
    private final HttpStatus status;

    /**
     * The message.
     */
    private final String message;

    /**
     * Instantiates a new service exception.
     *
     * @param status the status
     * @param message the message
     * @param errorCode the error code
     */
    public ServiceException(HttpStatus status, String message, List<ErrorsDTO> errorCode) {
        super(message);
        this.status = status;
        this.message = message;
        this.listErrors = errorCode;
    }

    /**
     * Instantiates a new service exception.
     *
     * @param status the status
     * @param errorCode the error code
     */
    public ServiceException(HttpStatus status, List<ErrorsDTO> errorCode) {
        super(status.toString());
        this.status = status;
        this.message = status.getReasonPhrase();
        this.listErrors = errorCode;
    }
}
