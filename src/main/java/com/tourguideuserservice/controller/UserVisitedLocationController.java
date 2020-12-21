package com.tourguideuserservice.controller;

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
public class UserVisitedLocationController {

	@Autowired
	private UserVisitedLocationService userVisitedLocationService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("users/{userId}/visited-locations/latest")
	public ResponseEntity<VisitedLocationBean> getLatestVisitedLocation(@PathVariable UUID userId){
		return ResponseEntity.ok(userVisitedLocationService.getUserLastVisitedLocation(userService.getUser(userId)));
	}
	
	@PostMapping("/users/{userId}/visited-locations")
	public ResponseEntity<VisitedLocationBean> addUserVisitedLocation (@PathVariable UUID userId, @RequestBody VisitedLocationBean visitedLocation){
		User user = userService.getUser(userId);
		return ResponseEntity.ok(userVisitedLocationService.addUserVisitedLocation(visitedLocation, user));
	}
	
	@GetMapping("/users/visited-locations/latest")
	public ResponseEntity< Map<UUID,LocationBean>>getEachUserLatestLocationList (){
		return ResponseEntity.ok(userVisitedLocationService.getEachUserLatestLocationList());
	}
}
