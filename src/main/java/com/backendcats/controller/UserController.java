package com.backendcats.controller;

import com.backendcats.dto.BreedDto;
import com.backendcats.model.User;
import com.backendcats.service.BreedService;
import com.backendcats.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
		allowedHeaders = "*", exposedHeaders = "*")
public class UserController {

	@Autowired
	private final UserService userService;


	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody User loginUser) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.LoginUser(loginUser));
	}

	@GetMapping("/validate")
	public ResponseEntity<Boolean> ValidateToken() {
		return ResponseEntity.status(HttpStatus.OK).body(true);
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<User>> GetAllUsers() {
		return ResponseEntity.status(HttpStatus.OK).body(userService.GetAllUser());
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> SaveUser(@RequestBody User user) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.SaveUser(user));
	}

	@GetMapping("/delete/{id}")
	public ResponseEntity<Boolean> DeleteUser(@PathVariable String id) {
		return ResponseEntity.status(HttpStatus.OK).body(userService.DeleteUser(id));
	}

}
