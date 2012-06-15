package com.epic.core.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.epic.core.CommonService;
import com.epic.core.model.Condition;
import com.epic.core.model.QueryObject;
import com.epic.core.model.ResponseJSON;
import com.epic.core.security.MD5Utils;
import com.epic.core.security.dao.RoleDAO;
import com.epic.core.security.dao.UserDAO;
import com.epic.core.security.dto.UserDTO;
import com.epic.core.security.entity.Role;
import com.epic.core.security.entity.User;

@Service
public class UserService extends CommonService {

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private RoleDAO roleDAO;
	
	public User getByUserName(String userName) {
		List<User> users = userDAO.getByProperty("userName", userName);
		if (users.isEmpty()) {
			return null;
		}
		return users.get(0);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseJSON query(QueryObject queryObject) {
		List<Condition> realConditions = new ArrayList<Condition>();
		Condition superFilter = new Condition("role.code", Role.SUPER_ROLE_FLAG);
		superFilter.setOperator(Condition.NOT_EQ);
		realConditions.add(superFilter);
		for (Condition condition : queryObject.getConditions()) {
			if (condition.getValue() == null
					|| !StringUtils.hasText(condition.getValue().toString())) {
				continue;
			}
			if (condition.getKey().equals("userName")) {
				condition.setOperator(Condition.LIKE);
				realConditions.add(condition);
			} else if (condition.getKey().equals("roleId")) {
				Long roleId = Long.parseLong(condition.getValue().toString());
				Condition roleCondition = new Condition("role.id", roleId);
				roleCondition.setValueType(Long.class);
				realConditions.add(roleCondition);
			}
		}
		queryObject.setConditions(realConditions);
		ResponseJSON json = userDAO.getAll(queryObject);
		List<UserDTO> dtos = new ArrayList<UserDTO>();
		for (User user : (List<User>) json.getData().get("data")) {
			UserDTO dto = new UserDTO(user);
			dtos.add(dto);
		}
		json.put("data", dtos);
		return json;
	}

	public User getUserById(Long id) {
		return userDAO.getById(id);
	}

	public void update(Long id, UserDTO dto) {
		User entity = getUserById(id);
		entity.setDisabled(dto.isDisabled());
		if ( ! entity.getRole().getId().equals(dto.getRoleId())) {
			Role role = roleDAO.getById(dto.getRoleId());
			entity.setRole(role);
		}
		update(entity);
	}

	public UserDTO getUserDTOById(Long id) {
		User entity = getUserById(id);
		UserDTO dto = new UserDTO(entity);
		return dto;
	}

	public void updatePassword(Long id, String password1, String password2) {
		User user = userDAO.getById(id);
		if (password1 != null && password1.equals(password2)) {
			user.setPassword(MD5Utils.encode(password1));
			update(user);
		} else {
			throw new RuntimeException("两次输入密码不一致!");
		}
		
	}
	
}
