package com.chainsys.socialmedia.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import com.chainsys.socialmedia.model.User;
@Component
public class UserMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		 User user = new User();
         user.setUserId(rs.getInt("user_id"));
         user.setFirstName(rs.getString("first_name"));
         user.setLastName(rs.getString("last_name"));
         user.setEmail(rs.getString("email"));
         user.setPassword(rs.getString("password"));  
         byte[] imageBytes = rs.getBytes("profile");
         if (imageBytes != null) {
             user.setProfile(Base64.getEncoder().encodeToString(imageBytes));
         } else {
             user.setProfile(null); // or set a default value if needed
         }
        // user.setProfile(Base64.getEncoder().encodeToString(rs.getBytes("profile")));
         return user;
	}
}
