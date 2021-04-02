package com.nucigent.elms.common.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    
    @Autowired
    private SendGrid sendGrid;
    
    @Autowired
	Environment environment;

    /*@Autowired
    private SpringTemplateEngine templateEngine;*/

   /* public void sendEmail(Mail mail) {
        try {
        	SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
			passwordResetEmail.setFrom("support@demo.com");
			passwordResetEmail.setTo(mail.getTo());
			passwordResetEmail.setSubject("Password Reset Request");
			//passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
					//+ "/reset?token=" + mail.getResetToken());

			javaMailSender.send(passwordResetEmail);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }*/
    
public void sendUserName(String userName,String emailTo){
    	
    	//String emailContent = "Your registration link is ";
    	Email from = new Email("activate@tightcap.com");
        String subject = "User Id for Elocker";
        Email to = new Email(emailTo);
        
        
        
        String emailContent = "Your Username is: "+userName;
        
        Content content = new Content("text/html", emailContent);
        Mail mail = new Mail(from, subject, to, content);
     
        Request request = new Request();
        Response response = null;
       try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sendGrid.api(request);
          
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
public void sendRestPwdEmail(String tokenId,String emailTo){
    	
    	//String emailContent = "Your registration link is ";
    	Email from = new Email("activate@tightcap.com","Reset your TightCap Credentials");
        String subject = "Complete Reset Pwd!";
        Email to = new Email(emailTo);
        
        String registrationLink = environment.getProperty("reset.email.url");
        
        String emailContent = "To Reset your password, please click here : "
    			+registrationLink+"?token="+tokenId;
        
        Content content = new Content("text/html", emailContent);
        Mail mail = new Mail(from, subject, to, content);
     
        Request request = new Request();
        Response response = null;
       try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sendGrid.api(request);
          
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public void sendRegistrationEmail(String tokenId,String emailTo){
    	
    	//String emailContent = "Your registration link is ";
    	Email from = new Email("welcome@tightcap.com","Activate your TightCap Account");
        String subject = "Complete Registration!";
        Email to = new Email(emailTo);
        
        String registrationLink = environment.getProperty("registration.email.url");
        
        String emailContent = "To confirm your account, please click here : "
    			+registrationLink+"?token="+tokenId;
        
        Content content = new Content("text/html", emailContent);
        Mail mail = new Mail(from, subject, to, content);
     
        Request request = new Request();
        Response response = null;
       try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sendGrid.api(request);
          
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

	@Override
	public void sendOtp(Integer otpId, String emailTo) {
		
		//String emailContent = "Your registration link is ";
    	Email from = new Email("activate@tightcap.com","TightCap OTP");
        String subject = "Complete Registration!";
        Email to = new Email(emailTo);
        
        //String registrationLink = environment.getProperty("registration.email.url");
        
        String emailContent = "Your OTP Password: " + otpId;
    		
        Content content = new Content("text/html", emailContent);
        Mail mail = new Mail(from, subject, to, content);
     
        Request request = new Request();
        Response response = null;
       try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sendGrid.api(request);
          
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
		
	}
	@Override
	public void sendSuccessfulRegnEmail(String emailTo) {
		//String emailContent = "Your registration link is ";
    	Email from = new Email("welcome@tightcap.com","Welcome to Tightcap");
        String subject = "Registration Confirmed!";
        Email to = new Email(emailTo);
        
        //String registrationLink = environment.getProperty("registration.email.url");
        
        String emailContent = "You have successfully completed your registration.";
    		
        Content content = new Content("text/html", emailContent);
        Mail mail = new Mail(from, subject, to, content);
     
        Request request = new Request();
        Response response = null;
       try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            response = sendGrid.api(request);
          
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
            
        } catch (IOException ex) {
        	System.out.println(ex.getMessage());
//        	throw new BusinessException("Successful registration mail could not be sent.");
        }
		
	}

}
