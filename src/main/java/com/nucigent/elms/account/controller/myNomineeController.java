package com.nucigent.elms.account.controller;

import java.util.List;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nucigent.elms.account.dto.AdditionalDetailDto;
import com.nucigent.elms.account.dto.AddressDetailDto;
import com.nucigent.elms.account.dto.ContactDto;
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.DocumentDto;
import com.nucigent.elms.account.dto.EmailDto;
import com.nucigent.elms.account.dto.MyFamilyDto;
import com.nucigent.elms.account.dto.MyNomineeDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.dto.PhoneDto;
import com.nucigent.elms.account.service.MyNomineeService;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class myNomineeController {
	
@Autowired private MyNomineeService myNomineeService;
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	@Value("${spring.datasource.driverClassName}")
	String driverclassname;
	
	@Value("${spring.datasource.url}")
	String datasource;
	
	@Value("${spring.datasource.username}")
	String username;
	
	@Value("${spring.datasource.password}")
	String password;
	
	@GetMapping(("/nominee/familymembers"))
	public ResponseEntity<?> getFamilyDetail() {
		MyFamilyDto familyMembers = new MyFamilyDto();
		familyMembers = myNomineeService.getMyNomineeDetails();

		return ResponseEntity.ok(familyMembers);

	}
	
	@GetMapping(("/nominee/members"))
	public ResponseEntity<?> getNomineeDetail(){
		MyFamilyDto nomineeMembers = new MyFamilyDto();
		nomineeMembers = myNomineeService.getNomineeMember();
		
		return ResponseEntity.ok(nomineeMembers);
	}
		
	@PutMapping(("/nominee/personalDetail"))
	public ResponseEntity<?> savePersonalDetail(@RequestBody @Valid PersonalDetailDto personalDetailDto) {
		Integer individualId = myNomineeService.saveorupdatePersonalDetails(personalDetailDto);

		return ResponseEntity.ok(individualId);
	}
	
	@PutMapping(("/nominee/emailDetail"))
	public ResponseEntity<?> saveEmailDetail(@RequestBody @Valid EmailDto emailDto){
		Integer individualId = myNomineeService.saveorupdateEmailDetails(emailDto);
		
		return ResponseEntity.ok(individualId);
	}
	
	@PutMapping(("/nominee/phoneDetail"))
	public ResponseEntity<?> savePhoneDetail(@RequestBody @Valid PhoneDto phoneDto){
		Integer individualId = myNomineeService.saveorupdatePhoneDetails(phoneDto);		
		return ResponseEntity.ok(individualId);		
	}
	
	@PutMapping(("/nominee/addressDetail"))
	public ResponseEntity<?> saveAddressDetail(@RequestBody @Valid AddressDetailDto addressDetailDto){
		Integer individualId = myNomineeService.saveorupdateAddressDetails(addressDetailDto);
		return ResponseEntity.ok(individualId);		
	}
	
	@PutMapping(("/nominee/AdditionalInfo"))
	public ResponseEntity<?> saveAdditionalInformation(@RequestBody @Valid AdditionalDetailDto additionalDetail){
		Integer individualId = additionalDetail.getIndividualId();
		String additional_info = additionalDetail.getAdditionalInformation();
		jdbcTemplate.update("update nominee_member set additional_info = ? where Individual_ID = ?", additional_info,individualId);
		return ResponseEntity.ok(individualId);
	}
		
	@PutMapping(("/nominee/removeIndividualDetail/{individualId}"))
	public ResponseEntity<?> removePersonalDetail(@PathVariable(value="individualId") Integer individualId){
				
		jdbcTemplate.update(" delete from address_master where Address_ID in (select Address_ID from nominee_member where Individual_ID in (?) and Individual_ID not in (select Individual_ID from family_member))",individualId);
		jdbcTemplate.update(" delete from email_master where Email_ID in (select Email_ID from nominee_member where Individual_ID in (?) and Individual_ID not in (select Individual_ID from family_member))",individualId);
		jdbcTemplate.update(" delete from phone_master where Phone_ID in (select Phone_ID from nominee_member where Individual_ID in (?) and Individual_ID not in (select Individual_ID from family_member))",individualId);
		jdbcTemplate.update(" delete from document_master where document_id in (select document_id from nominee_member where Individual_ID in (?) and Individual_ID not in (select Individual_ID from family_member))",individualId);
		jdbcTemplate.update(" delete from nominee_member where individual_id = ?",individualId);
		jdbcTemplate.update(" delete from Individual_Master where Individual_ID in(?) and Individual_ID not in (select Individual_ID from family_member)",individualId);
		
		return ResponseEntity.ok(1);
		
	}
	
	@PutMapping(("/nominee/removeAddressDetail/{addressId}"))
	public ResponseEntity<?> removeAddressDetail(@PathVariable(value="addressId") Integer addressId){
				
		jdbcTemplate.update(" delete from address_master where Address_ID in (?) and Individual_ID not in (select Individual_ID from family_member)",addressId);
		jdbcTemplate.update(" update nominee_member set Address_ID = null where Address_ID = ?",addressId);		
		
		return ResponseEntity.ok(2);
		
	}
	
	@PutMapping(("/nominee/removeEmailDetail/{emailId}"))
	public ResponseEntity<?> removeEmailDetail(@PathVariable(value="emailId") Integer emailId){
				
		jdbcTemplate.update(" delete from Email_Master where Email_ID in (?) and Individual_ID not in (select Individual_ID from family_member)",emailId);
		jdbcTemplate.update(" update nominee_member set Email_ID = null  where Email_ID = ?",emailId);
		return ResponseEntity.ok(3);
		
	}
	
	@PutMapping(("/nominee/removePhoneDetail/{phoneId}"))
	public ResponseEntity<?> removePhoneDetail(@PathVariable(value="phoneId") Integer phoneId){
				
		jdbcTemplate.update(" delete from Phone_Master where Phone_ID in (?) and Individual_ID not in (select Individual_ID from family_member)",phoneId);
		jdbcTemplate.update(" update nominee_member set Phone_ID = null where Phone_ID = ?",phoneId);
				
		return ResponseEntity.ok(4);
		
	}
	
	@PutMapping(("/nominee/removeDocumentDetail/{documentId}"))
	public ResponseEntity<?> removeDocumentDetail(@PathVariable(value="documentId") Integer documentId){
		jdbcTemplate.update(" update nominee_member set document_id = null where document_id = ?",documentId);
		jdbcTemplate.update(" delete from  document_master where document_id = ? and individual_id not in (select individual_id from family_member)",documentId);
		return ResponseEntity.ok(5);
	}
	
	
	
	@PutMapping(("/nominee/uploadFile"))
	public ResponseEntity<?> saveDocumentDetail(@RequestParam(value = "payload") String payload,
			@RequestParam(value = "file") List<MultipartFile> fileLs) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			
			DocumentDetailDto documentDetailDto = objectMapper.readValue(payload, DocumentDetailDto.class);
			Integer documentId = myNomineeService.saveorupdateDocumentDetails(documentDetailDto, fileLs);

			return ResponseEntity.ok(documentId);
		} catch (Exception e) {
			return ResponseEntity.ok("Files not loaded successfully");
		}

	}
		
}
