package com.nucigent.elms.user.domain;

public class EmailInfo {
	
	private long emailId;
	private long userId;
	private String emailAddress;
	private String sequenceNumber;
	private String isActive;
	private String recordCreatedDate;
	private String recordUpdatedDate;
	
	public long getEmailId() {
		return emailId;
	}
	public void setEmailId(long emailId) {
		this.emailId = emailId;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

}
