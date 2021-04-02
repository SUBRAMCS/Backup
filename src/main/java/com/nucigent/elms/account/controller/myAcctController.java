package com.nucigent.elms.account.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nucigent.elms.account.dto.AddressDetailDto;
import com.nucigent.elms.account.dto.EmailDto;
import com.nucigent.elms.account.dto.MyAccountDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.dto.PhoneDto;
import com.nucigent.elms.account.service.MyAcctService;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class myAcctController {
	

	@Autowired
	private MyAcctService myAcctService;
	
	@GetMapping(("myAccount/PersonalDetail"))
	public ResponseEntity<?> getPersonalDetail() {
		MyAccountDto myAccountDto = new MyAccountDto();
		myAccountDto = myAcctService.getFamilyPersonalDetail();
		return ResponseEntity.ok(myAccountDto);

	}
		
	@PutMapping(("/myAccount/personalDetail"))
	public ResponseEntity<?> saveorupdatePersonalDetails(@RequestBody @Valid PersonalDetailDto personalDetailDto) {
		Integer individualId = myAcctService.saveorupdatePersonalDetails(personalDetailDto);
		return ResponseEntity.ok(individualId);
	}	
	
	
	@PutMapping(("/myAccount/addressDetail"))
	public ResponseEntity<?> saveAddressDetail(@RequestBody @Valid AddressDetailDto addressDetailDto) {
		Integer addressId = myAcctService.saveorupdateAddressDetails(addressDetailDto);
		return ResponseEntity.ok(addressId);
	}
	
	@PutMapping(("myAccount/emailDetail"))
	public ResponseEntity<?> saveEmailDetails(@RequestBody @Valid EmailDto emailDto) {
		Integer emailId = myAcctService.saveorupdateEmailDetails(emailDto);
		return ResponseEntity.ok(emailId);
	}
	
	@PutMapping(("myAccount/phoneDetail"))
	public ResponseEntity<?> savePhoneDetails(@RequestBody @Valid PhoneDto phoneDto) {
		Integer phoneId = myAcctService.saveorupdatePhoneDetails(phoneDto);
		return ResponseEntity.ok(phoneId);
	}

}
