package com.tourguideuserservice.proxy;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feignClient.rewardsService.name}", url = "${feignClient.rewardsService.url}")
public interface RewardsProxy {

	@GetMapping("/{userId}/attractions/{attractionId}/reward-points")
	int getAttractionRewardPoints(@PathVariable("userId")UUID userId, @PathVariable("attractionId")UUID attractionId) ;
		
	
}
