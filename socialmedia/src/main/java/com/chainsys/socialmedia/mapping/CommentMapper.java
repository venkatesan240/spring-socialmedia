package com.chainsys.socialmedia.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.socialmedia.model.Comment;

public class CommentMapper implements RowMapper<Comment>{

	@Override
	public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
		Comment comment = new Comment();
        comment.setCommentId(rs.getInt("comment_id"));
        comment.setUserid(rs.getInt("user_id"));
        comment.setComment(rs.getString("content"));
        comment.setCreatedAt(rs.getString("created_at"));
        return comment;
	}

}
