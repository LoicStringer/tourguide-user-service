package com.tourguideuserservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tourguideuserservice.exception.DuplicateUserException;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.service.UserService;

@RestController
public class UserServiceController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/users")
	public ResponseEntity<List<UUID>> getAllUsersIdList(){
		return ResponseEntity.ok(userService.getAllUsersIdList());
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable UUID id){
		return ResponseEntity.ok(userService.getUser(id));
	}
		
	@PostMapping("/users")
	public ResponseEntity<User> addUser (@RequestBody User user) throws DuplicateUserException{
		return ResponseEntity.ok(userService.addUser(user));
	}
	
}
