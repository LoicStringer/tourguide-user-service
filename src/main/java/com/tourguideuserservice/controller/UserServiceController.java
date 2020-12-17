package com.tourguideuserservice.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.service.UserService;
import com.tourguideuserservice.service.UserVisitedLocationService;

@RestController
public class UserServiceController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserVisitedLocationService userVisitedLocationService;
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsersList(){
		return ResponseEntity.ok(userService.getAllUsersList());
	}
	
	@GetMapping("/users/visited-locations/latest")
	public ResponseEntity< Map<UUID,LocationBean>>getEachUserLatestLocationList (){
		return ResponseEntity.ok(userVisitedLocationService.getgetEachUserLatestLocationList());
	}
	

	@GetMapping("/users/{id}")
	public ResponseEntity<User> getUser(@PathVariable UUID id){
		return ResponseEntity.ok(userService.getUser(id));
	}
		
	@GetMapping("users/{userId}/visited-locations/latest")
	public ResponseEntity<VisitedLocationBean> getLatestVisitedLocation(@PathVariable UUID userId){
		return ResponseEntity.ok(userVisitedLocationService.getUserLastVisitedLocation(userService.getUser(userId)));
	}
	
	@PostMapping("/users/{userId}/visited-locations")
	public ResponseEntity<VisitedLocationBean> addUserVisitedLocation (@PathVariable ("userId")UUID userId, @RequestBody VisitedLocationBean visitedLocation){
		User user = userService.getUser(userId);
		return ResponseEntity.ok(userVisitedLocationService.addUserVisitedLocation(visitedLocation, user));
		
	}
}
