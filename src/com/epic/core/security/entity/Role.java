package com.epic.core.security.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.epic.core.BaseEntity;

@Entity
@Table(name = "tb_security_role")
@JsonIgnoreProperties("authorities")
public class Role extends BaseEntity {
	public static final String SUPER_ROLE_FLAG = "__SUPER_ROLE_FLAG__";
	private static final long serialVersionUID = 1L;

	@Column(name = "role_code", length = 100)
	private String code;
	@Column(length = 200)
	private String name;
	@Column(length = 1024)
	private String description;
	@Column
	private boolean disabled = false;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tb_security_role_authority", joinColumns = { @JoinColumn(name = "roleId") }, inverseJoinColumns = { @JoinColumn(name = "authorityId") })
	private Set<Authority> authorities = new HashSet<Authority>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

}
