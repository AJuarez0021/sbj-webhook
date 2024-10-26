package com.work.webhook.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "feignClient", decode404 = true)
public interface ClientService {

    @RequestMapping(method = RequestMethod.POST, value = "/{request-path}", produces = "application/json")
    ResponseEntity<String> send(
            @RequestHeader("transaction_id") String tx,
            @RequestHeader("Content-Type") String contentType,
            @RequestBody String request, 
            @PathVariable(value = "request-path") String requestPath);
}
