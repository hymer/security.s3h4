package com.epic.core.security.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.epic.core.BaseEntity;
import com.epic.core.CommonService;
import com.epic.core.model.Condition;
import com.epic.core.model.QueryObject;
import com.epic.core.model.ResponseJSON;
import com.epic.core.security.dao.ResourceDAO;
import com.epic.core.security.entity.Resource;

/**
 * 资源服务类
 * 
 * 注意：在对资源进行修改、新增、删除操作时，需要执行refreshProtectedResources方法，以刷新受保护的资源对象。
 * 
 * @author hymer
 *
 */
@Service
public class ResourceService extends CommonService {
	
	/**
	 * 受保护的资源
	 */
	public static Set<String> PROTECTED_RESOURCES = new HashSet<String>();
	
	public void refreshProtectedResources() {
		List<Resource> resources = getAllResources();
		for (Resource resource : resources) {
			PROTECTED_RESOURCES.add(resource.getUrl());
		}
	}

	@Autowired
	private ResourceDAO resourceDAO;
	
	public List<Resource> getAllResources() {
		List<Resource> resources = resourceDAO.getAll();
		return resources;
	}
	
	public List<Resource> getAllEnabledResources() {
		List<Resource> resources = resourceDAO.getByProperty("disabled", false);
		return resources;
	}

	public ResponseJSON query(QueryObject queryObject) {
		List<Condition> realConditions = new ArrayList<Condition>();
		for (Condition condition : queryObject.getConditions()) {
			if (condition.getValue() == null
					|| !StringUtils.hasText(condition.getValue().toString())) {
				continue;
			}
			if (condition.getKey().equals("name") || condition.getKey().equals("url")) {
				condition.setOperator(Condition.LIKE);
				realConditions.add(condition);
			}
		}
		queryObject.setConditions(realConditions);
		ResponseJSON json = resourceDAO.getAll(queryObject);
		return json;
	}

	public Resource getResourceById(Long id) {
		return resourceDAO.getById(id);
	}

	public List<Resource> getAvailableResources(List<Long> notInIds) {
		if (notInIds == null || notInIds.isEmpty()) {
			return resourceDAO.getAll();
		} else {
			Condition condition = new Condition("id", notInIds);
			condition.setOperator(Condition.NOT_IN);
			condition.setValueType(Long.class);
			return resourceDAO.getByCondition(condition);
		}
	}

	public void deleteByIds(Set<Long> ids) {
		for (Long id : ids) {
			Resource resource = resourceDAO.getById(id);
			delete(resource);
		}
		refreshProtectedResources();
	}

	@Override
	public <T extends BaseEntity> T save(T model) {
		T t = super.save(model);
		refreshProtectedResources();
		return t;
	}

	@Override
	public <T extends BaseEntity> void saveOrUpdate(T model) {
		super.saveOrUpdate(model);
		refreshProtectedResources();
	}

	@Override
	public <T extends BaseEntity> void update(T model) {
		super.update(model);
		refreshProtectedResources();
	}

	@Override
	public <T extends BaseEntity> void delete(T model) {
		super.delete(model);
		refreshProtectedResources();
	}

}
