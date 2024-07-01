package com.chainsys.socialmedia.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.chainsys.socialmedia.model.User;
@Component
public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		 User user = new User();
         user.setUserId(rs.getInt("id"));
         user.setFirstName(rs.getString("username"));
         user.setLastName(rs.getString("last_name"));
         user.setEmail(rs.getString("email"));
         user.setPassword(rs.getString("password"));       
         return user;
	}

}
