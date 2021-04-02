package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class FamilyMemberMapper implements RowMapper<FamilyMember> {
	
	public FamilyMember mapRow(ResultSet rs,int rowNum) throws SQLException {
		FamilyMember familyMember = new FamilyMember();
		familyMember.setFamilyMemberId(rs.getInt("Family_Member_ID"));
		familyMember.setIndividualId(rs.getInt("Individual_ID"));	
		familyMember.setParentIndividualId(rs.getInt("parentIndividualId"));
		return familyMember;
		
	}

}
