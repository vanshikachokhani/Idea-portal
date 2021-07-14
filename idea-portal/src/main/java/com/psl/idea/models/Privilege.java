package com.psl.idea.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="privilege")
public class Privilege {
	
	@Id
	@Column(name = "PRIVILEGE_ID")
	private long privilegeId;
	
	@Column(name = "PRIVILEGE")
	private String privilege;
	
	public long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(long privilegeId) {
		this.privilegeId = privilegeId;
	}
	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	@Override
	public String toString() {
		return "Privilege [privilegeId=" + privilegeId + ", privilege=" + privilege + "]";
	}
	
	
}
