package com.tourguideuserservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.exception.DuplicatedUserException;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.model.User;

class UserServiceTest {
	
	private static UserService userService;
	private static User user;
	private static User userBis;

	@BeforeAll
	static void setUp() {
		userService = new UserService();
		user = new User(UUID.randomUUID(),"Tony","0607080910","tony.montana@scarface.com");
		userBis = new User(UUID.randomUUID(),"Carlito","0605040302","carlito.brigante@depalma.com");
	}
	
	@BeforeEach
	void setUpForTests() {
		DataContainer.clearUsersData();
		DataContainer.usersData.put(user.getUserId(), user);
		DataContainer.usersData.put(userBis.getUserId(),userBis);
	}
	
	@Test
	void getUseTest() throws UserNotFoundException {
		assertEquals("Tony",userService.getUser(user.getUserId()).getUserName());
	}

	@Test
	void getAllUsersIdList() {
		List<UUID> usersIdList = userService.getAllUsersIdList();
		assertEquals(2,usersIdList.size());
		assertTrue(usersIdList.contains(userBis.getUserId()));
	}
	
	@Test
	void addUserTest() throws DuplicatedUserException {
		User userTer = new User(UUID.randomUUID(),"Vincent","0608101214","vincent.hanna@heat.com");
		assertEquals("Vincent",userService.addUser(userTer).getUserName());
		assertEquals(userTer,DataContainer.usersData.get(userTer.getUserId()));
	}
	
	@Test
	void clearUsersDataTest() {
		userService.clearAllUsersData();
		assertTrue(DataContainer.usersData.isEmpty());
	}
	
	@Test
	void isExpectedExceptionThrownWhenAddingAlreadyRegisteredUserTest() {
		assertThrows(DuplicatedUserException.class,()->userService.addUser(user));
	}
	
	@Test
	void isExpectedExceptionThrownWhenUserNotFoundTest(){
		User notFoundUser = new User();
		notFoundUser.setUserId(UUID.randomUUID());
		assertThrows(UserNotFoundException.class,()->userService.getUser(notFoundUser.getUserId()));
		
	}
}
