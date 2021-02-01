package com.tourguideuserservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tourguideuserservice.bean.AttractionBean;
import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.model.UserReward;
import com.tourguideuserservice.proxy.LocationProxy;
import com.tourguideuserservice.proxy.RewardsProxy;


@ExtendWith(MockitoExtension.class)
class UserRewardsServiceTest {
	
	@Mock
	private UserService userService;
	
	@Mock
	private UserVisitedLocationService userVisitedLocationService;
	
	@Mock
	private RewardsProxy rewxardProxy;
	
	@Mock
	private LocationProxy locationProxy;
	
	@InjectMocks
	private UserRewardsService userRewardsService;

	
	private List<UserReward> userRewardsList;
	private User user;
	private TreeMap<Double,AttractionBean> distancesToAttractions;
	private VisitedLocationBean userLocation;
	private AttractionBean attractionAround;
	
	@BeforeEach
	void setUp() throws UserNotFoundException {
		userRewardsService.setRewardingDistance(10);
		user = new User();
		user.setUserId(UUID.randomUUID());
		
		userRewardsList = new ArrayList<UserReward>();
		AttractionBean attraction = new AttractionBean(null, "Flatiron Building", "New York City", "NY", 40.741112,
				-73.989723);
		AttractionBean attractionBis = new AttractionBean(null, "Bronx Zoo", "Bronx", "NY", 40.852905, -73.872971);
		VisitedLocationBean visitedLocation = new VisitedLocationBean(UUID.randomUUID(),new LocationBean(20.50,20.50),new Date());
		VisitedLocationBean visitedLocationBis = new VisitedLocationBean(UUID.randomUUID(),new LocationBean(40.00,90.00),new Date());
		UserReward userReward = new UserReward(visitedLocation,attraction,500);
		UserReward userRewardBis = new UserReward(visitedLocationBis,attractionBis,1000);
		userRewardsList.add(userReward);
		userRewardsList.add(userRewardBis);
		user.setUserRewardsList(userRewardsList);
		
		distancesToAttractions = new TreeMap<Double,AttractionBean>();
		attractionAround = new AttractionBean(UUID.randomUUID(),"Buttes Chaumont","Paris","France",48.8809,2.3828);
		distancesToAttractions.put(8.20,attractionAround);
		
		userLocation = new VisitedLocationBean(user.getUserId(),new LocationBean(48.88,2.38),new Date());
		
		when(userService.getUser(user.getUserId())).thenReturn(user);
	}
	
	@Test
	void getUserRewardsPointsSumTest() throws UserNotFoundException {
		assertEquals(1500,userRewardsService.getUserRewardsPointsSum(user.getUserId()));
	}

	@Test
	void getUserRewardsListTest() throws UserNotFoundException {
		assertEquals(user.getUserRewardsList().get(0),userRewardsService.getUserRewardsList(user.getUserId()).get(0));
	}
	
	@Test
	void addUserRewardTest() throws UserNotFoundException  {
		when(userVisitedLocationService.getUserLastVisitedLocation(user)).thenReturn(userLocation);
		when(locationProxy.getDistancesToAttractions(userLocation.getLocation())).thenReturn(distancesToAttractions);
		when(rewxardProxy.getAttractionRewardPoints(user.getUserId(),attractionAround.getAttractionId())).thenReturn(3000);
		
		UserReward userReward = userRewardsService.addUserReward(user.getUserId());
		
		assertEquals(3000,userReward.getRewardCentralPoints());
		assertEquals("Buttes Chaumont",userReward.getAttractionBean().getAttractionName());
		assertEquals(4500,userRewardsService.getUserRewardsPointsSum(user.getUserId()));
	}
	
	
}
