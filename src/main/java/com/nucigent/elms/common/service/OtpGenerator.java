package com.nucigent.elms.common.service;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class OtpGenerator {
	
	private static final Integer EXPIRE_MIN = 10;
    private LoadingCache<String, Integer> otpCache;

    private final Logger LOGGER = LoggerFactory.getLogger(OtpServiceImpl.class);

    /**
     * Constructor configuration.
     */
    public OtpGenerator()
    {
        super();
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    /**
     * Method for generating OTP and put it in cache.
     *
     * @param key - cache key
     * @return cache value (generated OTP number)
     */
    public Integer generateOTP(String key)
    {
        Random random = new Random();
        int OTP = 100000 + random.nextInt(900000);
        otpCache.put(key, OTP);

        return OTP;
    }

     public Integer countOTP(String key)
    {

        //Random random = new Random();
        int OTPCount = 1;
        if(getOPTByKey(key) != null){
            LOGGER.info(" insidecountOTP"+OTPCount);
            OTPCount= getOPTByKey(key) + 1;
            
        }
        
        otpCache.put(key, OTPCount);
        LOGGER.info(" insidecountOTP11"+OTPCount);
        return OTPCount;
    }

    /**
     * Method for getting OTP value by key.
     *
     * @param key - target key
     * @return OTP value
     */
    public Integer getOPTByKey(String key)
    {
        try {
            return otpCache.get(key);
        }
        catch (ExecutionException e) {
            return -1;
        }
    }

    /**
     * Method for removing key from cache.
     *
     * @param key - target key
     */
    public void clearOTPFromCache(String key) {
        otpCache.invalidate(key);
    }

}
