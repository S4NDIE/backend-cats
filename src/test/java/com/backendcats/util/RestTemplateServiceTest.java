package com.backendcats.util;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.backendcats.util.RestTemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestTemplateServiceTest {

    @InjectMocks
    private RestTemplateService restTemplateService;

    @Mock
    private Environment env;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void TestCreateHeaderList_WithData() {
        String token = "test-token";
        String data = "{\"key\": \"value\"}";

        HttpEntity<String> result = restTemplateService.CreateHeaderList(token, data);

        HttpHeaders headers = result.getHeaders();
        assertEquals(token, headers.getFirst("x-api-key"));
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
        assertEquals(data, result.getBody());
    }

    @Test
    void TestCreateHeaderList_WithoutData() {
        String token = "test-token";
        String data = null;

        HttpEntity<String> result = restTemplateService.CreateHeaderList(token, data);

        HttpHeaders headers = result.getHeaders();
        assertEquals(token, headers.getFirst("x-api-key"));
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());
        assertEquals(data, result.getBody());
    }
}

