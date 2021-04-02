package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class ContactDetailDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer contactId;
	private Integer individualId;
	private String primaryEmailAddress;
	private String primaryCountryCode;
	private String primaryPhoneNumber;
	private String secondaryEmailAddress;
	private String secondaryCountryCode;
	private String secondaryPhoneNumber;
	
	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public Integer getIndividualId() {
		return individualId;
	}
	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}
	public String getPrimaryEmailAddress() {
		return primaryEmailAddress;
	}
	public void setPrimaryEmailAddress(String primaryEmailAddress) {
		this.primaryEmailAddress = primaryEmailAddress;
	}
	public String getPrimaryCountryCode() {
		return primaryCountryCode;
	}
	public void setPrimaryCountryCode(String primaryCountryCode) {
		this.primaryCountryCode = primaryCountryCode;
	}
	public String getPrimaryPhoneNumber() {
		return primaryPhoneNumber;
	}
	public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
		this.primaryPhoneNumber = primaryPhoneNumber;
	}
	public String getSecondaryEmailAddress() {
		return secondaryEmailAddress;
	}
	public void setSecondaryEmailAddress(String secondaryEmailAddress) {
		this.secondaryEmailAddress = secondaryEmailAddress;
	}
	public String getSecondaryCountryCode() {
		return secondaryCountryCode;
	}
	public void setSecondaryCountryCode(String secondaryCountryCode) {
		this.secondaryCountryCode = secondaryCountryCode;
	}
	public String getSecondaryPhoneNumber() {
		return secondaryPhoneNumber;
	}
	public void setSecondaryPhoneNumber(String secondaryPhoneNumber) {
		this.secondaryPhoneNumber = secondaryPhoneNumber;
	}
	


}
