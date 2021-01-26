package com.tourguideuserservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.data.DataInitializer;
import com.tourguideuserservice.exception.DuplicateUserException;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.service.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceTestIT {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private DataInitializer dataInitializer;
	
	private static User userTest;
	
	@BeforeAll
	static void setUp() {
		UUID id = UUID.fromString("fe48d110-fae8-4dc5-b739-8d02488678d5");
		userTest = new User();
		userTest.setUserId(id);
		userTest.setUserName("Tony");
	}
	
	@BeforeEach
	void setUpForTests() {
		DataContainer.clearUsersData();
	}
	
	@Test
	void addUserTest() throws JsonProcessingException, Exception {
	
		mockMvc.perform(post("/users")
				.content(objectMapper.writeValueAsString(userTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
				
		assertEquals("Tony",DataContainer.usersData.get(userTest.getUserId()).getUserName());
	}

	@Test
	void getAllUsersIdList() throws Exception {
		
		dataInitializer.initializeTestsUsers(4);
		DataContainer.usersData.put(userTest.getUserId(),userTest);
		
		MvcResult result = mockMvc.perform(get("/users"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(5))
				.andReturn();
		String resultContent = result.getResponse().getContentAsString();
		
		assertTrue(resultContent.contains("fe48d110-fae8-4dc5-b739-8d02488678d5"));
	}
	
	@Test
	void isExpectedExceptionThrownWhenAddingDuplicateUser() throws JsonProcessingException, Exception {
		
		DataContainer.usersData.put(userTest.getUserId(),userTest);
		
		mockMvc.perform(post("/users")
				.content(objectMapper.writeValueAsString(userTest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().is3xxRedirection())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof DuplicateUserException));
	}
}
