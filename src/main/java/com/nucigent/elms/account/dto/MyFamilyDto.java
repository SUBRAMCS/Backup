package com.nucigent.elms.account.dto;

import java.util.List;

public class MyFamilyDto {
	
	private Integer accountProfileId;
	private Integer parentIndividualId;
	private List<FamilyMemberDto> familyMembers;
	private List<AddressDetailDto> universalAddressDetail;

	
	public Integer getAccountProfileId() {
		return accountProfileId;
	}
	public void setAccountProfileId(Integer accountProfileId) {
		this.accountProfileId = accountProfileId;
	}
	public List<FamilyMemberDto> getFamilyMembers() {
		return familyMembers;
	}
	public void setFamilyMembers(List<FamilyMemberDto> familyMembers) {
		this.familyMembers = familyMembers;
	}
	public Integer getParentIndividualId() {
		return parentIndividualId;
	}
	public void setParentIndividualId(Integer parentIndividualId) {
		this.parentIndividualId = parentIndividualId;
	}
	public List<AddressDetailDto> getUniversalAddressDetail() {
		return universalAddressDetail;
	}
	public void setUniversalAddressDetail(List<AddressDetailDto> universalAddressDetail) {
		this.universalAddressDetail = universalAddressDetail;
	}
	


}
