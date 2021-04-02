package com.nucigent.elms.account.helper;

public class MyNomineeQueryConstants {
	
	public static final String SQL_INSERT_MYNOMINEE_DETAIL = "insert into individual_master (Title, First_Name, Middle_Name, Last_Name, DOB, Gender, Mother_Maiden_Name, Registration_ID) values(:Title, :First_Name, :Middle_Name, :Last_Name, :DOB, :Gender, :Mother_Maiden_Name, :Registration_ID)";

	public static final String SQL_INSERT_INTO_NOMINEE_MEMBER = "insert into nominee_member (Account_Profile_ID, Individual_ID, Address_ID, Document_ID, Relationship, Additional_Info, Record_Created_DateTime, Record_Updated_DateTime, primary_nominee) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	public static final String SQL_SELECT_NOMINEE_MEMBER_BY_ACCOUNT_ID = "SELECT nm.Nominee_Member_ID, nm.Account_Profile_ID, nm.Individual_ID, nm.Address_ID, nm.Document_ID, nm.Relationship, nm.Additional_Info,nm.Primary_Nominee FROM nominee_member nm where nm.Account_Profile_ID=?";

	public static final String SQL_UPDATE_INDIVIDUAL_ID_BY_ACCOUNT_ID = "update nominee_member set Individual_ID=?, Record_Updated_Date=? where Account_Profile_ID=?";

	public static final String SQL_UPDATE_INDIVIDUAL_MASTER_BY_INDIVIDUAL_ID = "update individual_master set title=?, First_Name=?, Middle_Name=?, Last_Name=?,  DOB=?, gender=?, Record_Updated_Date=? where individual_id=?";

	public static final String SQL_INSERT_ADDRESS_DETAIL = "insert into address_master(Individual_ID, Address_Line1, Address_Line2, Address_Line3, Address_Line4, County_State, PostCode, Country) values(?, upper(?), upper(?), upper(?), upper(?), upper(?), ?, ?)";

	public static final String SQL_SELECT_PERSONAL_DETAIL_BY_ID = "select im.Individual_ID, im.Title, im.First_Name, im.Middle_Name, im.Last_Name, im.DOB, im.Gender, im.mother_maiden_name, im.Registration_ID from individual_master im  where im.Individual_ID=?";

	public static final String SQL_UPDATE_EMAIL_BY_INDIVIDUAL_ID = " update email_master set Email_Address=?, IsPrimary=?, Record_Updated_Date=? where Individual_ID=? and Email_ID=? ";

	public static final String SQL_UPDATE_PHONE_NUMBER_BY_INDIVIDUAL_ID = " update phone_master set Country_Id=?, Phone_Number=?, IsPrimary=?, Record_Updated_Date=? where Individual_ID=? and Phone_ID=?";

	public static final String SQL_INSERT_DOCUMENT = "insert into document_master (Individual_ID, Document_Type, Country_of_Issue, Document_Number, Issueing_Authority, Issue_Date, Expiry_Date, Additional_Information, Document_Identifier, Document_Path) values(:Individual_ID, :Document_Type, :Country_of_Issue, :Document_Number, :Issueing_Authority, :Issue_Date, :Expiry_Date, :Additional_Information, :Document_Identifier, :Document_Path)";

	public static final String SQL_UPDATE_DUCUMENT_ID_AND_INDIVIDUAL_ID = "update document_master set Document_Type=?, Country_of_Issue=?, Document_Number=?, Issueing_Authority=?, Issue_Date=?, Expiry_Date=?, Additional_Information=?, Document_Identifier=?, Document_Path=?, Record_Updated_Date=? where individual_id=? and Document_ID=?";

	public static final String SQL_SELECT_DOCUMENT_BY_INDIVIDUAL_ID = "SELECT Document_ID, Individual_ID, Document_Type, Country_of_Issue, Document_Number, Issueing_Authority, Issue_Date, Expiry_Date, Additional_Information, Document_Identifier, Document_Path from document_master where Individual_ID=?";

}
