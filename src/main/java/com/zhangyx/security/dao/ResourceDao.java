package com.zhangyx.security.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.zhangyx.security.entity.Resource;

@Component
public class ResourceDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Map<String, String>> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("select ro.code as role_code,res.url from role ro, roles_resources rr, resources res where ro.id = rr.role_id and rr.resource_id = res.id");
		return jdbcTemplate.query(sql.toString(), new RowMapper<Map<String, String>>() {
			@Override
			public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put("roleCode", rs.getString("role_code"));
				map.put("url", rs.getString("url"));
				return map;
			}
		});
	}

	public List<Resource> getResourcesByRoleId(Long id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select res.* from resources res,roles_resources rr where rr.role_id=").append(id).append(" and rr.resource_id=res.id");
		return jdbcTemplate.query(sql.toString(), new RowMapper<Resource>() {
			@Override
			public Resource mapRow(ResultSet rs, int rowNum) throws SQLException {
				Resource resource = new Resource();
				resource.setId(rs.getLong("id"));
				resource.setName(rs.getString("name"));
				resource.setUrl(rs.getString("url"));
				resource.setComment(rs.getString("comment"));
				return resource;
			}
		});
	}
}
