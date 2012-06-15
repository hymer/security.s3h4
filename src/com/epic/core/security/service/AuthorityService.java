package com.epic.core.security.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epic.core.CommonService;
import com.epic.core.model.Condition;
import com.epic.core.model.QueryObject;
import com.epic.core.model.ResponseJSON;
import com.epic.core.security.dao.AuthorityDAO;
import com.epic.core.security.dao.ResourceDAO;
import com.epic.core.security.entity.Authority;
import com.epic.core.security.entity.Resource;

@Service
public class AuthorityService extends CommonService {

	@Autowired
	private AuthorityDAO authorityDAO;
	@Autowired
	private ResourceDAO resourceDAO;

	public ResponseJSON query(QueryObject queryObject) {
		return authorityDAO.getAll(queryObject);
	}

	public Authority getAuthorityById(Long id) {
		return authorityDAO.getById(id);
	}

	public void saveAssignedResources(Long authorityId, Set<Long> resourceIds) {
		Authority authority = authorityDAO.getById(authorityId);
		Set<Resource> resourcesSet = new HashSet<Resource>();
		if (!resourceIds.isEmpty()) {
			Condition condition = new Condition("id", resourceIds);
			condition.setOperator(Condition.IN);
			condition.setValueType(Long.class);
			List<Resource> resources = resourceDAO.getByCondition(condition);
			resourcesSet.addAll(resources);
		}
		authority.setResources(resourcesSet);
		authorityDAO.update(authority);
	}

	public Set<Resource> getResourcesByAuthorityId(Long id) {
		Authority authority = authorityDAO.getById(id);
		if (authority != null && authority.getResources().size() > 0) {
			return authority.getResources();
		}
		return new HashSet<Resource>();
	}

	public List<Authority> getAvailableAuthorities(List<Long> notInIds) {
		if (notInIds == null || notInIds.isEmpty()) {
			return authorityDAO.getAll();
		} else {
			Condition condition = new Condition("id", notInIds);
			condition.setOperator(Condition.NOT_IN);
			condition.setValueType(Long.class);
			return authorityDAO.getByCondition(condition);
		}
	}

}
