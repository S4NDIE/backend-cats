package com.backendcats.controller;

import com.backendcats.dto.BreedDto;
import com.backendcats.service.BreedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/breed")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
		allowedHeaders = "*", exposedHeaders = "*")
public class BreedController {
	@Autowired
	private final BreedService breedService;


	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<BreedDto>> GetBreeds() {
		return ResponseEntity.status(HttpStatus.OK).body(breedService.GetAllBreeds());
	}


	@GetMapping(path = "{breedid}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BreedDto> GetBreedsById(@PathVariable String breedid) {
		return ResponseEntity.status(HttpStatus.OK).body(breedService.GetBreedById(breedid));
	}

	@GetMapping(path = "/search/{search}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<BreedDto>> GetBreedsByFilter(@PathVariable(required = false) String search) {
		return ResponseEntity.status(HttpStatus.OK).body(breedService.GetBreedsByFilter(search));
	}

}
