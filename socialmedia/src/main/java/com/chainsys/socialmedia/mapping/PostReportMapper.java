package com.chainsys.socialmedia.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.socialmedia.model.ReportPost;

public class PostReportMapper implements RowMapper<ReportPost>{

	@Override
	public ReportPost mapRow(ResultSet rs, int rowNum) throws SQLException {
		ReportPost reportPost=new ReportPost();
		reportPost.setId(rs.getInt("id"));
		reportPost.setPostId(rs.getInt("post_id"));
		reportPost.setUserId(rs.getInt("user_id"));
		reportPost.setReportDate(rs.getString("report_date"));
		reportPost.setImage(rs.getBytes("post"));
		reportPost.setContent(rs.getString("content_type"));
		reportPost.setReason(rs.getString("reason"));
		return reportPost;
	}

}
