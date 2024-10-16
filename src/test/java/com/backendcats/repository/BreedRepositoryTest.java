package com.backendcats.repository;

import com.backendcats.dto.BreedDto;
import com.backendcats.repository.BreedRepository;
import com.backendcats.util.RestTemplateService;
import com.backendcats.util.exception.ErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BreedRepositoryTest {

    @InjectMocks
    private BreedRepository breedRepository;

    @Mock
    private Environment env;

    @Mock
    private RestTemplateService restTemplateService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private HttpEntity<String> headerList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void TestGetAllBreeds_Success() {
        String token = "someToken";
        String url = "http://api.example.com/breeds";

        when(env.getProperty("application.api-cat.token")).thenReturn(token);
        when(env.getProperty("application.api-cat.url")).thenReturn(url);
        when(restTemplateService.CreateHeaderList(token, "")).thenReturn(headerList);

        ArrayList<BreedDto> expectedBreeds = new ArrayList<>();
        when(restTemplate.exchange(
                eq(url + "/breeds"),
                eq(HttpMethod.GET),
                eq(headerList),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(expectedBreeds));

        ArrayList<BreedDto> actualBreeds = breedRepository.GetAllBreeds();

        assertNotNull(actualBreeds);
        assertEquals(expectedBreeds, actualBreeds);
        verify(restTemplate).exchange(eq(url + "/breeds"), eq(HttpMethod.GET), eq(headerList), any(ParameterizedTypeReference.class));
    }

    @Test
    void TestGetAllBreeds_Failure() {
        String token = "someToken";
        String url = "http://api.example.com/breeds";

        when(env.getProperty("constans.request-error-api-cat")).thenReturn("Request error from API");
        when(env.getProperty("application.api-cat.token")).thenReturn(token);
        when(env.getProperty("application.api-cat.url")).thenReturn(url);
        when(restTemplateService.CreateHeaderList(token, "")).thenReturn(headerList);
        when(restTemplate.exchange(
                eq(url + "/breeds"),
                eq(HttpMethod.GET),
                eq(headerList),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.badRequest().build());

        ErrorException exception = assertThrows(ErrorException.class, () -> breedRepository.GetAllBreeds());
        assertEquals("Request error from API", exception.getMessage()); // Ajusta el mensaje según tu implementación.
    }

    @Test
    void TestGetBreedById_Success() {
        String breedId = "123";
        String token = "someToken";
        String url = "http://api.example.com/breeds/";
        when(env.getProperty("application.api-cat.token")).thenReturn(token);
        when(env.getProperty("application.api-cat.url")).thenReturn(url);
        when(restTemplateService.CreateHeaderList(token, "")).thenReturn(headerList);

        BreedDto expectedBreed = new BreedDto();
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                eq(BreedDto.class)))
                .thenReturn(ResponseEntity.ok(expectedBreed));

        BreedDto actualBreed = breedRepository.GetBreedById(breedId);

        assertNotNull(actualBreed);
        assertEquals(expectedBreed, actualBreed);
    }

    @Test
    void TestGetBreedById_Failure() {
        String breedId = "123";
        String token = "someToken";
        String url = "http://api.example.com/breeds/";
        when(env.getProperty("constans.request-error-api-cat")).thenReturn("Request error from API");
        when(env.getProperty("application.api-cat.token")).thenReturn(token);
        when(env.getProperty("application.api-cat.url")).thenReturn(url);
        when(restTemplateService.CreateHeaderList(token, "")).thenReturn(headerList);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                eq(BreedDto.class)))
                .thenReturn(ResponseEntity.badRequest().build());

        ErrorException exception = assertThrows(ErrorException.class, () -> breedRepository.GetBreedById(breedId));
        assertEquals("Request error from API", exception.getMessage()); // Ajusta el mensaje según tu implementación.
    }

    @Test
    void TestGetBreedByFilter_Success() {
        String filter = "bulldog";
        String token = "someToken";
        String url = "http://api.example.com/breeds/search?q=";
        when(env.getProperty("application.api-cat.token")).thenReturn(token);
        when(env.getProperty("application.api-cat.url")).thenReturn(url);
        when(restTemplateService.CreateHeaderList(token, "")).thenReturn(headerList);

        ArrayList<BreedDto> expectedBreeds = new ArrayList<>();
        when(restTemplate.exchange(
                anyString(), any(HttpMethod.class), any(HttpEntity.class), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(expectedBreeds));

        ArrayList<BreedDto> actualBreeds = breedRepository.GetBreedByFilter(filter);

        assertNotNull(actualBreeds);
        assertEquals(expectedBreeds, actualBreeds);
    }

    @Test
    void TestGetBreedByFilter_Failure() {
        String filter = "bulldog";
        String token = "someToken";
        String url = "http://api.example.com/breeds/search?q=";

        when(env.getProperty("constans.request-error-api-cat")).thenReturn("Request error from API");
        when(env.getProperty("application.api-cat.token")).thenReturn(token);
        when(env.getProperty("application.api-cat.url")).thenReturn(url);
        when(restTemplateService.CreateHeaderList(token, "")).thenReturn(headerList);
        when(restTemplate.exchange(
                anyString(),
                any(HttpMethod.class),
                any(HttpEntity.class),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.badRequest().build());

        ErrorException exception = assertThrows(ErrorException.class, () -> breedRepository.GetBreedByFilter(filter));
        assertEquals("Request error from API", exception.getMessage()); // Ajusta el mensaje según tu implementación.
    }
}
