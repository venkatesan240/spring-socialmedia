package com.chainsys.socialmedia.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.chainsys.socialmedia.model.User;

@Repository
public interface UserDAO {

	public String save(User user);
	public int loginCredencial(User user);
	public User getUserDetails(User user);
	public String updateUser(User user);
	public int getId(String email);
	public User getUserById(int id);
}
