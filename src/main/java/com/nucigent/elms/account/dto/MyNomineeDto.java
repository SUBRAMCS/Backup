package com.nucigent.elms.account.dto;

import java.util.List;

public class MyNomineeDto {
	
	private Integer accountProfileId;
	private Integer parentIndividualId;
	private List<NomineeMemberDto> nomineeMembers;
	
	public Integer getAccountProfileId() {
		return accountProfileId;
	}
	public void setAccountProfileId(Integer accountProfileId) {
		this.accountProfileId = accountProfileId;
	}
	public Integer getParentIndividualId() {
		return parentIndividualId;
	}
	public void setParentIndividualId(Integer parentIndividualId) {
		this.parentIndividualId = parentIndividualId;
	}
	public List<NomineeMemberDto> getNomineeMembers() {
		return nomineeMembers;
	}
	public void setNomineeMembers(List<NomineeMemberDto> nomineeMembers) {
		this.nomineeMembers = nomineeMembers;
	}
	

}
