package com.berheley.ichart.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * 权限表
 * @author pwb
 */
@Entity
@Table(name="sys_permission")
public class SysPermission implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id",length=100)
	private String id;
	
	@Column(name="name",length=100)
	private String name;
	
	@Lob
	@Column(name="description")
	private String description;
	
	@Lob
	@Column(name="url")
	private String url;
	
	@Column(name="pid",length=100)
	private String pid;
	
	/**
	 * 角色
	 */
	@ManyToMany(targetEntity=SysRole.class,fetch=FetchType.EAGER)
	@JoinTable(name = "sys_permission_role",
	           joinColumns = {@JoinColumn(name = "sys_permission_id", referencedColumnName = "id")},
	           inverseJoinColumns = {@JoinColumn(name = "sys_role_id", referencedColumnName = "id")})
	private Set<SysRole> roles;

	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
