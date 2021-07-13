package com.psl.idea.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="participants")
public class Participants {
	
	private long userId;
	
	private long privilegeId;
	
	private long roleId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "Participants [userId=" + userId + ", privilegeId=" + privilegeId + ", roleId=" + roleId + "]";
	}
	
	
}

