package com.nucigent.elms.account.dao;

import java.util.List;

import com.nucigent.elms.account.domain.AccountProfile;
import com.nucigent.elms.account.domain.AddressDetail;
import com.nucigent.elms.account.domain.ContactDetail;
import com.nucigent.elms.account.domain.EmailDetail;
import com.nucigent.elms.account.domain.PersonalDetail;
import com.nucigent.elms.account.domain.PhoneDetail;

public interface AccountDao {

	Integer createAccountProfile(AccountProfile accountProfile);

	void updatePersonalDetail(PersonalDetail personalDetail);

	Integer getAccountProfileIdIfExists(Integer registeredId);

	Integer saveAddressDetail(AddressDetail addressDetail);

	void updateAddressIdByAccountProfileId(Integer addressId, Integer accountProfileId);

	void saveEmailAddress(List<EmailDetail> emailDetails);

	void savePhoneNumber(List<PhoneDetail> phoneDetails);

	AccountProfile getAccountProfileById(Integer accountProfileId);

	void updateAddressDetail(AddressDetail addressDetail);

	void updatePrimaryEmailAddress(List<EmailDetail> emailDetails);

	void updateSecondaryEmailAddress(List<EmailDetail> emailDetails);

	void updatePrimaryPhoneNumber(List<PhoneDetail> phoneDetails);

	void updateSecondaryPhoneNumber(List<PhoneDetail> phoneDetails);

	PersonalDetail getPersonalDetailByIndividualId(Integer individualId);

	AddressDetail getAddressDetailByIndividualId(Integer individualId);

	List<EmailDetail> getEmailDetailByIndividualId(Integer individualId);

	List<PhoneDetail> getPhoneDetailByIndividualId(Integer individualId);

	AccountProfile getAccountDetailsByUserLoginId(String userLoginId);

	Integer getIndividualIdByRegisteredId(Integer registeredId);

	ContactDetail getContactDetails(Integer individualId);
	
	List<AddressDetail> getAddressDetailsByIndividualId(Integer individualId);

}
