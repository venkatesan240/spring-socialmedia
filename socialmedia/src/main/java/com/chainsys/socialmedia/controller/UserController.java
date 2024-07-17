package com.chainsys.socialmedia.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chainsys.socialmedia.dao.UserDAO;
import com.chainsys.socialmedia.model.Comment;
import com.chainsys.socialmedia.model.Message;
import com.chainsys.socialmedia.model.Post;
import com.chainsys.socialmedia.model.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@Controller
public class UserController {

    User user = new User();

    @Autowired
    private UserDAO userDao;

    @RequestMapping("/")
    public String save() {
        return "signin.jsp";
    }

    @RequestMapping("/signup")
    public String toRegister(@RequestParam("first-name") String firstName, @RequestParam("last-name") String lastName, @RequestParam("email") String email, @RequestParam("password") String password, Model model) throws ClassNotFoundException {
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();

        if (!firstName.matches("[a-zA-Z]{5,}")) {
            isValid = false;
            errorMessage.append("First name should contain only letters and at least 5 characters long.<br>");
        }
        if (!lastName.matches("[a-zA-Z]{1,}")) {
            isValid = false;
            errorMessage.append("Last name should contain only letters.<br>");
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            isValid = false;
            errorMessage.append("Email should be at least 10 characters long and contain only letters, numbers, and '@'.<br>");
        }
        if (!password.matches("[a-zA-Z0-9@#]{4,}")) {
            isValid = false;
            errorMessage.append("Password should be at least 4 characters long and contain only letters, numbers, and '@' or '#'.<br>");
        }

        if (isValid) {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            String result = userDao.save(user);
            model.addAttribute("Message", result);
            return "signin.jsp";
        } else {
            model.addAttribute("error", errorMessage.toString());
            return "signup.jsp";
        }
    }

