package com.epic.core.security.dao;

import org.springframework.stereotype.Repository;

import com.epic.core.DAOHibernate;
import com.epic.core.security.entity.User;

@Repository
public class UserDAO extends DAOHibernate<User, Long> {
	
}
