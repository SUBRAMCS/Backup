package com.nucigent.elms.account.dto;

public class NomineeMemberDto {
	
	private PersonalDetailDto personalDetail;
	private AddressDetailDto addressDetail;
	private EmailDto emailDetail;
	private PhoneDto phoneDetail;
	private DocumentDetailDto documentDetail;
	
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
	public EmailDto getEmailDetail() {
		return emailDetail;
	}
	public void setEmailDetail(EmailDto emailDetail) {
		this.emailDetail = emailDetail;
	}
	public PhoneDto getPhoneDetail() {
		return phoneDetail;
	}
	public void setPhoneDetail(PhoneDto phoneDetail) {
		this.phoneDetail = phoneDetail;
	}
	public DocumentDetailDto getDocumentDetail() {
		return documentDetail;
	}
	public void setDocumentDetail(DocumentDetailDto documentDetail) {
		this.documentDetail = documentDetail;
	}
	
	

}
