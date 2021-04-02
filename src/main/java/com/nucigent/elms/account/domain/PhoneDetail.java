package com.nucigent.elms.account.domain;

import java.io.Serializable;

public class PhoneDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer individualId;
	private Integer phoneId;
	private Integer countryId;
	private Integer  countryCode;
	private String  phoneNumber;
	private String  primaryPhoneFlag;

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}

	public Integer getPhoneId() {
		return phoneId;
	}

	public void setPhoneId(Integer phoneId) {
		this.phoneId = phoneId;
	}

	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPrimaryPhoneFlag() {
		return primaryPhoneFlag;
	}

	public void setPrimaryPhoneFlag(String primaryPhoneFlag) {
		this.primaryPhoneFlag = primaryPhoneFlag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((phoneId == null) ? 0 : phoneId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneDetail other = (PhoneDetail) obj;
		if (phoneId == null) {
			if (other.phoneId != null)
				return false;
		} else if (!phoneId.equals(other.phoneId))
			return false;
		return true;
	}

}
