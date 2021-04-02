package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.DocumentExtDto;


public class DocumentExtMapper implements RowMapper<DocumentExtDto> {
	
public DocumentExtDto  mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		DocumentExtDto documentExtDto  = new DocumentExtDto();
		documentExtDto.setDoc_Extension_Value(rs.getString("Doc_Extension_Value"));
		documentExtDto.setMax_Size_Allowed(rs.getString("Max_Size_Allowed"));
		documentExtDto.setIsActive(rs.getString("IsActive"));	
		
		return documentExtDto;
	}

}
