package com.epic.core.security.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.epic.core.BaseEntity;

@Entity
@Table(name = "tb_security_userinfo")
@JsonIgnoreProperties("user")
public class UserInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(length = 50)
	private String realName;
	@Column(length = 200)
	private String email;
	@OneToOne(targetEntity = User.class, cascade = CascadeType.ALL, mappedBy = "info")
	private User user;

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
