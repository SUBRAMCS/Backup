package com.nucigent.elms.user.domain;

public class UserInfo {
	

	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String address;
	private String gender;
	private String dateOfBirth ;
	private String isActive ;
	private String motherMaidenName;
	
	private String recordCreatedDate;
	private String recordUpdatedDate;
	
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getRecordCreatedDate() {
		return recordCreatedDate;
	}
	public void setRecordCreatedDate(String recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}
	public String getRecordUpdatedDate() {
		return recordUpdatedDate;
	}
	public void setRecordUpdatedDate(String recordUpdatedDate) {
		this.recordUpdatedDate = recordUpdatedDate;
	}
	public String getMotherMaidenName() {
		return motherMaidenName;
	}
	public void setMotherMaidenName(String motherMaidenName) {
		this.motherMaidenName = motherMaidenName;
	}
	
}
