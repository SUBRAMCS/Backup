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
import com.nucigent.elms.account.domain.DocumentDetailMapper;
import com.nucigent.elms.account.domain.EmailMapper;
import com.nucigent.elms.account.domain.FamilyMember;
import com.nucigent.elms.account.domain.FamilyMemberMapper;
import com.nucigent.elms.account.domain.PersonalDetailMapper;
import com.nucigent.elms.account.domain.PhoneMapper;
import com.nucigent.elms.account.dto.AddressDetailDto;
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.EmailDto;
import com.nucigent.elms.account.dto.FamilyMemberDto;
import com.nucigent.elms.account.dto.MyFamilyDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.dto.PhoneDto;
import com.nucigent.elms.account.fileupload.AwsAdapter;
import com.nucigent.elms.common.util.UserDetail;


@Service
public class MyNomineeServiceImpl implements MyNomineeService {
	
	@Autowired private JdbcTemplate jdbcTemplate;
	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired private AwsAdapter awsAdapter;
	public Integer parent_individual_id;
	@Autowired private UserDetail userDetail;
	

	
	@Value("${spring.datasource.driverClassName}")
	String driverclassname;
	
	@Value("${spring.datasource.url}")
	String datasource;
	
	@Value("${spring.datasource.username}")
	String username;
	
	@Value("${spring.datasource.password}")
	String password;
	
	
	@Override
	public MyFamilyDto getMyNomineeDetails() {
		List<FamilyMemberDto> familyMemberDtos = new ArrayList<>();
	   
		
		//List<FamilyMember> existingFamilyMembers =  jdbcTemplate.query("select family_member_id,family_member.individual_id,account_profile.Individual_ID parentIndividualId from family_member,account_profile where family_member.account_profile_id = ? and family_member.account_profile_id = account_profile.Account_Profile_ID",new FamilyMemberMapper(),1);
		List<FamilyMember> existingFamilyMembers =  jdbcTemplate.query("select family_member_id,family_member.individual_id,account_profile.Individual_ID parentIndividualId from family_member,account_profile " + 
		"  where Registration_ID = (select Registration_ID from Register_User  where User_login_ID = ?) and family_member.account_profile_id = account_profile.account_profile_id",new FamilyMemberMapper(),userDetail.getUserNameFromSecurityContect());
	
		existingFamilyMembers.forEach(t -> {
		parent_individual_id = t.getParentIndividualId();
		
			FamilyMemberDto familyMembers = new FamilyMemberDto();
			PersonalDetailDto personalDetailDto = new PersonalDetailDto();
	/*		if(t.getFamilyMemberId()==0) {
				personalDetailDto = jdbcTemplate.queryForObject("select Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,DOB, 'Self' relationship from Individual_Master where Individual_Master.Individual_ID = ?",new PersonalDetailMapper(),t.getIndividualId());
			}
			else
			{ */
			//	personalDetailDto = jdbcTemplate.queryForObject("select Individual_Master.Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,DOB,relationship,'N' primary_nominee,Individual_Master.additional_info,null nominee_member_id from Individual_Master,family_member where Individual_Master.Individual_ID = ? and Individual_Master.Individual_ID = family_member.Individual_ID",new PersonalDetailMapper(),t.getIndividualId());
				personalDetailDto = jdbcTemplate.queryForObject("select a.Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,DOB,a.relationship,primary_nominee,\r\n" + 
						"  a.additional_info,nominee_member_id from\r\n" + 
						"  (select Individual_Master.Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,DOB,relationship,\r\n" + 
						"  Individual_Master.additional_info from Individual_Master,family_member \r\n" + 
						"  where Individual_Master.Individual_ID =? and Individual_Master.Individual_ID = family_member.Individual_ID)a\r\n" + 
						"  left  join nominee_member \r\n" + 
						"  on nominee_member.Individual_ID = a.Individual_ID",new PersonalDetailMapper(),t.getIndividualId());
				
				//}
			familyMembers.setPersonalDetail(personalDetailDto);
			
			List<AddressDetailDto> addressDetail = jdbcTemplate.query("select Address_ID,Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country from address_master where Individual_ID = ?",new AddressDetailMapper(),t.getIndividualId());
			familyMembers.setAddressDetail(addressDetail);
			
			List<EmailDto> emailDetail = jdbcTemplate.query("select Email_ID,Individual_ID,Email_Address,IsPrimary from Email_Master where Individual_ID = ?",new EmailMapper(), t.getIndividualId());
			familyMembers.setEmailDetail(emailDetail);
			
			List<PhoneDto> phoneDetail = jdbcTemplate.query("select Phone_ID,Individual_ID,Country_Code,Phone_Number,isPrimary from Phone_Master where Individual_ID = ?", new PhoneMapper(),  t.getIndividualId());
	        familyMembers.setPhoneDetail(phoneDetail);
		
			
			List<DocumentDetailDto> documentDetail = jdbcTemplate.query("select Document_ID,Individual_ID,Document_Type,Country_of_Issue,Document_Number,Issueing_Authority,Issue_Date,Expiry_Date,Additional_Information,Document_Identifier,Document_Size from document_master where Individual_ID = ?",new DocumentDetailMapper(),t.getIndividualId());
			familyMembers.setDocumentDetail(documentDetail);
			
			familyMemberDtos.add(familyMembers);			
			
			
		});
		
		MyFamilyDto familyMembers = new MyFamilyDto();
		familyMembers.setAccountProfileId(1);
		familyMembers.setParentIndividualId(parent_individual_id);
		familyMembers.setFamilyMembers(familyMemberDtos);
		
		
		
		
		return familyMembers;
	}
	
