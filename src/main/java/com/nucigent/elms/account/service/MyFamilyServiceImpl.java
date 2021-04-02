package com.nucigent.elms.account.service;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nucigent.elms.account.domain.AddressDetailMapper;
import com.nucigent.elms.account.domain.ContactDetailMapper;
import com.nucigent.elms.account.domain.DocumentDetailMapper;
import com.nucigent.elms.account.domain.EmailMapper;
import com.nucigent.elms.account.domain.FamilyMember;
import com.nucigent.elms.account.domain.FamilyMemberMapper;
import com.nucigent.elms.account.domain.PersonalDetailMapper;
import com.nucigent.elms.account.domain.PhoneMapper;
import com.nucigent.elms.account.dto.AddressDetailDto;
import com.nucigent.elms.account.dto.ContactDetailDto;
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.EmailDto;
import com.nucigent.elms.account.dto.FamilyMemberDto;
import com.nucigent.elms.account.dto.FamilyPersonalDto;
import com.nucigent.elms.account.dto.MyFamilyDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.dto.PhoneDto;
import com.nucigent.elms.account.fileupload.AwsAdapter;
import com.nucigent.elms.common.util.UserDetail;
import com.nucigent.elms.dao.MyFamilyDao;

@Service
public class MyFamilyServiceImpl implements MyFamilyService {

	@Autowired private JdbcTemplate jdbcTemplate;
	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired private AwsAdapter awsAdapter;
	public Integer parent_individual_id;
	@Autowired private UserDetail userDetail;
	@Autowired MyFamilyDao myFamilyDao;
	
	
	@Value("${spring.datasource.driverClassName}")
	String driverclassname;
	
	@Value("${spring.datasource.url}")
	String datasource;
	
	@Value("${spring.datasource.username}")
	String username;
	
	@Value("${spring.datasource.password}")
	String password;
	
	
	
