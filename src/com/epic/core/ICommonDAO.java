package com.epic.core;

public interface ICommonDAO {
	
	public <T extends BaseEntity> T save(T model);

	public <T extends BaseEntity> void saveOrUpdate(T model);

	public <T extends BaseEntity> void update(T model);

	public <T extends BaseEntity> void delete(T model);

}
