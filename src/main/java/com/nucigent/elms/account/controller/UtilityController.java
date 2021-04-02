package com.nucigent.elms.account.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nucigent.elms.account.dto.ListObjectDto;
import com.nucigent.elms.account.service.UtilityService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UtilityController {
	
	@Autowired
	private UtilityService utilityService;
	
	
	@GetMapping(("/utility/members"))
	public ResponseEntity<?> getFamilyDetail() {
		ListObjectDto listObjectDto = new  ListObjectDto();
		listObjectDto = utilityService.getListObject();

		return ResponseEntity.ok(listObjectDto);

	}
	
	@GetMapping(("/utility/banklookup"))
	public ResponseEntity<?> getBanksLookUp() {
		List<String> bankList = new ArrayList<String>();
		bankList = utilityService.getBanksLookUp();

		return ResponseEntity.ok(bankList);

	}

}
