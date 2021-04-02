package com.nucigent.elms.account.helper;

public class AccountQueryConstants {

	public static final String SQL_ACCOUNT_GET_ALL_ = "select * from account";
	public static final String SQL_ACCOUNT_FIND_BY_ID = "select * from account where id = ?";
	public static final String SQL_ACCOUNT_INSERT = "insert into account(name, balance_amount) values(?,?)";
	public static final String SQL_ACCOUNT_UPDATE = "update account set name = ?, balance_amount =? where id = ? ";
	public static final String SQL_ACCOUNT_DELETE = "delete from account where id = ?";


	public static final String SQL_INSERT_ACCOUNT_PROFILE = "insert into account_profile(Registration_ID, Individual_ID, Address_ID, Document_ID) values(:Registration_ID, :Individual_ID, :Address_ID, :Document_ID)";

	public static final String SQL_UPDATE_INDIVIDUAL_MASTER_ALL_INFO = "update individual_master set title=?, first_name=?, middle_name=?, last_name=?, dob=?, gender=?, Record_Updated_Date=? where individual_id=?";
	
	public static final String SQL_UPDATE_INDIVIDUAL_MASTER_EDITABLE_INFO = "update individual_master set title=?, middle_name=?, gender=?, Record_Updated_Date=? where individual_id=?";

	public static final String SQL_SELECT_ACCOUNT_PROFILE_BY_REGISTERED_ID = "select count(Account_Profile_ID) from account_profile WHERE Registration_ID = ?";

	public static final String SQL_INSERT_ADDRESS_DETAIL = "insert into address_master(Individual_ID, Address_Line1, Address_Line2, Address_Line3, Address_Line4, County_State, PostCode, Country) values(:Individual_ID, upper(:Address_Line1), upper(:Address_Line2), upper(:Address_Line3), upper(:Address_Line4), upper(:County_State), :PostCode, :Country)";

	public static final String SQL_UPDATE_ACCOUNT_PROFILE_BY_ID = "update account_profile set Address_ID=?, Record_Updated_Date=? where Account_Profile_ID=?";

	public static final String SQL_INSERT_EMAIL_ADDRESS = "insert into email_master (Individual_ID, Email_Address, isPrimary) values (?, ?, ?)";

	public static final String SQL_INSERT_PHONE_NUMBER = "insert into phone_master (Individual_ID, Country_Id, Phone_Number, isPrimary) values (?, ?, ?, ?)";

	public static final String SQL_SELECE_ACCOUNT_PROFILE_BY_ID = "select Account_Profile_ID, Registration_ID, Individual_ID, Address_ID, Document_ID from account_profile where Account_Profile_ID=?";

	public static final String SQL_UPDATE_ADDRESS_DETAIL_BY_ADDRESS_ID = "update address_master set Address_Line1=upper(?), Address_Line2=upper(?), Address_Line3=upper(?), Address_Line4=upper(?), County_State=upper(?), PostCode=?, Country=?, Record_Updated_Date=? where Address_ID=? and Individual_ID=?";

	public static final String SQL_UPDATE_PRIMARY_EMAIL_BY_INDIVIDUAL_ID = " update email_master set Email_Address=?, Record_Updated_Date=? where IsPrimary='Y' and Individual_ID=? ";
	
	public static final String SQL_UPDATE_SECONDARY_EMAIL_BY_INDIVIDUAL_ID = " update email_master set Email_Address=?, Record_Updated_Date=? where IsPrimary='N' and Individual_ID=? ";

	public static final String SQL_UPDATE_PRIMARY_PHONE_NUMBER_BY_INDIVIDUAL_ID = " update phone_master set Country_Code=?, Phone_Number=?, Record_Updated_Date=? where IsPrimary='Y' and Individual_ID=? ";
	
	public static final String SQL_UPDATE_SECONDARY_PHONE_NUMBER_BY_INDIVIDUAL_ID = " update phone_master set Country_Id=?, Phone_Number=?, Record_Updated_Date=? where IsPrimary='N' and Individual_ID=? ";

	public static final String SQL_SELECT_PERSONAL_DETAIL_BY_ID = "select im.Individual_ID, im.Title, im.First_Name, im.Middle_Name, im.Last_Name, im.DOB, im.Gender, im.mother_maiden_name, im.Registration_ID, pm.Phone_Number, pm.country_code, em.Email_ID from individual_master im LEFT JOIN  phone_master pm on im.Individual_ID = pm.Individual_ID LEFT JOIN email_master em on im.Individual_ID = em.Individual_ID where im.Individual_ID=? and pm.IsPrimary = 'Y' and  em.IsPrimary = 'Y'";

	public static final String SQL_SELECT_ADDRESS_DETAIL_BY_INDIVIDUAL_ID = "select Address_ID, Individual_ID, Address_Line1, Address_Line2, Address_Line3, Address_Line4, County_State, PostCode, Country from address_master where Individual_ID=?";

	public static final String SQL_SELECT_EMAIL_ADDRESS_BY_INDIVIDUAL_ID = "select Email_ID, Individual_ID, Email_Address, isPrimary from email_master where Individual_ID=?";

	public static final String SQL_SELECT_PHONE_DETAIL_BY_INDIVIDUAL_ID = "select pm.Phone_ID, pm.Individual_ID, pm.Country_Id, cc.Country_ph_code, pm.Phone_Number, pm.isPrimary from phone_master pm, country_code cc where pm.country_id = cc.country_id and pm.Individual_ID=?";

	public static final String SQL_SELECT_ACCOUNT_PROFILE_BY_USER_LOGIN_ID = "select ru.registration_id, ap.Individual_ID, ap.Account_Profile_ID, ap.Address_ID, ap.Document_ID from register_user ru left join account_profile ap on ru.registration_id = ap.registration_id where ru.User_login_ID=?" ;
	
	public static final String SQL_SELECT_INDIVIDUAL_ID_BY_REGISTERED_ID = "SELECT im.Individual_ID FROM individual_master im, register_user ru where im.Registration_ID = ru.Registration_ID and ru.registration_id=?";

}
