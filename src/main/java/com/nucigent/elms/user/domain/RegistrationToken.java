package com.nucigent.elms.user.domain;

public class RegistrationToken {
	
    
    private long tokenId;
	
	private long userId;
	private String confirmationToken;
	private String userLoginId;
	private String confirmationTokenLink;
	private String tokenCreatedDate;
	private String tokenExpireDate;
	private long userIndividualId;
	
	private boolean valid;
	
	public long getTokenId() {
		return tokenId;
	}
	public void setTokenId(long tokenId) {
		this.tokenId = tokenId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getConfirmationToken() {
		return confirmationToken;
	}
	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	public String getConfirmationTokenLink() {
		return confirmationTokenLink;
	}
	public void setConfirmationTokenLink(String confirmationTokenLink) {
		this.confirmationTokenLink = confirmationTokenLink;
	}
	public String getTokenCreatedDate() {
		return tokenCreatedDate;
	}
	public void setTokenCreatedDate(String tokenCreatedDate) {
		this.tokenCreatedDate = tokenCreatedDate;
	}
	public String getTokenExpireDate() {
		return tokenExpireDate;
	}
	public void setTokenExpireDate(String tokenExpireDate) {
		this.tokenExpireDate = tokenExpireDate;
	}
	public long getUserIndividualId() {
		return userIndividualId;
	}
	public void setUserIndividualId(long userIndividualId) {
		this.userIndividualId = userIndividualId;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	

}
