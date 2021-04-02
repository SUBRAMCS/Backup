package com.nucigent.elms.account.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nucigent.elms.account.dto.AddressDetailDto;
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.EmailDto;
import com.nucigent.elms.account.dto.FamilyMemberDto;
import com.nucigent.elms.account.dto.FamilyPersonalDto;
import com.nucigent.elms.account.dto.MyFamilyDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.dto.PhoneDto;




public interface MyFamilyService {
	
	MyFamilyDto getMyFamilyDetails();
	FamilyPersonalDto getFamilyPersonalDetail();
	Integer saveorupdatePersonalDetails(PersonalDetailDto personalDetailDto);
	Integer saveorupdateAddressDetails(AddressDetailDto addressDetailDto);
	Integer saveorupdateEmailDetails(EmailDto emailDto);
	Integer saveorupdatePhoneDetails(PhoneDto phoneDto);
	Integer saveorupdateDocumentDetails(DocumentDetailDto documentDetailDto,List<MultipartFile> fileLs);
	List<FamilyMemberDto> getFamMemberList();

}
