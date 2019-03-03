package com.berheley.ichart.authority;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * 
 * @author pwb
 *
 */
@Component
public class SysAccessDecisionManager implements AccessDecisionManager {
    
	/**
	 * authentication 该用户具有的权限集合
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		//无权限
		if(configAttributes==null||configAttributes.size()==0) {
			return;
		}
		Iterator<ConfigAttribute>  configattribute = configAttributes.iterator();
		while(configattribute.hasNext()) {
			ConfigAttribute temp = configattribute.next();
			String attr = temp.getAttribute();
			for (GrantedAuthority  grantedauthority  : authentication.getAuthorities()) {
				if(attr.trim().equals(grantedauthority.getAuthority())) {
					return;
				};
			};
		}
         throw new AccessDeniedException("无权限访问该资源");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
