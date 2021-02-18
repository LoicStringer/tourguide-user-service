package com.tourguideuserservice.controller;

import java.util.Map;
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

import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.service.UserVisitedLocationService;

@RestController
public class UserVisitedLocationController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserVisitedLocationService userVisitedLocationService;
	
	@PostMapping("/users/{userId}/locations")
	public ResponseEntity<VisitedLocationBean> addUserVisitedLocation (@PathVariable UUID userId, @RequestBody VisitedLocationBean visitedLocation) throws UserNotFoundException{
		log.info("Recording the actual user "+userId+" location to his locations history.");
		return ResponseEntity.ok(userVisitedLocationService.addUserVisitedLocation(visitedLocation, userId));
	}
	
	@GetMapping("/users/locations/latest")
	public ResponseEntity< Map<UUID,LocationBean>>getEachUserLatestLocationList (){
		log.info("Obtaining the each user latest recorded location list.");
		return ResponseEntity.ok(userVisitedLocationService.getEachUserLatestLocationList());
	}
}
