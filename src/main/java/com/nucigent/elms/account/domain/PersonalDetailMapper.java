package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.PersonalDetailDto;



public class PersonalDetailMapper implements RowMapper<PersonalDetailDto>{
	
	public PersonalDetailDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		PersonalDetailDto personalDetailDto = new PersonalDetailDto();
		personalDetailDto.setFirstName(rs.getString("First_Name"));
		personalDetailDto.setIndividualId(rs.getInt("Individual_ID"));
		personalDetailDto.setSelFamMemberId(personalDetailDto.getIndividualId());
		personalDetailDto.setLastName(rs.getString("Last_Name"));
		personalDetailDto.setTitle(rs.getString("Title"));
		personalDetailDto.setDateOfBirth(rs.getString("DOB"));
		personalDetailDto.setMiddleName(rs.getString("Middle_Name"));
		personalDetailDto.setGender(rs.getString("Gender"));
		personalDetailDto.setRelationship(rs.getString("relationship"));
		personalDetailDto.setPrimaryFlag(rs.getString("primary_nominee"));
		personalDetailDto.setAdditional_info(rs.getString("additional_info"));
		personalDetailDto.setNomineeMemberId(rs.getInt("nominee_member_id"));
		return personalDetailDto;
		
	}

}
