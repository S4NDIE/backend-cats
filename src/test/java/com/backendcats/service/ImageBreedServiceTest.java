package com.backendcats.service;

import com.backendcats.dto.ImageBreedDto;
import com.backendcats.repository.ImageBreedRepository;
import com.backendcats.util.exception.ErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ImageBreedServiceTest {

    @InjectMocks
    private ImageBreedService imageBreedService;

    @Mock
    private ImageBreedRepository imageBreedRepository;

    @Mock
    private Environment env;

    @Mock
    private Logger logger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void TestGetImagesBreedById() {
        ArrayList<ImageBreedDto> imageDtos = new ArrayList<>();
        when(imageBreedRepository.GetImagesBreedsById("123")).thenReturn(imageDtos);

        ArrayList<ImageBreedDto> result = imageBreedService.GetImagesBreedById("123");

        assertNotNull(result);
        assertEquals(imageDtos, result);
        verify(imageBreedRepository).GetImagesBreedsById("123");
    }

    @Test
    void TestGetImagesBreedById_WhenErrorExceptionThrown() {
        when(imageBreedRepository.GetImagesBreedsById("123")).thenThrow(new ErrorException("Error occurred"));

        Exception exception = assertThrows(ErrorException.class, () -> imageBreedService.GetImagesBreedById("123"));

        assertEquals("Error occurred", exception.getMessage());
        verify(imageBreedRepository).GetImagesBreedsById("123");
    }

    @Test
    void TestGetImagesBreedById_WhenGenericExceptionThrown() {
        when(imageBreedRepository.GetImagesBreedsById("123")).thenThrow(new RuntimeException("Runtime error"));
        when(env.getProperty("constans.request-error-get")).thenReturn("An error occurred");

        Exception exception = assertThrows(ErrorException.class, () -> imageBreedService.GetImagesBreedById("123"));

        assertEquals("An error occurred", exception.getMessage());
        verify(imageBreedRepository).GetImagesBreedsById("123");
    }
}
