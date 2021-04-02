package com.nucigent.elms.user.domain;

import java.util.Calendar;
import java.util.Date;

public class PasswordResetToken {
	
	    private long id;

	   	private String token;
	  
	    private User user;
	    
	    private Date expiryDate;

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(User user) {
	        this.user = user;
	    }

	    public Date getExpiryDate() {
	        return expiryDate;
	    }

	    public void setExpiryDate(Date expiryDate) {
	        this.expiryDate = expiryDate;
	    }

	    public void setExpiryDate(int minutes){
	        Calendar now = Calendar.getInstance();
	        now.add(Calendar.MINUTE, minutes);
	        this.expiryDate = now.getTime();
	    }

	    public boolean isExpired() {
	        return new Date().after(this.expiryDate);
	    }
	
}
