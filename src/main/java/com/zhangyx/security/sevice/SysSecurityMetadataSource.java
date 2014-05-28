package com.zhangyx.security.sevice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.zhangyx.security.dao.ResourceDao;

public class SysSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private ResourceDao resourceDao;

	private static Map<RequestMatcher, Collection<ConfigAttribute>> resourceMap = null;
	
	public SysSecurityMetadataSource(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
		loadResourceDefine();
	}

	public ResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(ResourceDao ResourceDao) {
		this.resourceDao = ResourceDao;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
	private void loadResourceDefine() {
		if(resourceMap == null) {
			resourceMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();
			List<Map<String, String>> resources = this.resourceDao.findAll();
			for (Map<String, String> map : resources) {
				AntPathRequestMatcher matcher = new AntPathRequestMatcher(map.get("url"));
				
				Collection<ConfigAttribute> configAttributes = resourceMap.get(matcher);
				if(configAttributes == null) {
					configAttributes = new ArrayList<ConfigAttribute>();
				}
				ConfigAttribute configAttribute = new SecurityConfig(map.get("roleCode"));
				configAttributes.add(configAttribute);
				resourceMap.put(matcher, configAttributes);
			}
		}
	}
	
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
 		for (Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            if (entry.getKey().matches(request)) {
                return entry.getValue();
            }
        }
		return null;
	}
}