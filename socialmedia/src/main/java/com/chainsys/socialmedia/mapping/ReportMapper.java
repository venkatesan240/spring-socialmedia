package com.chainsys.socialmedia.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.socialmedia.model.UserReport;

public class ReportMapper implements RowMapper<UserReport> {

	@Override
	public UserReport mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserReport userReport=new UserReport();
		userReport.setId(rs.getInt("id"));
		userReport.setSenderId(rs.getInt("sender_id"));
		userReport.setReportedId(rs.getInt("reported_id"));
		userReport.setReportDate(rs.getString("report_date"));
		userReport.setReason(rs.getString("reason"));
		userReport.setContent(rs.getString("content"));
		return userReport;
	}

}
