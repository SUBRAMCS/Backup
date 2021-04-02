package com.nucigent.elms.account.dto;


public class MyAccountDto {
	
	private Integer accountProfileId;
	private Integer parentIndividualId;
	private FamilyMemberDto familyMembers;
	
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
	public FamilyMemberDto getFamilyMembers() {
		return familyMembers;
	}
	public void setFamilyMembers(FamilyMemberDto familyMembers) {
		this.familyMembers = familyMembers;
	}

}
