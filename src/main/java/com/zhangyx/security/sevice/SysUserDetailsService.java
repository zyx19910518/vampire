package com.zhangyx.security.sevice;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.zhangyx.security.dao.ResourceDao;
import com.zhangyx.security.dao.RoleDao;
import com.zhangyx.security.entity.Role;
import com.zhangyx.security.entity.User;

@SuppressWarnings("deprecation")
@Service
public class SysUserDetailsService implements UserDetailsService {
	@Autowired
	private com.zhangyx.security.dao.UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceDao resourceDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getUserByName(username);
		if(user == null) {
			throw new UsernameNotFoundException("User " + username + "doesn't exist!");
		}
        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);  
        
        boolean enables = true;  
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = true;  
          
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);  
    }  
      
	private Set<GrantedAuthority> obtionGrantedAuthorities(User user) {  
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();  
        List<Role> roles = roleDao.getRolesByUserId(user.getId());
          
        for(Role role : roles) {
        	authSet.add(new GrantedAuthorityImpl(role.getCode()));  
        }  
        return authSet;
	}
}