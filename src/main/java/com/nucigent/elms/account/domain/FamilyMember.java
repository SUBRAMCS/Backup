package com.nucigent.elms.account.domain;

public class FamilyMember {


	private Integer FamilyMemberId;
	private Integer IndividualId;
	private Integer ParentIndividualId;
	
	public Integer getFamilyMemberId() {
		return FamilyMemberId;
	}
	public void setFamilyMemberId(Integer familyMemberId) {
		FamilyMemberId = familyMemberId;
	}
	public Integer getIndividualId() {
		return IndividualId;
	}
	public void setIndividualId(Integer individualId) {
		IndividualId = individualId;
	}
	public Integer getParentIndividualId() {
		return ParentIndividualId;
	}
	public void setParentIndividualId(Integer parentIndividualId) {
		ParentIndividualId = parentIndividualId;
	}
	
}
