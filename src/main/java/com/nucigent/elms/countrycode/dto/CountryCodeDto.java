package com.nucigent.elms.countrycode.dto;

public class CountryCodeDto {

	private Integer countryId;
	private String countryName;
	private Integer countryPhoneCode;
	private String topLevelDomain;

	public Integer getCountryId() {
		return countryId;
	}
	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public Integer getCountryPhoneCode() {
		return countryPhoneCode;
	}
	public void setCountryPhoneCode(Integer countryPhoneCode) {
		this.countryPhoneCode = countryPhoneCode;
	}
	public String getTopLevelDomain() {
		return topLevelDomain;
	}
	public void setTopLevelDomain(String topLevelDomain) {
		this.topLevelDomain = topLevelDomain;
	}
	
}
