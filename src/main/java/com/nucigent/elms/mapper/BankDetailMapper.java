package com.nucigent.elms.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.BankDtlsDto;



public class BankDetailMapper implements RowMapper<BankDtlsDto>{
	
	public BankDtlsDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		BankDtlsDto bankDtlsDto = new BankDtlsDto();
		bankDtlsDto.setValAccountId(rs.getInt("VA_Account_ID"));
		bankDtlsDto.setBankName(rs.getString("Name"));
		bankDtlsDto.setFirstName(rs.getString("First_Name"));
		bankDtlsDto.setMiddleName(rs.getString("Middle_Name"));
		bankDtlsDto.setLastName(rs.getString("Last_Name"));
		bankDtlsDto.setAccntNo(rs.getString("Account_Number"));
		return bankDtlsDto;
		
	}

}
