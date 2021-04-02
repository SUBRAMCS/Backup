package com.nucigent.elms.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nucigent.elms.account.domain.AddressDetailMapper;
import com.nucigent.elms.account.domain.DocumentDetailMapper;
import com.nucigent.elms.account.domain.EmailMapper;
import com.nucigent.elms.account.domain.FamilyMember;
import com.nucigent.elms.account.domain.FamilyMemberMapper;
import com.nucigent.elms.account.domain.PersonalDetailListMapper;
import com.nucigent.elms.account.domain.PersonalDetailMapper;
import com.nucigent.elms.account.domain.PhoneMapper;
import com.nucigent.elms.account.dto.AddressDetailDto;
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.EmailDto;
import com.nucigent.elms.account.dto.FamilyMemberDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.dto.PhoneDto;
import com.nucigent.elms.account.helper.MyFamilyQueryConstant;
import com.nucigent.elms.common.util.UserDetail;
import com.nucigent.elms.dao.MyFamilyDao;

@Repository
public class MyFamilyDaoImpl implements MyFamilyDao {

	Logger logger = LoggerFactory.getLogger(UserBankAcntDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired private UserDetail userDetail;
	
	@Value("${spring.datasource.driverClassName}")
	String driverclassname;
	
	@Value("${spring.datasource.url}")
	String datasource;
	
	@Value("${spring.datasource.username}")
	String username;
	
	@Value("${spring.datasource.password}")
	String password;
	
	int parent_individual_id = 0;
	
	@Override
	public List<FamilyMemberDto> getFamMemberList() {
		
		List<FamilyMemberDto> famMemberList = new ArrayList<FamilyMemberDto>();
		//List<FamilyMember> existingFamilyMembers =  jdbcTemplate.query(MyFamilyQueryConstant.SQL_SELECT_FAMILY_LIST,new FamilyMemberMapper(),userDetail.getUserNameFromSecurityContect());
		List<FamilyMember> existingFamilyMembers =  jdbcTemplate.query("select 0 family_member_id, Individual_ID,account_profile.Individual_ID parentIndividualId from account_profile where Registration_ID = (select Registration_ID from Register_User  where User_login_ID = ?)" + 
				"  union " + 
				"  select family_member_id,family_member.individual_id,account_profile.Individual_ID parentIndividualId from family_member,account_profile " + 
				"  where Registration_ID = (select Registration_ID from Register_User  where User_login_ID = ?) and family_member.account_profile_id =  account_profile.Account_Profile_ID",new FamilyMemberMapper(),userDetail.getUserNameFromSecurityContect(),userDetail.getUserNameFromSecurityContect());		
		existingFamilyMembers.forEach(t -> {
				parent_individual_id = t.getParentIndividualId();
					
					FamilyMemberDto familyMembers = new FamilyMemberDto();
					PersonalDetailDto personalDetailDto = new PersonalDetailDto();
					if(t.getFamilyMemberId()==0) {
						personalDetailDto = jdbcTemplate.queryForObject("select Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,DOB, 'Self' relationship,'N' primary_nominee,Individual_Master.additional_info,null nominee_member_id from Individual_Master where Individual_Master.Individual_ID = ?",new PersonalDetailMapper(),t.getIndividualId());
					}
					else
					{
						personalDetailDto = jdbcTemplate.queryForObject("select Individual_Master.Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,DOB,relationship, 'N' primary_nominee,Individual_Master.additional_info,null nominee_member_id from Individual_Master,family_member where Individual_Master.Individual_ID = ? and Individual_Master.Individual_ID = family_member.Individual_ID",new PersonalDetailMapper(),t.getIndividualId());
					}
					familyMembers.setPersonalDetail(personalDetailDto);
					
					List<AddressDetailDto> addressDetail = jdbcTemplate.query("select Address_ID,Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country,Address_Entry_Type_Flag addressflag, Primary_Address_Flag primaryFlag from address_master where Individual_ID = ? order by Primary_Address_Flag desc",new AddressDetailMapper(),t.getIndividualId());
					familyMembers.setAddressDetail(addressDetail);		
					
					List<EmailDto> emailDetail = jdbcTemplate.query("select Email_ID,Individual_ID,Email_Address,IsPrimary from Email_Master where Individual_ID = ? order by IsPrimary desc",new EmailMapper(), t.getIndividualId());
					familyMembers.setEmailDetail(emailDetail);
					
					List<PhoneDto> phoneDetail = jdbcTemplate.query("select Phone_ID,Individual_ID,Country_Code,Phone_Number,isPrimary from Phone_Master where Individual_ID = ? order by isPrimary desc", new PhoneMapper(),  t.getIndividualId());
			        familyMembers.setPhoneDetail(phoneDetail);
					
					List<DocumentDetailDto> documentDetail = jdbcTemplate.query("select * from document_master where Individual_ID = ?",new DocumentDetailMapper(),t.getIndividualId());
					familyMembers.setDocumentDetail(documentDetail);
					
					famMemberList.add(familyMembers);			
				});
		
		return famMemberList;
	}

}