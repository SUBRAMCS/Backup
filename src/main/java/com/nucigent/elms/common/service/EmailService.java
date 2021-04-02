package com.nucigent.elms.common.service;

public interface EmailService {
	
	//public void sendEmail();
	public void sendRegistrationEmail(String tokenId,String emailTo);
	
	public void sendOtp(Integer otpId,String emailTo);

	public void sendRestPwdEmail(String token, String email);
	public void sendUserName(String userName,String emailTo);
	
	public void sendSuccessfulRegnEmail(String emailTo);
}
