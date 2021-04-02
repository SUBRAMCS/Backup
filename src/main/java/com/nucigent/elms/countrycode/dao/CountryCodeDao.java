package com.nucigent.elms.countrycode.dao;

import java.util.List;

import com.nucigent.elms.countrycode.domain.CountryCode;

public interface CountryCodeDao {

	List<CountryCode> getAllCountryCodes();
	CountryCode getCountryPhoneCodeById(Integer countryId);
	List<CountryCode> getCountryIdByCountryCodes(List<String> countryCodes);
	List<CountryCode> getCountryIdByCountryPhoneCodeAndCountry(Integer countryPhCode, String capital);
	List<CountryCode> getCountryIdByCountryId(List<String> countryCodes);

}
