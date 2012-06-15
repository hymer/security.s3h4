package com.epic.core.security.dto;

import com.epic.core.BaseEntity;
import com.epic.core.DTO;
import com.epic.core.security.MD5Utils;
import com.epic.core.security.entity.User;
import com.epic.core.util.Formatters;

public class UserDTO extends DTO {
	private Long id;
	private String userName;
	private String password1;
	private String password2;
	private Long roleId;
	private String roleCode;
	private String roleName;
	private boolean disabled;
	private String createTime;

	public UserDTO() {
		super();
	}

	public UserDTO(User user) {
		this();
		fromEntity(user);
	}

	@Override
	public BaseEntity toEntity() {
		User user = new User();
		user.setDisabled(disabled);
		user.setUserName(userName);
		if (password1 != null && password1.equals(password2)) {
			user.setPassword(MD5Utils.encode(password1));
		} else {
			throw new RuntimeException("两次输入的密码不一致!");
		}
		return user;
	}

	@Override
	public void fromEntity(BaseEntity entity) {
		User user = (User) entity;
		this.id = user.getId();
		this.userName = user.getUserName();
		this.roleId = user.getRole().getId();
		this.roleCode = user.getRole().getCode();
		this.roleName = user.getRole().getName();
		this.disabled = user.isDisabled();
		this.createTime = Formatters.formatDate(user.getCreateTime());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
