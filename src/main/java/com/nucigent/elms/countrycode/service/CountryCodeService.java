package com.nucigent.elms.countrycode.service;

import java.util.List;

import com.nucigent.elms.countrycode.dto.CountryCodeDto;

public interface CountryCodeService {

	List<CountryCodeDto> getAllCountryCodes();
	CountryCodeDto getCountryPhoneCodeById(Integer countryId);
	CountryCodeDto getCountryIdByCountryPhoneCodeAndCountry(Integer countryPhoneCode, String capital);

}
