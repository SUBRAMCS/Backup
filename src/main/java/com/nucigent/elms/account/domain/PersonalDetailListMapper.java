package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.PersonalDetailDto;



public class PersonalDetailListMapper implements RowMapper<PersonalDetailDto>{
	
	public PersonalDetailDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		PersonalDetailDto personalDetailDto = new PersonalDetailDto();
		personalDetailDto.setFirstName(rs.getString("First_Name"));
		personalDetailDto.setIndividualId(rs.getInt("Individual_ID"));
		personalDetailDto.setLastName(rs.getString("Last_Name"));
		personalDetailDto.setTitle(rs.getString("Title"));
		personalDetailDto.setDateOfBirth(rs.getString("DOB"));
		personalDetailDto.setMiddleName(rs.getString("Middle_Name"));
		personalDetailDto.setGender(rs.getString("Gender"));
		personalDetailDto.setRegisterId(rs.getString("Registration_ID"));
		personalDetailDto.setAdditional_info(rs.getString("additional_info"));
		return personalDetailDto;
		
	}

}
