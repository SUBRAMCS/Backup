package com.nucigent.elms.user.helper;

public class UserQueryConstants {
	
	public static final String SQL_INDIVIDUAL_MASTER_INSERT = "insert into Individual_Master(Individual_ID,Registration_ID, First_Name, Last_Name, DOB, mother_maiden_name) values(NEXT VALUE FOR INDIVIDUAL_MASTER_SEQ,?,?,?,?,?)";
	public static final String SQL_PHONE_MASTER_INSERT = "insert into Phone_Master(Phone_ID,Individual_ID,Country_Code,Country_Id,Phone_Number, isPrimary) values(NEXT VALUE FOR PHONE_MASTER_SEQ,?,?,?,?,?)";
	public static final String SQL_EMAIL_MASTER_INSERT = "insert into Email_Master(Email_ID,Individual_ID,Email_Address,isPrimary,IsActive) values(NEXT VALUE FOR EMAIL_MASTER_SEQ,?,?,?,'Y')";
	public static final String SQL_REGISTRATION_ID = "select Registration_ID from Register_User WHERE User_login_ID = ?";
	public static final String SQL_INDIVIDUAL_ID = "select Individual_ID from Individual_Master WHERE Registration_ID = ?";
	public static final String SQL_EMAIL_EXISTS = "SELECT count(*) from Register_User WHERE LOWER(User_login_ID) = ? ";
	public static final String FETCH_ALL_USER_EMAILS = "SELECT User_login_ID from Register_User";
	public static final String SQL_TOKEN_EXISTS = "select Rem_Token_Expary_DateTime from Confirmation_Token where Confirmation_Token= ?";
	public static final String SQL_TOKEN_REGISTRATION_ID = "select Registration_ID from Confirmation_Token where Confirmation_Token= ?";
	public static final String SQL_INSERT_TOKEN = "insert into Confirmation_Token(Token_ID,Registration_ID,User_login_ID,Confirmation_Token,Confirmation_Token_Link,Rem_Token_Expary_DateTime) values(NEXT VALUE FOR CONFIRM_TOKEN_SEQ,?,?,?,?,?)";
	public static final String SQL_INSERT_REGISTER_USER = "insert into register_user(Registration_ID,User_login_ID,User_password,DOB,Country_Code,Mobile_Number) values(NEXT VALUE FOR REGISTER_USER_SEQ ,?,?,?,?,?)"; 
	public static final String SQL_INSERT_ACCOUNT_PROFILE = "insert into account_profile(Account_Profile_ID,Registration_ID, Individual_ID) values(NEXT VALUE FOR ACCOUNT_PROFILE_SEQ,?,?)";

	

	public static final String SQL_LOGIN_FIND_BY_ACTIVE_USER_REGISTERED = "select * from Register_User WHERE User_login_ID = ? AND IsActive = 'Y'";
	public static final String SQL_LOGIN_FIND_BY_USER_REGISTERED = "select * from Register_User WHERE User_login_ID = ?";
	public static final String SQL_INSERT_RESET_TOKEN = "insert into reset_pwd_token(Registration_ID, User_login_ID, reset_Token, reset_Token_Link, reset_Token_Expary_DateTime) values(?,?,?,?,?)";
	
	public static final String SQL_SELECT_LOGIN_ID_BY_RESET_TOKEN = "select registration_id, user_login_id, reset_token, reset_Token_Create_DateTime, reset_Token_Expary_DateTime from reset_pwd_token WHERE reset_token = ?";
	
	public static final String SQL_UPDATE_PWD = "update register_user set user_password=? WHERE User_login_ID = ?";
	
	public static final String SQL_UPDATE_RESET_TOKEN = "update  set reset_token = null and reset_Token_Link = null WHERE User_login_ID = ?";
	public static final String SQL_UPDATE_REGISTRATION_TOKEN = "updareset_pwd_tokente Register_User set isActive = 'Y' and reset_Token_Link = null WHERE User_login_ID = ?";
	
