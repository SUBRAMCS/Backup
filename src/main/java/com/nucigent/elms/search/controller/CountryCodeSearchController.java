package com.nucigent.elms.search.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nucigent.elms.countrycode.dto.CountryCodeDto;
import com.nucigent.elms.countrycode.service.CountryCodeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CountryCodeSearchController {

	Logger logger = LoggerFactory.getLogger(CountryCodeSearchController.class);

	@Autowired private CountryCodeService countryCodeService;

	@GetMapping(("/country/phones"))
	public ResponseEntity<?> searchAllCountryPhoneCode() {
		List<CountryCodeDto> countryCodeDtos = new ArrayList<>();
		try {
			countryCodeDtos = countryCodeService.getAllCountryCodes();
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Could not search country phone codes", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(countryCodeDtos);
	}

}
