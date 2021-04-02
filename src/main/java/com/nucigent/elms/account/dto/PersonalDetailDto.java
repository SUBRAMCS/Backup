package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class PersonalDetailDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer individualId;
	private Integer nomineeMemberId;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String dateOfBirth;
	private String relationship;
	private String primaryFlag;
	private String additional_info;
	private String registerId;
	private int addressId;
	private int emailId;
	private int phoneId;
	private int selectedEmailId;
	private int selectedPhoneId;
	private String valAccntId;
	private int vaAccntExtsnId;
	private boolean active;
	private String additionalInformation;
	private int selFamMemberId;
	
	
	public String getRegisterId() {
		return registerId;
	}
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	public Integer getIndividualId() {
		return individualId;
	}
	public void setIndividualId(Integer individualId) {
		this.individualId = individualId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public String getPrimaryFlag() {
		return primaryFlag;
	}
	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}
	public String getAdditional_info() {
		return additional_info;
	}
	public void setAdditional_info(String additional_info) {
		this.additional_info = additional_info;
	}
	public Integer getNomineeMemberId() {
		return nomineeMemberId;
	}
	public void setNomineeMemberId(Integer nomineeMemberId) {
		this.nomineeMemberId = nomineeMemberId;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	
	public String getValAccntId() {
		return valAccntId;
	}
	public void setValAccntId(String valAccntId) {
		this.valAccntId = valAccntId;
	}
	public int getVaAccntExtsnId() {
		return vaAccntExtsnId;
	}
	public void setVaAccntExtsnId(int vaAccntExtsnId) {
		this.vaAccntExtsnId = vaAccntExtsnId;
	}
	public int getEmailId() {
		return emailId;
	}
	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}
	public int getPhoneId() {
		return phoneId;
	}
	public void setPhoneId(int phoneId) {
		this.phoneId = phoneId;
	}
	public int getSelectedEmailId() {
		return selectedEmailId;
	}
	public void setSelectedEmailId(int selectedEmailId) {
		this.selectedEmailId = selectedEmailId;
	}
	public int getSelectedPhoneId() {
		return selectedPhoneId;
	}
	public void setSelectedPhoneId(int selectedPhoneId) {
		this.selectedPhoneId = selectedPhoneId;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getAdditionalInformation() {
		return additionalInformation;
	}
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	public int getSelFamMemberId() {
		return selFamMemberId;
	}
	public void setSelFamMemberId(int selFamMemberId) {
		this.selFamMemberId = selFamMemberId;
	}
		
}
