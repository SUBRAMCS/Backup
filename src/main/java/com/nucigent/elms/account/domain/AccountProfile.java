package com.nucigent.elms.account.domain;

import java.io.Serializable;

public class AccountProfile implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer accountProfileId;
	private Integer registrationId;
	private Integer individualId;
	private Integer addressId;
	private Integer documentId;

	public Integer getAccountProfileId() {
		return accountProfileId;
	}

	public void setAccountProfileId(Integer accountProfileId) {
		this.accountProfileId = accountProfileId;
	}

	public Integer getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Integer registrationId) {
		this.registrationId = registrationId;
	}

	public Integer getIndividualId() {
		return individualId;
	}

	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

}
