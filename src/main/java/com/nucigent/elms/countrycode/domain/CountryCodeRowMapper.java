package com.nucigent.elms.countrycode.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CountryCodeRowMapper implements RowMapper<CountryCode> {

	@Override
	public CountryCode mapRow(ResultSet rs, int rowNum) throws SQLException {
		CountryCode countryCode = new CountryCode();
		countryCode.setCountryId(rs.getInt("country_id"));
		countryCode.setCountryName(rs.getString("country_name"));
		countryCode.setCountryPhoneCode(rs.getInt("country_ph_code"));
		countryCode.setTopLevelDomain(rs.getString("top_level_domain"));
		return countryCode;
	}
}
