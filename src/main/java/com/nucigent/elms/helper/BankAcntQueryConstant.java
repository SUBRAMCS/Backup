package com.nucigent.elms.helper;

public class BankAcntQueryConstant {

	public static final String SQL_SELECT_BANKS_LOOK_UP = "SELECT SUB_NAME FROM REF_SUB_VALUABLE_ITEMS WHERE Valuable_Item_ID =1 and Sub_Type ='Bank Name' and IsActive ='Y' ";
	
	public static final String SQL_SELECT_VA_BANK_DETAILS = "SELECT VAC.VA_Account_ID,Name,First_Name,Middle_Name,Last_Name,Account_Number " + 
			" FROM VA_ACCOUNT1 VAC LEFT JOIN VA_ACCOUNT_EXTENSION1 VACEX1 ON VAC.VA_Account_ID = VACEX1.VA_Account_ID LEFT JOIN VA_ACCOUNT_EXTENSION2 VACEX2 ON VAC.VA_Account_ID = VACEX2.VA_Account_ID " + 
			" LEFT JOIN INDIVIDUAL_MASTER INDM ON VACEX1.Individual_ID = INDM.Individual_ID " + 
			" WHERE VAC.Individual_ID = (select Individual_ID from ACCOUNT_PROFILE where Registration_ID = (SELECT Registration_ID FROM REGISTER_USER WHERE User_Login_ID = ?)) ";
	
	public static final String SQL_SELECT_VA_BANK_SEQ = "select NEXT VALUE FOR VA_ACCOUNT_SEQ ";
	
	public static final String SQL_SELECT_VA_BANK_PER_SEQ = "select NEXT VALUE FOR tightcapdb.dbo.VA_ACCNT_EXTNSN_SEQ ";
	
	public static final String SQL_SELECT_VA_BANK_ACCNT_SEQ = "select NEXT VALUE FOR tightcapdb.dbo.VA_ACCNT_EXTNSN_TWO_SEQ ";
	
	public static final String SQL_SELECT_VA_BANK_ACCOUNT_DETAILS = " SELECT VAC.VA_Account_ID,Valuable_Item_ID,Name,Address_Line1,Address_Line2,Address_Line3,Address_Line4,PostCode,Country,VAC.IsActive,Email_Address1,Email_Address2,[Country Code1], Phone_Number1,[Country Code2],Phone_Number2,County_State, " + 
			" Website_URL,VAC.Additional_Info,VA_Account_Extension1_ID,VA_Account_Extension2_ID,Sortcode,Type_of_Account,Account_Opening_Date,Joint_Account_Flag,No_Of_Joint_Acc_Holder,Full_Name_1,Full_Name_2,Full_Name_3,Full_Name_4,Full_Name_5 ,  " + 
			" VACEX1.Individual_ID, VACEX1.Additional_Info as additionalInformation,VACEX2.Additional_Info as add_acc_Information,Title,First_Name,Last_Name, Gender,INDM.DOB,Email_ID,Phone_ID,Address_ID,Account_Number,VACEX1.Individual_ID ACCNT_HOLDER_ID,Address_Search_Entry_Type_Flag, " + 
			" Document_ID,Document_Type,Country_of_Issue,Document_Number,Issueing_Authority,Issue_Date,Expiry_Date,Additional_Information,Document_Identifier,Document_Stored_Path,Document_Size "+
			" FROM VA_ACCOUNT1 VAC LEFT JOIN VA_ACCOUNT_EXTENSION1 VACEX1 ON VAC.VA_Account_ID = VACEX1.VA_Account_ID LEFT JOIN VA_ACCOUNT_EXTENSION2 VACEX2 ON VAC.VA_Account_ID = VACEX2.VA_Account_ID " + 
			" LEFT JOIN INDIVIDUAL_MASTER INDM ON VACEX1.Individual_ID = INDM.Individual_ID LEFT JOIN DOCUMENT_MASTER DCM ON DCM.VA_Account_ID = VAC.VA_Account_ID " + 
			" WHERE VAC.Individual_ID = (select Individual_ID from ACCOUNT_PROFILE where Registration_ID = (SELECT Registration_ID FROM REGISTER_USER WHERE User_Login_ID = ?)) ";
	
