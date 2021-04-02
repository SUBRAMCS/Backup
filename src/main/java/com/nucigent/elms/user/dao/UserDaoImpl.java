package com.nucigent.elms.user.dao;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.nucigent.elms.common.util.DateUtil;
import com.nucigent.elms.user.domain.EmailInfo;
import com.nucigent.elms.user.domain.PasswordResetToken;
import com.nucigent.elms.user.domain.PhoneInfo;
import com.nucigent.elms.user.domain.RegistrationToken;
import com.nucigent.elms.user.domain.ResetPassword;
import com.nucigent.elms.user.domain.User;
import com.nucigent.elms.user.domain.UserInfo;
import com.nucigent.elms.user.dto.ForgotLoginDto;
import com.nucigent.elms.user.helper.UserQueryConstants;

@Repository
public class UserDaoImpl implements UserDao {

	Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;


	@Override
	public long registerUser(User user) {

		UserInfo userInfo = user.getUserInfo();
		PhoneInfo phoneInfo = user.getPhoneInfo();
		EmailInfo emailInfo = user.getEmailInfo();
		logger.info("user id"+user.getUsername());
		logger.info("first name"+user.getFirstName());

		// insert into registerUser table
		jdbcTemplate.update(UserQueryConstants.SQL_INSERT_REGISTER_USER, user.getUsername(), user.getPassword(), user.getDateOfBirth(),phoneInfo.getCountryCode(),phoneInfo.getPhoneNumber());

		//getting the primary key of registered User
		long userId = jdbcTemplate.queryForObject(UserQueryConstants.SQL_REGISTRATION_ID, new Object[] {user.getUsername()}, long.class);

		// inserting into individual_master table
		jdbcTemplate.update(UserQueryConstants.SQL_INDIVIDUAL_MASTER_INSERT, userId, userInfo.getFirstName(), userInfo.getLastName(),
				userInfo.getDateOfBirth(), userInfo.getMotherMaidenName());

		long individualId = jdbcTemplate.queryForObject(UserQueryConstants.SQL_INDIVIDUAL_ID, new Object[] {userId},
				long.class);

		// insert into phoneMaster table
		jdbcTemplate.update(UserQueryConstants.SQL_PHONE_MASTER_INSERT, individualId, phoneInfo.getCountryCode(), phoneInfo.getCountryId(), phoneInfo.getPhoneNumber(), "Y");

		// insert into emailMaster table
		jdbcTemplate.update(UserQueryConstants.SQL_EMAIL_MASTER_INSERT, individualId, emailInfo.getEmailAddress(), "Y");

		// insert into User_Attempts table
		jdbcTemplate.update(UserQueryConstants.SQL_USER_ATTEMPTS_INSERT, userId, emailInfo.getEmailAddress());
		//SQL_INSERT_ACCOUNT_PROFILE
		jdbcTemplate.update(UserQueryConstants.SQL_INSERT_ACCOUNT_PROFILE,userId,individualId);

		return userId;
	}

	@Override
	public boolean emailExists(String email) {
		boolean result = false;
		int ifExists = jdbcTemplate.queryForObject(UserQueryConstants.SQL_EMAIL_EXISTS, new Object[] { email },
				Integer.class);
		if (ifExists == 1) 
			result = true;
		return result;
	}
	
	@Override
	public List<String> fetchAllUsers(){
		return jdbcTemplate.queryForList(UserQueryConstants.FETCH_ALL_USER_EMAILS, String.class);
	}

	@Override
	public long saveEmailToken(RegistrationToken registrationToken) {
		jdbcTemplate.update(UserQueryConstants.SQL_INSERT_TOKEN, registrationToken.getUserId(),registrationToken.getUserLoginId(),
				registrationToken.getConfirmationToken(), registrationToken.getConfirmationTokenLink(),registrationToken.getTokenExpireDate());
		return 1;
	}

