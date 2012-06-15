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
@Table(name = "tb_security_authority")
@JsonIgnoreProperties("resources")
public class Authority extends BaseEntity {

	private static final long serialVersionUID = 1L;
	@Column(length = 200)
	private String name;
	@Column(length = 1024)
	private String description;
	@Column
	private boolean disabled = false;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "tb_security_authority_resource", joinColumns = { @JoinColumn(name = "authorityId") }, inverseJoinColumns = { @JoinColumn(name = "resourceId") })
	private Set<Resource> resources = new HashSet<Resource>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Set<Resource> getResources() {
		return resources;
	}

	public void setResources(Set<Resource> resources) {
		this.resources = resources;
	}

}
