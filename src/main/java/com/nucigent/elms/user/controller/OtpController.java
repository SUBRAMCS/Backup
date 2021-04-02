package com.nucigent.elms.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nucigent.elms.common.service.EmailService;
import com.nucigent.elms.common.service.OtpService;
import com.nucigent.elms.common.util.UserDetail;
import com.nucigent.elms.user.dto.OtpDto;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")

public class OtpController {

	Logger logger = LoggerFactory.getLogger(UserController.class);


	@Autowired
	private OtpService otpService;
	@Autowired private UserDetail userDetail;
	@Autowired private EmailService emailService;

	@GetMapping(("/otp/resend"))
	public ResponseEntity<?> resendOtp() {

		String username =  userDetail.getUserNameFromSecurityContect();
		if (username == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		logger.info("login username" +username);
		Integer otpId = otpService.generateOtp(username);
		String resendOtpKey = username+"_"+username;
		Integer otpCount = otpService.countOTP(resendOtpKey);
        logger.info("otp key " + resendOtpKey + "otpCount " + otpCount);

		if(otpCount>3){
			return new ResponseEntity<>("OTP  has been already send 3 times", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			emailService.sendOtp(otpId, username);
		} catch (Exception e) {
			return new ResponseEntity<>("Otp Could not be send", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping(("/otp/otpConfirm"))
	public ResponseEntity<?> otpConfirm(@RequestBody OtpDto otpDto) {


		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();

		Map<String, String> response = new HashMap<>(2);

		// check authentication
		if (username == null) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		logger.info("username"+username);

		logger.info("otp no "+Integer.parseInt(otpDto.getOtpNo()));

		// validate provided OTP.
		boolean isValid = otpService.validateOTP(username, Integer.parseInt(otpDto.getOtpNo()));
	//	isValid = true;
		if (!isValid)
		{
			response.put("status", "error");
			response.put("message", "OTP is not valid!");

			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}

		// success message
		response.put("status", "success");
		response.put("message", "Entered OTP is valid!");

		return new ResponseEntity<>(response, HttpStatus.OK);

	}



}
