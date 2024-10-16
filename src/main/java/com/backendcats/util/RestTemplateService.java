package com.backendcats.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RestTemplateService {

    @Autowired
    private Environment env;


    public HttpEntity<String> CreateHeaderList(String token, String data) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> headerList = new HttpEntity<>(data, headers);
        return headerList;
    }

}