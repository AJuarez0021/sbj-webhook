package com.work.webhook.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.webhook.dto.ErrorsDTO;
import com.work.webhook.exception.ServiceException;
import java.util.ArrayList;
import java.util.Collection;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Slf4j
@Component
public class ValidatorImpl implements Validator {

    @Autowired
    private ObjectMapper mapper;

    /**
     * Valid not null.
     *
     * @param object the object
     * @param name the name
     */
    @Override
    public void validNotNull(Object object, String name) {
        List<ErrorsDTO> errors = new ArrayList<>();
        if (object == null || !StringUtils.isNoneBlank(object.toString())) {
            errors.add(new ErrorsDTO(4001, "The field " + name + " is required"));
            throw new ServiceException(HttpStatus.BAD_REQUEST, errors);
        }
        if (object instanceof Collection && CollectionUtils.isEmpty((Collection<?>) object)) {
            errors.add(new ErrorsDTO(4001, "The field " + name + " is required"));
            throw new ServiceException(HttpStatus.BAD_REQUEST, errors);
        }
    }

    /**
     * Valid length.
     *
     * @param string the string
     * @param name the name
     * @param length the length
     */
    @Override
    public void validLength(String string, String name, int length) {
        List<ErrorsDTO> errors = new ArrayList<>();
        if (string.length() != length) {
            errors.add(new ErrorsDTO(4002, "The length of the field " + name + " is incorrect"));
            throw new ServiceException(HttpStatus.BAD_REQUEST, errors);
        }
    }

    @Override
    public boolean isValidJSON(final String json) {
        try {
            mapper.readTree(json);
            return true;
        } catch (JsonProcessingException ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    private List<ErrorsDTO> getError(int code, String error) {
        List<ErrorsDTO> errors = new ArrayList<>();
        errors.add(new ErrorsDTO(code, error));
        return errors;
    }

    @Override
    public void throwException(HttpStatus status, int code, String error) {
        throw new ServiceException(status, getError(code, error));
    }
}
