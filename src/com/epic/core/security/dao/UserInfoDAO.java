package com.epic.core.security.dao;

import org.springframework.stereotype.Repository;

import com.epic.core.DAOHibernate;
import com.epic.core.security.entity.UserInfo;

@Repository
public class UserInfoDAO extends DAOHibernate<UserInfo, Long> {
	
}
