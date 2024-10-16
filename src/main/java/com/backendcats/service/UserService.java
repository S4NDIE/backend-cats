package com.backendcats.service;

import com.backendcats.dto.BreedDto;
import com.backendcats.model.User;
import com.backendcats.repository.BreedRepository;
import com.backendcats.repository.UserRepository;
import com.backendcats.util.exception.ErrorException;
import com.backendcats.util.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
	private Environment env;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	@Lazy
	private PasswordEncoder passwordEncoder;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	@Lazy
	private JwtUtil jwtUtil;

	public List<User> GetAllUser() {
		Logger logger = Logger.getLogger(UserService.class.getName());
		try {
			return userRepository.findAll();
		}catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ErrorException(env.getProperty("constans.request-error-get"));
		}
	}

	public User SaveUser(User user) {
		Logger logger = Logger.getLogger(UserService.class.getName());
		try {
			if(FindUserById(user.getDocument()).isPresent()) {
				throw new ErrorException(env.getProperty("constans.request-error-duplicate-user"));
			}
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			return userRepository.save(user);
		}
		catch (ErrorException e) {
			throw new ErrorException(e.getMessage());
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ErrorException(env.getProperty("constans.request-error-save"));
		}
	}

	public Optional<User> FindUserById(String document) {
		Logger logger = Logger.getLogger(UserService.class.getName());
		try {
			return userRepository.findById(document);
		}catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ErrorException(env.getProperty("constans.request-error-get"));
		}
	}

	public boolean DeleteUser(String id) {
		Logger logger = Logger.getLogger(UserService.class.getName());
		try {
			userRepository.deleteById(id);
			return true;
		}catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ErrorException(env.getProperty("constans.request-error-get"));
		}
	}

	public User LoginUser(User user) {
		Logger logger = Logger.getLogger(UserService.class.getName());
		try {

			Optional<User> userLogin = this.FindUserById(user.getDocument());
			if(userLogin.isEmpty()) {
				throw new ErrorException(env.getProperty("constans.request-error-login"));
			}

			if(!passwordEncoder.matches(user.getPassword(),userLogin.get().getPassword())) {
				throw new ErrorException(env.getProperty("constans.request-error-login"));
			}
			userLogin.get().setToken(jwtUtil.generateToken(user.getDocument()));
			return userLogin.get();

		}
		catch (AuthenticationException e) {
			throw new ErrorException(env.getProperty("constans.request-error-login"));

		}
		catch (Exception e) {
			logger.log(Level.SEVERE, e.getMessage());
			throw new ErrorException(env.getProperty("constans.request-error-login"));
		}
	}


}
