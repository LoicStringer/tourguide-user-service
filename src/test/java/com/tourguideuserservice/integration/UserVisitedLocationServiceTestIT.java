package com.tourguideuserservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.data.DataInitializer;
import com.tourguideuserservice.model.User;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserVisitedLocationServiceTestIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private DataInitializer dataInitializer;
	
	private User user;
	private VisitedLocationBean visitedLocationBean;
	
	@BeforeEach
	void setUp() {
		user = new User();
		user.setUserId(UUID.randomUUID());
		user.setUserName("Tony");
		DataContainer.usersData.put(user.getUserId(),user);
		visitedLocationBean = new VisitedLocationBean(user.getUserId(),new LocationBean(48.80,2.40), new Date());
	}
	
	@Test
	void addVisitedLocationTest() throws JsonProcessingException, Exception {
		
		mockMvc.perform(post("/users/"+user.getUserId()+"/locations")
				.content(objectMapper.writeValueAsString(visitedLocationBean))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		assertEquals(48.80, user.getVisitedLocationsList().get(0).getLocation().getLatitude());
		
	}

	@Test
	void getUserLastVisitedLocationTest() throws Exception {
		
		user.getVisitedLocationsList().add(visitedLocationBean);
		
		mockMvc.perform(get("/users/"+user.getUserId()+"/locations/latest"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.location.latitude").value(48.80));
	}
	
	@Test
	void getAllUsersLastLocationTest() throws Exception {
		DataContainer.clearUsersData();
		dataInitializer.initializeTestsUsers(9);
		DataContainer.usersData.put(user.getUserId(),user);
		user.getVisitedLocationsList().add(visitedLocationBean);
		String userId = user.getUserId().toString();
		
		mockMvc.perform(get("/users/locations/latest"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()").value(10))
		.andExpect(jsonPath("$."+userId+".latitude").value(48.80));
	}
	
}
