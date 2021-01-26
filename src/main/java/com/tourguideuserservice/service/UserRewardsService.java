package com.tourguideuserservice.service;

import java.util.List;
import java.util.TreeMap;
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

import feign.FeignException;

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

	public UserRewardsService() {
	}

	public UserRewardsService(int rewardAwardingDistance) {
		this.rewardAwardingDistance = rewardAwardingDistance;
	}

	public int getUserRewardsPointsSum(UUID userId) {
		return userService.getUser(userId).getUserRewardsList().parallelStream()
				.mapToInt(ur -> ur.getRewardCentralPoints()).sum();
	}

	public List<UserReward> getUserRewardsList(UUID userId) {
		return userService.getUser(userId).getUserRewardsList();
	}

	public UserReward addUserReward(UUID userId) {
		UserReward userRewardToAdd = new UserReward();
		User userToReward = userService.getUser(userId);
		List<AttractionBean> visitedAttractionsList = getVisitedAttractionsList(userToReward);
		VisitedLocationBean userLocation = userVisitedLocationService.getUserLastVisitedLocation(userToReward);
		TreeMap<Double, AttractionBean> distancesToAttractionsMap;
		distancesToAttractionsMap = locationProxy.getDistancesToAttractions(userLocation.getLocation());
		distancesToAttractionsMap.entrySet().parallelStream().filter(
				e -> checkRewardAwardingDistance(e.getKey()) && !visitedAttractionsList.contains(e.getValue()))
				.forEach(e -> {
					userRewardToAdd.setVisitedLocationBean(userLocation);
					userRewardToAdd.setAttractionBean(e.getValue());
					userRewardToAdd.setRewardCentralPoints(rewardsProxy.getAttractionRewardPoints(userId, e.getValue().getAttractionId()));
					addUserReward(userToReward, userRewardToAdd);
				});
		return userRewardToAdd;
	}

	private UserReward addUserReward(User user, UserReward userReward) {
		user.getUserRewardsList().add(userReward);
		return userReward;
	}

	private List<AttractionBean> getVisitedAttractionsList(User user) {
		List<AttractionBean> VisitedAttractions = user.getUserRewardsList().parallelStream()
				.map(ur -> ur.getAttractionBean()).collect(Collectors.toList());
		return VisitedAttractions;
	}

	private boolean checkRewardAwardingDistance(double distance) {
		return (distance < rewardAwardingDistance);
	}

	public int getRewardAwardingDistance() {
		return rewardAwardingDistance;
	}

	public void setRewardAwardingDistance(int rewardAwardingDistance) {
		this.rewardAwardingDistance = rewardAwardingDistance;
	}

}
