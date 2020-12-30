package com.tourguideuserservice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.model.User;

@Service
public class UserVisitedLocationService {

	public VisitedLocationBean addUserVisitedLocation(VisitedLocationBean visitedLocation, User user) {
		user.getVisitedLocationsList().add(visitedLocation);
		return visitedLocation;
	}
	
	public VisitedLocationBean getUserLastVisitedLocation(User user) {
		VisitedLocationBean visitedLocation = new VisitedLocationBean();
		visitedLocation = null;
		if (user.getVisitedLocationsList().size() > 0)
			visitedLocation = user.getVisitedLocationsList().get((user.getVisitedLocationsList().size() - 1));
		return visitedLocation;
	}

	public Map<UUID,LocationBean> getEachUserLatestLocationList() {
		Map <UUID,LocationBean> allUsersLatestLocationMap = new HashMap<UUID,LocationBean>();
		DataContainer.usersData.entrySet().parallelStream().forEach(entry ->{
			LocationBean userLatestLocation = getUserLastVisitedLocation(entry.getValue()).getLocation();
			allUsersLatestLocationMap.put(entry.getKey(),userLatestLocation );
		});
		return allUsersLatestLocationMap;
	}

}
