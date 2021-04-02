package com.nucigent.elms.account.helper;

public class FamilyMember {

	private Integer familyMemberId;
	private Integer accountProfileId;
	private Integer individualId;
	private Integer addressId;
	private Integer documentId;
	private String relationship;
	private String additionalInfo;

	public Integer getFamilyMemberId() {
		return familyMemberId;
	}

	public void setFamilyMemberId(Integer familyMemberId) {
		this.familyMemberId = familyMemberId;
	}

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

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

}
