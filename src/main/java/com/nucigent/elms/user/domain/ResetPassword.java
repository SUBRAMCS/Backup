package com.nucigent.elms.user.domain;

import java.util.Date;

public class ResetPassword {
	
	private long id;
	private String userLoginId;
	private String resetToken;
	private Date resetCreationDate;
	private Date resetExpiaryDate;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	public String getResetToken() {
		return resetToken;
	}
	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}
	public Date getResetCreationDate() {
		return resetCreationDate;
	}
	public void setResetCreationDate(Date resetCreationDate) {
		this.resetCreationDate = resetCreationDate;
	}
	public Date getResetExpiaryDate() {
		return resetExpiaryDate;
	}
	public void setResetExpiaryDate(Date resetExpiaryDate) {
		this.resetExpiaryDate = resetExpiaryDate;
	}
	
	
	
	

}
