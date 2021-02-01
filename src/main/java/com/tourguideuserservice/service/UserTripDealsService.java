package com.tourguideuserservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tourguideuserservice.bean.ProviderBean;
import com.tourguideuserservice.dto.TripPricerDto;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.form.UserTripPreferencesForm;
import com.tourguideuserservice.mapper.UserTripPreferencesMapper;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.model.UserTripPreferences;
import com.tourguideuserservice.proxy.TripDealsProxy;

@Service
public class UserTripDealsService {

	@Autowired
	private TripDealsProxy tripDealsProxy;

	@Autowired
	private UserRewardsService userRewardsService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserTripPreferencesMapper userTripPreferencesMapper;

	public UserTripPreferences addUserTripPreferences(UUID userId, UserTripPreferencesForm userTripPreferencesForm) throws UserNotFoundException {
		UserTripPreferences userTripPreferences = userTripPreferencesMapper
				.mapUserTripPreferencesFormToModel(userTripPreferencesForm);
		userService.getUser(userId).setPreferences(userTripPreferences);
		return userTripPreferences;
	}

	public List<ProviderBean> getTripDeals(UUID userId) throws UserNotFoundException  {
		TripPricerDto tripPricerDto = buildTripPricerDto(userId);
		List<ProviderBean> tripDealsList;
		tripDealsList = tripDealsProxy.getTripDeals(tripPricerDto);
		return addUserTripDeals(userId, tripDealsList);
	}

	private List<ProviderBean> addUserTripDeals(UUID userId, List<ProviderBean> tripDeals) throws UserNotFoundException {
		User user = userService.getUser(userId);
		user.setTripDealsList(tripDeals);
		return user.getTripDealsList();
	}

	private TripPricerDto buildTripPricerDto(UUID userId) throws UserNotFoundException {
		TripPricerDto tripPricerDto = new TripPricerDto();
		UserTripPreferences userTripPreferences = userService.getUser(userId).getPreferences();
		tripPricerDto.setAdultsNumber(userTripPreferences.getNumberOfAdults());
		tripPricerDto.setChildrenNumber(userTripPreferences.getNumberOfChildren());
		tripPricerDto.setId(userId);
		tripPricerDto.setTripDuration(userTripPreferences.getTripDuration());
		tripPricerDto.setUserRewardsPointsSum(userRewardsService.getUserRewardsPointsSum(userId));
		return tripPricerDto;
	}
}
