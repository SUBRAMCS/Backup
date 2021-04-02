package com.nucigent.elms.common.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsServiceImpl implements SmsService {

	@Override
	public void sendMessage(String phoneNo) {

		// Find your Account Sid and Token at
				//twilio.com/user/account
			String ACCOUNT_SID = "ACce4ea1f72ed929970f7560b963f755e0";
			String AUTH_TOKEN = "28a2a8fea8abe255541d2b5caf99c6f7";

				Twilio.init(ACCOUNT_SID,
			  AUTH_TOKEN);
			  
			  Message message = Message.creator(new PhoneNumber(phoneNo),new
					  PhoneNumber(phoneNo),
			  "Your elocker account in created").create();
			  
			  System.out.println(message.getSid()); 
	}

}
