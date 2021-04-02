package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class EmailDetailDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer emailId;
	private Integer individualId;
	private String  emailAddress;
	private String  primaryEmailFlag;
	
	
	
	public Integer getEmailId() {
		return emailId;
	}
	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
	}
	public Integer getIndividualId() {
		return individualId;
	}
	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPrimaryEmailFlag() {
		return primaryEmailFlag;
	}
	public void setPrimaryEmailFlag(String primaryEmailFlag) {
		this.primaryEmailFlag = primaryEmailFlag;
	}
	
}
