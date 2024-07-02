package com.chainsys.socialmedia.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.socialmedia.mapping.PostRowMapper;
import com.chainsys.socialmedia.mapping.UserRowMapper;
import com.chainsys.socialmedia.model.Post;
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
		public int loginCredencial(String email,String password) {
			String query = "SELECT COUNT(*) FROM user WHERE email = ? AND password = ?";
			Object[] params= {email,password};
			 try {
		            Integer count = jdbcTemplate.queryForObject(query, params, Integer.class);
		            return count != null ? count : 0;
		        } catch (EmptyResultDataAccessException e) {
		            return 0;
		        }
		}

		@Override
		public User getUserDetails(String email) {
			String query="select * from user where email=?";
			Object[] params= {email};
			return jdbcTemplate.queryForObject(query,params,ur);
		}

		@Override
		public String updateUser(User user) {
			String updateQuery="update user set first_name=?,last_name=?,email=?,profile=? where user_id=?";
			Object[] params= {user.getFirstName(),user.getLastName(),user.getEmail(),user.getProfile(),user.getUserId()};
			jdbcTemplate.update(updateQuery, params);
			return "updated sucessfully";
		}

		@Override
		public int getId(String email) {
			String query="select user_id from user where email=?";
			Object[] params= {email};
			return jdbcTemplate.queryForObject(query, params, Integer.class);
		}

		@Override
		public User getUserById(int id) {
			String query="select * from user where user_id=?";
			Object[] params= {id};
			return jdbcTemplate.queryForObject(query,params,ur);
		}

		@Override
		public void savePost(Post post) {
			String query = "INSERT INTO posts (user_id,description, image,user_name) values (?,?,?,?)";
			Object[] params= {post.getUserId(),post.getDescription(),post.getImage(),post.getUserName()};
			jdbcTemplate.update(query, params);
		}

		@Override
		public List<Post> getAllPosts() {
			String query = "SELECT id,user_id,user_name,description,image,timestamp FROM posts";
			return jdbcTemplate.query(query,new PostRowMapper());			
		}
}
