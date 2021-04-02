package com.nucigent.elms.account.dto;

import java.util.List;

public class FamilyPersonalDto {

	private Integer individualId;
	private PersonalDetailDto personalDetail;
	private List<AddressDetailDto> addressDetail;
	private List<ContactDetailDto> contactDetail;
	
	public Integer getIndividualId() {
		return individualId;
	}
	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}
	public PersonalDetailDto getPersonalDetail() {
		return personalDetail;
	}
	public void setPersonalDetail(PersonalDetailDto personalDetail) {
		this.personalDetail = personalDetail;
	}
	public List<AddressDetailDto> getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(List<AddressDetailDto> addressDetail) {
		this.addressDetail = addressDetail;
	}
	public List<ContactDetailDto> getContactDetail() {
		return contactDetail;
	}
	public void setContactDetail(List<ContactDetailDto> contactDetail) {
		this.contactDetail = contactDetail;
	}
	
}
