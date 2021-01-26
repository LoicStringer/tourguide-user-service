package com.tourguideuserservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import com.tourguideuserservice.model.UserReward;
import com.tourguideuserservice.service.UserRewardsService;

@RestController
public class UserRewardController {

	@Autowired
	private UserRewardsService userRewardsService;
	
	@PostMapping("/users/{userId}/rewards/latest")
	public ResponseEntity<UserReward> addUserReward (@PathVariable UUID userId) {
		return ResponseEntity.ok(userRewardsService.addUserReward(userId));
	}
	
	@GetMapping("/users/{userId}/rewards")
	public ResponseEntity<List<UserReward>> getUserRewardsList(@PathVariable UUID userId){
		return ResponseEntity.ok(userRewardsService.getUserRewardsList(userId));
	}
	
}
