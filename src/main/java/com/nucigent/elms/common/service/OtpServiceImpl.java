package com.nucigent.elms.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpService {
	
	 private final Logger LOGGER = LoggerFactory.getLogger(OtpServiceImpl.class);

	 @Autowired
	  private OtpGenerator otpGenerator;
	
	public Integer generateOtp(String key)
    {
        // generate otp
        Integer otpValue = otpGenerator.generateOTP(key);
        if (otpValue == -1)
        {
            LOGGER.error("OTP generator is not working...");
            return  0;
        }

        LOGGER.info("Generated OTP: {}", otpValue);
        return otpValue;
       
    }

 public Integer countOTP(String key)
    {
        LOGGER.info("Key OTP: {}", key);
       Integer otpCount = otpGenerator.countOTP(key);
        LOGGER.info("Count OTP: {}", otpCount);
        return otpCount;
    }
    /**
     * Method for validating provided OTP
     *
     * @param key - provided key
     * @param otpNumber - provided OTP number
     * @return boolean value (true|false)
     */
    public boolean validateOTP(String key, Integer otpNumber)
    {
        // get OTP from cache
        Integer cacheOTP = otpGenerator.getOPTByKey(key);
        if (cacheOTP.equals(otpNumber))
        {
            otpGenerator.clearOTPFromCache(key);
            return true;
        }
        return false;
    }
	
}