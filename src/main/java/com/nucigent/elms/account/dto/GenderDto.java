package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class GenderDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer GenderID;
	private String Gender;
	private String value;
	
	public Integer getGenderID() {
		return GenderID;
	}
	public void setGenderID(Integer genderID) {
		GenderID = genderID;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
