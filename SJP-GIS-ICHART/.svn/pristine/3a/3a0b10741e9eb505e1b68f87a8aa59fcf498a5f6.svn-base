package com.berheley.ichart.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 用户表
 * @author pwb
 */
@Entity
@Table(name="sys_user")
public class SysUser implements UserDetails {

	private static final long serialVersionUID = 3442972923877585204L;
    
	/**
	 * id
	 */
	@Id
	@Column(name="id",length=100)
	private String id;
	
	
	/**
	 * 用户名
	 */
	@Column(name="username",length=200)
	private String username;
	
	/**
	 * 密码
	 */
	@Column(name="password",length=200)
	private String password;
	
	/**
	 * 联系电话
	 */
	@Column(name="telphone",length=50)
    private String telphone;
	
	/**
	 * 角色
	 * @return
	 */
	@ManyToMany(targetEntity=SysRole.class,fetch=FetchType.EAGER)
	@JoinTable(name = "sys_role_user",
	           joinColumns = {@JoinColumn(name = "Sys_User_id", referencedColumnName = "id")},
	           inverseJoinColumns = {@JoinColumn(name = "Sys_Role_id", referencedColumnName = "id")})
	private Set<SysRole> roles;
    
	public Set<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<SysRole> roles) {
		this.roles = roles;
	}

	public void setTelphone(String telphone) {
    	this.telphone = telphone;
    }
    
    public String getTelphone() {
    	return telphone;
    }


	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		List<String> roles = new ArrayList<String>();
		if (roles != null) {
			for (int i = 0; i < roles.size(); i++) {
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roles.get(i));
				authorities.add(authority);
			}

		}
		return authorities;
	}

	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
//	@JsonIgnore
//	public Optional<String> getEncodedPassword() {
//		return Optional.ofNullable(password).map(p -> new BCryptPasswordEncoder().encode(p));
//	}
