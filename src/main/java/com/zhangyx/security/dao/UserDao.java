package com.zhangyx.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.zhangyx.security.entity.User;

@Component
public class UserDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User getUserByName(String username) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT u.* FROM USER u WHERE u.username = '").append(username).append("'");
		return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<User>() {
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setEnabled(rs.getBoolean("enabled"));
				return user;
			}
		});
	}
}