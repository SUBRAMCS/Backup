package com.nucigent.elms.account.dto;

import java.util.List;

public class BankDtlsDto {
	
	private int valAccountId = 0;
	private String bankName;
	private int valItemId;
	private int addressId;
	private int individualId;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String addressLine4;
	private String countryState;
	private String country;
	private String selectCountry;
	private String countryId;
	private String postCode;
	private int addressflag;
	private String addressEntryTypeFlag;
	private String emailAddressOne;
	private String emailAddressTwo;
	private String countryCodePrim;
	private String countryCodeSec;
	private String phoneNumPrim;
	private String phoneNumSec;
	private String websiteUrl;
	private String addInfoBank;
	private boolean isActive;
		
	private BankAccountDto bankAccountDto;
	private PersonalDetailDto personalDetail;
	private List<DocumentDetailDto> documentDetail;
	
	private String firstName;
	private String middleName;
	private String lastName;
	private String accntNo;	
	
	public int getValAccountId() {
		return valAccountId;
	}
	public void setValAccountId(int valAccountId) {
		this.valAccountId = valAccountId;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public int getValItemId() {
		return valItemId;
	}
	public void setValItemId(int valItemId) {
		this.valItemId = valItemId;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public int getIndividualId() {
		return individualId;
	}
	public void setIndividualId(int individualId) {
		this.individualId = individualId;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public String getAddressLine4() {
		return addressLine4;
	}
	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}
	public String getCountryState() {
		return countryState;
	}
	public void setCountryState(String countryState) {
		this.countryState = countryState;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public int getAddressflag() {
		return addressflag;
	}
	public void setAddressflag(int addressflag) {
		this.addressflag = addressflag;
	}
	public String getAddressEntryTypeFlag() {
		return addressEntryTypeFlag;
	}
	public void setAddressEntryTypeFlag(String addressEntryTypeFlag) {
		this.addressEntryTypeFlag = addressEntryTypeFlag;
	}
	public String getEmailAddressOne() {
		return emailAddressOne;
	}
	public void setEmailAddressOne(String emailAddressOne) {
		this.emailAddressOne = emailAddressOne;
	}
	public String getEmailAddressTwo() {
		return emailAddressTwo;
	}
	public void setEmailAddressTwo(String emailAddressTwo) {
		this.emailAddressTwo = emailAddressTwo;
	}
	public String getCountryCodePrim() {
		return countryCodePrim;
	}
	public void setCountryCodePrim(String countryCodePrim) {
		this.countryCodePrim = countryCodePrim;
	}
	public String getCountryCodeSec() {
		return countryCodeSec;
	}
	public void setCountryCodeSec(String countryCodeSec) {
		this.countryCodeSec = countryCodeSec;
	}
	public String getPhoneNumPrim() {
		return phoneNumPrim;
	}
	public void setPhoneNumPrim(String phoneNumPrim) {
		this.phoneNumPrim = phoneNumPrim;
	}
	public String getPhoneNumSec() {
		return phoneNumSec;
	}
	public void setPhoneNumSec(String phoneNumSec) {
		this.phoneNumSec = phoneNumSec;
	}
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	
	public String getAddInfoBank() {
		return addInfoBank;
	}
	public void setAddInfoBank(String addInfoBank) {
		this.addInfoBank = addInfoBank;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public BankAccountDto getBankAccountDto() {
		return bankAccountDto;
	}
	public void setBankAccountDto(BankAccountDto bankAccountDto) {
		this.bankAccountDto = bankAccountDto;
	}
	public PersonalDetailDto getPersonalDetail() {
		return personalDetail;
	}
	public void setPersonalDetail(PersonalDetailDto personalDetail) {
		this.personalDetail = personalDetail;
	}
	public List<DocumentDetailDto> getDocumentDetail() {
		return documentDetail;
	}
	public void setDocumentDetail(List<DocumentDetailDto> documentDetail) {
		this.documentDetail = documentDetail;
	}
	public String getSelectCountry() {
		return selectCountry;
	}
	public void setSelectCountry(String selectCountry) {
		this.selectCountry = selectCountry;
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
	public String getAccntNo() {
		return accntNo;
	}
	public void setAccntNo(String accntNo) {
		this.accntNo = accntNo;
	}
	
}
