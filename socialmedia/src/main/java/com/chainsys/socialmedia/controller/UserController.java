package com.chainsys.socialmedia.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.chainsys.socialmedia.dao.UserDAO;
import com.chainsys.socialmedia.model.Comment;
import com.chainsys.socialmedia.model.Message;
import com.chainsys.socialmedia.model.Post;
import com.chainsys.socialmedia.model.User;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@Controller
public class UserController {

    User user = new User();

    @Autowired
    private UserDAO userDao;

    @RequestMapping("/")
    public String save() {
        return "SignIn";
    }
    
        @GetMapping("/signup")
        public String showSignupForm() {
            return "SignUp";
        }  
        
        @GetMapping("/signin")
        public String showSignInForm() {
            return "SignIn";
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
            return "SignIn";
        } else {
            model.addAttribute("error", errorMessage.toString());
            return "SignUp";
        }
    }

    @PostMapping("/signin")
    public String toLogin(HttpSession session, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        int count = userDao.loginCredencial(email, password);
        if (count > 0) {
            User user = userDao.getUserDetails(email);
            int userId = userDao.getId(email);
            String name = userDao.getName(email);
            session.setAttribute("user", user);
            session.setAttribute("userid", userId);
            session.setAttribute("name", name);
            if (email.endsWith("@connect.com")) {
                userDao.addToUser();
                return "adminmain";
            } else {
                return "redirect:/home";
            }
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "SignIn";
        }
    }
    
    @GetMapping("/home")
    public String getPost(Model model,HttpSession session) {
    	 Integer userId = (Integer) session.getAttribute("userid");
         if (userId == null) {
             return "redirect:/signin";
         }
    	 List<Post> posts = userDao.getAllPosts();
         for (Post post : posts) {
             User postUser = userDao.getUserById(post.getUserId());
             if (postUser != null && postUser.getProfile() != null) {
                 String base64Image = postUser.getProfile();
                 post.setUserProfileImage(base64Image);
             }
             String postImage = post.getImage();
             post.setImage(postImage);
             List<User> likedUsers = userDao.getUsersWhoLiked(post.getId());
             post.setLikedUsers(likedUsers);
             int likeCount = userDao.getLikeCount(post.getId());
             post.setLikeCount(likeCount);
             boolean liked = userDao.isLikedByUser(post.getId(),userId);
             post.setLiked(liked);
             List<Comment> comments = userDao.getCommentsByPostId(post.getId());
             for(Comment comment:comments) {
             	User commentUser = userDao.getUserById(comment.getUserId());
                 if (commentUser != null) {
                     comment.setUserName(commentUser.getFirstName());
                 }	               	
             }
             post.setComments(comments);
         }        
         model.addAttribute("posts", posts);
		return "Home";    	
    }
    
    @PostMapping("/resetpassword")
    public String resetPassword(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                @RequestParam("confirmPassword") String confirmPassword,
                                Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "Passwords do not match. Please try again.");
            return "resetpassword"; 
        }
        userDao.updatePassword(confirmPassword, email);
        model.addAttribute("successMessage", "Password has been successfully updated.");
        return "redirect:/login"; 
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("signin");
    }
    
    @GetMapping("/getupdate")
    public String getUpdate(HttpSession session,Model model) {
    	Integer userId = (Integer) session.getAttribute("userid");
        User user = userDao.getUserById(userId);
        model.addAttribute("user", user);
		return "update";
    }

    @PostMapping("/update")
    public String updateProfile(
        @RequestParam("first-name") String firstName,
        @RequestParam("last-name") String lastName,
        @RequestParam("email") String email,
        @RequestParam("profile-image") MultipartFile profileImage,
        HttpSession session,Model model,
        RedirectAttributes redirectAttributes) throws IOException {
        
        Integer userId = (Integer) session.getAttribute("userid");
        User user = userDao.getUserById(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        
        if (!profileImage.isEmpty()) {
            user.setProfile(Base64.getEncoder().encodeToString(profileImage.getBytes()));
        }
        model.addAttribute("user",user);
        userDao.updateUser(user);
        redirectAttributes.addFlashAttribute("alert", "Profile updated successfully!");
        return "redirect:/home";
    }
    
    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userid");
        User user = userDao.getUserById(userId);
        if (user != null && user.getProfile() != null) {
            String base64Image = user.getProfile();
            model.addAttribute("base64Image", base64Image);
        }
        model.addAttribute("user", user);
        return "profile";
    }
    
    @GetMapping("/post")
    public String showPostPage(Model model,HttpSession session) {
    	List<Post> Posts = userDao.getAllPosts();
    	 for (Post post : Posts) {
             User postUser = userDao.getUserById(post.getUserId());
             if (postUser != null && postUser.getProfile() != null) {
                 String base64Image = postUser.getProfile();
                 post.setUserProfileImage(base64Image);
             }
             List<User> likedUsers = userDao.getUsersWhoLiked(post.getId());
             post.setLikedUsers(likedUsers);
             int likeCount = userDao.getLikeCount(post.getId());
             post.setLikeCount(likeCount);
             Integer userId = (Integer) session.getAttribute("userid");
             boolean liked = userDao.isLikedByUser(post.getId(),userId);
             post.setLiked(liked);
             List<Comment> comments = userDao.getCommentsByPostId(post.getId());
             for(Comment comment:comments) {
             	User commentUser = userDao.getUserById(comment.getUserId());
                 if (commentUser != null) {
                     comment.setUserName(commentUser.getFirstName());
                 }	               	
             }
             post.setComments(comments);
         }        
    	model.addAttribute("posts", Posts);
		return "post";    	
    }

    @PostMapping("/post")
    public String createPost(@RequestParam("post-content") String content, 
                             @RequestParam("post-image") MultipartFile part, 
                             @RequestParam("userid") int userId, 
                             @RequestParam("username") String name, 
                             Model model) throws IOException {
        byte[] data = null;
        if (!part.isEmpty()) {
            InputStream is = part.getInputStream();
            data = new byte[is.available()];
            is.read(data);
            is.close();
        }
        
        Post post = new Post();
        post.setContentType(part.getContentType());
        post.setDescription(content);
        post.setImage(Base64.getEncoder().encodeToString(data));
        post.setUserId(userId);
        post.setUsername(name);
        userDao.savePost(post);
        
        List<Post> posts = userDao.getAllPosts();
        model.addAttribute("posts", posts);
        return "post";  // Assuming post.html or post.jsp is the Thymeleaf template
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
                List<Post> posts = userDao.getAllPosts();
                model.addAttribute("posts", posts);
                return "post";  
            } else {
                model.addAttribute("error", "Unable to delete the post.");
                return "post";
            }
        } else {
            model.addAttribute("error", "Invalid post ID.");
            return "post";
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

    @PostMapping("/comment")
    public String addComment(@RequestParam("postid") int postId, 
                             @RequestParam("userid") int userId, 
                             @RequestParam("comment") String comment, 
                             Model model) {
        Comment cmt = new Comment();
        cmt.setComment(comment);
        cmt.setPostId(postId);
        cmt.setUserId(userId);
        userDao.addComment(cmt);
        
        List<Post> posts = userDao.getAllPosts();
        model.addAttribute("posts", posts);
        return "post";  // Update view with the new list of posts
    }
    
//    @RequestMapping("/userlist")
//    public String getUserList(Model model) {
//    	List<User> selectUsers = userDao.selectUsers();
//    	model.addAttribute("users",selectUsers);
//		return "chat.jsp";
//    }
    @GetMapping("/userlist")
    public String chat(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userid");
        if (userId == null) {
            return "redirect:/signin";
        }
        List<User> users = userDao.selectUsers();
        model.addAttribute("users", users);
        model.addAttribute("currentUserId", userId);
        return "chat";
    }
    
    @GetMapping("/viewmessage")
    public String getChatPage(@RequestParam("receiverId") int receiverId, Model model, HttpSession session) {
        Integer senderId = (Integer) session.getAttribute("userid");
        if (senderId == null) {
            return "redirect:/signin";
        }
        System.out.println(receiverId);
        User user1 = userDao.getUserById(receiverId);
        String base64Image1 = "";
        if (user1 != null && user1.getProfile() != null) {
            base64Image1 = user1.getProfile();
        }
        Message msg=new Message();
		msg.setSenderId(senderId);
		msg.setReceiverId(receiverId);
        List<Message> messages = userDao.getMessage(msg);
        model.addAttribute("senderId", senderId);
        model.addAttribute("receiverId", receiverId);
        model.addAttribute("user1", user1);
        model.addAttribute("base64Image1", base64Image1);
        model.addAttribute("messages", messages);
        return "message";
    }
    
    @PostMapping("/Chat")
    public String addMessage(@RequestParam("senderId") int senderId, @RequestParam("receiverId") int receiverId, @RequestParam("message") String message, Model model) {
        Message msg = new Message();
        msg.setSenderId(senderId);
        msg.setReceiverId(receiverId);
        
        if (message.matches("[a-zA-Z\\s]+")) { // Allow spaces in the message
            msg.setMessage(message);
            userDao.insertMessage(msg);            
        } else {
            model.addAttribute("msg", "Only alphabets and spaces are allowed.");
        }
        List<Message> messages = userDao.getMessage(msg);
        User user1 = userDao.getUserById(receiverId);
        model.addAttribute("messages", messages);
        model.addAttribute("user1", user1);
        String base64Image1 = "";
        if (user1 != null && user1.getProfile() != null) {
            base64Image1 = user1.getProfile();
        }
        model.addAttribute("senderId", senderId);
        model.addAttribute("base64Image1", base64Image1);
        model.addAttribute("receiverId", receiverId);
        return "message"; 
    }

    
    @GetMapping("/deleteChat")
    public String deleteChat(@RequestParam("delete") int messageId, @RequestParam("id") int receiverId, Model model) {
        boolean success = userDao.deleteMessage(messageId);
        if (success) {
            model.addAttribute("status", "success");
        } else {
            model.addAttribute("status", "error");
        }
        return "redirect:/viewmessage?receiverId=" + receiverId;
    }

//    @GetMapping("/tomessage")
//    public String getViewMessage(@RequestParam("receiverId") int receiverId, Model model) {
//        model.addAttribute("receiverId", receiverId);  
//        return "message"; // Return Thymeleaf template name without .jsp
//    }

    
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
            String image = post.getImage();
           if(userId != 0 ) {
        	   userDao.reportPost(postId, userId, image,post.getContentType(),reason);
               model.addAttribute("reportStatus","Reported successfully");
   				return "post.jsp";
           }else {
        	   model.addAttribute("reportStatus","Report failure");
        	   return "post.jsp";
           }
            
    }
    
    @GetMapping("/admin1")
    public String toAdminAction() {
		return "adminaction";   	
    }
    
    @GetMapping("admin")
    public String toAdminReport() {
		return null;
    	
    }
    
    @PostMapping("/search")
    public String search(@RequestParam("name") String name, Model model, HttpSession session) {
        List<User> users = userDao.toSearch(name);
        Integer userId = (Integer) session.getAttribute("userid");
        model.addAttribute("users", users);
        model.addAttribute("currentUserId", userId);
        return "chat";
    }
    
    @PostMapping("/deletepost")
    public String deletePost(@RequestParam("postid") int postId) {
    	userDao.deletePost(postId);
		return "admin.jsp";    	
    }
}
