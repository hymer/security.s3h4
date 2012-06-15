package com.epic.core.security.dao;

import org.springframework.stereotype.Repository;

import com.epic.core.DAOHibernate;
import com.epic.core.security.entity.Resource;

@Repository
public class ResourceDAO extends DAOHibernate<Resource, Long> {
	
}
