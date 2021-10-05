package com.luka.bookinfoapp.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luka.bookinfoapp.dao.UserDao;
import com.luka.bookinfoapp.models.User;

@Service
public class MyUserDetailsService implements UserDetailsService {

	private UserDao userDao;
	
	public MyUserDetailsService(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> userOpt = userDao.findUserByUsername(username);
		User user = userOpt.get();
		
		if (null == user) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
        	return new MyUserDetails(user);
        }
	}

}
