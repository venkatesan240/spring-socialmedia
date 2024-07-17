package com.chainsys.socialmedia.dao;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.chainsys.socialmedia.model.Comment;
import com.chainsys.socialmedia.model.Message;
import com.chainsys.socialmedia.model.Post;
import com.chainsys.socialmedia.model.ReportPost;
import com.chainsys.socialmedia.model.User;
import com.chainsys.socialmedia.model.UserReport;

@Repository
public interface UserDAO {

	public String save(User user);
	public int loginCredencial(String email,String password);
	public User getUserDetails(String email);
	public String updateUser(User user);
	public int getId(String email);
	public String getName(String name);
	public User getUserById(int id);
	public void savePost(Post post);
	public List<Post> getAllPosts();
	public boolean deletePost(int postId);
	public void addComment(Comment comment);
	public List<Comment> getCommentsByPostId(int postId);
	public void addLike(int userId,int postId);
	public void removeLike(int postId,int userId);
	public int getLikeCount(int postId);
	public boolean isLikedByUser(int postId,int userId);
	public List<User> getUsersWhoLiked(int postId);
	public List<User> selectUsers();
	public List<Message> getMessage(Message message);
	public boolean deleteMessage(int messageId);
	public void insertMessage(Message message);
	public List<User> getUsers();
	public void addToUser();
	public void insertReport(int reportedId,String reason,int senderId,String message);
	public List<UserReport> getReport();
	public void deleteUser(int userId);
	public Message getReportedMessage(int senderId,int receiverId); 
	public void reportPost(int postId,int userId,byte[] image,String contnt,String reason);
	public Post getPost(int postId);
	public List<ReportPost> getPostReport();
	public List<User> toSearch(String name);
}