	@Override
	public RegistrationToken checkToken(String token) {

		Date currDate = DateUtil.getCurrentDate();
		
		RegistrationToken registrationToken = jdbcTemplate.queryForObject(UserQueryConstants.SQL_USER_BY_REGISTRATION_TOKEN,
				(rs, rowNum) -> {
					RegistrationToken regnToken = new RegistrationToken();
					regnToken.setUserId(rs.getLong("Registration_ID"));
					regnToken.setUserLoginId(rs.getString("User_login_ID"));
					regnToken.setUserIndividualId(rs.getLong("Individual_ID"));
					regnToken.setTokenExpireDate(rs.getString("Rem_Token_Expary_DateTime"));
					
					return regnToken;
				},token);
		
		Date tokenExpiryDate = DateUtil.parseDate(registrationToken.getTokenExpireDate(), "yyyy-MM-dd HH:mm:ss");
		
		registrationToken.setValid(false);
		
		if (tokenExpiryDate != null) {
			long diff = tokenExpiryDate.getTime()-currDate.getTime();
			
			if(diff>0) {
				long hrs = diff / (60 * 60 * 1000) % 24;
				logger.info("Diff between curr date and expiry date  is",hrs);
				jdbcTemplate.update(UserQueryConstants.SQL_DELETE_REGISTRATION_TOKEN, token);
				jdbcTemplate.update(UserQueryConstants.SQL_UPDATE_IS_ACTIVE, registrationToken.getUserId());
				jdbcTemplate.update(UserQueryConstants.SQL_UPDATE_IS_ACTIVE_INDIVIDUAL, registrationToken.getUserId());
				jdbcTemplate.update(UserQueryConstants.SQL_UPDATE_IS_EMAIL_MASTER, registrationToken.getUserIndividualId());
				jdbcTemplate.update(UserQueryConstants.SQL_UPDATE_IS_PHONE_MASTER, registrationToken.getUserIndividualId());
				
				registrationToken.setValid(true);
			}
		}

		return registrationToken;
	}

