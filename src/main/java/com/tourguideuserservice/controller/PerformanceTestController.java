package com.tourguideuserservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.data.DataInitializer;

@RestController
public class PerformanceTestController {

	@Autowired
	private DataInitializer dataInitializer;
	
	@PostMapping("/tests/performance/locations")
	public void generateUsersForTest(@RequestBody int userNumber) {
		DataContainer.clearUsersData();
		dataInitializer.initializeTestsUsers(userNumber);
	}
	
}
