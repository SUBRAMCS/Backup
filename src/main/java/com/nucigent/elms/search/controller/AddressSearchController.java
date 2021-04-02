package com.nucigent.elms.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nucigent.elms.search.dto.AddressSearchResponse;




@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class AddressSearchController {

	@Autowired 
	private RestTemplate restTemplate;
	
	private static final String API_KEY="?api_key=";

	@Value("${endpoint}")
	String endpoint;
	
	@Value("${api_key}")
	String apiKey;
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@GetMapping(("/address/search/{postCode}"))
	public ResponseEntity<?> searchAddressByPostCode(@PathVariable(value="postCode") String postCode) {
		
		String searchEndpoint = endpoint + postCode + API_KEY + apiKey;
	
		AddressSearchResponse searchResponse = new AddressSearchResponse();
		try {
			final ResponseEntity<AddressSearchResponse> responseEntity = restTemplate.getForEntity(searchEndpoint, AddressSearchResponse.class);
			searchResponse = responseEntity.getBody();
		} catch (Exception e) {
			return new ResponseEntity<>("Could not search address details", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(searchResponse);
	}

}
