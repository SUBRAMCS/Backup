package com.nucigent.elms.account.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.EmailDto;
import com.nucigent.elms.account.dto.FamilyMemberDto;
import com.nucigent.elms.account.dto.FamilyPersonalDto;
import com.nucigent.elms.account.dto.MyFamilyDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.dto.PhoneDto;
import com.nucigent.elms.account.fileupload.AwsAdapter;
import com.nucigent.elms.account.service.MyFamilyService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class myFamilyController {

	@Autowired private MyFamilyService myFamilyService;
	
	@Autowired private AwsAdapter awsAdapter;
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	@Value("${spring.datasource.driverClassName}")
	String driverclassname;
	
	@Value("${spring.datasource.url}")
	String datasource;
	
	@Value("${spring.datasource.username}")
	String username;
	
	@Value("${spring.datasource.password}")
	String password;
	
	@GetMapping(("/myFamily/members"))
	public ResponseEntity<?> getFamilyDetail() {
		MyFamilyDto familyMembers = new MyFamilyDto();
		familyMembers = myFamilyService.getMyFamilyDetails();

		return ResponseEntity.ok(familyMembers);

	}

	@GetMapping(("/myFamily/Personal"))
	public ResponseEntity<?> getPersonalDetail() {
		FamilyPersonalDto familyPersonalDto = new FamilyPersonalDto();
		familyPersonalDto = myFamilyService.getFamilyPersonalDetail();
		return ResponseEntity.ok(familyPersonalDto);

	}

	@PutMapping(("/myFamily/personalDetail"))
	public ResponseEntity<?> savePersonalDetail(@RequestBody @Valid PersonalDetailDto personalDetailDto) {
		Integer individualId = myFamilyService.saveorupdatePersonalDetails(personalDetailDto);

		return ResponseEntity.ok(individualId);
	}

	@PutMapping(("/myFamily/addressDetail"))
	public ResponseEntity<?> saveAddressDetail(@RequestBody @Valid AddressDetailDto addressDetailDto) {
		Integer addressId = myFamilyService.saveorupdateAddressDetails(addressDetailDto);
		return ResponseEntity.ok(addressId);
	}
	
	@PutMapping(("/myFamily/EmailDetail"))
	public ResponseEntity<?> saveEmailDetail(@RequestBody @Valid EmailDto emailDto) {
		Integer emailId = myFamilyService.saveorupdateEmailDetails(emailDto);
		return ResponseEntity.ok(emailId);
	}
	
	@PutMapping(("/myFamily/PhoneDetail"))
	public ResponseEntity<?> savePhoneDetail(@RequestBody @Valid PhoneDto phoneDto) {
		Integer phoneId = myFamilyService.saveorupdatePhoneDetails(phoneDto);
		return ResponseEntity.ok(phoneId);
	}
	
	@PutMapping(("/myFamily/uploadFile"))
	public ResponseEntity<?> saveDocumentDetail(@RequestParam(value = "payload") String payload,
			@RequestParam(value = "file") List<MultipartFile> fileLs) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			
			DocumentDetailDto documentDetailDto = objectMapper.readValue(payload, DocumentDetailDto.class);
			Integer documentId = myFamilyService.saveorupdateDocumentDetails(documentDetailDto, fileLs);

			return ResponseEntity.ok(documentId);
		} catch (Exception e) {
			return ResponseEntity.ok("Files not loaded successfully");
		}

	}
	
	@PutMapping(("/myFamily/AdditionalInfo"))
	public ResponseEntity<?> saveAdditionalInformation(@RequestBody @Valid AdditionalDetailDto additionalDetail){
		Integer individualId = additionalDetail.getIndividualId();
		String additional_info = additionalDetail.getAdditionalInformation();
		jdbcTemplate.update("update Individual_Master set additional_info = ? where Individual_ID = ?", additional_info,individualId);
		return ResponseEntity.ok(individualId);
	}
		
	@GetMapping(("/myFamily/download/{documentId}"))
	public ResponseEntity<?> downloadDocument(@PathVariable(value="documentId") String documentId) {
		byte[] photoBytes = null;
		String keys = null;
		try{  
			Class.forName(driverclassname);  
			Connection con=DriverManager.getConnection(datasource,username,password);  
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select Document_Identifier from document_master where Document_ID = " + documentId);  
			while(rs.next())  
			{
		      	keys = rs.getString("Document_Identifier"); 
			}
			con.close(); 
			photoBytes=awsAdapter.download(keys);
			
			}catch(Exception e){ System.out.println(e);}  
		
		return ResponseEntity.ok(photoBytes);
	}

	@PutMapping(("/myFamily/removeIndividualDetail/{individualId}"))
	public ResponseEntity<?> removePersonalDetail(@PathVariable(value="individualId") Integer individualId){
				
		jdbcTemplate.update(" delete from address_master where individual_id = ?",individualId);
		jdbcTemplate.update(" delete from document_master where individual_id = ?",individualId);
		jdbcTemplate.update(" delete from email_master where individual_id = ?",individualId);
		jdbcTemplate.update(" delete from phone_master where individual_id = ?",individualId);
		jdbcTemplate.update(" delete from family_member where individual_id = ?",individualId);
		jdbcTemplate.update(" delete from nominee_member where individual_id = ?",individualId);
		jdbcTemplate.update(" delete from individual_master where individual_id = ?",individualId);
		
		return ResponseEntity.ok(1);
		
	}
	
	@PutMapping(("/myFamily/removeAddressDetail/{addressId}"))
	public ResponseEntity<?> removeAddressDetail(@PathVariable(value="addressId") Integer addressId){
				
		jdbcTemplate.update(" update account_profile set Address_ID = null where Address_ID = ?",addressId);
		jdbcTemplate.update(" delete from address_master where Address_ID = ?",addressId);
		jdbcTemplate.update(" update nominee_member set Address_ID = null where Address_ID = ?",addressId);		
		
		return ResponseEntity.ok(2);
		
	}
	
	@PutMapping(("/myFamily/removeEmailDetail/{emailId}"))
	public ResponseEntity<?> removeEmailDetail(@PathVariable(value="emailId") Integer emailId){
		
		jdbcTemplate.update(" update email_master set IsPrimary = 'Y' where rtrim(ltrim(str(email_id)))+'-2' in (select rtrim(ltrim(str(email_id)))+'-'+rtrim(ltrim(str(ROW_NUMBER() over  (partition by Individual_ID order by IsPrimary desc,Email_Id)))) from email_master where Individual_ID in (select Individual_ID from EMAIL_MASTER where Email_ID = ?))",emailId);
		jdbcTemplate.update(" delete from Email_Master where Email_ID = ?",emailId);
		jdbcTemplate.update(" update nominee_member set Email_ID = null  where Email_ID = ?",emailId);
		return ResponseEntity.ok(3);
		
	}
	
	@PutMapping(("/myFamily/removePhoneDetail/{phoneId}"))
	public ResponseEntity<?> removePhoneDetail(@PathVariable(value="phoneId") Integer phoneId){
		
		jdbcTemplate.update(" update Phone_Master set IsPrimary = 'Y' where rtrim(ltrim(str(Phone_ID)))+'-2' in (select rtrim(ltrim(str(Phone_ID)))+'-'+rtrim(ltrim(str(ROW_NUMBER() over  (partition by Individual_ID order by IsPrimary desc,Phone_ID)))) from Phone_Master where Individual_ID in (select Individual_ID from Phone_Master where Phone_ID = ?))",phoneId);
		jdbcTemplate.update(" delete from Phone_Master where Phone_ID = ?",phoneId);
		jdbcTemplate.update(" update nominee_member set Phone_ID = null where Phone_ID = ?",phoneId);
				
		return ResponseEntity.ok(4);
		
	}
	
	@PutMapping(("/myFamily/removeDocumentDetail/{documentId}"))
	public ResponseEntity<?> removeDocumentDetail(@PathVariable(value="documentId") Integer documentId){
		
		String keys = null;
		try{  
			Class.forName(driverclassname);  
			Connection con=DriverManager.getConnection(datasource,username,password);  
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select Document_Identifier from document_master where Document_ID = " + documentId);  
			while(rs.next())  
			{
		      	keys = rs.getString("Document_Identifier"); 
			}
			con.close(); 
			awsAdapter.deleteObject(keys);
			jdbcTemplate.update(" delete from document_master where document_id = ?",documentId);
		}
		catch(Exception e) {
			
		}

		return ResponseEntity.ok(5);
		
	}
	
	@GetMapping(("/myFamily/getfammemberlist"))
	public ResponseEntity<?> getFamMemberList() {
		List<FamilyMemberDto> familyMembers = new ArrayList<FamilyMemberDto>();
		familyMembers = myFamilyService.getFamMemberList();

		return ResponseEntity.ok(familyMembers);

	}
	
	
}
