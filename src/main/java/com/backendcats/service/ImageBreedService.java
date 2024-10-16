package com.backendcats.service;

import com.backendcats.dto.BreedDto;
import com.backendcats.dto.ImageBreedDto;
import com.backendcats.repository.BreedRepository;
import com.backendcats.repository.ImageBreedRepository;
import com.backendcats.util.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ImageBreedService {

	@Autowired
	private Environment env;

	@Autowired
	private ImageBreedRepository imageBreedRepository;


	public ArrayList<ImageBreedDto> GetImagesBreedById(String imagesbybreedid) {
		Logger logger = Logger.getLogger(ImageBreedService.class.getName());
		try {
			return imageBreedRepository.GetImagesBreedsById(imagesbybreedid);
		}
		catch (ErrorException e) {
			logger.log(Level.INFO, e.getMessage());
			throw new ErrorException(e.getMessage());
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ErrorException(env.getProperty("constans.request-error-get"));
		}
	}

}
