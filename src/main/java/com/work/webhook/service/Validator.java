package com.work.webhook.service;

import org.springframework.http.HttpStatus;

public interface Validator {

    /**
     * Valid not null.
     *
     * @param object the object
     * @param name the name
     */
    void validNotNull(Object object, String name);

    /**
     * Valid length.
     *
     * @param string the string
     * @param name the name
     * @param length the length
     */
    void validLength(String string, String name, int length);

    boolean isValidJSON(final String json);

    void throwException(HttpStatus status, int code, String error);
}
