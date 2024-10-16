package com.backendcats.controller;

import com.backendcats.dto.BreedDto;
import com.backendcats.dto.ImageBreedDto;
import com.backendcats.service.ImageBreedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ImageBreedControllerTest {

    @Mock
    private ImageBreedService imageService;

    @InjectMocks
    private ImageBreedController imageBreedController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void TestGetBreedsById() {
        ImageBreedDto mockBreedDto = ImageBreedDto.builder().id("test").build();
        ArrayList<ImageBreedDto>listBreed = new ArrayList<>();
        listBreed.add(mockBreedDto);
        when(imageService.GetImagesBreedById("test")).thenReturn(listBreed);

        ResponseEntity<ArrayList<ImageBreedDto>> response = imageBreedController.GetImageBreedsById("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listBreed, response.getBody());
        verify(imageService, times(1)).GetImagesBreedById("test");
    }



}
