package com.backendcats.repository;

import com.backendcats.dto.ImageBreedDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageBreedRepositoryTest {

    @InjectMocks
    private ImageBreedRepository imageBreedRepository;

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
    void TestGetImagesBreedsById_Success() {
        String imagesByBreedId = "123";
        String token = "someToken";
        String url = "http://api.example.com/images/search?limit=100&breed_id=";

        when(env.getProperty("application.api-cat.token")).thenReturn(token);
        when(env.getProperty("application.api-cat.url")).thenReturn("http://api.example.com");
        when(restTemplateService.CreateHeaderList(token, "")).thenReturn(headerList);

        ArrayList<ImageBreedDto> expectedImages = new ArrayList<>();
        when(restTemplate.exchange(
                eq(url + imagesByBreedId),
                eq(HttpMethod.GET),
                eq(headerList),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(expectedImages));

        ArrayList<ImageBreedDto> actualImages = imageBreedRepository.GetImagesBreedsById(imagesByBreedId);

        assertNotNull(actualImages);
        assertEquals(expectedImages, actualImages);
        verify(restTemplate).exchange(eq(url + imagesByBreedId), eq(HttpMethod.GET), eq(headerList), any(ParameterizedTypeReference.class));
    }

    @Test
    void TestGetImagesBreedsById_Failure() {
        String imagesByBreedId = "123";
        String token = "someToken";
        String url = "http://api.example.com/images/search?limit=100&breed_id=";

        when(env.getProperty("constans.request-error-api-cat")).thenReturn("Request error from API");
        when(env.getProperty("application.api-cat.token")).thenReturn(token);
        when(env.getProperty("application.api-cat.url")).thenReturn("http://api.example.com");
        when(restTemplateService.CreateHeaderList(token, "")).thenReturn(headerList);
        when(restTemplate.exchange(
                eq(url + imagesByBreedId),
                eq(HttpMethod.GET),
                eq(headerList),
                any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.badRequest().build());

        ErrorException exception = assertThrows(ErrorException.class, () -> imageBreedRepository.GetImagesBreedsById(imagesByBreedId));
        assertEquals("Request error from API", exception.getMessage());
    }
}

