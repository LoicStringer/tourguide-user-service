package com.tourguideuserservice.integration;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tourguideuserservice.bean.ProviderBean;
import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.dto.TripPricerDto;
import com.tourguideuserservice.exception.TripDealsProxyException;
import com.tourguideuserservice.form.UserTripPreferencesForm;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.proxy.TripDealsProxy;

import feign.FeignException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserTripDealsServiceTestIT {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TripDealsProxy tripDealsProxy;

	private static User user;

	@BeforeAll
	static void setUp() {
		user = new User();
		user.setUserId(UUID.randomUUID());
		user.setUserName("Tony");
		DataContainer.usersData.put(user.getUserId(), user);
	}

	@Test
	void addUserPreferencesTest() throws JsonProcessingException, Exception {

		UserTripPreferencesForm userTripPreferencesForm = new UserTripPreferencesForm();
		userTripPreferencesForm.setTripDuration(3);
		userTripPreferencesForm.setCurrency("USD");

		mockMvc.perform(post("/users/" + user.getUserId() + "/trip-preferences")
				.content(objectMapper.writeValueAsString(userTripPreferencesForm))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	@Test
	void getTripDealsTest() throws Exception {

		List<ProviderBean> tripDealsList = new ArrayList<ProviderBean>();
		ProviderBean providerBean = new ProviderBean();
		ProviderBean providerBeanBis = new ProviderBean();
		providerBean.setProviderName("Belleville FairyTail");
		providerBeanBis.setProviderName("Menilmontant Leisure Park");
		tripDealsList.add(providerBean);
		tripDealsList.add(providerBeanBis);

		when(tripDealsProxy.getTripDeals(any(TripPricerDto.class))).thenReturn(tripDealsList);

		MvcResult result = mockMvc.perform(get("/users/" + user.getUserId() + "/trip-deals"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andReturn();
		String resultContent = result.getResponse().getContentAsString();

		assertTrue(resultContent.contains("Belleville FairyTail"));
	}
	/*
	@Test
	void isExpectedExceptionThrownWhenTripDealsProxyFailTest() throws Exception {
		
		when(tripDealsProxy.getTripDeals(any(TripPricerDto.class))).thenThrow(TripDealsProxyException.class);
		
		mockMvc.perform(get("/users/" + user.getUserId() + "/trip-deals"))
				.andExpect(status().is4xxClientError())
				.andExpect(result-> assertTrue(result.getResolvedException() instanceof TripDealsProxyException));
	}
	*/
}
