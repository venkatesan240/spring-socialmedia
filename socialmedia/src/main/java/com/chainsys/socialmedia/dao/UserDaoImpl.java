package com.chainsys.socialmedia.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.socialmedia.mapping.UserRowMapper;
import com.chainsys.socialmedia.model.User;

@Repository
public class UserDaoImpl implements UserDAO{

	 @Autowired
	    JdbcTemplate  jdbcTemplate;
	    @Autowired
	    UserRowMapper ur;
	  
	    public boolean emailExists(String email) {
	        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
	        Integer count = jdbcTemplate.queryForObject(query, new Object[]{email}, Integer.class);
	        return count != null && count > 0;
	    }

	    public String save(User user) {
	        if (emailExists(user.getEmail())) {
	            return "User already exists";
	        }

	        String query = "INSERT INTO user(first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
	        Object[] params = {user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword()};
	        jdbcTemplate.update(query, params);
	        return "Registration successful";
	    }

		@Override
		public int loginCredencial(User user) {
			String query = "SELECT COUNT(*) FROM user WHERE email = ? AND password = ?";
			Object[] params= {user.getEmail(),user.getPassword()};
			 try {
		            Integer count = jdbcTemplate.queryForObject(query, params, Integer.class);
		            return count != null ? count : 0;
		        } catch (EmptyResultDataAccessException e) {
		            return 0;
		        }
		}

		@Override
		public List<User> getUserDetails(User user) {
			String query="select * from user where email=?";
			Object[] params= {user.getEmail()};
			return jdbcTemplate.query(query,params,ur);
		}
}
