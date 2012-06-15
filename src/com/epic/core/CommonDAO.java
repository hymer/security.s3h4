package com.epic.core;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommonDAO implements ICommonDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public <T extends BaseEntity> T save(T model) {
		getSession().save(model);
		return model;
	}

	@Override
	public <T extends BaseEntity> void saveOrUpdate(T model) {
		getSession().saveOrUpdate(model);
	}

	@Override
	public <T extends BaseEntity> void update(T model) {
		getSession().update(model);
	}

	@Override
	public <T extends BaseEntity> void delete(T model) {
		getSession().delete(model);
	}

}
