package com.tourguideuserservice.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.tourguideuserservice.model.User;



@Component
public class DataContainer {

	private static DataInitializer dataInitializer ;
	public static Map<UUID, User> usersData;
	
	static {
		usersData = new HashMap<UUID, User>();
		dataInitializer = new DataInitializer();
		dataInitializer.initializeTestsUsers(1000);
	}

	public static void clearUsersData() {
		usersData.clear();
	}

}
