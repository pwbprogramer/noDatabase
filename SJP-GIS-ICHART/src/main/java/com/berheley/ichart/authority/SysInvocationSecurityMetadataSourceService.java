package com.berheley.ichart.authority;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.berheley.ichart.dao.PermissionRepository;
import com.berheley.ichart.domain.SysPermission;
@Service
public class SysInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    
	@Autowired
	private PermissionRepository  permissionRepository;
	
	private ConcurrentHashMap<String , Collection<ConfigAttribute>> map = null;
	
	public void getPermissions() {
		map = new ConcurrentHashMap<>();
		List<SysPermission> permissions = permissionRepository.findAll();
		if(permissions!=null&&permissions.size()!=0) {
			for (SysPermission sysPermission : permissions) {
				Collection<ConfigAttribute> configAttributes = new LinkedList<>();
				//目前仅添加权限name，如有需要可以添加更多信息
				ConfigAttribute configAttribute = new SecurityConfig(sysPermission.getName());
				configAttributes.add(configAttribute);
				map.put(sysPermission.getUrl(), configAttributes);
			}
		}
	}
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if(map==null) {
			getPermissions();
		}
		//object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        Iterator<Entry<String,Collection<ConfigAttribute>>> it= map.entrySet().iterator();
        while(it.hasNext()) {
        	Entry<String,Collection<ConfigAttribute>> entry = it.next();
        	AntPathRequestMatcher matcher = new AntPathRequestMatcher(entry.getKey());
        	if(matcher.matches(request)) {
        		return entry.getValue();
        	}
        }
		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

}
