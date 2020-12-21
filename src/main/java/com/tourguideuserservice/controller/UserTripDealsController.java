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

import com.tourguideuserservice.bean.ProviderBean;
import com.tourguideuserservice.form.UserTripPreferencesForm;
import com.tourguideuserservice.service.UserTripDealsService;

@RestController
public class UserTripDealsController {

	@Autowired
	private UserTripDealsService userTripDealsService;

	@PostMapping("/users/{userId}/trip-preferences")
	public ResponseEntity<UserTripPreferencesForm> addUserTripPreferences (@PathVariable UUID userId, @RequestBody UserTripPreferencesForm userTripPreferencesForm){
		return ResponseEntity.ok(userTripDealsService.addUserTripPreferences(userId, userTripPreferencesForm));
	}
	
	@GetMapping("/users/{userId}/trip-deals")
	public ResponseEntity<List<ProviderBean>> getUserTripDeals (@PathVariable UUID userId){
		return ResponseEntity.ok(userTripDealsService.getTripDeals(userId));
	}
	
}