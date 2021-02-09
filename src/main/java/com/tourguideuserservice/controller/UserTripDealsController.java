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

import com.tourguideuserservice.bean.ProviderBean;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.form.UserTripPreferencesForm;
import com.tourguideuserservice.service.UserTripDealsService;

@RestController
public class UserTripDealsController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserTripDealsService userTripDealsService;

	@PostMapping("/users/{userId}/trip-preferences")
	public ResponseEntity<UserTripPreferencesForm> addUserTripPreferences (@PathVariable UUID userId, @RequestBody UserTripPreferencesForm userTripPreferencesForm) throws UserNotFoundException{
		log.info("Adding user trip preferences for user "+userId);
		userTripDealsService.addUserTripPreferences(userId, userTripPreferencesForm);
		return ResponseEntity.ok(userTripPreferencesForm);
	}
	
	@GetMapping("/users/{userId}/trip-deals")
	public ResponseEntity<List<ProviderBean>> getUserTripDeals (@PathVariable UUID userId) throws UserNotFoundException {
		log.info("Querying trip pricer external API to obtain a discounted trip deals for user "+userId);
		return ResponseEntity.ok(userTripDealsService.getTripDeals(userId));
	}
	
}
