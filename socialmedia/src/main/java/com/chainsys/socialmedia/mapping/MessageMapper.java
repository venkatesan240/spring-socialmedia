package com.chainsys.socialmedia.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chainsys.socialmedia.model.Message;

public class MessageMapper implements RowMapper<Message>{

	@Override
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message msg = new Message();
        msg.setId(rs.getInt("id"));
        msg.setMessage(rs.getString("message"));
        msg.setSenderId(rs.getInt("sender_id"));
        msg.setReceiverId(rs.getInt("receiver_id"));
        msg.setTimestamp(rs.getString("timestamp"));
        return msg;
	}
	
	

}
