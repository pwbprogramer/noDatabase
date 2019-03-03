package com.berheley.ichart.dao;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.berheley.ichart.domain.SysPermission;
import com.berheley.ichart.domain.SysRole;
import com.berheley.ichart.domain.SysUser;

@Component
public class PermissionListRepository {

	public Collection<GrantedAuthority> getPermissionCollection(SysUser user){
		Set<SysPermission> permission = new HashSet<SysPermission>();
		/**
		 * 获取角色
		 * 
		 */
		Set<SysRole> role = user.getRoles();
		Iterator<SysRole> itRole = role.iterator();
		while(itRole.hasNext()) {
			SysRole temp = itRole.next();
			permission.addAll(temp.getPermissions());
		}
		/**
		 * 权限集合
		 */
		LinkedList<GrantedAuthority> grantedAuthorities = new LinkedList <GrantedAuthority>();
		Iterator<SysPermission> itPermission = permission.iterator();
		while(itPermission.hasNext()) {
			SysPermission temp = itPermission.next();
			GrantedAuthority grantedauthority = new SimpleGrantedAuthority(temp.getName());
			grantedAuthorities.push(grantedauthority);
		}
		return grantedAuthorities;
	}
}
