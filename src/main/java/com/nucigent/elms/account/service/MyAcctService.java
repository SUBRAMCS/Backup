package com.nucigent.elms.account.service;

import com.nucigent.elms.account.dto.AddressDetailDto;
import com.nucigent.elms.account.dto.EmailDto;
import com.nucigent.elms.account.dto.MyAccountDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.dto.PhoneDto;

public interface MyAcctService {
	
	MyAccountDto getFamilyPersonalDetail();
	Integer saveorupdatePersonalDetails(PersonalDetailDto personalDetailDto);
	Integer saveorupdateAddressDetails(AddressDetailDto addressDetailDto);	
	Integer saveorupdateEmailDetails(EmailDto emailDto);
	Integer saveorupdatePhoneDetails(PhoneDto phoneDto);
	MyAccountDto getBankAccntDetail();
	
}
