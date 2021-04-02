package com.nucigent.elms.user.domain;

public class User {
	
	private long id;
	
	private String username;
	private String password;
	
	private String firstName;
	private String lastName;
	private String motherMaidenName;
	
	private String email;
	private String phoneNo;
	private String lockedExpiryDate;
	private String passwordHashingAlgorithim ;
	private String dateOfBirth ;
	private String question ;
	private String questionAnswer ;
	private String isActive ;
	private String acceptTermofService ;
	private String accountClosedDateTime ;
	private String passwordUpdatedDateTime ;
	private String accountLocked;
	private String confirmationToken;
	
	private String recordCreatedDate;
	private String recordUpdatedDate;
	
	private UserInfo userInfo;
	private EmailInfo emailInfo;
	private PhoneInfo phoneInfo;
	
	
	public String getLockedExpiryDate() {
		return lockedExpiryDate;
	}
	public void setLockedExpiryDate(String lockedExpiryDate) {
		this.lockedExpiryDate = lockedExpiryDate;
	}
	public String getAccountLocked() {
		return accountLocked;
	}
	public void setAccountLocked(String accountLocked) {
		this.accountLocked = accountLocked;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordHashingAlgorithim() {
		return passwordHashingAlgorithim;
	}
	public void setPasswordHashingAlgorithim(String passwordHashingAlgorithim) {
		this.passwordHashingAlgorithim = passwordHashingAlgorithim;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getAcceptTermofService() {
		return acceptTermofService;
	}
	public void setAcceptTermofService(String acceptTermofService) {
		this.acceptTermofService = acceptTermofService;
	}
	public String getAccountClosedDateTime() {
		return accountClosedDateTime;
	}
	public void setAccountClosedDateTime(String accountClosedDateTime) {
		this.accountClosedDateTime = accountClosedDateTime;
	}
	public String getPasswordUpdatedDateTime() {
		return passwordUpdatedDateTime;
	}
	public void setPasswordUpdatedDateTime(String passwordUpdatedDateTime) {
		this.passwordUpdatedDateTime = passwordUpdatedDateTime;
	}
	public String getConfirmationToken() {
		return confirmationToken;
	}
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	public String getRecordCreatedDate() {
		return recordCreatedDate;
	}
	public void setRecordCreatedDate(String recordCreatedDate) {
		this.recordCreatedDate = recordCreatedDate;
	}
	public String getRecordUpdatedDate() {
		return recordUpdatedDate;
	}
	public void setRecordUpdatedDate(String recordUpdatedDate) {
		this.recordUpdatedDate = recordUpdatedDate;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public EmailInfo getEmailInfo() {
		return emailInfo;
	}
	public void setEmailInfo(EmailInfo emailInfo) {
		this.emailInfo = emailInfo;
	}
	public PhoneInfo getPhoneInfo() {
		return phoneInfo;
	}
	public void setPhoneInfo(PhoneInfo phoneInfo) {
		this.phoneInfo = phoneInfo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMotherMaidenName() {
		return motherMaidenName;
	}
	public void setMotherMaidenName(String motherMaidenName) {
		this.motherMaidenName = motherMaidenName;
	}
	

}