package com.epic.core.security.dao;

import org.springframework.stereotype.Repository;

import com.epic.core.DAOHibernate;
import com.epic.core.security.entity.Authority;

@Repository
public class AuthorityDAO extends DAOHibernate<Authority, Long> {
	
}
