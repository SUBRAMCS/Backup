package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.AddressDetailDto;



public class AddressDetailMapper implements RowMapper<AddressDetailDto>{
	
	public AddressDetailDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		AddressDetailDto addressDetailDto = new AddressDetailDto();
		addressDetailDto.setAddressId(rs.getInt("Address_ID"));
		addressDetailDto.setIndividualId(rs.getInt("Individual_ID"));
		addressDetailDto.setAddressLine1(rs.getString("Address_Line1"));
		addressDetailDto.setAddressLine2(rs.getString("Address_Line2"));
		addressDetailDto.setAddressLine3(rs.getString("Address_Line3"));
		addressDetailDto.setAddressLine4(rs.getString("Address_Line4"));
		addressDetailDto.setCountry(rs.getString("Country"));
		addressDetailDto.setCountryState(rs.getString("County_State"));
		addressDetailDto.setPostCode(rs.getString("PostCode"));	
		//addressDetailDto.setAddressflag(rs.getInt("addressflag"));
		//addressDetailDto.setPrimaryFlag(rs.getString("primaryFlag"));
		return addressDetailDto;
		
	}

}
