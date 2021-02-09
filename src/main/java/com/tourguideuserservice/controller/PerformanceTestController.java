package com.tourguideuserservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.data.DataInitializer;

@RestController
public class PerformanceTestController {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private DataInitializer dataInitializer;
	
	@PostMapping("/tests/performance/locations")
	public void generateUsersForTest(@RequestBody int userNumber) {
		log.info("Entering the dedicated performance test controller, generating users to locate.");
		DataContainer.clearUsersData();
		dataInitializer.initializeTestsUsers(userNumber);
	}
	
}
