package com.chainsys.socialmedia.dao;

import java.util.List;
import org.springframework.stereotype.Repository;

import com.chainsys.socialmedia.model.Comment;
import com.chainsys.socialmedia.model.Post;
import com.chainsys.socialmedia.model.User;

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
}
