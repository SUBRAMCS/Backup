package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class EmailRowMapper implements RowMapper<EmailDetail> {

	@Override
	public EmailDetail mapRow(ResultSet rs, int arg1) throws SQLException {
		EmailDetail emailDetail = new EmailDetail();
		emailDetail.setEmailId(rs.getInt("Email_ID"));
		emailDetail.setIndividualId(rs.getInt("Individual_ID"));
		emailDetail.setEmailAddress(rs.getString("Email_Address"));
		emailDetail.setPrimaryEmailFlag(rs.getString("IsPrimary"));
		return emailDetail;
	}

}
