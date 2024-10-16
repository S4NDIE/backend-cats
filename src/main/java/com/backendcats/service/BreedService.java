package com.backendcats.service;

import com.backendcats.dto.BreedDto;
import com.backendcats.repository.BreedRepository;
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
public class BreedService {

	@Autowired
	private Environment env;

	@Autowired
	private BreedRepository breedRepository;

	public ArrayList<BreedDto> GetAllBreeds() {
		Logger logger = Logger.getLogger(BreedService.class.getName());
		try {
			return breedRepository.GetAllBreeds();
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

	public BreedDto GetBreedById(String breedid) {
		Logger logger = Logger.getLogger(BreedService.class.getName());
		try {
			return breedRepository.GetBreedById(breedid);
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

	public ArrayList<BreedDto> GetBreedsByFilter(String filter) {
		Logger logger = Logger.getLogger(BreedService.class.getName());
		try {
			return breedRepository.GetBreedByFilter(filter);
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
