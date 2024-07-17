package com.chainsys.socialmedia.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.socialmedia.model.Post;

public class PostMapper implements RowMapper<Post> {
	
	public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
		Post post=new Post();
		post.setDescription(rs.getString("description"));
        post.setImage(rs.getBytes("image"));
        post.setUsername(rs.getString("user_name"));
        post.setTimestamp(rs.getString("timestamp"));
        post.setContentType(rs.getString("content"));
        post.setId(rs.getInt("id"));
        post.setUserId( rs.getInt("user_id"));
		return post;
	}

}
