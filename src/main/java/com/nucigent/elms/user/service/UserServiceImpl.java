package com.nucigent.elms.user.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nucigent.elms.common.service.EmailService;
import com.nucigent.elms.countrycode.service.CountryCodeService;
import com.nucigent.elms.exception.BusinessException;
import com.nucigent.elms.user.dao.UserDao;
import com.nucigent.elms.user.domain.EmailInfo;
import com.nucigent.elms.user.domain.PasswordResetToken;
import com.nucigent.elms.user.domain.PhoneInfo;
import com.nucigent.elms.user.domain.RegistrationToken;
import com.nucigent.elms.user.domain.User;
import com.nucigent.elms.user.domain.UserInfo;
import com.nucigent.elms.user.dto.ForgotLoginDto;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserDao userDao;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	EmailService emailService;
	
	@Autowired
	CountryCodeService countryCodeService;

	@Override
	public void authenticateUser(User user) {
		userDao.authenticateUser(user);
	}
	

	@Override
	@Transactional
	public long reigsterUser(User user) throws BusinessException {

		user.setUsername(user.getEmail());
		System.out.println(user.getEmail());

		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		UserInfo userInfo = new UserInfo();
		EmailInfo emailInfo = new EmailInfo();
		PhoneInfo phoneInfo = new PhoneInfo();

		userInfo.setFirstName(user.getFirstName());
		userInfo.setLastName(user.getLastName());
		userInfo.setDateOfBirth(user.getDateOfBirth());
		userInfo.setMotherMaidenName(user.getMotherMaidenName());
		emailInfo.setEmailAddress(user.getEmail());
		System.out.println(user.getMotherMaidenName());
		phoneInfo.setPhoneNumber(user.getPhoneNo());
		phoneInfo.setCountryCode("44");
		//phoneInfo.setCountryId(countryCodeService.getCountryIdByCountryPhoneCodeAndCountry(44, "London").getCountryId());

		user.setUserInfo(userInfo);
		user.setEmailInfo(emailInfo);
		user.setPhoneInfo(phoneInfo);

		// user.setPasswordHashingAlgorithim("SHA-256");
		try {
			return userDao.registerUser(user);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("User registration failed");
		}

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
	public User findUserByEmail(String email) throws BusinessException {

		User user = null;
		try {
			user = userDao.findUserByEmail(email);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to find user with email: " + email + " or user is inactive");
		}

		return user;
	}

	@Override
	public User findUserByResetToken(String resetToken) {
		// TODO Auto-generated method stub
		return userDao.findUserByResetToken(resetToken);
	}


	@Override
	public String saveResetToken(PasswordResetToken passwordResetToken) throws BusinessException {
		try {
			return userDao.saveResetToken(passwordResetToken);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to save password reset token.");
		}

	}

	@Override
	public boolean findByEmailIdIgnoreCase(String email) throws BusinessException {
		try {
			return userDao.emailExists(email);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to find user with email: "+email);
		}

	}
	
	@Override
	public List<String> fetchAllUsers() throws BusinessException {
		try {
			return userDao.fetchAllUsers();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to fetch all users: ");
		}
	}

	@Override
	public long saveEmailToken(RegistrationToken registrationToken) throws BusinessException {

		try {
			return userDao.saveEmailToken(registrationToken);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to save registration confirmation token");
		}

	}

	@Override
	public boolean checkToken(String confirmationToken) throws BusinessException {
		
		RegistrationToken registrationToken = null;
		try {
			registrationToken = userDao.checkToken(confirmationToken);
			
			if(registrationToken.isValid()) {
				emailService.sendSuccessfulRegnEmail(registrationToken.getUserLoginId());
			}
			
			return registrationToken.isValid();
		} catch (Exception e) {
			logger.error(e.getMessage());
			if(registrationToken != null && registrationToken.isValid()) {
				throw new BusinessException("User registartion confirmation successful. Email confirming successful registration could not be sent.");
			}
			throw new BusinessException("User registartion confirmation failed during token validation");
		}

	}

	@Override
	public User findUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void resetToken(String resetToken, String password) throws BusinessException {
		try {
			userDao.resetToken(resetToken, password);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Password reset failed");
		}

	}

	@Override
	public String validateDobAndGetUserId(ForgotLoginDto forgotLoginDto) throws BusinessException {
		User user = null;
		try {
			user = userDao.getUserByDobAndSecurityQuestionAndLastName(forgotLoginDto);

		} catch (Exception e) {
			throw new BusinessException("User Not Found for the given data");
		}

		return user.getUsername();
	}

	@Override
	public long getUserId(String userName) throws BusinessException {
		try {
			return userDao.getUserId(userName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to find user with username: "+userName);
		}

	}

	@Override
	public boolean isUserExists(String username) throws BusinessException {
		boolean userExists = false;
		try {
			userExists = userDao.isUserExists(username);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to find user with username: "+username);
		}
		return userExists;
	}

	@Override
	public boolean isActive(String username) throws BusinessException {
		boolean userIsActive = false;
		try {
			userIsActive = userDao.isActive(username);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to find active user with username: "+username);
		}
		return userIsActive;
	}

	@Override
	public long getUserAttempts(String username) throws BusinessException {
		long userAttempts = 0;
		try {
			userAttempts = userDao.getUserAttempts(username);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to find login attempts for user with username: "+username);
		}
		return userAttempts;
	}

	@Override
	public void updateUserAttempts(String username) throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		//Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());
		try {
			userDao.updateUserAttempts(username, updatetedDate);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Update failed while incrementing the login attempts count for user with username: "+username);
		}
	}

	@Override
	public void resetFailAttempts(String username) throws BusinessException {
		try {
			userDao.resetFailAttempts(username);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Update failed while resetting the login attempts count for user with username: "+username);
		}
	}

	@Override
	public boolean isLocked(String username) throws BusinessException {
		boolean isLocked = false;
		try {
			isLocked = userDao.isLocked(username);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Check for account locked status failed for user with username: "+username);
		}
		return isLocked;
	}

	@Override
	public void lockAccount(String username) throws BusinessException {
		try {
			userDao.lockAccount(username);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Account lock failed for user with username: "+username);
		}
	}

	@Override
	public User findByUserName(String userName) throws BusinessException {
		User user = null;
		try {
			user = userDao.findByUserName(userName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to find user with username: " + userName + " or user is inactive");
		}
		
		return user;
	}

	@Override
	public void unlockAccount(String username) throws BusinessException {
		try {
			userDao.unlockAccount(username);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Update failed while unlockign the account for user with username: "+username);
		}
	}

	@Override
	public boolean isUserAlreadyTriedFailedAttempts(String username) throws BusinessException {
		boolean failedLoginAttemptsMade = false;
		try {
			failedLoginAttemptsMade = userDao.isUserAlreadyTriedFailedAttempts(username);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Unable to find login attempts for user with username: "+username);
		}
		return failedLoginAttemptsMade;
	}

	@Override
	public void insertFailedAttempts(String username) throws BusinessException {
		try {
			userDao.insertFailedAttempts(username);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Insert for failed login attempts failed for user with username: "+username);
		}
	}

	@Override
	public void updateTimeStampInUserAttempts(String username) throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		java.sql.Timestamp updatetedDate = new java.sql.Timestamp(calendar.getTime().getTime());
		try {
			userDao.updateTimeStampInUserAttempts(username, updatetedDate);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new BusinessException("Update failed for last login attempt for user with username: "+username);
		}

	}

	@Override
	public java.util.Date getLastModifiedTime(String username) throws ParseException {
		java.sql.Timestamp sqlDate =  userDao.getLastModifiedTime(username);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		java.util.Date date = new java.util.Date(sqlDate.getTime());
		String formattedDate = dateFormat.format(date);
		return dateFormat.parse(formattedDate);
	}

}