	@Override
	public MyFamilyDto getNomineeMember() {
		
		List<FamilyMemberDto> familyMemberDtos = new ArrayList<>();
		
		List<FamilyMember> existingFamilyMembers =  jdbcTemplate.query("select nominee_member_id Family_Member_ID,nominee_member.individual_id,account_profile.Individual_ID parentIndividualId from nominee_member,account_profile " + 
				"  where Registration_ID = (select Registration_ID from Register_User  where User_login_ID = ?) and nominee_member.account_profile_id =  account_profile.Account_Profile_ID",new FamilyMemberMapper(),userDetail.getUserNameFromSecurityContect());
			
				existingFamilyMembers.forEach(t -> {
				parent_individual_id = t.getParentIndividualId();
				
				FamilyMemberDto familyMembers = new FamilyMemberDto();
				
												
				PersonalDetailDto personalDetail = new PersonalDetailDto();
				personalDetail = jdbcTemplate.queryForObject("select Individual_Master.Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,DOB,relationship, primary_nominee,nominee_member.additional_info,nominee_member_id from Individual_Master,nominee_member where Individual_Master.Individual_ID = ? and Individual_Master.Individual_ID = nominee_member.Individual_ID",new PersonalDetailMapper(),t.getIndividualId());
				familyMembers.setPersonalDetail(personalDetail);
			
				List<AddressDetailDto> addressDetail = jdbcTemplate.query("select Address_ID,Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country,addressflag from address_master where Address_ID in (select address_id from nominee_member where Individual_ID = ?)",new AddressDetailMapper(),t.getIndividualId());
				familyMembers.setAddressDetail(addressDetail);
				
				List<EmailDto> emailDetail = jdbcTemplate.query("select Email_ID,Individual_ID,Email_Address,IsPrimary from Email_Master where Email_ID in (select email_id from nominee_member where Individual_ID = ?)",new EmailMapper(), t.getIndividualId());
				familyMembers.setEmailDetail(emailDetail);
				
				List<PhoneDto> phoneDetail = jdbcTemplate.query("select Phone_ID,Individual_ID,Country_Code,Phone_Number,isPrimary from Phone_Master where Phone_ID in (select phone_id from nominee_member where Individual_ID = ?)", new PhoneMapper(),  t.getIndividualId());
				familyMembers.setPhoneDetail(phoneDetail);
				
				List<DocumentDetailDto> documentDetail = jdbcTemplate.query("select Document_ID,Individual_ID,Document_Type,Country_of_Issue,Document_Number,Issueing_Authority,Issue_Date,Expiry_Date,Additional_Information,Document_Identifier,Document_Size from document_master where Document_ID in  (select document_id from nominee_member where Individual_ID = ?)",new DocumentDetailMapper(),t.getIndividualId());
				familyMembers.setDocumentDetail(documentDetail);
				
				familyMemberDtos.add(familyMembers);				
				
				});
				
				MyFamilyDto nomineeMembers = new MyFamilyDto();
				nomineeMembers.setAccountProfileId(1);
				nomineeMembers.setParentIndividualId(parent_individual_id);
				nomineeMembers.setFamilyMembers(familyMemberDtos);
				
				return nomineeMembers;
				
		
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

			namedParameterJdbcTemplate.update("insert into Individual_Master(Title, First_Name, Middle_Name, Last_Name, DOB, Gender, Mother_Maiden_Name, Registration_ID) select :Title, :First_Name, :Middle_Name, :Last_Name, :DOB, :Gender, :Mother_Maiden_Name, Registration_ID from Register_User where User_login_ID = '"+userDetail.getUserNameFromSecurityContect()+"'", parameters, holder);
			
			Integer individualId = holder.getKey().intValue();
			
			holder = new GeneratedKeyHolder();
			parameters = new MapSqlParameterSource()
					.addValue("individual_id", individualId)
					.addValue("relationship", personalDetailDto.getRelationship())
					.addValue("primary_nominee", personalDetailDto.getPrimaryFlag());
	
	
	
			
			if(personalDetailDto.getPrimaryFlag().equals("Y")) {
				jdbcTemplate.update("update nominee_member set primary_nominee = 'N' where account_profile_id in (select account_profile_id from account_profile where Registration_ID = (select Registration_ID from Register_User where User_login_ID = '"+userDetail.getUserNameFromSecurityContect()+"'))");
			}
			
			
			
			namedParameterJdbcTemplate.update("insert into nominee_member(account_profile_id,individual_id,relationship,primary_nominee,address_id,email_id,phone_ID) select account_profile_id,:individual_id,:relationship,:primary_nominee,(select top(1) address_id from address_master where Individual_ID = :individual_id order by Address_ID),(select top(1) email_id from Email_Master where Individual_ID = :individual_id order by Email_ID), (select top(1) phone_id from Phone_Master where Individual_ID = :individual_id order by phone_id) from account_profile where Registration_ID = (select Registration_ID from Register_User where User_login_ID = '"+userDetail.getUserNameFromSecurityContect()+"')"
					, parameters, holder);
			
			
			return individualId;
		}
		else
		{
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());
	            
