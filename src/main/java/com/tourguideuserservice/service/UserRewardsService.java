package com.tourguideuserservice.service;

import java.util.List;
import java.util.TreeMap;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tourguideuserservice.bean.AttractionBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.model.UserReward;
import com.tourguideuserservice.proxy.LocationProxy;
import com.tourguideuserservice.proxy.RewardsProxy;

@Service
public class UserRewardsService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LocationProxy locationProxy;

	@Autowired
	private RewardsProxy rewardsProxy;

	@Autowired
	private UserService userService;

	@Autowired
	private UserVisitedLocationService userVisitedLocationService;

	@Value("${rewardingDistance}")
	private int rewardingDistance;

	public UserRewardsService() {
	}

	public UserRewardsService(int rewardingDistance) {
		this.rewardingDistance = rewardingDistance;
	}

	public int getUserRewardsPointsSum(UUID userId) throws UserNotFoundException {
		log.debug("Sum up recorded reward points for user "+userId);
		return userService.getUser(userId).getUserRewardsList().parallelStream()
				.mapToInt(ur -> ur.getRewardCentralPoints()).sum();
	}

	public List<UserReward> getUserRewardsList(UUID userId) throws UserNotFoundException {
		return userService.getUser(userId).getUserRewardsList();
	}

	public UserReward addUserReward(UUID userId) throws UserNotFoundException {
		log.debug("Generating and recording the potential user reward for user "+userId+System.lineSeparator()
		+" according to the predifined awarding distance between latest location and attractions.");
		
		UserReward userRewardToAdd = new UserReward();
		User userToReward = userService.getUser(userId);
		List<AttractionBean> visitedAttractionsList = getVisitedAttractionsList(userToReward);
		VisitedLocationBean userLocation = userVisitedLocationService.getUserLastVisitedLocation(userToReward);
		TreeMap<Double, AttractionBean> distancesToAttractionsMap;
		distancesToAttractionsMap = locationProxy.getDistancesToAttractions(userLocation.getLocation());
		
		distancesToAttractionsMap.entrySet().stream().filter(
				e -> checkRewardAwardingDistance(e.getKey())==true && !visitedAttractionsList.contains(e.getValue()) ).forEach(e -> {
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
		List<AttractionBean> VisitedAttractions = user.getUserRewardsList().stream()
				.map(ur -> ur.getAttractionBean()).collect(Collectors.toList());
		return VisitedAttractions;
	}

	private boolean checkRewardAwardingDistance(double distance) {
		return (distance < rewardingDistance);
	}

	public int getRewardingDistance() {
		return rewardingDistance;
	}

	public void setRewardingDistance(int rewardingDistance) {
		this.rewardingDistance = rewardingDistance;
	}

}