	@Override
	public void authenticateUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void forgotPwd(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetPwd(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void otpGeneration(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void otpConfirm(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User findUserByEmail(String email) {
		return jdbcTemplate.queryForObject(UserQueryConstants.SQL_LOGIN_FIND_BY_USER_REGISTERED,
				(rs, rowNum) -> {
					User user = new User();
					user.setId(rs.getLong("Registration_ID"));
					user.setUsername(rs.getString("User_login_ID"));
					return user;
				},email);
	}

	@Override
	public User findUserByResetToken(String resetToken) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String saveResetToken(PasswordResetToken passwordResetToken) {
		jdbcTemplate.update(UserQueryConstants.SQL_INSERT_RESET_TOKEN, passwordResetToken.getUser().getId(),passwordResetToken.getUser().getUsername(),
				passwordResetToken.getToken(), passwordResetToken.getToken(),passwordResetToken.getExpiryDate());
		return null;
	}
	@Override
	public User findByUserName(String userName) {
		return jdbcTemplate.queryForObject(UserQueryConstants.SQL_LOGIN_FIND_BY_USER_REGISTERED,
				(rs, rowNum) -> {
					User user = new User();
					user.setId(rs.getLong("Registration_ID"));
					user.setUsername(rs.getString("User_login_ID"));
					user.setPassword(rs.getString("User_password"));
					user.setIsActive(rs.getString("isActive"));
					user.setAccountLocked(rs.getString("AccountLocked"));
					//user.setLockedExpiryDate(rs.getString("LockedExpiryDate"));
					return user;
				},userName);
	}

	@Override
	public boolean findByEmailIdIgnoreCase(String email) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetToken(String resetToken, String password) {


		ResetPassword resetPassword = jdbcTemplate.queryForObject(UserQueryConstants.SQL_SELECT_LOGIN_ID_BY_RESET_TOKEN,
				(rs, rowNum) -> {
					ResetPassword resetPwd = new ResetPassword();
					resetPwd.setId(rs.getLong("Registration_ID"));
					resetPwd.setUserLoginId(rs.getString("user_login_id"));
					resetPwd.setResetToken(rs.getString("reset_token"));
					resetPwd.setResetCreationDate(rs.getDate("reset_Token_Create_DateTime"));
					resetPwd.setResetExpiaryDate(rs.getDate("reset_Token_Expary_DateTime"));
					return resetPwd;
				},resetToken);

		jdbcTemplate.update(UserQueryConstants.SQL_UPDATE_PWD, password, resetPassword.getUserLoginId());
		//jdbcTemplate.update(UserQueryConstants.SQL_UPDATE_RESET_TOKEN, resetPassword.getUserLoginId());
		jdbcTemplate.update(UserQueryConstants.SQL_DELETE_RESET_TOKEN, resetPassword.getUserLoginId());

	}

	@Override
	public User getUserByDobAndSecurityQuestionAndLastName(ForgotLoginDto forgotLoginDto) {


		return jdbcTemplate.queryForObject(UserQueryConstants.SQL__SELECT_LOGINID__BY_DOB_AND_QUESTION_AND_LASTNAME,
				(rs, rowNum) -> {
					User user = new User();
					user.setId(rs.getLong("Registration_ID"));
					user.setUsername(rs.getString("User_login_ID"));
					user.setLastName(rs.getString("last_name"));
					user.setDateOfBirth(rs.getString("DOB"));
					user.setMotherMaidenName(rs.getString("mother_maiden_name"));
					return user;
				},forgotLoginDto.getDateOfBirth(), forgotLoginDto.getLastName(), forgotLoginDto.getMotherMaidenName());

	}

	@Override
	public long getUserId(String userName) {
		long userId = jdbcTemplate.queryForObject(UserQueryConstants.SQL_REGISTRATION_ID, new Object[] {userName},
				long.class);
		return userId;
	}

	public boolean isActive(String username) {
		boolean result = false;

		String isActive = jdbcTemplate.queryForObject(
				UserQueryConstants.SQL_IS_ACTIVE, new Object[] { username },String.class);
		if (isActive.equals("Y")) {
			result = true;
		}

		return result;
	}


	@Override
	public boolean isUserExists(String username) {

		boolean result = false;

		int count = jdbcTemplate.queryForObject(
				UserQueryConstants.SQL_USERS_COUNT, new Object[] { username }, Integer.class);
		if (count > 0) {
			result = true;
		}

		return result;
	}


	@Override
	public void resetFailAttempts(String username) {

		jdbcTemplate.update(
				UserQueryConstants.SQL_USER_ATTEMPTS_RESET_ATTEMPTS, new Object[] { username });

	}

	@Override
	public long getUserAttempts(String username) {
		long userAttempts = 0 ;
		userAttempts = jdbcTemplate.queryForObject(UserQueryConstants.SQL_SELECT_USER_ATTEMPTS,new Object[] {username},	long.class);
		return userAttempts;

	}

	@Override
	public void updateUserAttempts(String username, java.sql.Timestamp updatetedDate) {
		jdbcTemplate.update(UserQueryConstants.SQL_USER_ATTEMPTS_UPDATE_ATTEMPTS, new Object[] { updatetedDate, username });
	}

	@Override
	public void lockAccount(String username) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(UserQueryConstants.SQL_USERS_UPDATE_LOCKED, new Object[] { username });
	}

	@Override
	public void unlockAccount(String username) {
		jdbcTemplate.update(UserQueryConstants.SQL_USERS_ACCOUNT_UNLOCK, new Object[] { username });
	}


	@Override
	public boolean isLocked(String username ) {
		boolean result = false;

		String locked = jdbcTemplate.queryForObject(
				UserQueryConstants.SQL_IS_LOCKED, new Object[] { username }, String.class);
		if (locked.equals("Y")) {
			result = true;
		}

		return result;
	}

	@Override
	public boolean isUserAlreadyTriedFailedAttempts(String username) {
		int count = jdbcTemplate.queryForObject(UserQueryConstants.SQL_SELECT_USER_ATTEMPTS_COUNT, new Object[] { username }, Integer.class);
		return count > 0 ? true : false;
	}

	@Override
	public void insertFailedAttempts(String username) {
		jdbcTemplate.update(UserQueryConstants.SQL_INSERT_FAILED_ATTEMPTS, username, 1);

	}

	@Override
	public void updateTimeStampInUserAttempts(String username, java.sql.Timestamp updatetedDate) {
		jdbcTemplate.update(UserQueryConstants.SQL_UPDATE_TIMESTAMP_IN_USER_ATTEMPTS, new Object[] { updatetedDate, username });
	}

	@Override
	public java.sql.Timestamp getLastModifiedTime(String username) {
		return jdbcTemplate.queryForObject(UserQueryConstants.SQL_SELECT_LAST_ATTEMPT_TIME, new Object[] { username }, java.sql.Timestamp.class);
	}
	
	@Override
	public String getIsActiveByRegisteredId(long registeredId) {
		return jdbcTemplate.queryForObject(UserQueryConstants.SQL_SELECT_ISACTIVE_BY_REGISTERED_ID, new Object[] { registeredId }, String.class);
	}

}