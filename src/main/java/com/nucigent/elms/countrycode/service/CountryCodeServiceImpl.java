package com.nucigent.elms.countrycode.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.nucigent.elms.countrycode.dao.CountryCodeDao;
import com.nucigent.elms.countrycode.domain.CountryCode;
import com.nucigent.elms.countrycode.dto.CountryCodeDto;
import com.nucigent.elms.countrycode.mapper.CountryCodeMapper;

@Service
public class CountryCodeServiceImpl implements CountryCodeService {

	@Autowired private CountryCodeDao countryCodeDao;
	@Autowired private CountryCodeMapper countryCodeMapper;

	@Override
	@Transactional
	public List<CountryCodeDto> getAllCountryCodes() {
		List<CountryCode> countryCodes = countryCodeDao.getAllCountryCodes();
		return countryCodeMapper.mapToCountryCodeDtos(countryCodes);
	}

	@Override
	@Transactional
	public CountryCodeDto getCountryPhoneCodeById(Integer countryId) {
		CountryCode countryCode = countryCodeDao.getCountryPhoneCodeById(countryId);
		return countryCodeMapper.mapToCountryCodeDto(countryCode);
	}

	@Override
	public CountryCodeDto getCountryIdByCountryPhoneCodeAndCountry(Integer countryPhoneCode, String capital) {
		List<CountryCode> countryCodes = countryCodeDao.getCountryIdByCountryPhoneCodeAndCountry(countryPhoneCode, capital);
		if(countryCodes != null && countryCodes.size()> 0 ) {
			return countryCodeMapper.mapToCountryCodeDto(countryCodes.get(0));
		} else {
			return null;
		}
	}


}
