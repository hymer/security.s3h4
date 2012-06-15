package com.epic.core;

import java.util.List;

import com.epic.core.model.Condition;
import com.epic.core.model.QueryObject;
import com.epic.core.model.ResponseJSON;

public interface IDAO<T extends BaseEntity, PK extends java.io.Serializable> {

	PK save(T entity);

	void update(T entity);
	
	void saveOrUpdate(T entity);

	void delete(T entity);

	void deleteById(PK id);

	T getById(PK id);
	
	List<T> getByProperty(String propertyName, Object propertyValue);

	List<T> getByCondition(Condition condition);
	
	List<T> getByConditions(List<Condition> conditions);

	List<T> getAll();

	ResponseJSON getAll(QueryObject queryObject);

	boolean exists(PK id);

}
