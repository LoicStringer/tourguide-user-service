package com.tourguideuserservice.proxy;

import java.util.TreeMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tourguideuserservice.bean.AttractionBean;
import com.tourguideuserservice.bean.LocationBean;

@FeignClient(name = "${feign.client.location.name}", url = "${feign.client.location.url}")
public interface LocationProxy {
	
	@PostMapping("/attractions/distances")
	TreeMap<Double,AttractionBean> getDistancesToAttractions(@RequestBody LocationBean location);
	
}
