package com.nucigent.elms.account.dto;

import java.util.List;

public class FamilyMemberDto {
	
	private PersonalDetailDto personalDetail;
	private List<AddressDetailDto> addressDetail;
	private List<EmailDto> emailDetail;
	private List<PhoneDto> phoneDetail;
	private List<DocumentDetailDto> documentDetail;
		
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
	public List<EmailDto> getEmailDetail() {
		return emailDetail;
	}
	public void setEmailDetail(List<EmailDto> emailDetail) {
		this.emailDetail = emailDetail;
	}
	public List<PhoneDto> getPhoneDetail() {
		return phoneDetail;
	}
	public void setPhoneDetail(List<PhoneDto> phoneDetail) {
		this.phoneDetail = phoneDetail;
	}
	public List<DocumentDetailDto> getDocumentDetail() {
		return documentDetail;
	}
	public void setDocumentDetail(List<DocumentDetailDto> documentDetail) {
		this.documentDetail = documentDetail;
	}
}
