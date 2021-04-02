package com.nucigent.elms.common.service;

public interface OtpService {
	
	public Integer generateOtp(String key);
	public boolean validateOTP(String key, Integer otpNumber);
	 public Integer countOTP(String key);
    

}
