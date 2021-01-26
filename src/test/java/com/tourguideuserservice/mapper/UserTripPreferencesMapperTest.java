package com.tourguideuserservice.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.tourguideuserservice.form.UserTripPreferencesForm;

class UserTripPreferencesMapperTest {

	private static UserTripPreferencesMapper userTripPreferencesMapper;
	private static UserTripPreferencesForm form;
	
	@BeforeAll
	static void setUp() {
		userTripPreferencesMapper = new UserTripPreferencesMapper();
		form = new UserTripPreferencesForm();
		form.setCurrency("USD");
		form.setTripDuration(3);
	}
	
	@Test
	void mapUserTripPreferencesFormToModelTest() {
		assertEquals(3,userTripPreferencesMapper.mapUserTripPreferencesFormToModel(form).getTripDuration());
	}

}