			jdbcTemplate.update("update individual_master set title=?, First_Name=?, Middle_Name=?, Last_Name=?,  DOB=?, gender=?, Record_Updated_Date=? where individual_id=?", personalDetailDto.getTitle(), personalDetailDto.getFirstName(),
					personalDetailDto.getMiddleName(), personalDetailDto.getLastName(), personalDetailDto.getDateOfBirth(), 
					personalDetailDto.getGender(), updatetedDate, personalDetailDto.getIndividualId());
			
			if(personalDetailDto.getPrimaryFlag().equals("Y")) {
				jdbcTemplate.update("update nominee_member set primary_nominee = 'N' where account_profile_id in (select account_profile_id from account_profile where Registration_ID = (select Registration_ID from Register_User where User_login_ID = '"+userDetail.getUserNameFromSecurityContect()+"'))");
			}
			
			if(personalDetailDto.getNomineeMemberId()==0) {
			KeyHolder holder = new GeneratedKeyHolder();
			holder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("individual_id", personalDetailDto.getIndividualId())
					.addValue("relationship", personalDetailDto.getRelationship())
					.addValue("primary_nominee", personalDetailDto.getPrimaryFlag());
			
			namedParameterJdbcTemplate.update("insert into nominee_member(account_profile_id,individual_id,relationship,primary_nominee,address_id,email_id,phone_ID) select account_profile_id,:individual_id,:relationship,:primary_nominee,(select top(1) address_id from address_master where Individual_ID = :individual_id order by Address_ID),(select top(1) email_id from Email_Master where Individual_ID = :individual_id order by Email_ID), (select top(1) phone_id from Phone_Master where Individual_ID = :individual_id order by phone_id) from account_profile where Registration_ID = (select Registration_ID from Register_User where User_login_ID = '"+userDetail.getUserNameFromSecurityContect()+"')"
					, parameters, holder);		
			}
			else
			{
				jdbcTemplate.update("update nominee_member set relationship = ?,primary_nominee = ? where nominee_member_id=?",personalDetailDto.getRelationship(),personalDetailDto.getPrimaryFlag(),personalDetailDto.getNomineeMemberId());
			}
			
