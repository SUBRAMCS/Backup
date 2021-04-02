package com.nucigent.elms.account.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nucigent.elms.account.dto.BankAccountDto;
import com.nucigent.elms.account.dto.BankDtlsDto;
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.service.UserBankAcntService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserBankAcntController {
	
	@Autowired
	private UserBankAcntService userBankAcntService;
	
	@GetMapping(("/valueitem/getBankAccntList"))
	public ResponseEntity<?> getBankAccntList() {
		
		List<BankDtlsDto> bankDtlList = userBankAcntService.getBankAccntList();
		return ResponseEntity.ok(bankDtlList);

	}
	
	@GetMapping(("/valueitem/bankaccntdtl"))
	public ResponseEntity<?> getBankAccntDetail() {
		Map<String,BankDtlsDto> bankDtlMap = userBankAcntService.getBankAccntDetail();
		return ResponseEntity.ok(bankDtlMap);

	}
	
	@GetMapping(("/valueitem/bankaccntdtlforitem/{valAccountId}"))
	public ResponseEntity<?> getBankAccntDetailFrItem(@PathVariable(value="valAccountId") Integer valAccountId) {
		Map<String,BankDtlsDto> bankDtlMap = userBankAcntService.getBankAccntDetailFrItem(valAccountId);
		return ResponseEntity.ok(bankDtlMap);

	}
	
	@PutMapping(("/valueitem/saveuserbankaccnt"))
	public ResponseEntity<?> saveUserBankAccnt(@RequestBody @Valid BankDtlsDto bankDtlsDto) {
		Integer valItemId  = userBankAcntService.saveUserBankAccnt(bankDtlsDto);
		return ResponseEntity.ok(valItemId);

	}
	
	@PutMapping(("/valueitem/savebankpersonalinfo"))
	public ResponseEntity<?> saveUserBankPersonalAccnt(@RequestBody @Valid PersonalDetailDto personalDtlsDto) {
		Integer valItemId  = userBankAcntService.saveUserBankPersonalAccnt(personalDtlsDto);
		return ResponseEntity.ok(valItemId);

	}
	
	@PutMapping(("/valueitem/savebankaccntdtlinfo"))
	public ResponseEntity<?> saveUserBankAccntDtlInfo(@RequestBody @Valid BankAccountDto bankAccountDto) {
		Integer valItemId  = userBankAcntService.saveUserBankAccntDtlInfo(bankAccountDto);
		return ResponseEntity.ok(valItemId);

	}
	
	@PutMapping(("/valueitem/savebankaccntaddinfo"))
	public ResponseEntity<?> updateBankAccntAddInfo(@RequestBody @Valid PersonalDetailDto personalDtlsDto) {
		Integer valItemId  = userBankAcntService.updateBankAccntAddInfo(personalDtlsDto);
		return ResponseEntity.ok(valItemId);
	}
	
	@PutMapping(("/valueitem/deleteBankAccountInfo/{valAccountId}"))
	public ResponseEntity<?> removeBankinfo(@PathVariable(value="valAccountId") Integer valAccountId){
				
		int itemupdated  = userBankAcntService.removeBankinfo(valAccountId);
		
		return ResponseEntity.ok(itemupdated);
		
	}
	
	@PutMapping(("/valueitem/uploadFile"))
	public ResponseEntity<?> saveDocumentDetail(@RequestParam(value = "payload") String payload,
			@RequestParam(value = "file") List<MultipartFile> fileLs) {

		ObjectMapper objectMapper = new ObjectMapper();

		try {
			
			DocumentDetailDto documentDetailDto = objectMapper.readValue(payload, DocumentDetailDto.class);
			Integer documentId = userBankAcntService.saveorupdateDocumentDetails(documentDetailDto, fileLs);

			return ResponseEntity.ok(documentId);
		} catch (Exception e) {
			return ResponseEntity.ok("Files not loaded successfully");
		}

	}
	
}
