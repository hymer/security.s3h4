package com.epic.core.security.dao;

import org.springframework.stereotype.Repository;

import com.epic.core.DAOHibernate;
import com.epic.core.security.entity.Role;

@Repository
public class RoleDAO extends DAOHibernate<Role, Long> {
	
}
