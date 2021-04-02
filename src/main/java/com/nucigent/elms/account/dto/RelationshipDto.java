package com.nucigent.elms.account.dto;

import java.io.Serializable;

public class RelationshipDto implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private Integer RelationshipID;
	private String Relationship;
	private String value;
	private String Relationship_type;
	
	public Integer getRelationshipID() {
		return RelationshipID;
	}
	public void setRelationshipID(Integer relationshipID) {
		RelationshipID = relationshipID;
	}
	public String getRelationship() {
		return Relationship;
	}
	public void setRelationship(String relationship) {
		Relationship = relationship;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRelationship_type() {
		return Relationship_type;
	}
	public void setRelationship_type(String relationship_type) {
		Relationship_type = relationship_type;
	}

}
