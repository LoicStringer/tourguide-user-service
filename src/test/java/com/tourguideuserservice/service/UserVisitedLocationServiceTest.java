package com.tourguideuserservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.model.User;

class UserVisitedLocationServiceTest {

	private static UserVisitedLocationService userVisitedLocationService;
	private User user;
	private VisitedLocationBean visitedLocationBean;
	private static LocationBean locationBean;
	
	@BeforeAll
	static void setUp() {
		userVisitedLocationService = new UserVisitedLocationService();
		locationBean = new LocationBean(20.50,80.50);
	}
	
	@BeforeEach
	void setUpForTests() {
		DataContainer.usersData.clear();
		visitedLocationBean = new VisitedLocationBean();
		visitedLocationBean.setLocation(locationBean);
		user = new User();
		user.setUserId(UUID.randomUUID());
	}
	
	@Test
	void addUserVisitedLocationTest() {
		assertEquals(locationBean,userVisitedLocationService.addUserVisitedLocation(visitedLocationBean, user).getLocation());
		assertEquals(visitedLocationBean,user.getVisitedLocationsList().get(0));
	}

	@Test
	void getUserLastVisitedLocationTest() {
		user.getVisitedLocationsList().add(visitedLocationBean);
		assertEquals(visitedLocationBean, userVisitedLocationService.getUserLastVisitedLocation(user));
	}
	
	@Test
	void getEachUserLatestLocationListTest() {
		user.getVisitedLocationsList().add(visitedLocationBean);
		DataContainer.usersData.put(user.getUserId(),user);
		assertEquals(locationBean,userVisitedLocationService.getEachUserLatestLocationList().get(user.getUserId()));
	}
}