			return personalDetailDto.getIndividualId();
		}
		
		
		
		
	}

	@Override
	public Integer saveorupdateEmailDetails(EmailDto emailDto) {
		
		if(emailDto.getEmailId()==null) {
			
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("IndividualId", emailDto.getIndividualId())
					.addValue("Email_Address", emailDto.getEmailAddress());				

			namedParameterJdbcTemplate.update("insert into Email_master(Individual_ID, Email_Address, IsPrimary) values (:IndividualId,:Email_Address,'Y')", parameters, holder);
			
			Integer Email_Id = holder.getKey().intValue();
			
			jdbcTemplate.update("update nominee_member set email_id = ? where individual_id = ?", Email_Id, emailDto.getIndividualId());
			
			return Email_Id;			
			
		}
		else
		{
			jdbcTemplate.update("update Email_master set Email_Address = ? where Email_ID = ?", emailDto.getEmailAddress(), emailDto.getEmailId());
			
			jdbcTemplate.update("update nominee_member set email_id = ? where individual_id = ?", emailDto.getEmailId(), emailDto.getIndividualId());
			return emailDto.getEmailId();
		}
		
	}
	
	@Override
	public Integer saveorupdatePhoneDetails(PhoneDto phoneDto) {
		
		if(phoneDto.getPhoneId()==null) {
			
			KeyHolder holder = new GeneratedKeyHolder();
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue("IndividualId", phoneDto.getIndividualId())
					.addValue("Country_code", phoneDto.getCountryCode())
					.addValue("PhoneNo", phoneDto.getPhoneNumber());

			namedParameterJdbcTemplate.update("insert into Phone_Master(Individual_ID, Country_Code,Phone_Number, isPrimary) values (:IndividualId,:Country_code,:PhoneNo,'Y')", parameters, holder);
			
			Integer Phone_Id = holder.getKey().intValue();
			
			jdbcTemplate.update("update nominee_member set phone_ID = ? where individual_id = ?", Phone_Id, phoneDto.getIndividualId());
			
			return Phone_Id;			
			
		}
		else
		{
			jdbcTemplate.update("update Phone_Master set Country_Code = ?,Phone_Number = ? where Phone_ID = ?",phoneDto.getCountryCode(),phoneDto.getPhoneNumber(), phoneDto.getPhoneId());
						
			jdbcTemplate.update("update nominee_member set phone_ID = ? where individual_id = ?", phoneDto.getPhoneId(), phoneDto.getIndividualId());
			return phoneDto.getPhoneId();		
		}
		
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
			
		namedParameterJdbcTemplate.update("insert into address_master(Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country,addressflag) values (:individualId,:addressLine1,:addressLine2,:addressLine3,:addressLine4,:countryState,:postCode,:country,:addressflag)", parameters, holder);
		
		Integer addressId = holder.getKey().intValue();
		
		jdbcTemplate.update("update nominee_member set address_id = ? where individual_id = ?", addressId, addressDetailDto.getIndividualId());
		
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
			
			jdbcTemplate.update("update nominee_member set address_id = ? where individual_id = ?", addressDetailDto.getAddressId(), addressDetailDto.getIndividualId());
			return addressDetailDto.getAddressId();

		}
		
		
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
					.addValue("expiaryDate", documentDetailDto.getExpiaryDate())
					.addValue("additionalInformation", documentDetailDto.getAdditionalInformation())
					.addValue("fileName", fileLs.get(0).getOriginalFilename())
					.addValue("fileContent",null)
			        .addValue("fileSize",fileLs.get(0).getSize());
			
			namedParameterJdbcTemplate.update("insert into document_master (Individual_ID, Document_Type, Country_of_Issue, Document_Number, Issueing_Authority, Issue_Date, Expiry_Date, Additional_Information,Document_Identifier,Document_Content,Document_Size) values "
			 +  "(:IndividualId,:documentType,:countryOfIssue,:documentNumber,:issueingAuthority,:issueDate,:expiaryDate,:additionalInformation,:fileName,:fileContent,:fileSize)", parameters, holder);
		
			 documentId =  holder.getKey().intValue();	
			 
			 jdbcTemplate.update("update nominee_member set document_id = ? where individual_id = ?", documentId, documentDetailDto.getIndividualId());
			 
			 
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
				 jdbcTemplate.update("update nominee_member set document_id = ? where individual_id = ?", documentDetailDto.getDocumentId(), documentDetailDto.getIndividualId());
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
}
