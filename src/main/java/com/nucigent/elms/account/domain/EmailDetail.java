package com.nucigent.elms.account.domain;

import java.io.Serializable;

public class EmailDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer individualId;
	private Integer emailId;
	private String  emailAddress;
	private String  primaryEmailFlag;

	public Integer getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}

	public Integer getEmailId() {
		return emailId;
	}

	public void setEmailId(Integer emailId) {
		this.emailId = emailId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
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
		EmailDetail other = (EmailDetail) obj;
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		return true;
	}

}
