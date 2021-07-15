package com.psl.idea.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class Roles {
	
	@Id
	@Column(name = "role_id")
	private long roleId;
	
	@Column(name = "role")
	private String roles;
	
	

	public Roles(long roleId, String roles) {
		super();
		this.roleId = roleId;
		this.roles = roles;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "Roles [roleId=" + roleId + ", roles=" + roles + "]";
	}
	
	
}
