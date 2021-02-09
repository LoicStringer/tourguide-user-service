package com.tourguideuserservice.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.exception.DuplicatedUserException;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.model.User;

@Service
public class UserService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	public User getUser(UUID userId) throws UserNotFoundException {
		log.debug("Checking if user id "+userId+" is recorded, then retrieving corresponding user");
		if(checkIfUserAlreadyRegistered(userId)==false) {
			throw new UserNotFoundException("This user id is not registered");
		}
		return DataContainer.usersData.get(userId);
	}
	
	public List<UUID> getAllUsersIdList() {
		return DataContainer.usersData.keySet().stream().collect(Collectors.toList());
	}
	
	public User addUser(User user) throws DuplicatedUserException {
		log.debug("Checking if user id "+user.getUserId()+" is not already recorded, then inserting user "+user.getUserName());
		if(checkIfUserAlreadyRegistered(user.getUserId())==true) {
			throw new DuplicatedUserException("This user " + user.getUserName() + " is already registered.");
		}
		DataContainer.usersData.put(user.getUserId(), user);
		return user;
	}
	
	public void clearAllUsersData() {
		log.debug("Clear user data Map.");
		DataContainer.clearUsersData();
	}
	
	private boolean checkIfUserAlreadyRegistered(UUID userId) {
		boolean isRegistered = false;
		if(DataContainer.usersData.containsKey(userId))
			isRegistered = true;
		return isRegistered;
	}
	
}
