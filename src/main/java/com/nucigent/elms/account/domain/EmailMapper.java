package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.EmailDto;


public class EmailMapper implements RowMapper<EmailDto> {
	
	public EmailDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		EmailDto emailDto = new EmailDto();
		emailDto.setEmailId(rs.getInt("Email_ID"));
		emailDto.setIndividualId(rs.getInt("Individual_ID"));
		emailDto.setEmailAddress(rs.getString("Email_Address"));
		emailDto.setPrimaryFlag(rs.getString("IsPrimary"));
		
		
		
		return emailDto;
		
		
		
	}

}
