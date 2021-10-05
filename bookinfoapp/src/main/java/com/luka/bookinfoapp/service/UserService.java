package com.luka.bookinfoapp.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luka.bookinfoapp.dao.RoleDao;
import com.luka.bookinfoapp.dao.UserDao;
import com.luka.bookinfoapp.models.Role;
import com.luka.bookinfoapp.models.User;

@Service
public class UserService {

	private UserDao userDao;
	private RoleDao roleDao;
	private PasswordEncoder encoder;
	private Set<Role> roles = new HashSet<Role>();
	
	public UserService(UserDao userDao,RoleDao roleDao, PasswordEncoder encoder) {
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.encoder = encoder;
	}
	@Transactional
	public User saveUser(User user) {
		Role role = roleDao.findRoleByRoleName("ROLE_USER");
		roles.add(role);
		user.setRoles(roles);
		user.setPassword(encoder.encode(user.getPassword()));
		return userDao.save(user);
	}
	@Transactional(readOnly = true)
	public User getUserById(Long id) {
		return userDao.findById(id).orElse(null);
	}
	//dodato nakndano zbog book service save metode
	@Transactional(readOnly = true)
	public User getUserByName(String username) {
		return userDao.findUserByUsername(username).orElse(null);
	}
	@Transactional(readOnly = true)
	public List<User> getAllUsers(){
		return userDao.findAll();
	}
	
	public User updateUser(User UpdatedUser, Long id) {
		Optional<User> userOpt = userDao.findById(id);
		User user = userOpt.get();
		user.setPassword(UpdatedUser.getPassword());
		user.setEmail(UpdatedUser.getEmail());
		user.setUsername(UpdatedUser.getUsername());
		
		userDao.save(user);
		return user;
		
	}
	public String deleteUser(Long id) {
		userDao.deleteById(id);
		return "Deleted User with id = " + id;
	}
	@Transactional(readOnly = true)
	public boolean checkIfEmailExists(String email) {
		return userDao.findUserByEmail(email).isPresent();
	}
	@Transactional(readOnly = true)
	public boolean checkIfUsernameExists(String username) {
		return userDao.findUserByUsername(username).isPresent();		
	}
	public User getCurrentLoggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		User user = getUserByName(username);
		return user;
	}
}
