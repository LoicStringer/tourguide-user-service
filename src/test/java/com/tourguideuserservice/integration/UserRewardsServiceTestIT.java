package com.tourguideuserservice.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.TreeMap;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.tourguideuserservice.bean.AttractionBean;
import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;
import com.tourguideuserservice.data.DataContainer;
import com.tourguideuserservice.model.User;
import com.tourguideuserservice.proxy.LocationProxy;
import com.tourguideuserservice.proxy.RewardsProxy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserRewardsServiceTestIT {

	@Autowired
	private MockMvc mockMvc;
		
	@MockBean
	private LocationProxy locationProxy;
	
	@MockBean
	private RewardsProxy rewardsProxy;
	
	private static TreeMap<Double, AttractionBean> distancesToAttractionsMap;
	private static User userToReward;
	private static VisitedLocationBean userLocation;
	private static AttractionBean attractionAround; 
	
	@BeforeAll
	static void setUp() {
		DataContainer.clearUsersData();
		userToReward = new User();
		userToReward.setUserId(UUID.randomUUID());
		userLocation = new VisitedLocationBean(userToReward.getUserId(),new LocationBean(48.88,2.38),new Date());
		userToReward.getVisitedLocationsList().add(userLocation);
		DataContainer.usersData.put(userToReward.getUserId(), userToReward);
		
		distancesToAttractionsMap = new TreeMap<Double,AttractionBean>();
		attractionAround = new AttractionBean(UUID.randomUUID(),"Buttes Chaumont","Paris","France",48.8809,2.3828);
		distancesToAttractionsMap.put(8.52,attractionAround);
	}
	
	@Test
	void addUserRewardTest() throws Exception {
		
		when(locationProxy.getDistancesToAttractions(userLocation.getLocation())).thenReturn(distancesToAttractionsMap);
		when(rewardsProxy.getAttractionRewardPoints(userToReward.getUserId(), attractionAround.getAttractionId())).thenReturn(5000);
		
		mockMvc.perform(get("/users/"+userToReward.getUserId()+"/rewards/latest"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.rewardCentralPoints").value(5000))
				.andExpect(jsonPath("$.visitedLocationBean.userId").value(userToReward.getUserId().toString()))
				.andExpect(jsonPath("$.attractionBean.attractionName").value("Buttes Chaumont"));
		
		assertEquals(5000,userToReward.getUserRewardsList().get(0).getRewardCentralPoints());
		assertEquals(attractionAround,userToReward.getUserRewardsList().get(0).getAttractionBean());
	}

	@Test
	void getUserRewardsListTest() throws Exception {
		
		mockMvc.perform(get("/users/"+userToReward.getUserId()+"/rewards"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].rewardCentralPoints").value(5000));
	}
}
