package com.berheley.ichart.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/**
 * 角色表
 * @author pwb
 */
@Entity
@Table(name="sys_role")
public class SysRole implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@Id
	@Column(length=100,name="id")
	private String id;
	
	/**
	 * 角色名称
	 */
	@Column(length=100,name="name")
	private String name;
    
	/**
	 * 用户
	 */
	@ManyToMany(targetEntity=SysUser.class,fetch=FetchType.EAGER)
	@JoinTable(name = "sys_role_user", 
	           joinColumns = {@JoinColumn(name = "sys_role_id", referencedColumnName = "id") },
	           inverseJoinColumns = {@JoinColumn(name = "sys_user_id", referencedColumnName = "id") })
	private Set<SysUser> users;
	
	@ManyToMany(targetEntity=SysPermission.class,fetch=FetchType.EAGER)
	@JoinTable(name = "sys_permission_role", 
	           joinColumns = {@JoinColumn(name = "sys_role_id", referencedColumnName = "id") },
	           inverseJoinColumns = {@JoinColumn(name = "sys_permission_id", referencedColumnName = "id") })
	private Set<SysPermission> permissions;
	
	public Set<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<SysPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<SysUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
