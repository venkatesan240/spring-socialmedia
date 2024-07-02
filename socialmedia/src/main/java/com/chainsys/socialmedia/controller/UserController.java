package com.chainsys.socialmedia.controller;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.chainsys.socialmedia.dao.UserDAO;
import com.chainsys.socialmedia.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

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
	public String toRegister(@RequestParam("first-name") String firstName,@RequestParam("last-name") String lastName,@RequestParam("email") String email,@RequestParam("password") String password,Model model) throws ClassNotFoundException {
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(password);
		String result = userDao.save(user);
        if ("Registration successful".equals(result)) {
            model.addAttribute("rmsg", result);
            return "signin.jsp";
        } else {
            model.addAttribute("error", result);
            return "signup.jsp";
        }
	}
	
	@PostMapping("/signin")
	public String toLogin(HttpSession session ,	@RequestParam("email")String email,@RequestParam("password")String password,Model model) {
		user.setEmail(email);
		user.setPassword(password);
		int count = userDao.loginCredencial(user);
		System.out.println("count"+count);
        if (count > 0) {
        	System.out.println("inside session");
        	User users = userDao.getUserDetails(user);
        	int userid = userDao.getId(email);
            session.setAttribute("user", users);
            session.setAttribute("userid", userid);
            return "header.jsp";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "signin.jsp";
        }		
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("signin.jsp");
	}
	
	@PostMapping("/UpdateProfile")
	public String updateUser(HttpSession session, @RequestParam("first-name") String firstName,@RequestParam("last-name") String lastName,@RequestParam("email") String email,@RequestParam("profile-image") Part part,Model model) throws IOException {
		 int userid = (Integer) session.getAttribute("userid");
		    if (userid == 0) {
		        return "signin.jsp";
		    } 
		InputStream is=null;
		byte[] data = null;
		if(part != null) {
			is=part.getInputStream();
			data=new byte[is.available()];
			is.read(data);
			 is.close();
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setProfile(data);
		user.setUserId(userid);
		System.out.println("user id"+user.getUserId());
		String  result = userDao.updateUser(user);
		if(result.equals("updated sucessfully")) {
			 model.addAttribute("alert", "profile updated sucessfully");
			 return "header.jsp";
		}
		model.addAttribute("alert","updation failed");
		return  "update.jsp";
	}
}