	@Override
	public MyFamilyDto getMyFamilyDetails() {

		
		List<FamilyMemberDto> familyMemberDtos = new ArrayList<>();
	   
		
		//List<FamilyMember> existingFamilyMembers =  jdbcTemplate.query("select family_member_id,family_member.individual_id,account_profile.Individual_ID parentIndividualId from family_member,account_profile where family_member.account_profile_id = ? and family_member.account_profile_id = account_profile.Account_Profile_ID",new FamilyMemberMapper(),1);
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
			
			List<DocumentDetailDto> documentDetail = jdbcTemplate.query("select Document_ID,Individual_ID,Document_Type,Country_of_Issue,Document_Number,Issueing_Authority,Issue_Date,Expiry_Date,Additional_Information,Document_Identifier,Document_Size from document_master where Individual_ID = ?",new DocumentDetailMapper(),t.getIndividualId());
			familyMembers.setDocumentDetail(documentDetail);
			
			familyMemberDtos.add(familyMembers);			
			
			
		});
		
		List<AddressDetailDto> universalAddressDetail = jdbcTemplate.query("select distinct null Address_ID,null Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country, Address_Entry_Type_Flag addressflag, 0 primaryFlag " + 
				" from address_master where (Individual_ID in (select Individual_ID from family_member where account_profile_id in " + 
				" (select account_profile.account_profile_id from  account_profile, Register_User where " + 
				" account_profile.Registration_ID = Register_User.Registration_ID and Register_User.User_login_ID= ?)))" + 
				" or (Individual_ID in (select Individual_ID from account_profile,Register_User where account_profile.Registration_ID = Register_User.Registration_ID " + 
				" and Register_User.User_login_ID= ?))",new AddressDetailMapper(),userDetail.getUserNameFromSecurityContect(),userDetail.getUserNameFromSecurityContect());
			
		MyFamilyDto familyMembers = new MyFamilyDto();
		familyMembers.setAccountProfileId(1);
		familyMembers.setParentIndividualId(parent_individual_id);
		familyMembers.setFamilyMembers(familyMemberDtos);	
		familyMembers.setUniversalAddressDetail(universalAddressDetail);
		
		return familyMembers;
	}

	@Override
	public FamilyPersonalDto getFamilyPersonalDetail() {
		FamilyPersonalDto familyPersonalDto = new FamilyPersonalDto();
		PersonalDetailDto personalDetailDto = jdbcTemplate.queryForObject("select Individual_Master.Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,Individual_Master.DOB,'' relationship  from Individual_Master,Register_User,account_profile where User_login_ID = ? and Individual_Master.Registration_ID = account_profile.Registration_ID and Individual_Master.Individual_ID = account_profile.Individual_ID",new PersonalDetailMapper(),userDetail.getUserNameFromSecurityContect());
		List<AddressDetailDto> addressDetail = jdbcTemplate.query("select Address_ID,Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country from address_master where Individual_ID = ?",new AddressDetailMapper(),personalDetailDto.getIndividualId());
		List<ContactDetailDto> contactDetail = jdbcTemplate.query("select Contact_ID,Individual_ID,Email_Address_Primary,Country_Code_Primary,Phone_Number_Primary,Email_Address_Secondary,Country_Code_Secondary,Phone_Number_Secondary from Contact_Master where Individual_ID = ?",new ContactDetailMapper(),personalDetailDto.getIndividualId());
		familyPersonalDto.setIndividualId(personalDetailDto.getIndividualId());
		familyPersonalDto.setPersonalDetail(personalDetailDto);
		familyPersonalDto.setAddressDetail(addressDetail);
		familyPersonalDto.setContactDetail(contactDetail);
		
		return familyPersonalDto;
		
	}

	@Override
	public Integer saveorupdatePersonalDetails(PersonalDetailDto personalDetailDto) {

		if(personalDetailDto.getIndividualId()==null) {
			KeyHolder holder = new GeneratedKeyHolder();

			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("Title", personalDetailDto.getTitle())
					.addValue("First_Name", personalDetailDto.getFirstName())
					.addValue("Middle_Name", personalDetailDto.getMiddleName())
					.addValue("Last_Name", personalDetailDto.getLastName())
					.addValue("DOB",  personalDetailDto.getDateOfBirth())
					.addValue("Gender", personalDetailDto.getGender())
					.addValue("Mother_Maiden_Name", "")
					.addValue("Registration_ID", 1);

			namedParameterJdbcTemplate.update("insert into Individual_Master(Title, First_Name, Middle_Name, Last_Name, DOB, Gender, Mother_Maiden_Name, Registration_ID) select :Title, :First_Name, :Middle_Name, :Last_Name, :DOB, :Gender, :Mother_Maiden_Name, Registration_ID from Register_User where User_login_ID = '" + userDetail.getUserNameFromSecurityContect() + "'", parameters, holder);
			
			Integer individualId = holder.getKey().intValue();
			
			holder = new GeneratedKeyHolder();
			parameters = new MapSqlParameterSource()
					.addValue("individual_id", individualId)
					.addValue("relationship", personalDetailDto.getRelationship());
			
			namedParameterJdbcTemplate.update("insert into family_member(account_profile_id,individual_id,relationship) select account_profile_id,:individual_id,:relationship from account_profile where Registration_ID = (select Registration_ID from Register_User where User_login_ID = '"+userDetail.getUserNameFromSecurityContect()+"')"
					, parameters, holder);
					
		//	namedParameterJdbcTemplate.update("insert into nominee_member(account_profile_id,individual_id,relationship) select account_profile_id,:individual_id,:relationship from account_profile where Registration_ID = (select Registration_ID from Register_User where User_login_ID = 'mehar.vaibhav@gmail.com')", parameters, holder);
			
			
			return individualId;
		}
		else
		{
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());
	            
			jdbcTemplate.update("update individual_master set title=?, First_Name=?, Middle_Name=?, Last_Name=?,  DOB=?, gender=?, Record_Updated_Date=? where individual_id=?", personalDetailDto.getTitle(), personalDetailDto.getFirstName(),
					personalDetailDto.getMiddleName(), personalDetailDto.getLastName(), personalDetailDto.getDateOfBirth(), 
					personalDetailDto.getGender(), updatetedDate, personalDetailDto.getIndividualId());
			
			jdbcTemplate.update("update family_member set relationship = ? where individual_id = ?",personalDetailDto.getRelationship(),personalDetailDto.getIndividualId());
			
			jdbcTemplate.update("update nominee_member set relationship = ? where individual_id = ?",personalDetailDto.getRelationship(),personalDetailDto.getIndividualId());
			

		}
		
		
		return personalDetailDto.getIndividualId();
	}

	@Override
	public Integer saveorupdateAddressDetails(AddressDetailDto addressDetailDto) {
		
		if(addressDetailDto.getAddressId()==null) {
			
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("addressLine1", addressDetailDto.getAddressLine1())
					.addValue("addressLine2", addressDetailDto.getAddressLine2())
					.addValue("addressLine3", addressDetailDto.getAddressLine3())
					.addValue("addressLine4", addressDetailDto.getAddressLine4())
					.addValue("countryState", addressDetailDto.getCountryState())
					.addValue("postCode", addressDetailDto.getPostCode())
					.addValue("country", addressDetailDto.getCountry())
					.addValue("individualId", addressDetailDto.getIndividualId())
					.addValue("addressflag", addressDetailDto.getAddressflag());		
			
		namedParameterJdbcTemplate.update("insert into address_master(Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country,Address_Entry_Type_Flag,Primary_Address_Flag) values (:individualId,:addressLine1,:addressLine2,:addressLine3,:addressLine4,:countryState,:postCode,:country,:addressflag,0)", parameters, holder);
		
		Integer addressId = holder.getKey().intValue();
		
		return addressId;
		
		}
		else
		{
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());

			jdbcTemplate.update("update address_master set Address_Line1=?, Address_Line2=?, Address_Line3=?, Address_Line4=?,  County_State=?, PostCode=?, Country=?, Record_Updated_Date=? where Address_ID=?", 
					addressDetailDto.getAddressLine1(), addressDetailDto.getAddressLine2(),
					addressDetailDto.getAddressLine3(), addressDetailDto.getAddressLine4(), addressDetailDto.getCountryState(), 
					addressDetailDto.getPostCode(),addressDetailDto.getCountry(), updatetedDate, addressDetailDto.getAddressId());

		}
		
		return addressDetailDto.getAddressId();
	}

	@Override
	public Integer saveorupdateEmailDetails(EmailDto emailDto) {
		
	if(emailDto.getEmailId()==null) {
			
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("IndividualId", emailDto.getIndividualId())
					.addValue("EmailAddress", emailDto.getEmailAddress())
					.addValue("PrimaryFlag", emailDto.getPrimaryFlag());
			
			if(emailDto.getPrimaryFlag().equals("Y")) {
				jdbcTemplate.update("update Email_Master set IsPrimary = 'N' where Individual_ID = ?",emailDto.getIndividualId());
			}
							
			namedParameterJdbcTemplate.update("insert into Email_Master (Individual_ID,Email_Address,IsPrimary,IsActive) "		
			+ "select :IndividualId,:EmailAddress,:PrimaryFlag,case Mother_Maiden_Name when '' then 'N' else 'Y' END from INDIVIDUAL_MASTER where Individual_ID = :IndividualId ", parameters, holder);
			
		
		Integer emailId = holder.getKey().intValue();	
		
		return emailId;
		
		}
		else
		{
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());
	        
			if(emailDto.getPrimaryFlag().equals("Y")) {
				jdbcTemplate.update("update Email_Master set IsPrimary = 'N' where Individual_ID = ?",emailDto.getIndividualId());
			}
			
			jdbcTemplate.update("update Email_Master set Email_Address = ?,IsPrimary = ?,Record_Updated_Date = ? where Email_ID = ?", 
					emailDto.getEmailAddress(),emailDto.getPrimaryFlag(),updatetedDate,emailDto.getEmailId());

		}
		
		return emailDto.getEmailId();
		
	}

	@Override
	public Integer saveorupdatePhoneDetails(PhoneDto phoneDto) {
		
	if(phoneDto.getPhoneId()==null) {
			
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("IndividualId", phoneDto.getIndividualId())
					.addValue("countryCode", phoneDto.getCountryCode())
					.addValue("phoneNumber", phoneDto.getPhoneNumber())
					.addValue("PrimaryFlag", phoneDto.getPrimaryFlag());
			
			if(phoneDto.getPrimaryFlag().equals("Y")) {
				jdbcTemplate.update("update Phone_Master set isPrimary = 'N' where Individual_ID = ?",phoneDto.getIndividualId());
			}
							
		namedParameterJdbcTemplate.update("insert into Phone_Master (Individual_ID,Country_Code,Phone_Number,isPrimary,IsActive) "
				+ "select :IndividualId,:countryCode,:phoneNumber,:PrimaryFlag,case Mother_Maiden_Name when '' then 'N' else 'Y' END from INDIVIDUAL_MASTER where Individual_ID = :IndividualId ", parameters, holder);
			
		Integer phoneId = holder.getKey().intValue();	
		
		return phoneId;
		
		}
		else
		{
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());
	        
			if(phoneDto.getPrimaryFlag().equals("Y")) {
				jdbcTemplate.update("update Phone_Master set isPrimary = 'N' where Individual_ID = ?",phoneDto.getIndividualId());
			}
			
			jdbcTemplate.update("update Phone_Master set Country_Code = ?,Phone_Number = ?,isPrimary = ?, Record_Updated_Date = ? where Phone_ID = ?", 
					phoneDto.getCountryCode(),phoneDto.getPhoneNumber(),phoneDto.getPrimaryFlag(),updatetedDate,phoneDto.getPhoneId());

		}
		
		return phoneDto.getPhoneId();
		
	}

	@Override
	public Integer saveorupdateDocumentDetails(DocumentDetailDto documentDetailDto,List<MultipartFile> fileLs) {
			
		Integer documentId = null;

		
		try {	
		
		if(documentDetailDto.getDocumentId()==null) {
			KeyHolder holder = new GeneratedKeyHolder();
			String  fileName = null;
			
			
				 fileName = fileLs.get(0).getOriginalFilename();
			
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("IndividualId", documentDetailDto.getIndividualId())
					.addValue("documentType", documentDetailDto.getDocumentType())
					.addValue("countryOfIssue", documentDetailDto.getCountryOfIssue())
					.addValue("documentNumber", documentDetailDto.getDocumentNumber())
					.addValue("issueingAuthority", documentDetailDto.getIssueingAuthority())
					.addValue("issueDate", documentDetailDto.getIssueDate())
					.addValue("expiaryDate",documentDetailDto.getExpiaryDate())
					.addValue("additionalInformation", documentDetailDto.getAdditionalInformation())
					.addValue("fileName", fileLs.get(0).getOriginalFilename())
					.addValue("fileContent",null)
			        .addValue("fileSize",fileLs.get(0).getSize());
			
			namedParameterJdbcTemplate.update("insert into document_master (Individual_ID, Document_Type, Country_of_Issue, Document_Number, Issueing_Authority, Issue_Date, Expiry_Date, Additional_Information,Document_Identifier,Document_Content,Document_Size) values "
			 +  "(:IndividualId,:documentType,:countryOfIssue,:documentNumber,:issueingAuthority,:issueDate,:expiaryDate,:additionalInformation,:fileName,:fileContent,:fileSize)", parameters, holder);
		
			 documentId =  holder.getKey().intValue();	
			 
			 
			 URL url = awsAdapter.storeObjectInS3(fileLs.get(0), fileLs.get(0).getOriginalFilename(), fileLs.get(0).getContentType());
			 System.out.println(url);
			
		}
		else
		{
		
			
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());

			if(fileLs.size()==0) {
			
				jdbcTemplate.update("update document_master set Document_Type = ?,Country_of_Issue = ?,Document_Number = ?,Issueing_Authority = ?,Issue_Date = ?,Expiry_Date = ?,Additional_Information = ?,Record_Updated_Date = ? where Document_ID = ?", 
						documentDetailDto.getDocumentType(),documentDetailDto.getCountryOfIssue(),
						documentDetailDto.getDocumentNumber(),documentDetailDto.getIssueingAuthority(),
						documentDetailDto.getIssueDate(),documentDetailDto.getExpiaryDate(),
						documentDetailDto.getAdditionalInformation(),updatetedDate,documentDetailDto.getDocumentId());
				
				documentId =  documentDetailDto.getDocumentId();
			}
			else
			{
			
				String keys = null;
				try{  
					Class.forName(driverclassname);  
					Connection con=DriverManager.getConnection(datasource,username,password);   
					
					Statement stmt=con.createStatement();  
					ResultSet rs=stmt.executeQuery("select Document_Identifier from document_master where Document_ID = " + documentDetailDto.getDocumentId());  
					while(rs.next())  
					{
				      	keys = rs.getString("Document_Identifier"); 
					}
					con.close(); 
					awsAdapter.deleteObject(keys);
				}
				catch(Exception e) {
					
				}
				
					
				jdbcTemplate.update("update document_master set Document_Type = ?,Country_of_Issue = ?,Document_Number = ?,Issueing_Authority = ?,Issue_Date = ?,Expiry_Date = ?,Additional_Information = ?,Record_Updated_Date = ?,Document_Identifier = ?,Document_Size = ? where Document_ID = ?", 
						documentDetailDto.getDocumentType(),documentDetailDto.getCountryOfIssue(),
						documentDetailDto.getDocumentNumber(),documentDetailDto.getIssueingAuthority(),
						documentDetailDto.getIssueDate(),documentDetailDto.getExpiaryDate(),
						documentDetailDto.getAdditionalInformation(),updatetedDate,fileLs.get(0).getOriginalFilename(),
						fileLs.get(0).getSize(),documentDetailDto.getDocumentId());
				
				documentId =  documentDetailDto.getDocumentId();
				URL url = awsAdapter.storeObjectInS3(fileLs.get(0), fileLs.get(0).getOriginalFilename(), fileLs.get(0).getContentType());
				System.out.println(url);
				
				
				
			}
			
			
			
		}
		}
		catch(Exception e) {
			System.out.println(e.toString());
		}
		
		
		return documentId;
		
	}

	@Override
		public List<FamilyMemberDto> getFamMemberList() {
			// TODO Auto-generated method stub
			return myFamilyDao.getFamMemberList();
		}
}
