package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class CountrycodeDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer country_Id;
	private String country_name;
	private String value;
	private String search_flag;
	
	public Integer getCountry_Id() {
		return country_Id;
	}
	public void setCountry_Id(Integer country_Id) {
		this.country_Id = country_Id;
	}
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSearch_flag() {
		return search_flag;
	}
	public void setSearch_flag(String search_flag) {
		this.search_flag = search_flag;
	}
	
	

}
