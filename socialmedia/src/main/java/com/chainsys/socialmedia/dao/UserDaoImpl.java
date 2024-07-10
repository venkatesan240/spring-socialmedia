package com.chainsys.socialmedia.dao;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.chainsys.socialmedia.mapping.CommentMapper;
import com.chainsys.socialmedia.mapping.LikeMapper;
import com.chainsys.socialmedia.mapping.UserMapper;
import com.chainsys.socialmedia.mapping.PostMapper;
import com.chainsys.socialmedia.mapping.MessageMapper;
import com.chainsys.socialmedia.mapping.ReportMapper;
import com.chainsys.socialmedia.model.Comment;
import com.chainsys.socialmedia.model.Message;
import com.chainsys.socialmedia.model.Post;
import com.chainsys.socialmedia.model.User;
import com.chainsys.socialmedia.model.UserReport;

@Repository("userDao")
public class UserDaoImpl implements UserDAO{

	 @Autowired
	    JdbcTemplate  jdbcTemplate;
	 
	    @Autowired
	    UserMapper ur; 
	    
	    CommentMapper crm;
	  
	    public boolean emailExists(String email) {
	        String query = "SELECT COUNT(*) FROM user WHERE email = ? AND is_deleted = 0";
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
			String query = "SELECT COUNT(*) FROM user WHERE email = ? AND password = ? AND is_deleted = 0";
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
			String query="select * from user where email=? AND is_deleted = 0";
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
			String query="select user_id from user where email=? AND is_deleted = 0";
			Object[] params= {email};
			return jdbcTemplate.queryForObject(query, params, Integer.class);
		}

		@Override
		public User getUserById(int id) {
			String query="select * from user where user_id=? AND is_deleted = 0";
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
			return jdbcTemplate.query(query,new PostMapper());			
		}

		@Override
		public boolean deletePost(int postId) {
			 String sql = "DELETE FROM posts WHERE id = ?";
		        int rowsAffected = jdbcTemplate.update(sql, postId);
		        return rowsAffected > 0;
		}

		@Override
		public void addComment(Comment comment) {
			 String query = "INSERT INTO comments (post_id, user_id, content) VALUES (?, ?, ?)";
			 Object[] params= {comment.getPostId(),comment.getUserId(),comment.getComment()};
			 jdbcTemplate.update(query, params);
		}

		@Override
		public List<Comment> getCommentsByPostId(int postId) {
			String query = "SELECT * FROM comments WHERE post_id = ?";
			return jdbcTemplate.query(query,new CommentMapper() ,new Object[]{postId});
		}

		@Override
		public void addLike(int userId, int postId) {
			String query = "INSERT INTO likes (user_id, post_id) VALUES (?, ?)";
			 try {
		            jdbcTemplate.update(query, userId, postId);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		}

		@Override
		public void removeLike(int postId, int userId) {
			String query = "DELETE FROM likes WHERE user_id = ? AND post_id = ?";
			try {
	            jdbcTemplate.update(query, userId, postId);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}

		@Override
		public int getLikeCount(int postId) {
			String query = "SELECT COUNT(*) AS like_count FROM likes WHERE post_id = ?";
			 try {
		            return jdbcTemplate.queryForObject(query, new Object[]{postId}, Integer.class);
		        } catch (Exception e) {
		            e.printStackTrace();
		            return 0;
		        }
		}

		@Override
		public boolean isLikedByUser(int postId, int userId) {
			String query = "SELECT COUNT(*) FROM likes WHERE user_id = ? AND post_id = ?";
			try {
	            Integer count = jdbcTemplate.queryForObject(query, new Object[]{userId, postId}, Integer.class);
	            return count != null && count > 0;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
		}

		@Override
		public String getName(String email) {
			String query="select first_name from user where email=? AND is_deleted = 0";
			Object[] params= {email};
			return jdbcTemplate.queryForObject(query, params, String.class); 
		}

		@Override
		public List<User> getUsersWhoLiked(int postId) {
			final String SELECT_USERS_WHO_LIKED = "SELECT u.user_id, u.first_name, u.last_name, u.profile FROM likes l INNER JOIN user u ON l.user_id = u.user_id WHERE l.post_id = ?";
	        return jdbcTemplate.query(SELECT_USERS_WHO_LIKED, new Object[]{postId}, new LikeMapper());
		}

		@Override
		public List<User> selectUsers() {
			 String query = "SELECT user_id, first_name,last_name,profile FROM user where  is_deleted = 0";
			return jdbcTemplate.query(query,new LikeMapper() );
		}

		@Override
		public void insertMessage(Message message) {
			 String query = "INSERT INTO messages (sender_id, receiver_id, message) VALUES (?, ?, ?)";
			 Object[] param= {message.getSenderId(),message.getReceiverId(),message.getMessage()};
			 jdbcTemplate.update(query,param);			
		}

		@Override
		public List<Message> getMessage(Message message) {
			String query = "SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (receiver_id = ? AND sender_id = ?) ORDER BY timestamp";
			Object[] param= {message.getSenderId(),message.getReceiverId(),message.getSenderId(),message.getReceiverId()};
			return 	jdbcTemplate.query(query, param, new MessageMapper());	
		}

		@Override
		public boolean deleteMessage(int messageId) {
			String query="DELETE FROM messages WHERE id = ?";
			Object[] param= {messageId};
			jdbcTemplate.update(query, param);
			return  true;
		}
		
		public List<User> getUsers() {
			String query="select * from user where is_active=0";
			return  jdbcTemplate.query(query, new UserMapper());
		}

		@Override
		public void addToUser() {
			String query="UPDATE user SET is_active = 1	WHERE email LIKE '%@connect.com'";
			jdbcTemplate.update(query);		
		}

		@Override
		public void insertReport(int reportedId,String reason,int senderId,String message) {
			String query="insert into reports(sender_id,reason,reported_id,content)values(?,?,?,?)";
			Object[] param= {senderId,reason,reportedId,message};
			jdbcTemplate.update(query, param);
		}

		@Override
		public List<UserReport> getReport() {
			String query="select id,sender_id,reported_id,report_date,reason,content from reports";
			return jdbcTemplate.query(query, new ReportMapper());
		}		
		
		public void deleteUser(int userId) {
			String query="update user set is_deleted=1 where user_id=?";
			Object[] param= {userId};
			jdbcTemplate.update(query, param);
		}

		@Override
		public Message getReportedMessage(int senderId,int receiverId) {
			String query="SELECT id,sender_id,receiver_id,message,timestamp FROM messages WHERE sender_id = ? AND receiver_id = ? ORDER BY id DESC LIMIT 1";
			Object[] param= {receiverId,senderId};
			return   jdbcTemplate.queryForObject(query,new MessageMapper(),param);
		}
}
