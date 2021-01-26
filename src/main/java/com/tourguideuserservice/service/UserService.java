package com.tourguideuserservice.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.exception.DuplicateUserException;
import com.tourguideuserservice.model.User;

@Service
public class UserService {

	public User getUser(UUID userId) {
		return DataContainer.usersData.get(userId);
	}
	
	public List<UUID> getAllUsersIdList() {
		return DataContainer.usersData.keySet().stream().collect(Collectors.toList());
	}
	
	public User addUser(User user) throws DuplicateUserException {
		if(checkIfUserAlreadyRegistered(user.getUserId())==true) {
			throw new DuplicateUserException("This user " + user.getUserName() + " is already registered.");
		}
		DataContainer.usersData.put(user.getUserId(), user);
		return user;
	}
	
	public void clearAllUsersData() {
		DataContainer.clearUsersData();
	}
	
	private boolean checkIfUserAlreadyRegistered(UUID userId) {
		boolean isRegistered = false;
		if(DataContainer.usersData.containsKey(userId))
			isRegistered = true;
		return isRegistered;
	}
	
}
