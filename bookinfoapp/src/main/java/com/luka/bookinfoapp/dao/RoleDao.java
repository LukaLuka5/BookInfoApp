package com.luka.bookinfoapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luka.bookinfoapp.models.Role;

public interface RoleDao extends JpaRepository<Role, Long>{
	public Role findRoleByRoleName(String roleName);
}
