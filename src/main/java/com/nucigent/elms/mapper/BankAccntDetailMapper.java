package com.nucigent.elms.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.nucigent.elms.account.dto.BankAccountDto;
import com.nucigent.elms.account.dto.BankAccountMapDto;
import com.nucigent.elms.account.dto.BankDtlsDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.common.util.CommonUtil;



public class BankAccntDetailMapper implements RowMapper<BankAccountMapDto>{
	
	public BankAccountMapDto mapRow(ResultSet rs,int rowNum) throws SQLException {
		BankAccountMapDto bankAccountMapDto = new BankAccountMapDto();
			BankDtlsDto bankDtlsDto = new BankDtlsDto();
			bankDtlsDto.setValAccountId(rs.getInt("VA_Account_ID"));
			bankDtlsDto.setBankName(rs.getString("Name"));
			bankDtlsDto.setValItemId(rs.getInt("Valuable_Item_ID"));
			bankDtlsDto.setIndividualId(rs.getInt("Individual_ID"));
			bankDtlsDto.setAddressLine1(rs.getString("Address_Line1"));
			bankDtlsDto.setAddressLine2(rs.getString("Address_Line2"));
			bankDtlsDto.setAddressLine3(rs.getString("Address_Line3"));
			bankDtlsDto.setAddressLine4(rs.getString("Address_Line4"));
			bankDtlsDto.setCountry(rs.getString("Country"));
			bankDtlsDto.setSelectCountry(bankDtlsDto.getCountry());
			bankDtlsDto.setCountryState(rs.getString("County_State"));
			bankDtlsDto.setPostCode(rs.getString("PostCode"));	
			bankDtlsDto.setAddressflag(rs.getInt("Address_Entry_Type_Flag"));
			bankDtlsDto.setEmailAddressOne(rs.getString("Email_Address1"));
			bankDtlsDto.setEmailAddressTwo(rs.getString("Email_Address2"));
			bankDtlsDto.setCountryCodePrim(rs.getString("Country Code1"));
			bankDtlsDto.setPhoneNumPrim(rs.getString("Phone_Number1"));
			bankDtlsDto.setCountryCodeSec(rs.getString("Country Code2"));
			bankDtlsDto.setPhoneNumSec(rs.getString("Phone_Number2"));
			bankDtlsDto.setWebsiteUrl(rs.getString("Website_URL"));
			bankDtlsDto.setAddInfoBank(rs.getString("Additional_Info"));
			String isActive = rs.getString("IsActive");
			if(isActive !=null)
			bankDtlsDto.setActive(isActive.equals("Y")? true : false);
			
			BankAccountDto bankAccountDto = new BankAccountDto();
				
			bankAccountDto.setAccountNo(rs.getString("Account_Number"));
			bankAccountDto.setAccountHolderNo(CommonUtil.maskString(bankAccountDto.getAccountNo(), 5, 8, '*'));
			bankAccountDto.setSecAccountNo(rs.getString("Account_Number"));
			bankAccountDto.setValAccntId(rs.getInt("VA_Account_ID"));
			bankAccountDto.setVaAccntExtsnAccntId(rs.getInt("VA_Account_Extension2_ID"));
			bankAccountDto.setShortCode(rs.getString("Sortcode"));
			bankAccountDto.setAccounType(rs.getString("Type_of_Account"));
			bankAccountDto.setAccountOpenDate(rs.getString("Account_Opening_Date"));	
			String jointFlag = rs.getString("Joint_Account_Flag");
			if(jointFlag !=null)
			bankAccountDto.setJointAccount(jointFlag.equals("Y")? true : false);
			bankAccountDto.setJntAccntHldrName1(rs.getString("Full_Name_1"));
			bankAccountDto.setJntAccntHldrName1(rs.getString("Full_Name_2"));
			bankAccountDto.setJntAccntHldrName1(rs.getString("Full_Name_3"));
			bankAccountDto.setJntAccntHldrName1(rs.getString("Full_Name_4"));
			bankAccountDto.setJntAccntHldrName1(rs.getString("Full_Name_5"));
			bankAccountDto.setAdditionalAccountInfo(rs.getString("Additional_Info"));
			
			bankDtlsDto.setBankAccountDto(bankAccountDto);
			
			PersonalDetailDto personalDetailDto = new PersonalDetailDto();
			personalDetailDto.setTitle(rs.getString("Title"));
			personalDetailDto.setFirstName(rs.getString("First_Name"));
			personalDetailDto.setLastName(rs.getString("Last_Name"));
			personalDetailDto.setGender(rs.getString("Gender"));
			personalDetailDto.setDateOfBirth(rs.getString("DOB"));
			//personalDetailDto.setIndividualId(rs.getInt("Individual_ID "));
			
			bankDtlsDto.setPersonalDetail(personalDetailDto);
			
			bankAccountMapDto.setValAcntId(bankDtlsDto.getValAccountId());
			bankAccountMapDto.setBankDtlsDto(bankDtlsDto);
			return bankAccountMapDto;
			
		}

}
