package com.epic.core;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public abstract class CommonService {
	@Autowired
	private ICommonDAO commonDAO;

	protected <T extends BaseEntity> void preSave(T model) {
		model.setCreateTime(new Date());
	}
	
	public <T extends BaseEntity> T save(T model) {
		preSave(model);
		return commonDAO.save(model);
	}

	public <T extends BaseEntity> void saveOrUpdate(T model) {
		if (model.getId() == null) {
			preSave(model);
		}
		commonDAO.saveOrUpdate(model);
	}

	public <T extends BaseEntity> void update(T model) {
		commonDAO.update(model);
	}

	public <T extends BaseEntity> void delete(T model) {
		commonDAO.delete(model);
	}
}
