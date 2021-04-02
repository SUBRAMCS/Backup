package com.nucigent.elms.account.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class CountrycodephoneMapper implements RowMapper<CountrycodephoneDto> {
	
	public CountrycodephoneDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		CountrycodephoneDto countrycodephone = new CountrycodephoneDto();
		countrycodephone.setCountry_name(rs.getString("country_name"));
		countrycodephone.setCountry_code(rs.getString("country_code"));	
		return countrycodephone;
	}

}
