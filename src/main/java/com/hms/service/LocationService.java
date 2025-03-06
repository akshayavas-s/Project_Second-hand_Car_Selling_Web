package com.hms.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationService {
    private static final String IPINFO_API_KEY = "03082cb5b3df80";  // Replace with your actual key
    private static final String IPINFO_URL = "https://ipinfo.io/";
    private static final String IPIFY_URL = "https://api64.ipify.org?format=text"; // To get your public IP

    private final RestTemplate restTemplate = new RestTemplate();

    // Get public IP dynamically
    public String getPublicIP() {
        return restTemplate.getForObject(IPIFY_URL, String.class);
    }

    // Fetch location details from ipinfo.io
    public String getLocation() {
        String publicIP = getPublicIP();  // Get the system's public IP
        String url = IPINFO_URL + publicIP + "?token=" + IPINFO_API_KEY;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }



//    private static final String API_KEY = "5c6e27a622e1aa84cac01110ee671091";
//    private static final String URL = "http://api.ipstack.com/";
//
//    public String getLocation(String ipAddress) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = URL + ipAddress + "?access_key=" + API_KEY;
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        return response.getBody();
//    }
}
