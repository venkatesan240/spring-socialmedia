package com.chainsys.socialmedia.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
	  
	public void save(User user) {
		String query="insert into user(first_name,last_name,email,password)values(?,?,?,?)";
		Object[] parems= {user.getFirstName(),user.getLastName(),user.getEmail(),user.getPassword()};
		int  rows = jdbcTemplate.update(query, parems);
	}
}
