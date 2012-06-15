package com.epic.core.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.epic.core.BaseEntity;

@Entity
@Table(name = "tb_security_resource")
public class Resource extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Column(length = 200)
	private String name;
	@Column(length = 50)
	private String type;
	@Column
	private int priority;
	@Column(length = 1024)
	private String url;
	@Column(length = 1024)
	private String description;
	@Column
	private boolean disabled = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
