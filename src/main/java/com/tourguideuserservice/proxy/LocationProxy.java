package com.tourguideuserservice.proxy;

import java.util.TreeMap;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tourguideuserservice.bean.AttractionBean;
import com.tourguideuserservice.bean.LocationBean;
import com.tourguideuserservice.bean.VisitedLocationBean;

@FeignClient(name = "${feign.client.location.name}", url="${feign.client.location.url}")
public interface LocationProxy {
	
	@PostMapping("/attractions/distances")
	TreeMap<Double,AttractionBean> getDistancesToAttractions(@RequestBody LocationBean location);
	
	@GetMapping("/users/{userId}/locations/latest")
	VisitedLocationBean getUserLocation(@PathVariable ("userId")UUID userId);
}
