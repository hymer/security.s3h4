package com.epic.core.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.epic.core.BaseEntity;

@Entity
@Table(name = "tb_security_user")
@JsonIgnoreProperties("role")
public class User extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(length = 18)
	private String userName;
	@Column(length = 32)
	private String password;
	@Column
	private boolean disabled = false;
	@OneToOne
	@JoinColumn(name = "infoId")
	private UserInfo info;
	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role role;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public UserInfo getInfo() {
		return info;
	}

	public void setInfo(UserInfo info) {
		this.info = info;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
