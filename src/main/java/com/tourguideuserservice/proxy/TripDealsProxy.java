package com.tourguideuserservice.proxy;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tourguideuserservice.bean.ProviderBean;
import com.tourguideuserservice.dto.TripPricerDto;


@FeignClient(name = "tourguide-tripdeals-service", url= "localhost:9002")
public interface TripDealsProxy {

	@PostMapping("/trip-deals")
	List<ProviderBean> getTripDeals(@RequestBody TripPricerDto tripPricerDto); 
	
}
