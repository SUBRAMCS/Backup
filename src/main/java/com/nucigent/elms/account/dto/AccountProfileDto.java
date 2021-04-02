package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class AccountProfileDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer accountProfileId;
	private Integer registrationId;
	private Integer individualId;
	private PersonalDetailDto personalDetail;
	private AddressDetailDto addressDetail;
	private ContactDetailDto contactDetail;

	public Integer getAccountProfileId() {
		return accountProfileId;
	}

	public void setAccountProfileId(Integer accountProfileId) {
		this.accountProfileId = accountProfileId;
	}

	public Integer getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Integer registrationId) {
		this.registrationId = registrationId;
	}

	public PersonalDetailDto getPersonalDetail() {
		return personalDetail;
	}

	public void setPersonalDetail(PersonalDetailDto personalDetail) {
		this.personalDetail = personalDetail;
	}

	public AddressDetailDto getAddressDetail() {
		return addressDetail;
	}

	public void setAddressDetail(AddressDetailDto addressDetail) {
		this.addressDetail = addressDetail;
	}

	public ContactDetailDto getContactDetail() {
		return contactDetail;
	}

	public void setContactDetail(ContactDetailDto contactDetail) {
		this.contactDetail = contactDetail;
	}

	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}
	
	public Boolean hasPersonalDetail() {
		return null == personalDetail ? false : true;
	}

	public Integer getIndividualId() {
		return individualId;
	}
	

	/*public Optional<Integer> getIndividualId() {
		if(hasPersonalDetail()) {
			return Optional.ofNullable(getPersonalDetail().getIndividualId());
		}
		return Optional.empty();
	}*/

}