	public static final String SQL_SELECT_VA_BANK_ACCOUNT_DETAILS_ITEM = " SELECT VAC.VA_Account_ID,Valuable_Item_ID,Name,Address_Line1,Address_Line2,Address_Line3,Address_Line4,PostCode,Country,VAC.IsActive,Email_Address1,Email_Address2,[Country Code1], Phone_Number1,[Country Code2],Phone_Number2,County_State, " + 
			" Website_URL,VAC.Additional_Info,VA_Account_Extension1_ID,VA_Account_Extension2_ID,Sortcode,Type_of_Account,Account_Opening_Date,Joint_Account_Flag,No_Of_Joint_Acc_Holder,Full_Name_1,Full_Name_2,Full_Name_3,Full_Name_4,Full_Name_5 ,  " + 
			" VACEX1.Individual_ID,VACEX1.Additional_Info as additionalInformation ,Title,First_Name,Last_Name, Gender,INDM.DOB,Email_ID,Phone_ID,Address_ID,Account_Number,VACEX1.Individual_ID ACCNT_HOLDER_ID,Address_Search_Entry_Type_Flag " + 
			" FROM VA_ACCOUNT1 VAC LEFT JOIN VA_ACCOUNT_EXTENSION1 VACEX1 ON VAC.VA_Account_ID = VACEX1.VA_Account_ID LEFT JOIN VA_ACCOUNT_EXTENSION2 VACEX2 ON VAC.VA_Account_ID = VACEX2.VA_Account_ID " + 
			" LEFT JOIN INDIVIDUAL_MASTER INDM ON VACEX1.Individual_ID = INDM.Individual_ID " + 
			" WHERE VAC.Individual_ID = (select Individual_ID from ACCOUNT_PROFILE where Registration_ID = (SELECT Registration_ID FROM REGISTER_USER WHERE User_Login_ID = ?)) AND VAC.VA_Account_ID=?";
		
	public static final String SQL_INSERT_VA_BANK_ACCOUNT_DETAILS =	"INSERT INTO tightcapdb.dbo.VA_ACCOUNT1 (VA_Account_ID, Valuable_Item_ID, Name, Address_Line1, Address_Line2, Address_Line3, Address_Line4, PostCode, "+
			" Country,County_State, Address_Entry_Type_Flag, Email_Address1, Email_Address2, [Country Code1], Phone_Number1, [Country Code2], Phone_Number2, Website_URL, "+
			" Additional_Info, IsActive, Record_Created_Date, Record_Updated_Date, Individual_ID, ID, Address_Search_Entry_Type_Flag) VALUES(:valAccountId, 1, :bankName, :addressLine1, :addressLine2, :addressLine3, :addressLine4,  :postCode, :country, :CountyState,0, :emailAddressOne, :emailAddressTwo, "+
			" :countryCodePrim, :phoneNumPrim, :countryCodeSec, :phoneNumSec, :websiteUrl, :addInfoBank, 'Y', GETDATE(), GETDATE(), (SELECT Individual_ID FROM ACCOUNT_PROFILE WHERE Registration_ID = (SELECT Registration_ID FROM REGISTER_USER WHERE User_Login_ID = :userLoginId)), 0, :addressflag) " ;
	
	public static final String SQL_UPDATE_VA_BANK_ACCOUNT_DETAILS =	"UPDATE tightcapdb.dbo.VA_ACCOUNT1 SET Name=:bankName, Address_Line1=:addressLine1, Address_Line2=:addressLine2, Address_Line3=:addressLine3, Address_Line4=:addressLine4, PostCode=:postCode, Country=:country, "+
			" Address_Entry_Type_Flag=0, Email_Address1=:emailAddressOne, Email_Address2=:emailAddressTwo, [Country Code1]=:countryCodePrim, Phone_Number1=:phoneNumPrim, [Country Code2]=:countryCodeSec, Phone_Number2=:phoneNumSec, Website_URL=:websiteUrl,County_State=:CountyState , "+
			" Additional_Info=:addInfoBank, Record_Updated_Date=GETDATE(), Address_Search_Entry_Type_Flag=:addressflag WHERE VA_Account_ID = :valAccountId " ;
	
