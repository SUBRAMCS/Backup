package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.ContactDetailDto;



public class ContactDetailMapper implements RowMapper<ContactDetailDto> {
	
	public ContactDetailDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		ContactDetailDto contactDetailDto = new ContactDetailDto();
		contactDetailDto.setContactId(rs.getInt("Contact_ID"));
		contactDetailDto.setIndividualId(rs.getInt("Individual_ID"));
		contactDetailDto.setPrimaryEmailAddress(rs.getString("Email_Address_Primary"));
		contactDetailDto.setPrimaryCountryCode(rs.getString("Country_Code_Primary"));
		contactDetailDto.setPrimaryPhoneNumber(rs.getString("Phone_Number_Primary"));
		contactDetailDto.setSecondaryEmailAddress(rs.getString("Email_Address_Secondary"));
		contactDetailDto.setSecondaryCountryCode(rs.getString("Country_Code_Secondary"));
		contactDetailDto.setSecondaryPhoneNumber(rs.getString("Phone_Number_Secondary"));
				
		return contactDetailDto;
		
	}
	
}
