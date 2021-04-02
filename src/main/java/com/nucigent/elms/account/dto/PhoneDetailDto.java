package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class PhoneDetailDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer primaryPhoneId;
	private String  primaryCountryCode;
	private String  primaryPhoneNumber;
	private Integer secondaryPhoneId;
	private String  secondaryCountryCode;
	private String  secondaryPhoneNumber;

	public Integer getPrimaryPhoneId() {
		return primaryPhoneId;
	}

	public void setPrimaryPhoneId(Integer primaryPhoneId) {
		this.primaryPhoneId = primaryPhoneId;
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

	public Integer getSecondaryPhoneId() {
		return secondaryPhoneId;
	}

	public void setSecondaryPhoneId(Integer secondaryPhoneId) {
		this.secondaryPhoneId = secondaryPhoneId;
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
