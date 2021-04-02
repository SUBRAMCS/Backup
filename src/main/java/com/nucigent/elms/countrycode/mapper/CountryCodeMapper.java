package com.nucigent.elms.countrycode.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.nucigent.elms.countrycode.domain.CountryCode;
import com.nucigent.elms.countrycode.dto.CountryCodeDto;

@Component
public class CountryCodeMapper {

	public List<CountryCodeDto> mapToCountryCodeDtos(List<CountryCode> countryCodes) {
		List<CountryCodeDto> countryCodeDtos = new ArrayList<>();
		countryCodes.forEach(t -> {
			CountryCodeDto countryCodeDto = new CountryCodeDto();
			BeanUtils.copyProperties(t, countryCodeDto);
			countryCodeDtos.add(countryCodeDto);
		});
		return countryCodeDtos;
	}

	public CountryCodeDto mapToCountryCodeDto(CountryCode countryCodes) {
		CountryCodeDto countryCodeDto = new CountryCodeDto();
		BeanUtils.copyProperties(countryCodes, countryCodeDto);
		return countryCodeDto;
	}

}