	public static final String SQL_DELETE_RESET_TOKEN = "delete from reset_pwd_token WHERE User_login_ID = ?";
	public static final String SQL_DELETE_REGISTRATION_TOKEN = "delete from Confirmation_Token WHERE Confirmation_Token = ?";
	public static final String SQL_UPDATE_IS_ACTIVE = "UPDATE Register_User SET IsActive = 'Y' WHERE Registration_ID= ?";
	public static final String SQL_UPDATE_IS_EMAIL_MASTER = "UPDATE Email_Master SET IsActive = 'Y' WHERE Individual_ID= ?";
	public static final String SQL_GET_INDV_ID = "select Individual_ID from  Individual_Master  WHERE Registration_ID= ?";
	public static final String SQL_UPDATE_IS_ACTIVE_INDIVIDUAL = "UPDATE Individual_Master SET IsActive = 'Y' WHERE Registration_ID= ?";
	public static final String SQL_UPDATE_IS_PHONE_MASTER = "UPDATE Phone_Master SET IsActive = 'Y' WHERE Individual_ID= ?";
	public static final String SQL__SELECT_LOGINID__BY_DOB_AND_QUESTION_AND_LASTNAME = "select ru.Registration_ID, ru.User_login_ID, ru.DOB, im.last_name, im.mother_maiden_name from Register_User ru, Individual_Master im where ru.Registration_ID = im.Registration_ID and ru.dob=? and im.last_name=? and im.mother_maiden_name=?";
	public static final String SQL_USER_ATTEMPTS_INSERT="insert into User_Attempts(id,username) values(?,?)";
	public static final String SQL_SELECT_USER_ATTEMPTS = "SELECT attempts FROM USER_ATTEMPTS WHERE username = ?";
	public static final String SQL_SELECT_USER_ATTEMPTS_COUNT = "SELECT count(id) FROM USER_ATTEMPTS WHERE username = ?";
	public static final String SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS = "UPDATE USER_ATTEMPTS SET attempts = attempts + 1, lastModified=? WHERE username = ?";
	public static final String SQL_UPDATE_TIMESTAMP_IN_USER_ATTEMPTS = "UPDATE USER_ATTEMPTS SET lastModified=? WHERE username = ?";
	public static final String SQL_USER_ATTEMPTS_RESET_ATTEMPTS = "UPDATE USER_ATTEMPTS SET attempts = 0 WHERE username = ?";
	public static final String SQL_USERS_COUNT = "SELECT count(*) FROM register_user WHERE user_login_id = ?";
	public static final String SQL_IS_ACTIVE = "SELECT IsActive FROM Register_User WHERE User_login_ID = ?";
	public static final String SQL_IS_LOCKED = "SELECT AccountLocked FROM Register_User WHERE User_login_ID = ?";
	public static final String SQL_USERS_UPDATE_LOCKED = "UPDATE Register_User SET AccountLocked = 'Y' WHERE User_login_ID = ?";
	public static final String SQL_USERS_ACCOUNT_UNLOCK = "UPDATE Register_User SET AccountLocked = 'N' WHERE User_login_ID = ?";
	public static final String SQL_INSERT_FAILED_ATTEMPTS = "insert into USER_ATTEMPTS(username, attempts) values(?, ?)";
	public static final String SQL_SELECT_LAST_ATTEMPT_TIME = "SELECT lastModified FROM user_attempts WHERE username = ?";
	public static final String SQL_SELECT_ISACTIVE_BY_REGISTERED_ID = "SELECT im.IsActive FROM individual_master im, register_user ru where im.Registration_ID = ru.Registration_ID and ru.registration_id=?";
	public static final String SQL_USER_BY_REGISTRATION_TOKEN = "select ru.User_login_ID, ru.Registration_ID, im.Individual_ID, ct.Rem_Token_Expary_DateTime from Register_User ru inner join Confirmation_Token ct on ru.Registration_ID = ct.Registration_ID inner join Individual_Master im on ru.Registration_ID = im.Registration_ID where ct.Confirmation_Token = ?;";
	
}
