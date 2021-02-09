package com.tourguideuserservice.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tourguideuserservice.exception.DuplicatedUserException;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.service.UserService;

@RestController
public class UserServiceController {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UUID>> getAllUsersIdList(){
		log.info("Retrieving the complete users id list.");
		return ResponseEntity.ok(userService.getAllUsersIdList());
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<User> getUser(@PathVariable UUID userId) throws UserNotFoundException{
		log.info("Retrieving user "+userId);
		return ResponseEntity.ok(userService.getUser(userId));
	}
		
	@PostMapping("/users")
	public ResponseEntity<User> addUser (@RequestBody User user) throws DuplicatedUserException{
		log.info("Adding user "+user.getUserName());
		return ResponseEntity.ok(userService.addUser(user));
	}
	
}
