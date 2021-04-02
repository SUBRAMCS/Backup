package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.RelationshipDto;


public class RelationshipMapper implements RowMapper<RelationshipDto> {
	
	
public RelationshipDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
	RelationshipDto relationshipDto = new RelationshipDto();
	relationshipDto.setRelationshipID(rs.getInt("Relationship_ID"));
	relationshipDto.setRelationship(rs.getString("Relationship"));
	relationshipDto.setValue(rs.getString("Relationship_value"));
	relationshipDto.setRelationship_type(rs.getString("Relationship_type"));
	  
	 return relationshipDto;
	}

}
