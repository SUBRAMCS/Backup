package com.nucigent.elms.user.domain;

public class PhoneInfo {
	
	private long phoneId;
	private long userId;	
	//@NotNull(message = "countryCode must not be blank.")
	private String countryCode;
	private String phoneNumber;
	private String sequenceNumber;
	private String isActive;
	private String recordCreatedDate;
	private String recordUpdatedDate;
	private long countryId;
	public long getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(long phoneId) {
		this.phoneId = phoneId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getRecordCreatedDate() {
		return recordCreatedDate;
	}
	public void setRecordCreatedDate(String recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}
	public String getRecordUpdatedDate() {
		return recordUpdatedDate;
	}
	public void setRecordUpdatedDate(String recordUpdatedDate) {
		this.recordUpdatedDate = recordUpdatedDate;
	}
	public long getCountryId() {
		return countryId;
	}
	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}

}
