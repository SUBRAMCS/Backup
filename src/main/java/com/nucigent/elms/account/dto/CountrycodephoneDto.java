package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class CountrycodephoneDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String country_name;
	private String country_code;
	
	public String getCountry_name() {
		return country_name;
	}
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}
	public String getCountry_code() {
		return country_code;
	}
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	
	

}
