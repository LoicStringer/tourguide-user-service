package com.tourguideuserservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tourguideuserservice.bean.ProviderBean;
import com.tourguideuserservice.dto.TripPricerDto;
import com.tourguideuserservice.exception.UserNotFoundException;
import com.tourguideuserservice.form.UserTripPreferencesForm;
import com.tourguideuserservice.mapper.UserTripPreferencesMapper;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.model.UserTripPreferences;
import com.tourguideuserservice.proxy.TripDealsProxy;

@ExtendWith(MockitoExtension.class)
class UserTripDealsServiceTest {

	@Mock
	private TripDealsProxy tripDealsProxy;

	@Mock
	private UserRewardsService userRewardsService;

	@Mock
	private UserService userService;

	@Mock
	private UserTripPreferencesMapper userTripPreferencesMapper;

	@InjectMocks
	private UserTripDealsService userTripDealsService;
	
	private static User user;
	
	@BeforeAll
	static void setUp() {
		user = new User();
		user.setUserId(UUID.randomUUID());	
	}
	
	@BeforeEach
	void setUpForTests() throws UserNotFoundException {
		when(userService.getUser(user.getUserId())).thenReturn(user);
	}

	@Test
	void addUserTripPreferencesTest() throws UserNotFoundException {
		UserTripPreferencesForm userTripPreferencesForm = new UserTripPreferencesForm();
		userTripPreferencesForm.setTripDuration(3);
		UserTripPreferences userTripPreferences = new UserTripPreferences();
		userTripPreferences.setTripDuration(3);
	
		when(userTripPreferencesMapper.mapUserTripPreferencesFormToModel(userTripPreferencesForm))
				.thenReturn(userTripPreferences);
		
		assertEquals(userTripPreferences,userTripDealsService.addUserTripPreferences(user.getUserId(),userTripPreferencesForm));
		assertEquals(3,user.getPreferences().getTripDuration());
	}

	@Test
	void getTripDealsTest() throws UserNotFoundException {
		List<ProviderBean> tripDealsList = new ArrayList<ProviderBean>();
		ProviderBean providerBean = new ProviderBean();
		ProviderBean providerBeanBis = new ProviderBean();
		providerBean.setProviderName("Belleville FairyTail");
		providerBeanBis.setProviderName("Menilmontant Leisure Park");
		tripDealsList.add(providerBean);
		tripDealsList.add(providerBeanBis);

		when(tripDealsProxy.getTripDeals(any(TripPricerDto.class))).thenReturn(tripDealsList);
		
		assertEquals(tripDealsList,userTripDealsService.getTripDeals(user.getUserId()));
		assertEquals("Belleville FairyTail", user.getTripDealsList().get(0).getProviderName());
	}
	
}
