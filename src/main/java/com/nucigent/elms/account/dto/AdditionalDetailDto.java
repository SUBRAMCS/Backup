package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class AdditionalDetailDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer individualId;
	private String additionalInformation;
	
	public Integer getIndividualId() {
		return individualId;
	}
	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	
	
}
