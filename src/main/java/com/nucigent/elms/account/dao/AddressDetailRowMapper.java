package com.nucigent.elms.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.domain.AddressDetail;

public class AddressDetailRowMapper implements RowMapper<AddressDetail> {

	@Override
	public AddressDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		AddressDetail addressDetail = new AddressDetail();
		addressDetail.setAddressId(rs.getInt("Address_ID"));
		addressDetail.setIndividualId(rs.getInt("Individual_ID"));
		addressDetail.setAddressLine1(rs.getString("Address_Line1"));
		addressDetail.setAddressLine2(rs.getString("Address_Line2"));
		addressDetail.setAddressLine3(rs.getString("Address_Line3"));
		addressDetail.setAddressLine4(rs.getString("Address_Line4"));
		addressDetail.setCountryState(rs.getString("County_State"));
		addressDetail.setPostCode(rs.getString("PostCode"));
		addressDetail.setCountry(rs.getString("Country"));
		return addressDetail;
	}

}
