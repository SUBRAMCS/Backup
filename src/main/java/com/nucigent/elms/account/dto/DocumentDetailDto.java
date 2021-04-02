package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class DocumentDetailDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer documentId;
	private Integer individualId;
	private String documentType;
	private String countryOfIssue;
	private String documentNumber;
	private String issueingAuthority;
	private String issueDate;
	private String expiaryDate;
	private String additionalInformation;
	private String documentIdentifier;
	private String documentSize;
	private String documentStorePath;
	private int valAccntId;
	private int selectedDocumentId;
		
	
	public Integer getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}
	public Integer getIndividualId() {
		return individualId;
	}
	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}
	public String getDocumentType() {
		return documentType;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public String getCountryOfIssue() {
		return countryOfIssue;
	}
	public void setCountryOfIssue(String countryOfIssue) {
		this.countryOfIssue = countryOfIssue;
	}
	public String getDocumentNumber() {
		return documentNumber;
	}
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	public String getIssueingAuthority() {
		return issueingAuthority;
	}
	public void setIssueingAuthority(String issueingAuthority) {
		this.issueingAuthority = issueingAuthority;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getExpiaryDate() {
		return expiaryDate;
	}
	public void setExpiaryDate(String expiaryDate) {
		this.expiaryDate = expiaryDate;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public String getDocumentIdentifier() {
		return documentIdentifier;
	}
	public void setDocumentIdentifier(String documentIdentifier) {
		this.documentIdentifier = documentIdentifier;
	}
	public String getDocumentSize() {
		return documentSize;
	}
	public void setDocumentSize(String documentSize) {
		this.documentSize = documentSize;
	}
	public int getValAccntId() {
		return valAccntId;
	}
	public void setValAccntId(int valAccntId) {
		this.valAccntId = valAccntId;
	}
	public String getDocumentStorePath() {
		return documentStorePath;
	}
	public void setDocumentStorePath(String documentStorePath) {
		this.documentStorePath = documentStorePath;
	}
	public int getSelectedDocumentId() {
		return selectedDocumentId;
	}
	public void setSelectedDocumentId(int selectedDocumentId) {
		this.selectedDocumentId = selectedDocumentId;
	}
		
}