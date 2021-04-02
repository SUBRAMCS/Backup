package com.nucigent.elms.account.helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.domain.NomineeMember;


public class NomineeMemberMapper implements RowMapper<NomineeMember> {

	@Override
	public NomineeMember mapRow(ResultSet rs, int rowNum) throws SQLException {
		NomineeMember nomineeMember = new NomineeMember();
		nomineeMember.setNomineeMemberId(rs.getInt("Nominee_Member_ID"));
		nomineeMember.setAccountProfileId(rs.getInt("Account_Profile_ID"));
		nomineeMember.setIndividualId(rs.getInt("Individual_ID"));
		nomineeMember.setAddressId(rs.getInt("Address_ID"));
		nomineeMember.setDocumentId(rs.getInt("Document_ID"));
		nomineeMember.setRelationship(rs.getString("Relationship"));
		nomineeMember.setAdditionalInfo(rs.getString("Additional_Info"));
		nomineeMember.setPrimaryFlag(rs.getString("Primary_Nominee"));
		return nomineeMember;
	}

}
