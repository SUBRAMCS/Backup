package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.CountrycodeDto;



public class CountrycodeMapper implements RowMapper<CountrycodeDto> {
	
	public CountrycodeDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		CountrycodeDto countrycodeDto = new CountrycodeDto();
		countrycodeDto.setCountry_Id(rs.getInt("Country_Id"));
		countrycodeDto.setCountry_name(rs.getString("country_name"));
		countrycodeDto.setValue(rs.getString("value"));
		countrycodeDto.setSearch_flag(rs.getString("search_flag"));
		
		return countrycodeDto;
		
		
	}

}
