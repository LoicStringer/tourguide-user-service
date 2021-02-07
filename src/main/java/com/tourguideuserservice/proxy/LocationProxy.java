package com.tourguideuserservice.proxy;

import java.util.TreeMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tourguideuserservice.bean.AttractionBean;
import com.tourguideuserservice.bean.LocationBean;

@FeignClient(name = "tourguide-location-service", url = "localhost:9004")
public interface LocationProxy {
	
	@PostMapping("/attractions/distances")
	TreeMap<Double,AttractionBean> getDistancesToAttractions(@RequestBody LocationBean location);
	
}
