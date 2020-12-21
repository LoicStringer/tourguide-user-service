package com.tourguideuserservice.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tourguideuserservice.bean.AttractionBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.model.UserReward;
import com.tourguideuserservice.proxy.LocationProxy;
import com.tourguideuserservice.proxy.RewardsProxy;

@Service
public class UserRewardsService {

	@Autowired
	private LocationProxy locationProxy;

	@Autowired
	private RewardsProxy rewardsProxy;
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserVisitedLocationService userVisitedLocationService;

	@Value("${rewardAwardingDistance}")
	private int rewardAwardingDistance;

	public int getUserRewardsPointsSum(UUID userId) {
		return userService.getUser(userId).getUserRewardsList().stream().mapToInt(i -> i.getRewardCentralPoints())
				.sum();
	}

	public List<UserReward> getUserRewardsList(UUID userId) {
		return userService.getUser(userId).getUserRewardsList();
	}
	
	public UserReward addUserReward (UUID userId){
		UserReward userRewardToAdd = new UserReward();
		User userToReward = userService.getUser(userId);
		List<AttractionBean> visitedAttractionsList = getVisitedAttractionsList(userToReward);
		VisitedLocationBean userLocation = userVisitedLocationService.getUserLastVisitedLocation(userToReward);
		locationProxy.getDistancesToAttractions(userLocation.getLocation()).entrySet().stream()
			.forEach(e->{
				if(checkRewardAwardingDistance(e.getKey()) && !visitedAttractionsList.contains(e.getValue())) {
					int attractionRewardPoints = rewardsProxy.getAttractionRewardPoints(userId, e.getValue().getAttractionId());
					userRewardToAdd.setVisitedLocationBean(userLocation);
					userRewardToAdd.setAttractionbean(e.getValue());
					userRewardToAdd.setRewardCentralPoints(attractionRewardPoints);
					addUserReward(userToReward,userRewardToAdd);
				}
			});
		return userRewardToAdd;
	}

	private UserReward addUserReward (User user, UserReward userReward) {
		user.getUserRewardsList().add(userReward);
		return userReward;
	}
	
	private List<AttractionBean> getVisitedAttractionsList(User user) {
		List<AttractionBean> VisitedAttractions = user.getUserRewardsList().stream()
				.map(ur -> ur.getAttractionbean())
				.collect(Collectors.toList());
		return VisitedAttractions;
	}

	private boolean checkRewardAwardingDistance(double distance) {
		return (distance < rewardAwardingDistance);
	}

}
