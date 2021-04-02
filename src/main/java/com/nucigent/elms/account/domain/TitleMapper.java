package com.nucigent.elms.account.domain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.TitleDto;




public class TitleMapper implements RowMapper<TitleDto>{
	
	public TitleDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		
		TitleDto titleDto = new TitleDto();
		titleDto.setTitleID(rs.getInt("Title_ID"));
		titleDto.setTitle(rs.getString("Title"));
		titleDto.setValue(rs.getString("Title_value"));
		
		return titleDto;
	}
	
	

}
