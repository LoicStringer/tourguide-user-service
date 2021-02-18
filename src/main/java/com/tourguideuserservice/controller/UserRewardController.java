package com.tourguideuserservice.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.model.UserReward;
import com.tourguideuserservice.service.UserRewardsService;

@RestController
public class UserRewardController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRewardsService userRewardsService;
	
	@GetMapping("/users/{userId}/rewards/latest")
	public ResponseEntity<UserReward> addUserReward (@PathVariable UUID userId) throws UserNotFoundException {
		log.info("Adding reward to user "+userId);
		return ResponseEntity.ok(userRewardsService.addUserReward(userId));
	}
	
	@GetMapping("/users/{userId}/rewards")
	public ResponseEntity<List<UserReward>> getUserRewardsList(@PathVariable UUID userId) throws UserNotFoundException{
		log.info("Querying the rewards list for user "+userId);
		return ResponseEntity.ok(userRewardsService.getUserRewardsList(userId));
	}
	
}
