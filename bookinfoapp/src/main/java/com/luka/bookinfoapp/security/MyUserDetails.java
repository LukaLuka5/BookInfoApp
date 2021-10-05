package com.luka.bookinfoapp.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.luka.bookinfoapp.models.Role;
import com.luka.bookinfoapp.models.User;

public class MyUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private User user;
	private Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();

	public MyUserDetails(User user) {
		this.user = user;
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		for(Role role : user.getRoles()) {
			roles.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		return roles;
	}
	/* Do i even need this?
	public String getEmail() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}
	*/
	public Long getId() {
		return user.getId();
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
