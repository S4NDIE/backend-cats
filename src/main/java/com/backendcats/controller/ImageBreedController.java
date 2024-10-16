package com.backendcats.controller;

import com.backendcats.dto.BreedDto;
import com.backendcats.dto.ImageBreedDto;
import com.backendcats.service.BreedService;
import com.backendcats.service.ImageBreedService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
	@RequestMapping("/image")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
		allowedHeaders = "*", exposedHeaders = "*")
public class ImageBreedController {

	@Autowired
	private final ImageBreedService imageBreedService;



	@GetMapping(path = "/{imagesbybreedid}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<ImageBreedDto>> GetImageBreedsById(@PathVariable String imagesbybreedid ) {
		return ResponseEntity.status(HttpStatus.OK).body(imageBreedService.GetImagesBreedById(imagesbybreedid));
	}


}
