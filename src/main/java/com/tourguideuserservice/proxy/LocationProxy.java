package com.tourguideuserservice.proxy;

import java.util.List;
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



@FeignClient(name = "tourguide-location-service", url = "localhost:9004")
public interface LocationProxy {

	@GetMapping("{userId}/location")
	VisitedLocationBean getUserLocation(@PathVariable("userId") UUID userId);
	
	@GetMapping("/attractions")
	List<AttractionBean> getAttractions();
	
	@PostMapping("/attractions/distances")
	TreeMap<Double,AttractionBean> getDistancesToAttractions(@RequestBody LocationBean location);
	
}
