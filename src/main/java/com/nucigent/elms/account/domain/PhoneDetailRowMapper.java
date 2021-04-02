package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PhoneDetailRowMapper implements RowMapper<PhoneDetail> {

	@Override
	public PhoneDetail mapRow(ResultSet rs, int arg1) throws SQLException {
		PhoneDetail phoneDetail = new PhoneDetail();
		phoneDetail.setPhoneId(rs.getInt("Phone_ID"));
		phoneDetail.setIndividualId(rs.getInt("Individual_ID"));
		phoneDetail.setCountryId(rs.getInt("Country_Id"));
		phoneDetail.setCountryCode(rs.getInt("Country_ph_code"));
		phoneDetail.setPhoneNumber(rs.getString("Phone_Number"));
		phoneDetail.setPrimaryPhoneFlag(rs.getString("isPrimary"));
		return phoneDetail;
	}



}
