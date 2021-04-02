package com.nucigent.elms.account.domain;

public class NomineeMember {
	
	private Integer nomineeMemberId;
	private Integer accountProfileId;
	private Integer individualId;
	private Integer addressId;
	private Integer documentId;
	private String relationship;
	private String additionalInfo;
	private Integer sequenceNumber;
	private String isActive;
	private String primaryFlag;
	
	public Integer getNomineeMemberId() {
		return nomineeMemberId;
	}
	public void setNomineeMemberId(Integer nomineeMemberId) {
		this.nomineeMemberId = nomineeMemberId;
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
	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getPrimaryFlag() {
		return primaryFlag;
	}
	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}
	
}
