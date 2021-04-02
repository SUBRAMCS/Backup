package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.GenderDto;


public class GenderMapper implements RowMapper<GenderDto> {
	
public GenderDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
	    GenderDto genderDto = new GenderDto();
	    genderDto.setGenderID(rs.getInt("Gender_ID"));
	    genderDto.setGender(rs.getString("Gender"));
	    genderDto.setValue(rs.getString("Gender_value"));
	    
		return genderDto;
	}

}


