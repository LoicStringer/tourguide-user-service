package com.tourguideuserservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.model.User;

@ExtendWith(MockitoExtension.class)
class UserVisitedLocationServiceTest {

	@InjectMocks
	private UserVisitedLocationService userVisitedLocationService;
	
	@Mock
	private UserService userService;
	
	private User user;
	private VisitedLocationBean visitedLocationBean;
	private static LocationBean locationBean;
	
	@BeforeAll
	static void setUp() {
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
	void addUserVisitedLocationTest() throws UserNotFoundException {
		when(userService.getUser(any(UUID.class))).thenReturn(user);
		
		assertEquals(locationBean,userVisitedLocationService.addUserVisitedLocation(visitedLocationBean, user.getUserId()).getLocation());
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
