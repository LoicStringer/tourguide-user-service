package com.tourguideuserservice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.proxy.LocationProxy;

@Service
public class UserVisitedLocationService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LocationProxy locationProxy;

	public VisitedLocationBean addUserVisitedLocation(VisitedLocationBean visitedLocation, User user) {
		log.debug("Recording latest " + user.getUserName() + " location to his location history.");
		user.getVisitedLocationsList().add(visitedLocation);
		return visitedLocation;
	}

	public VisitedLocationBean getUserLastVisitedLocation(User user) {
		log.debug("Checking if user location history is empty.");
		VisitedLocationBean visitedLocation = new VisitedLocationBean();
		
		if (user.getVisitedLocationsList().isEmpty())
			visitedLocation = locationProxy.getUserLocation(user.getUserId());

		log.debug("If user location history is empty, then querying tour guide location service."
				+ System.lineSeparator() + "Else, retrieving the latest recorded location from user history.");
		visitedLocation = user.getVisitedLocationsList().get((user.getVisitedLocationsList().size() - 1));

		return visitedLocation;
	}

	public Map<UUID, LocationBean> getEachUserLatestLocationList() {
		log.debug("Building the Map exposing each user latest recorded location" + System.lineSeparator()
				+ "where key=user id and value=latest location.");
		Map<UUID, LocationBean> allUsersLatestLocationMap = new HashMap<UUID, LocationBean>();
		DataContainer.usersData.entrySet().parallelStream().forEach(entry -> {
			LocationBean userLatestLocation = getUserLastVisitedLocation(entry.getValue()).getLocation();
			allUsersLatestLocationMap.put(entry.getKey(), userLatestLocation);
		});
		return allUsersLatestLocationMap;
	}

}
