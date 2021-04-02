package com.nucigent.elms.user.service;



import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.nucigent.elms.exception.BusinessException;
import com.nucigent.elms.user.domain.PasswordResetToken;
import com.nucigent.elms.user.domain.RegistrationToken;
import com.nucigent.elms.user.domain.User;
import com.nucigent.elms.user.dto.ForgotLoginDto;

public interface UserService {

	public void authenticateUser(User user);

	public long reigsterUser(User user) throws BusinessException;
	public boolean findByEmailIdIgnoreCase(String email) throws BusinessException;
	public long saveEmailToken(RegistrationToken registrationToken) throws BusinessException;
	public boolean checkToken(String confirmationToken) throws BusinessException;
	public long getUserId(String userName) throws BusinessException;
	public void forgotPwd(User user) throws BusinessException;
	public void resetPwd(User user) throws BusinessException;
	public User findByUserName(String userName) throws BusinessException;
	public void otpGeneration(User user) throws BusinessException;
	public void otpConfirm(User user) throws BusinessException;

	public User findUserByEmail(String email) throws BusinessException;
	public User findUserByUsername(String username) throws BusinessException;   	

	public boolean isUserExists(String username) throws BusinessException;
	public User findUserByResetToken(String resetToken)throws BusinessException;

	public String saveResetToken(PasswordResetToken passwordResetToken) throws BusinessException;
	public void resetToken(String resetToken, String password) throws BusinessException;
	String validateDobAndGetUserId(ForgotLoginDto forgotLoginDto) throws BusinessException;
	//public String findPhoneNoByUsername(String username);
	public boolean isActive(String username) throws BusinessException;
	public long getUserAttempts(String username) throws BusinessException;
	public void updateUserAttempts(String username) throws BusinessException;
	public void resetFailAttempts(String username) throws BusinessException;
	public boolean isLocked(String username) throws BusinessException;
	public void lockAccount(String username) throws BusinessException;
	public void unlockAccount(String username) throws BusinessException;
	public boolean isUserAlreadyTriedFailedAttempts(String username) throws BusinessException;
	void insertFailedAttempts(String username) throws BusinessException;
	void updateTimeStampInUserAttempts(String username) throws BusinessException;
	Date getLastModifiedTime(String username) throws ParseException;
	List<String> fetchAllUsers() throws BusinessException;
}