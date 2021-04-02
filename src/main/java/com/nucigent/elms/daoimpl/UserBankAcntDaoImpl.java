package com.nucigent.elms.daoimpl;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.nucigent.elms.account.domain.DocumentDetailMapper;
import com.nucigent.elms.account.dto.BankAccountDto;
import com.nucigent.elms.account.dto.BankDtlsDto;
import com.nucigent.elms.account.dto.DocumentDetailDto;
import com.nucigent.elms.account.dto.PersonalDetailDto;
import com.nucigent.elms.account.fileupload.AwsAdapter;
import com.nucigent.elms.common.util.CommonUtil;
import com.nucigent.elms.common.util.UserDetail;
import com.nucigent.elms.dao.UserBankAcntDao;
import com.nucigent.elms.helper.BankAcntQueryConstant;
import com.nucigent.elms.mapper.BankDetailMapper;

@Repository
public class UserBankAcntDaoImpl implements UserBankAcntDao {

	Logger logger = LoggerFactory.getLogger(UserBankAcntDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	@Autowired private UserDetail userDetail;
	@Autowired private AwsAdapter awsAdapter;
	
	@Value("${spring.datasource.driverClassName}")
	String driverclassname;
	
	@Value("${spring.datasource.url}")
	String datasource;
	
	@Value("${spring.datasource.username}")
	String username;
	
	@Value("${spring.datasource.password}")
	String password;

	@Override
	public List<BankDtlsDto> getBankDetail() {
		
		String UserId = userDetail.getUserNameFromSecurityContect();
		List<BankDtlsDto> bankAccountList = jdbcTemplate.query(BankAcntQueryConstant.SQL_SELECT_VA_BANK_DETAILS,new BankDetailMapper(),UserId);			
		return bankAccountList;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,BankDtlsDto> getBankAccntDetail() {
		
		String UserId = userDetail.getUserNameFromSecurityContect();
		Map<String,BankDtlsDto> mapBankAccntDtls = new HashMap<String,BankDtlsDto>();
		mapBankAccntDtls = jdbcTemplate.query(BankAcntQueryConstant.SQL_SELECT_VA_BANK_ACCOUNT_DETAILS,
				
				new ResultSetExtractor<Map>(){
		    @Override
		    public Map extractData(ResultSet rs) throws SQLException,DataAccessException {
		        HashMap<String,BankDtlsDto> mapRet= new HashMap<String,BankDtlsDto>();
		        while(rs.next()){
		            
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
					bankDtlsDto.setAddressflag(rs.getInt("Address_Search_Entry_Type_Flag"));
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
					bankAccountDto.setVaAccntExtsnAccntId(rs.getInt("VA_Account_Extension2_ID"));
					bankAccountDto.setShortCode(rs.getString("Sortcode"));
					bankAccountDto.setAccounType(rs.getString("Type_of_Account"));
					bankAccountDto.setAccountOpenDate(rs.getString("Account_Opening_Date"));	
					String jointFlag = rs.getString("Joint_Account_Flag");
					if(jointFlag !=null)
					bankAccountDto.setJointAccount(jointFlag.equals("Y")? true : false);
					
					bankAccountDto.setNoJointAccntHolder(rs.getInt("No_Of_Joint_Acc_Holder"));
					bankAccountDto.setJntAccntHldrName1(rs.getString("Full_Name_1"));
					bankAccountDto.setJntAccntHldrName2(rs.getString("Full_Name_2"));
					bankAccountDto.setJntAccntHldrName3(rs.getString("Full_Name_3"));
					bankAccountDto.setJntAccntHldrName4(rs.getString("Full_Name_4"));
					bankAccountDto.setJntAccntHldrName5(rs.getString("Full_Name_5"));
					bankAccountDto.setAdditionalAccountInfo(rs.getString("add_acc_Information"));
					
					
					bankDtlsDto.setBankAccountDto(bankAccountDto);
					
					PersonalDetailDto personalDetailDto = new PersonalDetailDto();
					personalDetailDto.setVaAccntExtsnId(rs.getInt("VA_Account_Extension1_ID"));
					personalDetailDto.setTitle(rs.getString("Title"));
					personalDetailDto.setFirstName(rs.getString("First_Name"));
					personalDetailDto.setLastName(rs.getString("Last_Name"));
					personalDetailDto.setGender(rs.getString("Gender"));
					personalDetailDto.setDateOfBirth(rs.getString("DOB"));
					personalDetailDto.setEmailId(rs.getInt("Email_ID"));
					personalDetailDto.setPhoneId(rs.getInt("Phone_ID"));
					personalDetailDto.setAddressId(rs.getInt("Address_ID"));
					personalDetailDto.setAdditionalInformation(rs.getString("additionalInformation"));
					personalDetailDto.setSelectedEmailId(personalDetailDto.getEmailId());
					personalDetailDto.setSelectedPhoneId(personalDetailDto.getPhoneId());
					personalDetailDto.setAddressId(personalDetailDto.getAddressId());
					personalDetailDto.setIndividualId(bankDtlsDto.getIndividualId());
					personalDetailDto.setSelFamMemberId(bankDtlsDto.getIndividualId());
					bankDtlsDto.setPersonalDetail(personalDetailDto);
					
					List<DocumentDetailDto> documentDetail = jdbcTemplate.query("select * from document_master where VA_Account_ID = ?",new DocumentDetailMapper(),bankDtlsDto.getValAccountId());
					bankDtlsDto.setDocumentDetail(documentDetail);
					
					mapRet.put(bankDtlsDto.getValAccountId()+"", bankDtlsDto);
					
		        }
		        
		        return mapRet;
		    }
		}
				,UserId);		
		return mapBankAccntDtls;
	}

	@Override
	public Integer saveUserBankAccnt(@Valid BankDtlsDto bankDtlsDto) {
		
		String userLoginId = userDetail.getUserNameFromSecurityContect();		
		int valAccountId = bankDtlsDto.getValAccountId();
		
		if(bankDtlsDto.getValAccountId()>0) {
			valAccountId = bankDtlsDto.getValAccountId();
		}else {
			valAccountId = jdbcTemplate.queryForObject(BankAcntQueryConstant.SQL_SELECT_VA_BANK_SEQ, Integer.class);
		}
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("userLoginId", userLoginId)
				.addValue("valAccountId", valAccountId)
				.addValue("bankName", bankDtlsDto.getBankName())
				.addValue("addressLine1", bankDtlsDto.getAddressLine1())
				.addValue("addressLine2", bankDtlsDto.getAddressLine2())
				.addValue("addressLine3", bankDtlsDto.getAddressLine3())
				.addValue("addressLine4", bankDtlsDto.getAddressLine4())
				.addValue("postCode", bankDtlsDto.getPostCode())
				.addValue("country", bankDtlsDto.getSelectCountry())
				.addValue("emailAddressOne", bankDtlsDto.getEmailAddressOne())
				.addValue("emailAddressTwo", bankDtlsDto.getEmailAddressTwo())
				.addValue("countryCodePrim", (bankDtlsDto.getPhoneNumPrim()!=null || bankDtlsDto.getPhoneNumPrim() !="" ) ? bankDtlsDto.getCountryCodePrim() : null)
				.addValue("phoneNumPrim", bankDtlsDto.getPhoneNumPrim())
				.addValue("countryCodeSec", (bankDtlsDto.getPhoneNumSec()!=null || bankDtlsDto.getPhoneNumSec() !="" ) ? bankDtlsDto.getCountryCodeSec() : null)
				.addValue("phoneNumSec", bankDtlsDto.getPhoneNumSec())
				.addValue("websiteUrl", bankDtlsDto.getWebsiteUrl())
				.addValue("addInfoBank", bankDtlsDto.getAddInfoBank())
				.addValue("individualId", bankDtlsDto.getIndividualId())
				.addValue("addressflag", bankDtlsDto.getAddressflag())
				.addValue("CountyState", bankDtlsDto.getCountryState());
		
		// TODO Auto-generated method stub
		if(bankDtlsDto.getValAccountId() > 0) {	
			
			int updatedRow = namedParameterJdbcTemplate.update(BankAcntQueryConstant.SQL_UPDATE_VA_BANK_ACCOUNT_DETAILS, parameters);	
			if(updatedRow>0) {
				return valAccountId;
			}
			else {
				return 0;
			}			
		}
		
		else
		{					
				int updatedRow = namedParameterJdbcTemplate.update(BankAcntQueryConstant.SQL_INSERT_VA_BANK_ACCOUNT_DETAILS, parameters);
		
				if(updatedRow>0) {
					return valAccountId;
				}
				else {
					return 0;
				}

		}
	}

	@Override
	public Integer saveUserBankPersonalAccnt(PersonalDetailDto personalDtlsDto) {
		// TODO Auto-generated method stub
		String userLoginId = userDetail.getUserNameFromSecurityContect();
		int vaAccntExtsnId = personalDtlsDto.getVaAccntExtsnId();
		if(personalDtlsDto.getVaAccntExtsnId()>0) {
			vaAccntExtsnId = personalDtlsDto.getVaAccntExtsnId();
		}else {
			vaAccntExtsnId = jdbcTemplate.queryForObject(BankAcntQueryConstant.SQL_SELECT_VA_BANK_PER_SEQ , Integer.class);
		}
		
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("userLoginId", userLoginId)
				.addValue("vaAccntExtsnId", vaAccntExtsnId)
				.addValue("valAccountId", personalDtlsDto.getValAccntId())
				.addValue("individualId", personalDtlsDto.getIndividualId())
				.addValue("addressId", personalDtlsDto.getAddressId())
				.addValue("emailId", personalDtlsDto.getEmailId())
				.addValue("phoneId", personalDtlsDto.getPhoneId())
				.addValue("isactive", "Y")
				.addValue("additianalInfo", "");
				
			if(personalDtlsDto.getVaAccntExtsnId() > 0) {	
				
				int updatedRow = namedParameterJdbcTemplate.update(BankAcntQueryConstant.SQL_UPDATE_VA_BANK_ACCOUNT_PERSONAL_DETAILS, parameters);	
				if(updatedRow>0) {
					return vaAccntExtsnId;
				}
				else {
					return 0;
				}			
			}
			else
			{					
				int updatedRow = namedParameterJdbcTemplate.update(BankAcntQueryConstant.SQL_INSERT_VA_BANK_ACCOUNT_PERSONAL_DETAILS, parameters);
				if(updatedRow>0) {
					return vaAccntExtsnId;
				}
				else {
					return 0;
				}
			}
	}

	@Override
	public int removeBankinfo(Integer valAccountId) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(BankAcntQueryConstant.SQL_DELETE_VA_BANK_ACCOUNT_INFO_EXT2,valAccountId);
		jdbcTemplate.update(BankAcntQueryConstant.SQL_DELETE_VA_BANK_ACCOUNT_INFO_EXT1,valAccountId);
		return jdbcTemplate.update(BankAcntQueryConstant.SQL_DELETE_VA_BANK_ACCOUNT_INFO,valAccountId);
	}


	@Override
	public Integer saveUserBankAccntDtlInfo(@Valid BankAccountDto bankAccountDto) {
		// TODO Auto-generated method stub
				String userLoginId = userDetail.getUserNameFromSecurityContect();
				int vaAccntExtsnId = bankAccountDto.getVaAccntExtsnAccntId();
				if(bankAccountDto.getVaAccntExtsnAccntId()>0) {
					vaAccntExtsnId = bankAccountDto.getVaAccntExtsnAccntId();
				}else {
					vaAccntExtsnId = jdbcTemplate.queryForObject(BankAcntQueryConstant.SQL_SELECT_VA_BANK_ACCNT_SEQ , Integer.class);
				}
				
				SqlParameterSource parameters = new MapSqlParameterSource()
						.addValue("userLoginId", userLoginId)
						.addValue("vaAccntExtsnId", vaAccntExtsnId)
						.addValue("valAccountId", bankAccountDto.getValAccntId())
						.addValue("accountNo", bankAccountDto.getAccountNo())
						.addValue("shortCode", bankAccountDto.getShortCode())
						.addValue("typeOfAccount", bankAccountDto.getAccounType())
						.addValue("accntOpenDate", bankAccountDto.getAccountOpenDate())
						.addValue("jointAccntFlag", bankAccountDto.isJointAccount() ? "Y" : "N")
						.addValue("joinAccntHolder1", bankAccountDto.isJointAccount() ? bankAccountDto.getJntAccntHldrName1() : null)
						.addValue("joinAccntHolder2", bankAccountDto.isJointAccount() ? bankAccountDto.getJntAccntHldrName2() : null)
						.addValue("joinAccntHolder3", bankAccountDto.isJointAccount() ? bankAccountDto.getJntAccntHldrName3() : null)
						.addValue("joinAccntHolder4", bankAccountDto.isJointAccount() ? bankAccountDto.getJntAccntHldrName4() : null)
						.addValue("joinAccntHolder5", bankAccountDto.isJointAccount() ? bankAccountDto.getJntAccntHldrName5() : null)
						.addValue("isactive", "Y")
						.addValue("additianalInfo", bankAccountDto.getAdditionalAccountInfo());
						
					if(bankAccountDto.getVaAccntExtsnAccntId() > 0) {	
						
						int updatedRow = namedParameterJdbcTemplate.update(BankAcntQueryConstant.SQL_UPDATE_VA_BANK_ACCOUNT_HOLDER_DETAILS, parameters);	
						if(updatedRow>0) {
							return vaAccntExtsnId;
						}
						else {
							return 0;
						}			
					}
					else
					{					
						int updatedRow = namedParameterJdbcTemplate.update(BankAcntQueryConstant.SQL_INSERT_VA_BANK_ACCOUNT_HOLDER_DETAILS, parameters);
						if(updatedRow>0) {
							return vaAccntExtsnId;
						}
						else {
							return 0;
						}
					}
	}


	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BankDtlsDto> getBankAccntDetailFrItem(Integer valAccountId) {
		
		
		String UserId = userDetail.getUserNameFromSecurityContect();
		Map<String,BankDtlsDto> mapBankAccntDtls = new HashMap<String,BankDtlsDto>();
		mapBankAccntDtls = jdbcTemplate.query(BankAcntQueryConstant.SQL_SELECT_VA_BANK_ACCOUNT_DETAILS_ITEM,
				
				new ResultSetExtractor<Map>(){
		    @Override
		    public Map extractData(ResultSet rs) throws SQLException,DataAccessException {
		        HashMap<String,BankDtlsDto> mapRet= new HashMap<String,BankDtlsDto>();
		        while(rs.next()){
		            
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
					bankDtlsDto.setAddressflag(rs.getInt("Address_Search_Entry_Type_Flag"));
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
					bankAccountDto.setVaAccntExtsnAccntId(rs.getInt("VA_Account_Extension2_ID"));
					bankAccountDto.setShortCode(rs.getString("Sortcode"));
					bankAccountDto.setAccounType(rs.getString("Type_of_Account"));
					bankAccountDto.setAccountOpenDate(rs.getString("Account_Opening_Date"));	
					String jointFlag = rs.getString("Joint_Account_Flag");
					if(jointFlag !=null)
					bankAccountDto.setJointAccount(jointFlag.equals("Y")? true : false);					
					bankAccountDto.setNoJointAccntHolder(rs.getInt("No_Of_Joint_Acc_Holder"));
					bankAccountDto.setJntAccntHldrName1(rs.getString("Full_Name_1"));
					bankAccountDto.setJntAccntHldrName2(rs.getString("Full_Name_2"));
					bankAccountDto.setJntAccntHldrName3(rs.getString("Full_Name_3"));
					bankAccountDto.setJntAccntHldrName4(rs.getString("Full_Name_4"));
					bankAccountDto.setJntAccntHldrName5(rs.getString("Full_Name_5"));
					bankAccountDto.setAdditionalAccountInfo(rs.getString("Additional_Info"));
					
					bankDtlsDto.setBankAccountDto(bankAccountDto);
					
					PersonalDetailDto personalDetailDto = new PersonalDetailDto();
					personalDetailDto.setVaAccntExtsnId(rs.getInt("VA_Account_Extension1_ID"));
					personalDetailDto.setTitle(rs.getString("Title"));
					personalDetailDto.setFirstName(rs.getString("First_Name"));
					personalDetailDto.setLastName(rs.getString("Last_Name"));
					personalDetailDto.setGender(rs.getString("Gender"));
					personalDetailDto.setDateOfBirth(rs.getString("DOB"));
					personalDetailDto.setEmailId(rs.getInt("Email_ID"));
					personalDetailDto.setPhoneId(rs.getInt("Phone_ID"));
					personalDetailDto.setAddressId(rs.getInt("Address_ID"));
					personalDetailDto.setAdditionalInformation(rs.getString("additionalInformation"));
					personalDetailDto.setSelectedEmailId(personalDetailDto.getEmailId());
					personalDetailDto.setSelectedPhoneId(personalDetailDto.getPhoneId());
					personalDetailDto.setAddressId(personalDetailDto.getAddressId());
					bankDtlsDto.setPersonalDetail(personalDetailDto);
					
					DocumentDetailDto documentDetailDto = new DocumentDetailDto();
					documentDetailDto.setDocumentId(rs.getInt("Document_ID"));
					documentDetailDto.setDocumentNumber(rs.getString("Document_Number"));
					documentDetailDto.setDocumentSize(rs.getString("Document_Size"));
					documentDetailDto.setDocumentType(rs.getString("Document_Type"));
					documentDetailDto.setDocumentIdentifier(rs.getString("Document_Stored_Path"));
					
					mapRet.put(bankDtlsDto.getValAccountId()+"", bankDtlsDto);
		        	
		        }
		        
		        return mapRet;
		    }
		},UserId,valAccountId);		
		return mapBankAccntDtls;
	}
	
	@Override
	public Integer saveorupdateDocumentDetails(DocumentDetailDto documentDetailDto,List<MultipartFile> fileLs) {
				
			Integer documentId = null;
			URL url = null;
			try {	
									
			if(documentDetailDto.getDocumentId()==null || documentDetailDto.getDocumentId()==0) {
				KeyHolder holder = new GeneratedKeyHolder();
				String  fileName = null;
				 
				documentId = jdbcTemplate.queryForObject(BankAcntQueryConstant.SQL_SELECT_VA_DOC_MASTER_SEQ , Integer.class);
				 
					 fileName = fileLs.get(0).getOriginalFilename();
					 
					 if(fileLs !=null && fileLs.size()>0) {					 
						 url = awsAdapter.storeObjectInS3(fileLs.get(0), fileLs.get(0).getOriginalFilename(), fileLs.get(0).getContentType());
					 }
					 SqlParameterSource parameters = new MapSqlParameterSource()
						.addValue("documentId", documentId)
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
						.addValue("filePath",url!=null ? url.toURI().toString() : "")
				        .addValue("fileSize",fileLs.get(0).getSize())
				        .addValue("valAccntId", documentDetailDto.getValAccntId());
				
				namedParameterJdbcTemplate.update("insert into document_master (Document_ID, Individual_ID, Document_Type, Country_of_Issue, Document_Number, Issueing_Authority, Issue_Date, Expiry_Date, Additional_Information,Document_Identifier,Document_Size,VA_Account_ID,Document_Stored_Path) values "
				 +  "(:documentId,:IndividualId,:documentType,:countryOfIssue,:documentNumber,:issueingAuthority,:issueDate,:expiaryDate,:additionalInformation,:fileName,:fileSize,:valAccntId,:filePath)", parameters, holder);
			
			}
			else
			{
						
				Calendar calendar = Calendar.getInstance();
				java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());

				if(fileLs.size()==0) {
				
					jdbcTemplate.update("update document_master set Document_Type = ?,Country_of_Issue = ?,Document_Number = ?,Issueing_Authority = ?,Issue_Date = ?,Expiry_Date = ?,Additional_Information = ?,Record_Updated_Date = ?,VA_Account_ID =? where Document_ID = ?", 
							documentDetailDto.getDocumentType(),documentDetailDto.getCountryOfIssue(),
							documentDetailDto.getDocumentNumber(),documentDetailDto.getIssueingAuthority(),
							documentDetailDto.getIssueDate(),documentDetailDto.getExpiaryDate(),
							documentDetailDto.getAdditionalInformation(),updatetedDate,documentDetailDto.getValAccntId(),documentDetailDto.getDocumentId());
					
					documentId =  documentDetailDto.getDocumentId();
				}
				else
				{
				
					if(fileLs !=null && fileLs.size()>0) {		
						url = awsAdapter.storeObjectInS3(fileLs.get(0), fileLs.get(0).getOriginalFilename(), fileLs.get(0).getContentType());
					}
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
					jdbcTemplate.update("update document_master set Document_Type = ?,Country_of_Issue = ?,Document_Number = ?,Issueing_Authority = ?,Issue_Date = ?,Expiry_Date = ?,Additional_Information = ?,Record_Updated_Date = ?,Document_Identifier = ?,Document_Size = ?,VA_Account_ID =?,Document_Stored_Path=? where Document_ID = ?", 
							documentDetailDto.getDocumentType(),documentDetailDto.getCountryOfIssue(),
							documentDetailDto.getDocumentNumber(),documentDetailDto.getIssueingAuthority(),
							documentDetailDto.getIssueDate(),documentDetailDto.getExpiaryDate(),
							documentDetailDto.getAdditionalInformation(),updatetedDate,fileLs.get(0).getOriginalFilename(),
							fileLs.get(0).getSize(),documentDetailDto.getValAccntId(),url!=null ? url.toURI().toString() : "",documentDetailDto.getDocumentId());
					
					documentId =  documentDetailDto.getDocumentId();
					
				}	
			 }
			}
			catch(Exception e) {
				System.out.println(e.toString());
			}
			return documentId;
			
		}


	@Override
	public Integer updateBankAccntAddInfo(@Valid PersonalDetailDto personalDtlsDto) {
		
		String userLoginId = userDetail.getUserNameFromSecurityContect();
						
		SqlParameterSource parameters = new MapSqlParameterSource()
				.addValue("userLoginId", userLoginId)
				.addValue("vaAccntExtsnId", personalDtlsDto.getVaAccntExtsnId())
				.addValue("valAccountId", personalDtlsDto.getValAccntId())
				.addValue("additianalInfo", personalDtlsDto.getAdditionalInformation());

				int updatedRow = namedParameterJdbcTemplate.update(BankAcntQueryConstant.SQL_UPDATE_VA_BANK_ACCOUNT_ADD_INFO, parameters);
				
				return updatedRow;
	}
	
	

}