	public static final String SQL_INSERT_VA_BANK_ACCOUNT_PERSONAL_DETAILS = "INSERT INTO tightcapdb.dbo.VA_ACCOUNT_EXTENSION1 (VA_Account_Extension1_ID, VA_Account_ID, Registration_ID, Individual_ID, Address_ID ,Email_ID, Phone_ID, Additional_Info, "+
			 " IsActive, Record_Created_Date, Record_Updated_Date) VALUES(:vaAccntExtsnId, :valAccountId, (SELECT Registration_ID FROM REGISTER_USER WHERE User_Login_ID = :userLoginId ), :individualId, :addressId , :emailId, :phoneId, :additianalInfo, 'Y', GETDATE(), GETDATE())";
	
	public static final String SQL_UPDATE_VA_BANK_ACCOUNT_PERSONAL_DETAILS = "UPDATE tightcapdb.dbo.VA_ACCOUNT_EXTENSION1 SET Individual_ID=:individualId, Address_ID=:addressId ,Email_ID=:emailId, Phone_ID=:phoneId, IsActive=:isactive , Record_Updated_Date = GETDATE() WHERE VA_Account_Extension1_ID=:vaAccntExtsnId AND VA_Account_ID=:valAccountId ";
	
	public static final String SQL_DELETE_VA_BANK_ACCOUNT_INFO_EXT2 = "delete from tightcapdb.dbo.VA_ACCOUNT_EXTENSION2 where VA_Account_ID = ?";
	
	public static final String SQL_DELETE_VA_BANK_ACCOUNT_INFO_EXT1 = "delete from tightcapdb.dbo.VA_ACCOUNT_EXTENSION1 where VA_Account_ID = ?";
	
	public static final String SQL_DELETE_VA_BANK_ACCOUNT_INFO = "delete from tightcapdb.dbo.VA_ACCOUNT1 where VA_Account_ID = ?";
	
	public static final String SQL_UPDATE_VA_BANK_ACCOUNT_HOLDER_DETAILS = "UPDATE tightcapdb.dbo.VA_ACCOUNT_EXTENSION2 SET VA_Account_Extension2_ID=:vaAccntExtsnId,VA_Account_ID=:valAccountId, Account_Number=:accountNo, Sortcode=:shortCode, Type_of_Account=:typeOfAccount,Account_Opening_Date=:accntOpenDate, Joint_Account_Flag=:jointAccntFlag, Full_Name_1=:joinAccntHolder1, "+ 
			 " Full_Name_2=:joinAccntHolder2, Full_Name_3=:joinAccntHolder3, Full_Name_4=:joinAccntHolder4, Full_Name_5=:joinAccntHolder5, Additional_Info=:additianalInfo, IsActive=:isactive,Record_Updated_Date=GETDATE() ";
	public static final String SQL_INSERT_VA_BANK_ACCOUNT_HOLDER_DETAILS = " INSERT INTO tightcapdb.dbo.VA_ACCOUNT_EXTENSION2 (VA_Account_Extension2_ID, VA_Account_ID, Account_Number, Sortcode, Type_of_Account, Account_Opening_Date, Joint_Account_Flag, Full_Name_1, Full_Name_2, Full_Name_3, Full_Name_4, Full_Name_5, Additional_Info, IsActive, Record_Created_Date) VALUES(:vaAccntExtsnId, :valAccountId, :accountNo, :shortCode, :typeOfAccount, :accntOpenDate, :jointAccntFlag, :joinAccntHolder1, :joinAccntHolder2, :joinAccntHolder3, :joinAccntHolder4, :joinAccntHolder5, :additianalInfo, :isactive, GETDATE()) ";
	
	public static final String SQL_SELECT_VA_DOC_MASTER_SEQ = "select NEXT VALUE FOR tightcapdb.dbo.DOC_MASTER_SEQ ";
	
	public static final String SQL_UPDATE_VA_BANK_ACCOUNT_ADD_INFO = "UPDATE tightcapdb.dbo.VA_ACCOUNT_EXTENSION1 SET Additional_Info=:additianalInfo , Record_Updated_Date = GETDATE() WHERE VA_Account_Extension1_ID=:vaAccntExtsnId AND VA_Account_ID=:valAccountId ";

}
