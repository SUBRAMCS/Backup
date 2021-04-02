package com.nucigent.elms.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.nucigent.elms.account.domain.CountrycodeMapper;
import com.nucigent.elms.account.domain.DocumentExtMapper;
import com.nucigent.elms.account.domain.GenderMapper;
import com.nucigent.elms.account.domain.RelationshipMapper;
import com.nucigent.elms.account.domain.TitleMapper;
import com.nucigent.elms.account.dto.CountrycodeDto;
import com.nucigent.elms.account.dto.CountrycodephoneDto;
import com.nucigent.elms.account.dto.CountrycodephoneMapper;
import com.nucigent.elms.account.dto.DocumentExtDto;
import com.nucigent.elms.account.dto.GenderDto;
import com.nucigent.elms.account.dto.ListObjectDto;
import com.nucigent.elms.account.dto.RelationshipDto;
import com.nucigent.elms.account.dto.TitleDto;
import com.nucigent.elms.account.helper.AccountQueryConstants;
import com.nucigent.elms.helper.BankAcntQueryConstant;


@Service
public class UtilityServiceImpl implements UtilityService{

	@Autowired private JdbcTemplate jdbcTemplate;
	
	

	@Override	
	public ListObjectDto getListObject() {
		
		ListObjectDto listObjectDto = new ListObjectDto();
		

		List<TitleDto> titleDto =  jdbcTemplate.query("select Title_ID,Title,Title_value from Ref_Title",new TitleMapper());
		listObjectDto.setTitle(titleDto);
		List<GenderDto> genderDto =  jdbcTemplate.query("select Gender_ID,Gender,Gender_value from Ref_Gender",new GenderMapper());
		listObjectDto.setGender(genderDto);
		List<RelationshipDto> relationshipDto =  jdbcTemplate.query("select Relationship_ID,Relationship,Relationship_value,Relationship_type from Ref_Relationship",new RelationshipMapper());
		listObjectDto.setRelationship(relationshipDto);
		List<CountrycodeDto> countrycode =  jdbcTemplate.query("select Country_Id,country_name,Country_Value value,search_flag from Ref_Address_country",new CountrycodeMapper());
		listObjectDto.setCountrycode(countrycode);
		List<CountrycodephoneDto> countrycodephone = jdbcTemplate.query("select country_name,country_code from ref_country_code where IsActive='Y'",new CountrycodephoneMapper());
		listObjectDto.setCountrycodephone(countrycodephone);
		List<DocumentExtDto> documentExt = jdbcTemplate.query("select Doc_Extension_Value,Max_Size_Allowed_In_Mb Max_Size_Allowed,IsActive from ref_doc_extention where IsActive='Y'",new DocumentExtMapper());
		listObjectDto.setDocumentExt(documentExt);
		return listObjectDto;
			
		}

		@Override
		public List<String> getBanksLookUp() {
	
			return jdbcTemplate.queryForList(BankAcntQueryConstant.SQL_SELECT_BANKS_LOOK_UP,String.class);
		}

	}
