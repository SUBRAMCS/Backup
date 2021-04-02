package com.nucigent.elms.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.nucigent.elms.account.dto.BankAccountDto;
import com.nucigent.elms.account.dto.BankAccountMapDto;
import com.nucigent.elms.account.dto.BankDtlsDto;
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;

public interface UserBankAcntService {
	
	public Map<String,BankDtlsDto> getBankAccntDetail();

	public Integer saveUserBankAccnt(@Valid BankDtlsDto bankDtlsDto);

	public List<BankDtlsDto> getBankAccntList();

	public Integer saveUserBankPersonalAccnt(@Valid PersonalDetailDto personalDtlsDto);

	public int removeBankinfo(Integer valAccountId);

	public Integer saveUserBankAccntDtlInfo(@Valid BankAccountDto bankAccountDto);

	public Map<String, BankDtlsDto> getBankAccntDetailFrItem(Integer valAccountId);

	public Integer saveorupdateDocumentDetails(DocumentDetailDto documentDetailDto, List<MultipartFile> fileLs);

	public Integer updateBankAccntAddInfo(@Valid PersonalDetailDto personalDtlsDto);


	
}
