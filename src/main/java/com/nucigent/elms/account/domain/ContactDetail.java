package com.nucigent.elms.account.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactDetail implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer accountProfileId;
	private Integer individualId;
	private List<EmailDetail> emails;
	private List<PhoneDetail> phoneDetails;

	public Integer getAccountProfileId() {
		return accountProfileId;
	}

	public void setAccountProfileId(Integer accountProfileId) {
		this.accountProfileId = accountProfileId;
	}

	public Integer getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}

	public List<EmailDetail> getEmails() {
		return emails;
	}

	public void setEmails(List<EmailDetail> emails) {
		this.emails = emails;
	}

	public List<PhoneDetail> getPhoneDetails() {
		if(null == phoneDetails) {
			phoneDetails = new ArrayList<>();
		}
		return phoneDetails;
	}

	public void setPhoneDetails(List<PhoneDetail> phoneDetails) {
		this.phoneDetails = phoneDetails;
	}

}
