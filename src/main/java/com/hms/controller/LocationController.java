package com.hms.controller;

import com.hms.service.LocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/get-location")
    public String getLocation() {
        return locationService.getLocation(); // Fetches the system's location dynamically
    }

//    @GetMapping("/get-Location")
//    public String getLocation(HttpServletRequest request){
//        String cilentIp = getCilentIp(request);
//        String locationInfo = locationService.getLocation("115.99.142.60");
//        return locationInfo;
//    }
//
//
//    private String getCilentIp(HttpServletRequest request) {
//        String remoteAddr = request.getRemoteAddr();
//        String xForwardFor = request.getHeader("X-Forwarded-For");
//        if (xForwardFor != null && !xForwardFor.isEmpty()){
//            remoteAddr = xForwardFor.split(",")[0];
//        }
//        return remoteAddr;
//    }
}
