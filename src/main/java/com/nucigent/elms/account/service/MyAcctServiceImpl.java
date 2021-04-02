package com.nucigent.elms.account.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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

import com.nucigent.elms.account.domain.AddressDetailMapper;
import com.nucigent.elms.account.domain.EmailMapper;
import com.nucigent.elms.account.domain.PersonalDetailMapper;
import com.nucigent.elms.account.domain.PhoneMapper;
import com.nucigent.elms.account.dto.AddressDetailDto;
import com.nucigent.elms.account.dto.EmailDto;
import com.nucigent.elms.account.dto.FamilyMemberDto;
import com.nucigent.elms.account.dto.MyAccountDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.dto.PhoneDto;
import com.nucigent.elms.common.util.UserDetail;

@Service
public class MyAcctServiceImpl implements MyAcctService  {

	@Autowired 
	private JdbcTemplate jdbcTemplate;
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
	
	@Override
	public MyAccountDto getFamilyPersonalDetail() {
		String UserId = userDetail.getUserNameFromSecurityContect();
		MyAccountDto myAccountDto = new MyAccountDto();
		

		FamilyMemberDto familyMemberDto = new FamilyMemberDto();
		
		PersonalDetailDto personalDetail = jdbcTemplate.queryForObject("select Individual_Master.Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,Individual_Master.DOB, 'Self' relationship,'N' primary_nominee,Individual_Master.additional_info,null nominee_member_id from Individual_Master,Register_User,account_profile where User_login_ID = ? and account_profile.Registration_ID = Register_User.Registration_ID " + 
				"and account_profile.Individual_ID = Individual_Master.Individual_ID",new PersonalDetailMapper(),UserId);
		List<AddressDetailDto> addressDetail = jdbcTemplate.query("  select account_profile.Address_ID,account_profile.Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country,Address_Entry_Type_Flag addressflag, Primary_Address_Flag primaryFlag" + 
				"  from address_master, account_profile, Register_User where Register_User.User_login_ID = ? and " + 
				"  Register_User.Registration_ID = account_profile.Registration_ID and account_profile.Address_ID = address_master.Address_ID",new AddressDetailMapper(),UserId);
		List<EmailDto> emailDetail = jdbcTemplate.query("select Email_Master.Email_ID,Individual_Master.Individual_ID,User_login_ID Email_Address,IsPrimary from Individual_Master,Register_User,account_profile,Email_Master where User_login_ID = ? and   account_profile.Registration_ID = Register_User.Registration_ID and Email_Master.Email_Address = User_login_ID   and account_profile.Individual_ID = Individual_Master.Individual_ID",new EmailMapper(), UserId);
		List<PhoneDto> phoneDetail = jdbcTemplate.query("select Phone_Master.Phone_ID,Individual_Master.Individual_ID,Phone_Master.Country_Code,Phone_Master.Phone_Number,Phone_Master.IsPrimary from Individual_Master, " + 
				"Register_User,account_profile,Phone_Master where User_login_ID = ? and account_profile.Registration_ID = Register_User.Registration_ID " + 
				"and Phone_Master.Country_Code = Register_User.Country_Code and Phone_Master.Phone_Number = Register_User.Mobile_Number and account_profile.Individual_ID = Individual_Master.Individual_ID ", new PhoneMapper(),  UserId);
 		familyMemberDto.setPersonalDetail(personalDetail);
		familyMemberDto.setAddressDetail(addressDetail);
		familyMemberDto.setEmailDetail(emailDetail);
		familyMemberDto.setPhoneDetail(phoneDetail);
		
		myAccountDto.setFamilyMembers(familyMemberDto);
		
		return myAccountDto;
	}
	
	@Override
	public Integer saveorupdatePersonalDetails(PersonalDetailDto personalDetailDto) {
		    String UserId = userDetail.getUserNameFromSecurityContect();
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());
	            
			jdbcTemplate.update("update individual_master set title=?, First_Name=?, Middle_Name=?, Last_Name=?,  DOB=?, gender=?, Record_Updated_Date=? where individual_id=?", personalDetailDto.getTitle(), personalDetailDto.getFirstName(),
					personalDetailDto.getMiddleName(), personalDetailDto.getLastName(), personalDetailDto.getDateOfBirth(), 
					personalDetailDto.getGender(), updatetedDate, personalDetailDto.getIndividualId());
		
			jdbcTemplate.update("update register_user set DOB=? where User_Login_ID = ? ",personalDetailDto.getDateOfBirth(), UserId);	
			
