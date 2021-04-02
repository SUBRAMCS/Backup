package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.PhoneDto;




public class PhoneMapper implements RowMapper<PhoneDto>{
	
	public PhoneDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		PhoneDto phoneDto =  new PhoneDto();
		phoneDto.setPhoneId(rs.getInt("Phone_ID"));
		phoneDto.setIndividualId(rs.getInt("Individual_ID"));
		phoneDto.setCountryCode(rs.getString("Country_Code"));
		phoneDto.setPhoneNumber(rs.getString("Phone_Number"));
		phoneDto.setPrimaryFlag(rs.getString("isPrimary"));
				
		return phoneDto;
		
		
		
	}

}
