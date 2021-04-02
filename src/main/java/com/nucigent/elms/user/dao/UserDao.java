package com.nucigent.elms.user.dao;

import java.util.List;

import com.nucigent.elms.user.domain.PasswordResetToken;
import com.nucigent.elms.user.domain.RegistrationToken;
import com.nucigent.elms.user.domain.User;
import com.nucigent.elms.user.dto.ForgotLoginDto;

public interface UserDao {

	public void authenticateUser(User user);


	public long registerUser(User user);
	public boolean findByEmailIdIgnoreCase(String email);
	public long saveEmailToken(RegistrationToken registrationToken);
	public RegistrationToken checkToken(String confirmationToken);
	public boolean emailExists(String email);
	public void resetToken(String resetToken, String password);
	public void forgotPwd(User user);
	public void resetPwd(User user);
	User getUserByDobAndSecurityQuestionAndLastName(ForgotLoginDto forgotLoginDto);
	public void otpGeneration(User user);
	public void otpConfirm(User user);
	public long getUserId(String userName);
	public User findUserByEmail(String email);
	public User findUserByResetToken(String resetToken);
	String saveResetToken(PasswordResetToken passwordResetToken);
	public User findByUserName(String userName);
	public boolean isUserExists(String username);
	public void resetFailAttempts(String username);
	public long getUserAttempts(String username);
	public void updateUserAttempts(String username, java.sql.Timestamp updatetedDate);
	public void lockAccount(String username);
	public boolean isActive(String username);
	public boolean isLocked(String username);
	public void unlockAccount(String username);
	public boolean isUserAlreadyTriedFailedAttempts(String username);
	void insertFailedAttempts(String username);
	void updateTimeStampInUserAttempts(String username, java.sql.Timestamp updatetedDate);
	java.sql.Timestamp getLastModifiedTime(String username);
	String getIsActiveByRegisteredId(long registeredId);
	List<String> fetchAllUsers();
}
