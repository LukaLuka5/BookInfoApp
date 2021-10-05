package com.luka.bookinfoapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.luka.bookinfoapp.dao.RoleDao;
import com.luka.bookinfoapp.dao.UserDao;
import com.luka.bookinfoapp.models.Role;
import com.luka.bookinfoapp.models.User;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserDao userDao;
	@Mock
	RoleDao roleDao;
	@Mock
	PasswordEncoder encoder;
	@InjectMocks
	UserService userService;
	
	User user;
	
	private static final String USER_ROLE = "ROLE_USER";
	private static final String USER_USERNAME = "username";
	private static final String USER_PASSWORD = "password";
	private static final String USER_EMAIL = "test@test.rs";
	
	@BeforeEach
	void setUp() {
		user = new User(USER_USERNAME, USER_PASSWORD, USER_EMAIL, null, null, null);
	}
	@Test
	void testSaveUser() {
		Role role = new Role();
		role.setRoleName(USER_ROLE);
		
		when(roleDao.findRoleByRoleName(USER_ROLE)).thenReturn(role);
		when(encoder.encode(user.getPassword())).thenReturn(USER_PASSWORD);
		when(userDao.save(any(User.class))).thenReturn(user);
		
		User result = userService.saveUser(user);
		
		assertThat(result.getRoles().contains(role));
		assertThat(result).isEqualTo(user);
		
	}

	@Test
	void testGetUserById() {
		when(userDao.findById(any(Long.class))).thenReturn(Optional.of(user));
		
		User result = userService.getUserById(1l);
		
		assertEquals(user.getId(), result.getId(),"Ids dont match");
	}

	@Test
	void testGetUserByName() {
		when(userDao.findUserByUsername(USER_USERNAME)).thenReturn(Optional.of(user));
		
		User result = userService.getUserByName(USER_USERNAME);
		
		assertEquals(user.getId(), result.getId(),"Ids dont match");
	}

	@Test
	void testGetAllUsers() {
		when(userDao.findAll()).thenReturn(Lists.newArrayList(new User(),new User()));
		
		List<User> result = userService.getAllUsers();
		
		assertThat(result).hasSize(2);
	}

	@Test
	void testUpdateUser() {
		User updatedUser = new User();
		updatedUser.setUsername("changedUsername");
		
		when(userDao.findById(any(Long.class))).thenReturn(Optional.of(user));
		when(userDao.save(any(User.class))).thenReturn(user);
				
		User result = userService.updateUser(updatedUser, 1l);
		
		assertEquals("changedUsername", result.getUsername(),"Username doesnt match");
	}

	@Test
	void testDeleteUser() {
		String result = userService.deleteUser(1l);
		assertEquals("Deleted User with id = 1", result);
	}

	@Test
	void testCheckIfEmailExists() {
		when(userDao.findUserByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
		
		boolean result = userService.checkIfEmailExists(USER_EMAIL);
		
		assertThat(result).isEqualTo(true);
	}

	@Test
	void testCheckIfUsernameExists() {
		when(userDao.findUserByUsername(USER_USERNAME)).thenReturn(Optional.of(user));
		
		boolean result = userService.checkIfUsernameExists(USER_USERNAME);
		
		assertThat(result).isEqualTo(true);
	}

	@Test
	void testGetCurrentLoggedInUser() {
		Authentication auth = Mockito.mock(Authentication.class);
		SecurityContext context = Mockito.mock(SecurityContext.class);
		SecurityContextHolder.setContext(context);
		
		when(context.getAuthentication()).thenReturn(auth);
		when(auth.getName()).thenReturn(USER_USERNAME);
		when(userDao.findUserByUsername(USER_USERNAME)).thenReturn(Optional.of(user));
		
		User result = userService.getCurrentLoggedInUser();
		
		assertThat(result).isEqualTo(user);
		
	}

}
