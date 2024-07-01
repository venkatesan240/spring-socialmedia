package com.chainsys.socialmedia.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.socialmedia.dao.UserDAO;
import com.chainsys.socialmedia.model.User;

@Controller
public class UserController {

User user=new User();
	
	@Autowired
	private UserDAO userDao;
	
	@RequestMapping("/")
	public String save() {		
		return "signin.jsp";
	}
	
	@RequestMapping("/signup")
	public String toRegister(@RequestParam("first-name") String firstName,@RequestParam("last-name") String lastName,@RequestParam("email") String email,@RequestParam("password") String password,Model model) {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		userDao.save(user);
		return "signin.jsp";
	}
	
}
