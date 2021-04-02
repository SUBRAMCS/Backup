package com.nucigent.elms.user.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nucigent.elms.common.service.CaptchaService;
import com.nucigent.elms.common.service.EmailService;
import com.nucigent.elms.common.service.OtpService;
//import com.nucigent.elms.common.util.BusinessException;
import com.nucigent.elms.common.util.CommonUtil;
import com.nucigent.elms.common.util.DateUtil;
import com.nucigent.elms.config.JwtTokenUtil;
import com.nucigent.elms.exception.BusinessException;
import com.nucigent.elms.user.domain.EmailInfo;
//import com.nucigent.elms.user.domain.Mail;
import com.nucigent.elms.user.domain.PasswordResetToken;
import com.nucigent.elms.user.domain.RegistrationToken;
import com.nucigent.elms.user.domain.ResendToken;
import com.nucigent.elms.user.domain.User;
import com.nucigent.elms.user.dto.ForgotLoginDto;
import com.nucigent.elms.user.dto.ForgotPwdDto;
import com.nucigent.elms.user.dto.LoginDto;
import com.nucigent.elms.user.dto.ResetPwdDto;
import com.nucigent.elms.user.dto.TokenDto;
import com.nucigent.elms.user.service.UserService;
import com.sendgrid.SendGrid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CaptchaService captchaService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private EmailService emailService;

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserDetailsService jwtUserDetailsService;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private SendGrid sendGrid;

	@Autowired
	CommonUtil commonUtil;

	private static final int LOCKOUT_TIME = 20;
	private static final int MAX_ATTEMPTS = 5;

	@PostMapping(("/users/authenticate"))
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody LoginDto loginDto) throws Exception {

		logger.info("user name " + loginDto.getUsername());
		Boolean isUserExists = null;
		try {
			isUserExists = userService.isUserExists(loginDto.getUsername());
			if(!isUserExists) {
				return new ResponseEntity<>("Invalid Credentials, Please provide Correct UserName / Password", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

			boolean captchaVerified = true;//captchaService.verify(loginDto.getRecaptchaResponse());
			
			if (!captchaVerified) { 
				return new ResponseEntity<>("Invalid captcha", HttpStatus.INTERNAL_SERVER_ERROR); 
			}

		}
		catch(BadCredentialsException ex) {
			try {
				if(isUserExists) {
					boolean isFailedAttemptsExists = userService.isUserAlreadyTriedFailedAttempts(loginDto.getUsername());
					if(isFailedAttemptsExists){
						long attempts = userService.getUserAttempts(loginDto.getUsername());
						if(attempts < MAX_ATTEMPTS) {
							userService.updateUserAttempts(loginDto.getUsername());
							//return new ResponseEntity<>("Invalid attempt "+attempts, HttpStatus.INTERNAL_SERVER_ERROR);
						}else {
							if(!userService.isLocked(loginDto.getUsername())) {
								userService.lockAccount(loginDto.getUsername());
								userService.updateTimeStampInUserAttempts(loginDto.getUsername());
								//return new ResponseEntity<>("Account is locked. please try with valid credentials", HttpStatus.INTERNAL_SERVER_ERROR);
							}
						}
					} 
					else {
						userService.insertFailedAttempts(loginDto.getUsername());
						//return new ResponseEntity<>("Bad Credentials provided", HttpStatus.INTERNAL_SERVER_ERROR);
					}
					
				}
				return new ResponseEntity<>("Invalid Credentials, Please provide Correct UserName / Password", HttpStatus.INTERNAL_SERVER_ERROR);
			} catch (BusinessException e) {
				return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		catch(LockedException  ex) {
			java.util.Date date = userService.getLastModifiedTime(loginDto.getUsername()) ;
			logger.info("last attempt timestamp:" + date);
			long diff = new java.util.Date().getTime() - date.getTime() ;
			long diffMinutes = diff / (60 * 1000) % 60;
			logger.info("Total time interval in minutes::" + date);
			if(diffMinutes > LOCKOUT_TIME) {
				try {
					userService.unlockAccount(loginDto.getUsername());
					userService.resetFailAttempts(loginDto.getUsername());
				} catch (BusinessException e) {
					return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} else {
				return new ResponseEntity<>("Account is locked, Please try after "+LOCKOUT_TIME+" minutes", HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
		catch(DisabledException de) {
			return new ResponseEntity<>("Account is Disabled", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		catch(Exception e) {
			return new ResponseEntity<>("No active user found", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginDto.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		logger.info("after token");
		logger.info("login username" + loginDto.getUsername());
		Integer otpId = otpService.generateOtp(loginDto.getUsername());

		logger.info("otp cache " + loginDto.getUsername());

		try {
			emailService.sendOtp(otpId, loginDto.getUsername());
		} catch (Exception e) {
			return new ResponseEntity<>("Otp Could not be send", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		TokenDto tokenDto = new TokenDto();
		tokenDto.setTokenId(token);
		tokenDto.setUsername(loginDto.getUsername());
		return ResponseEntity.ok(tokenDto);
	}

	@SuppressWarnings("unchecked")
	@PostMapping(("/users/register"))
	public ResponseEntity<?> reigsterUser(@RequestBody User user) {

		logger.info("Enter : reigsterUser");

		boolean userExists = false;
		long userId;
		EmailInfo emailInfo = null;
		RegistrationToken registrationToken = null;
		String token = null;
		try {
			userExists = userService.findByEmailIdIgnoreCase(user.getEmail().toLowerCase());
			if (userExists) {
				return new ResponseEntity("User Already Exists", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			userId = userService.reigsterUser(user);
			logger.info("userId " + userId);

			emailInfo = user.getEmailInfo();
			registrationToken = new RegistrationToken();
			registrationToken.setUserId(userId);
			registrationToken.setUserLoginId(emailInfo.getEmailAddress());

			token = UUID.randomUUID().toString();
			registrationToken.setConfirmationToken(token);
			registrationToken.setConfirmationTokenLink(token);
			String tokenExpireDate = DateUtil.getExpireTime();
			registrationToken.setTokenExpireDate(tokenExpireDate);

			userService.saveEmailToken(registrationToken);
		} catch (BusinessException e1) {

			return new ResponseEntity(e1.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			emailService.sendRegistrationEmail(token, emailInfo.getEmailAddress());
		} catch (Exception e) {
			logger.error("error<>"+e);
			return new ResponseEntity("Error in sending registration confirmation mail",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		logger.info("Exit : reigsterUser");

		return new ResponseEntity(token, HttpStatus.OK);

	}

	@RequestMapping(value = "/users/resendConfirm", method = { RequestMethod.POST })
	public ResponseEntity<?> resend(@RequestBody ResendToken resendToken) {
		long userId;
		RegistrationToken registrationToken = null;
		String token = null;
		String tokenExpireDate = null;
		try {
			userId = userService.getUserId(resendToken.getUsername());
			registrationToken = new RegistrationToken();
			registrationToken.setUserId(userId);
			registrationToken.setUserLoginId(resendToken.getUsername());
			token = UUID.randomUUID().toString();
			registrationToken.setConfirmationToken(token);
			registrationToken.setConfirmationTokenLink(token);
			tokenExpireDate = DateUtil.getExpireTime();
			registrationToken.setTokenExpireDate(tokenExpireDate);
			userService.saveEmailToken(registrationToken);
		} catch (BusinessException e1) {

			return new ResponseEntity(e1.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try {

			emailService.sendRegistrationEmail(token, resendToken.getUsername());
		} catch (Exception e) {
			return new ResponseEntity("Error in sending registration confirmation mail",
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return new ResponseEntity("Verfication link sent to your Email", HttpStatus.OK);
	}

	@RequestMapping(value = "/users/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {


		boolean tokenProcessed = false;
		try {
			tokenProcessed = userService.checkToken(confirmationToken);
			if (tokenProcessed) {
				return new ResponseEntity("Email Verified. Successful registration mail sent to registered mail id.", HttpStatus.OK);
			}
		} catch (BusinessException e1) {
			return new ResponseEntity(e1.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity("Incorrect Token Or Token is expired", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping(("/users/forgotPassword"))
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPwdDto forgotPwdDto) {

		User user = null;
		PasswordResetToken token = null;
		try {
			user = userService.findUserByEmail(forgotPwdDto.getEmail());
			if (user == null) {
				return new ResponseEntity("User not found / Registration pending.", HttpStatus.INTERNAL_SERVER_ERROR);
			}

			token = new PasswordResetToken();
			token.setToken(UUID.randomUUID().toString());
			token.setUser(user);
			token.setExpiryDate(30);
			userService.saveResetToken(token);
		} catch (BusinessException e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			emailService.sendRestPwdEmail(token.getToken(), forgotPwdDto.getEmail());
		} catch (Exception e) {
			return new ResponseEntity("Email sending failed for password reset",HttpStatus.OK);
		}


		return new ResponseEntity(HttpStatus.OK);

	}

	@PostMapping(("/users/resetPassword"))
	public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPwdDto resetPwdDto) {

		try {
			userService.resetToken(resetPwdDto.getToken(), bCryptPasswordEncoder.encode(resetPwdDto.getPassword()));
		} catch (BusinessException e) {

			return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity("success", HttpStatus.OK);

	}

	@PostMapping(("/users/forgotLogin"))
	public ResponseEntity<String> forgotLoginId(@RequestBody @Valid ForgotLoginDto forgotLoginDto) {
		String username = null;
		try {
			username = userService.validateDobAndGetUserId(forgotLoginDto);
			//emailService.sendUserName(username, username);

		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>("Your username is: "+username, HttpStatus.OK);
	}
	
	@GetMapping(("/users/fetchAllUsers"))
	public ResponseEntity<?> fetchAllUsers() {
		List<String> userLs=null;
		try {
			userLs = userService.fetchAllUsers();
		} catch (Exception e) {
			return new ResponseEntity<>("Could not fetch all users", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(userLs);
	}

}