    @PostMapping("/signin")
    public String toLogin(HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        int count = userDao.loginCredencial(email, password);
        if (count > 0) {
            User users = userDao.getUserDetails(email);
            int userid = userDao.getId(email);
            String name = userDao.getName(email);
            session.setAttribute("user", users);
            session.setAttribute("userid", userid);
            session.setAttribute("name", name);
            if(email.endsWith("@connect.com")){
            	userDao.addToUser();
            	return "adminmain.jsp";
            }else {
            	return "home.jsp";
            }
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
    public String updateUser(HttpSession session, @RequestParam("first-name") String firstName, @RequestParam("last-name") String lastName, @RequestParam("email") String email, @RequestParam("profile-image") Part part, Model model) throws IOException {
        int userid = (Integer) session.getAttribute("userid");
        if (userid == 0) {
            return "signin.jsp";
        }
        InputStream is = null;
        byte[] data = null;
        if (part != null) {
            is = part.getInputStream();
            data = new byte[is.available()];
            is.read(data);
            is.close();
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setProfile(data);
        user.setUserId(userid);
        String result = userDao.updateUser(user);
        if (result.equals("updated successfully")) {
            session.setAttribute("alert", result);
            return "header.jsp";
        }
        model.addAttribute("alert", "updation failed");
        return "profile.jsp";
    }

    @PostMapping("/post")
    public String createPost(@RequestParam("post-content") String content, @RequestParam("post-image") Part part, @RequestParam("userid") int userId, @RequestParam("username") String name) throws IOException {
        InputStream is = null;
        byte[] data = null;
        if (part != null) {
            is = part.getInputStream();
            data = new byte[is.available()];
            is.read(data);
            is.close();
        }
        Post post = new Post();
        System.out.println(part.getContentType());
        post.setContentType(part.getContentType());
        post.setDescription(content);
        post.setImage(data);
        post.setUserId(userId);
        post.setUsername(name);
        userDao.savePost(post);
        return "post.jsp";
    }

    @PostMapping("/deletePost")
    public String deletePost(@RequestParam("id") String postId, Model model) {
        if (postId != null) {
            boolean deleted = false;
            try {
                deleted = userDao.deletePost(Integer.parseInt(postId));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (deleted) {
                return "post.jsp";
            } else {
                model.addAttribute("error", "Unable to delete the post.");
                return "post.jsp";
            }
        } else {
            model.addAttribute("error", "Invalid post ID.");
            return "post.jsp";
        }
    }

    @PostMapping("/like")
    public ResponseEntity<Map<String, Object>> handleLike(@RequestBody String requestBody) {
        JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
        int postId = jsonObject.get("postId").getAsInt();
        int userId = jsonObject.get("userId").getAsInt();
        boolean liked = false;
        int likeCount = 0;
        try {
            if (userDao.isLikedByUser(postId, userId)) {
                userDao.removeLike(postId, userId);
            } else {
                userDao.addLike(userId, postId);
                liked = true;
            }
            likeCount = userDao.getLikeCount(postId);
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("success", true);
            jsonResponse.put("liked", liked);
            jsonResponse.put("likeCount", likeCount);
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("success", false);
            return ResponseEntity.status(500).body(jsonResponse);
        }
    }

    @PostMapping("/Comment")
    public String addComment(@RequestParam("postid") int postId, @RequestParam("userid") int userId, @RequestParam("comment") String comment) {
        Comment cmt = new Comment();
        cmt.setComment(comment);
        cmt.setPostId(postId);
        cmt.setUserid(userId);
        userDao.addComment(cmt);
        return "post.jsp";
    }
    
    @RequestMapping("/userlist")
    public String getUserList(Model model) {
    	List<User> selectUsers = userDao.selectUsers();
    	model.addAttribute("users",selectUsers);
		return "chat.jsp";
    }
    
    @PostMapping("/Chat")
    public String addMessage(@RequestParam("senderId") int senderId,@RequestParam("receiverId") int receiverId,@RequestParam("message") String message,Model model) {
    	Message msg=new Message();
		msg.setSenderId(senderId);
		msg.setReceiverId(receiverId);
		if(message.matches("[a-zA-Z]")) {
			msg.setMessage(message);
			userDao.insertMessage(msg);			
		}
		else {
			model.addAttribute("msg","only alphabets");
		}
		model.addAttribute("receiverId", receiverId); 
		return "viewmessage.jsp?";
    }
    
    @GetMapping("/deleteChat")
    public String deleteChat( @RequestParam("delete") int messageId,@RequestParam("id") int id,
                             Model model) {
            boolean success = userDao.deleteMessage(messageId);			
			  if (success) { model.addAttribute("status", "success");
			  model.addAttribute("receiverId", id);
			  
			  } else { model.addAttribute("status", "error"); }
        return "redirect:/viewmessage?receiverId="+id;
    }
    
    @GetMapping("/viewmessage")
    public String getViewMessage(HttpServletRequest request, Model model) {
		int receiverId =Integer.parseInt(request.getParameter("receiverId"));
    	model.addAttribute("receiverId", receiverId);  	
//    	System.out.println("receiverId---->"+receiverId);
		return "viewmessage.jsp";
    }
    
    @PostMapping("/reportUser")
    public ResponseEntity<String> reportUser(@RequestBody String requestBody) {
        JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
        int reportedId = jsonObject.get("reportedId").getAsInt();
        String reason = jsonObject.get("reason").getAsString();
        int senderId=jsonObject.get("senderId").getAsInt();
        Message message=userDao.getReportedMessage(senderId,reportedId);
        if(message != null) {
        	String msg=message.getMessage();
            userDao.insertReport(reportedId, reason,senderId,msg);
            return new ResponseEntity<>("User reported successfully", HttpStatus.OK);
        }
		return ResponseEntity.status(HttpStatus.NOT_FOUND) .body("Message not found or you are not authorized to report this user");
        
    }
    
    @PostMapping("/delete")
    public String toDeleteUser(@RequestParam("userid") int userId, Model model) {
    	userDao.deleteUser(userId);
    	model.addAttribute("isblocked","true");
		return "admin.jsp"; 
	} 
    
    @PostMapping("/reportPost")
    public String reportPost(@RequestParam("id") int postId,@RequestParam("reason") String reason, Model model) {
            Post post = userDao.getPost(postId);
            int userId=post.getUserId();
            byte[] image = post.getImage();
           if(userId != 0 ) {
        	   userDao.reportPost(postId, userId, image,post.getContentType(),reason);
               model.addAttribute("reportStatus","Reported successfully");
   				return "post.jsp";
           }else {
        	   model.addAttribute("reportStatus","Report failure");
        	   return "post.jsp";
           }
            
    }
    
    @PostMapping("/Search")
    public String toSearch(@RequestParam("name") String name,Model model) {
    	List<User>  users = userDao.toSearch(name);
    	model.addAttribute("users", users);
		return "chat.jsp";   	
    }
    
    @PostMapping("/deletepost")
    public String deletePost(@RequestParam("postid") int postId) {
    	userDao.deletePost(postId);
		return "admin.jsp";    	
    }
}
