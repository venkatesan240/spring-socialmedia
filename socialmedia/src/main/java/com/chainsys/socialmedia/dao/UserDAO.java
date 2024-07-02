package com.chainsys.socialmedia.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chainsys.socialmedia.model.Post;
import com.chainsys.socialmedia.model.User;

@Repository
public interface UserDAO {

	public String save(User user);
	public int loginCredencial(String email,String password);
	public User getUserDetails(String email);
	public String updateUser(User user);
	public int getId(String email);
	public User getUserById(int id);
	public void savePost(Post post);
	public List<Post> getAllPosts();
}