			return personalDetailDto.getIndividualId();
	}

	@Override
	public Integer saveorupdateAddressDetails(AddressDetailDto addressDetailDto) {
		Integer addressId = null;
		Integer cnt = 0;
			try{  
				Class.forName(driverclassname);  
				Connection con=DriverManager.getConnection(datasource,username,password);   
				
				Statement stmt=con.createStatement();  
				System.out.println("select address_id,count(1) cnt from address_master where Individual_ID = '"+addressDetailDto.getIndividualId()+"'"
						+ " and Address_Line1 = '" + addressDetailDto.getAddressLine1() + "'"
						+ " and Address_Line2 = '" + addressDetailDto.getAddressLine2() + "'"
						+ " and Address_Line3 = '" + addressDetailDto.getAddressLine3() + "'"
						+ " and Address_Line4 = '" + addressDetailDto.getAddressLine4() + "'" 
						+ " and County_State = '" + addressDetailDto.getCountryState() + "'" 
						+ " and PostCode = '" + addressDetailDto.getPostCode() + "'" 
						+ " and Country = '" + addressDetailDto.getCountry() + "' group by address_id");
				ResultSet rs=stmt.executeQuery("select address_id,count(1) cnt from address_master where Individual_ID = '"+addressDetailDto.getIndividualId()+"'"
						+ " and Address_Line1 = '" + addressDetailDto.getAddressLine1() + "'"
						+ " and Address_Line2 = '" + addressDetailDto.getAddressLine2() + "'"
						+ " and Address_Line3 = '" + addressDetailDto.getAddressLine3() + "'"
						+ " and Address_Line4 = '" + addressDetailDto.getAddressLine4() + "'" 
						+ " and County_State = '" + addressDetailDto.getCountryState() + "'" 
						+ " and PostCode = '" + addressDetailDto.getPostCode() + "'" 
						+ " and Country = '" + addressDetailDto.getCountry() + "' group by address_id");
					
				while(rs.next())  
				{
			      	cnt = rs.getInt("cnt"); 
			      	addressId = rs.getInt("address_id");
				}
				
				
				con.close();
			} catch(Exception e) {}		
			
		if(addressDetailDto.getAddressId()==null || cnt==0) {			
			if(cnt==0){				
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
			
		namedParameterJdbcTemplate.update("insert into address_master(Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country,Address_Entry_Type_Flag,Primary_Address_Flag) values (:individualId,:addressLine1,:addressLine2,:addressLine3,:addressLine4,:countryState,:postCode,:country,:addressflag,1)", parameters, holder);
		
		addressId = holder.getKey().intValue();
		
			}
		jdbcTemplate.update("update address_master set Primary_Address_Flag = 0 where Individual_ID=?",addressDetailDto.getIndividualId());	
		jdbcTemplate.update("update address_master set Primary_Address_Flag = 1 where Address_ID=?",addressId);			
		jdbcTemplate.update("update account_profile set Address_ID = ? where Individual_ID = ?",addressId,addressDetailDto.getIndividualId());
		
		return addressId;
			}
		
		else
		{
			Calendar calendar = Calendar.getInstance();
			java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());
            
			System.out.println(addressId);
			
			jdbcTemplate.update("update address_master set Primary_Address_Flag = 0 where Individual_ID=?",addressDetailDto.getIndividualId());	
			jdbcTemplate.update("update address_master set Primary_Address_Flag = 1 where Address_ID=?",addressId);			
			jdbcTemplate.update("update account_profile set Address_ID = ? where Individual_ID = ?",addressId,addressDetailDto.getIndividualId());
			jdbcTemplate.update("update address_master set Address_Line1=?, Address_Line2=?, Address_Line3=?, Address_Line4=?,  County_State=?, PostCode=?, Country=?, Record_Updated_Date=?,Primary_Address_Flag = 1 where Address_ID=? and Primary_Address_Flag = 1 and Address_ID = ? ", 
					addressDetailDto.getAddressLine1(), addressDetailDto.getAddressLine2(), 
					addressDetailDto.getAddressLine3(), addressDetailDto.getAddressLine4(), addressDetailDto.getCountryState(), 
					addressDetailDto.getPostCode(),addressDetailDto.getCountry(), updatetedDate, addressDetailDto.getAddressId(), addressId);
			
			

		}
			
		return addressDetailDto.getAddressId();
		 
		
	}

	@Override
	public Integer saveorupdateEmailDetails(EmailDto emailDto) {
		Integer emailId = null;
		Integer cnt = 0;
		try{  

			Class.forName(driverclassname);  
			Connection con=DriverManager.getConnection(datasource,username,password);   
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select email_id,count(1) cnt from email_master where Individual_ID = '"+emailDto.getIndividualId()+"'"
					+ " and Email_Address = '" + emailDto.getEmailAddress() + "' group by email_id");
				
			while(rs.next())  
			{
		      	cnt = rs.getInt("cnt"); 
		      	emailId = rs.getInt("email_id");
			}
			
			
			con.close();
		} catch(Exception e) {}	
		
		jdbcTemplate.update("update Register_User set User_login_ID = ? where User_login_ID in (select Email_Address from Email_Master where Email_ID = ?)", 
				emailDto.getEmailAddress(),emailDto.getEmailId());
		
		if(cnt == 0 ) {
			jdbcTemplate.update("update Email_Master set isPrimary = 'N' where Individual_ID = ?", 
					emailDto.getIndividualId());
			
			jdbcTemplate.update("update Email_Master set Email_Address = ?, isPrimary = 'Y' where Email_ID = ?", 
					emailDto.getEmailAddress(),emailDto.getEmailId());
		}
		
	
		
		return emailDto.getEmailId();
		
	}

	@Override
	public Integer saveorupdatePhoneDetails(PhoneDto phoneDto) {
		Integer phoneId = null;
		Integer cnt = 0;
		try{  

			Class.forName(driverclassname);  
			Connection con=DriverManager.getConnection(datasource,username,password);   
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select Phone_ID,count(1) cnt from phone_master where Individual_ID = '"+ phoneDto.getIndividualId()+"'"
					+ " and Country_Code = '" + phoneDto.getCountryCode() + "' and Phone_Number = '"+ phoneDto.getPhoneNumber()  + "' group by Phone_ID");
				
			while(rs.next())  
			{
		      	cnt = rs.getInt("cnt"); 
		      	phoneId = rs.getInt("Phone_ID");
			}
			
			
			con.close();
		} catch(Exception e) {}	
		
		jdbcTemplate.update("update Register_User set Country_Code = ?, Mobile_Number = ? where Country_Code+'-'+Mobile_Number in (select Country_Code+'-'+Phone_Number from phone_master where Phone_ID = ?)", 
				phoneDto.getCountryCode(),phoneDto.getPhoneNumber(),phoneDto.getPhoneId());
		
		if(cnt == 0 ) {
			
			jdbcTemplate.update("update phone_master set isPrimary = 'N' where Individual_ID = ?", 
					phoneDto.getIndividualId());
		
			jdbcTemplate.update("update phone_master set Country_Code = ?, Phone_Number = ?, isPrimary = 'Y' where Phone_ID = ?", 
					phoneDto.getCountryCode(),phoneDto.getPhoneNumber(),phoneDto.getPhoneId());
		}
		
	
		
		return phoneDto.getPhoneId();
	}

	@Override
	public MyAccountDto getBankAccntDetail() {
		
		String UserId = userDetail.getUserNameFromSecurityContect();
		MyAccountDto myAccountDto = new MyAccountDto();
		

		FamilyMemberDto familyMemberDto = new FamilyMemberDto();
		
		PersonalDetailDto personalDetail = jdbcTemplate.queryForObject("select Individual_Master.Individual_ID,title,First_Name,Middle_Name,Last_Name,gender,Individual_Master.DOB, 'Self' relationship,'N' primary_nominee,Individual_Master.additional_info,null nominee_member_id from Individual_Master,Register_User,account_profile where User_login_ID = ? and account_profile.Registration_ID = Register_User.Registration_ID " + 
				"and account_profile.Individual_ID = Individual_Master.Individual_ID",new PersonalDetailMapper(),UserId);
		List<AddressDetailDto> addressDetail = jdbcTemplate.query("  select account_profile.Address_ID,account_profile.Individual_ID,Address_Line1,Address_Line2,Address_Line3,Address_Line4,County_State,PostCode,Country,Address_Entry_Type_Flag addressflag, Primary_Address_Flag primaryFlag" + 
				"  from address_master, account_profile, Register_User where Register_User.User_login_ID = ? and " + 
				"  Register_User.Registration_ID = account_profile.Registration_ID and account_profile.Address_ID = address_master.Address_ID",new AddressDetailMapper(),UserId);
		List<EmailDto> emailDetail = jdbcTemplate.query("select Email_Master.Email_ID,Individual_Master.Individual_ID,User_login_ID Email_Address,IsPrimary from Individual_Master,Register_User,account_profile,Email_Master where User_login_ID = ? and   account_profile.Registration_ID = Register_User.Registration_ID and Email_Master.Email_Address = User_login_ID   and account_profile.Individual_ID = Individual_Master.Individual_ID",new EmailMapper(), UserId);
		List<PhoneDto> phoneDetail = jdbcTemplate.query("select Phone_Master.Phone_ID,Individual_Master.Individual_ID,Phone_Master.Country_Code,Phone_Master.Phone_Number,Phone_Master.IsPrimary from Individual_Master, " + 
				"Register_User,account_profile,Phone_Master where User_login_ID = ? and account_profile.Registration_ID = Register_User.Registration_ID " + 
				"and Phone_Master.Country_Code = Register_User.Country_Code and Phone_Master.Phone_Number = Register_User.Mobile_Number and account_profile.Individual_ID = Individual_Master.Individual_ID ", new PhoneMapper(),  UserId);
 		familyMemberDto.setPersonalDetail(personalDetail);
		familyMemberDto.setAddressDetail(addressDetail);
		familyMemberDto.setEmailDetail(emailDetail);
		familyMemberDto.setPhoneDetail(phoneDetail);
		
		myAccountDto.setFamilyMembers(familyMemberDto);
		
		return myAccountDto;
	}
}
