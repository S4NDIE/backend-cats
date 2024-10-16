package com.backendcats.service;

import com.backendcats.dto.BreedDto;
import com.backendcats.repository.BreedRepository;
import com.backendcats.util.exception.ErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BreedServiceTest {

    @InjectMocks
    private BreedService breedService;

    @Mock
    private BreedRepository breedRepository;

    @Mock
    private Environment env;

    private List<BreedDto> breedDtos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        breedDtos = new ArrayList<>();
    }

    @Test
    void TestGetAllBreeds() {
        when(breedRepository.GetAllBreeds()).thenReturn(new ArrayList<>(breedDtos));

        ArrayList<BreedDto> result = breedService.GetAllBreeds();

        assertNotNull(result);
        assertEquals(breedDtos.size(), result.size());
        verify(breedRepository).GetAllBreeds();
    }

    @Test
    void TestGetAllBreeds_WhenErrorExceptionThrown() {
        when(breedRepository.GetAllBreeds()).thenThrow(new ErrorException("Error occurred"));

        Exception exception = assertThrows(ErrorException.class, () -> breedService.GetAllBreeds());

        assertEquals("Error occurred", exception.getMessage());
        verify(breedRepository).GetAllBreeds();
    }

    @Test
    void TestGetBreedById() {
        BreedDto breedDto = new BreedDto();
        when(breedRepository.GetBreedById("123")).thenReturn(breedDto);

        BreedDto result = breedService.GetBreedById("123");

        assertNotNull(result);
        assertEquals(breedDto, result);
        verify(breedRepository).GetBreedById("123");
    }

    @Test
    void TestGetBreedsByFilter() {
        when(breedRepository.GetBreedByFilter("filter")).thenReturn(new ArrayList<>(breedDtos));

        ArrayList<BreedDto> result = breedService.GetBreedsByFilter("filter");

        assertNotNull(result);
        assertEquals(breedDtos.size(), result.size());
        verify(breedRepository).GetBreedByFilter("filter");
    }
}

