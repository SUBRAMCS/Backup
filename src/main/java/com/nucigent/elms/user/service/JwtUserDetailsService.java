package com.nucigent.elms.user.service;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.nucigent.elms.user.dao.UserDao;
import com.nucigent.elms.user.domain.User;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) {

		User user = null;
		try {
			user = userDao.findByUserName(username);
			logger.info("user id"+user.getId());
			boolean accountNonLocked = user.getAccountLocked().equals("Y") ? false :true ;
		//	boolean accountEnabled = user.getIsActive().equals("Y") ? true : false;
			//Change by Vaibhav
			boolean accountEnabled = user.getIsActive().equals("Y") ? true : false;
			
//			long registrationId = user.getId();
//			String isActive = userDao.getIsActiveByRegisteredId(registrationId);
//			boolean enabled = isActive.equals("Y") ? true : false;
			/*return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
					new ArrayList<>());*/
			return  new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), accountEnabled, true, true, accountNonLocked, new ArrayList<>());
			
		} catch (Exception e) {
			return null;
		}
		
	}

	
	/*public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if ("elocker".equals(username)) {
			return new User("elocker", "$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6",
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}*/
	/*public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	   User user = userDao.findByUserName(username);
	   if(user == null){
		   throw new UsernameNotFoundException("Invalid username or password.");
	   }
	     return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
     }

     private List getAuthority() {
	      return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
     }*/
   
}
