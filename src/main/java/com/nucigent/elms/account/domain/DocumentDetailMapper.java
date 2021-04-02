package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.DocumentDetailDto;



public class DocumentDetailMapper implements RowMapper<DocumentDetailDto> {
	
	public DocumentDetailDto  mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		DocumentDetailDto documentDetailDto = new DocumentDetailDto();
		documentDetailDto.setDocumentId(rs.getInt("Document_ID"));
		documentDetailDto.setIndividualId(rs.getInt("Individual_ID"));
		documentDetailDto.setDocumentType(rs.getString("Document_Type"));
		documentDetailDto.setCountryOfIssue(rs.getString("Country_of_Issue"));
		documentDetailDto.setDocumentNumber(rs.getString("Document_Number"));
		documentDetailDto.setIssueingAuthority(rs.getString("Issueing_Authority"));
		documentDetailDto.setIssueDate(rs.getString("Issue_Date"));
		documentDetailDto.setExpiaryDate(rs.getString("Expiry_Date"));
		documentDetailDto.setAdditionalInformation(rs.getString("Additional_Information"));
		documentDetailDto.setDocumentIdentifier(rs.getString("Document_Identifier"));
		documentDetailDto.setDocumentSize(rs.getString("Document_Size"));
		documentDetailDto.setSelectedDocumentId(documentDetailDto.getDocumentId());
		return documentDetailDto;
	}

}
