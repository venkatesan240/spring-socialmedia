package com.chainsys.socialmedia.dao;

import org.springframework.stereotype.Repository;

import com.chainsys.socialmedia.model.User;

@Repository
public interface UserDAO {

	public void save(User user);
}
