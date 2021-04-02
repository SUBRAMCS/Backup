package com.nucigent.elms.account.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ContactDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<EmailDetailDto> emails;
	private List<PhoneDto> phones;

	public List<EmailDetailDto> getEmails() {
		if(null == emails) {
			emails = new ArrayList<>();
		}
		return emails;
	}

	public void setEmails(List<EmailDetailDto> emails) {
		this.emails = emails;
	}

	public List<PhoneDto> getPhones() {
		if(null == phones) {
			phones = new ArrayList<>();
		}
		return phones;
	}

	public void setPhones(List<PhoneDto> phones) {
		this.phones = phones;
	}

} 
