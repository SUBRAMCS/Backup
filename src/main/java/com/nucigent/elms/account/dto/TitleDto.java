package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class TitleDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer TitleID;
	private String Title;
	private String value;
	
	public Integer getTitleID() {
		return TitleID;
	}
	public void setTitleID(Integer titleID) {
		TitleID = titleID;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
