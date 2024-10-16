package com.backendcats.controller;

import com.backendcats.dto.BreedDto;
import com.backendcats.service.BreedService;
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

public class BreedControllerTest {

    @Mock
    private BreedService breedService;

    @InjectMocks
    private BreedController breedController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void TestGetAll() {
        BreedDto mockBreedDto = BreedDto.builder().id("test").build();
        ArrayList<BreedDto>listBreed = new ArrayList<>();
        listBreed.add(mockBreedDto);
        when(breedService.GetAllBreeds()).thenReturn(listBreed);

        ResponseEntity<ArrayList<BreedDto>> response = breedController.GetBreeds();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listBreed, response.getBody());
        verify(breedService, times(1)).GetAllBreeds();
    }

    @Test
    public void TestGetBreedsById() {
        BreedDto mockBreedDto = BreedDto.builder().id("test").build();
        when(breedService.GetBreedById("test")).thenReturn(mockBreedDto);

        ResponseEntity<BreedDto> response = breedController.GetBreedsById("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockBreedDto, response.getBody());
        verify(breedService, times(1)).GetBreedById("test");
    }

    @Test
    public void TestGetBreedsByFilter() {
        BreedDto mockBreedDto = BreedDto.builder().id("test").build();
        ArrayList<BreedDto>listBreed = new ArrayList<>();
        listBreed.add(mockBreedDto);
        when(breedService.GetBreedsByFilter("test")).thenReturn(listBreed);

        ResponseEntity<ArrayList<BreedDto>> response = breedController.GetBreedsByFilter("test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listBreed, response.getBody());
        verify(breedService, times(1)).GetBreedsByFilter("test");
    }


}
