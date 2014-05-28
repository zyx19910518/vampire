package com.zhangyx.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.zhangyx.security.entity.Role;

@Component
public class RoleDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Role> getRolesByUserId(Long userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT r.* FROM role r,user_role ur WHERE ur.user_id=").append(userId).append(" AND ur.role_id=r.id");
		return jdbcTemplate.query(sql.toString(), new RowMapper<Role>() {
			@Override
			public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
				Role role = new Role();
				role.setId(rs.getLong("id"));
				role.setName(rs.getString("name"));
				role.setCode(rs.getString("code"));
				return role;
			}
		});
	}
}