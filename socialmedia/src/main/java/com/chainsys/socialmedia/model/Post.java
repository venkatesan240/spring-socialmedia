package com.chainsys.socialmedia.model;

import java.util.Arrays;
import java.util.List;

public class Post {
	
	private int id;
	private int userId;
    private String userName;
    private String description;
    private String contentType;
    private String image;
    private String timeStamp;
    private int likeCount; 
    private Boolean liked;
    private String userProfileImage;
    private List<User> likedUsers;
    private List<Comment> comments;
    
	public Post() {
		super();
	}
	public Post(int id, String username, String description, String image, String timestamp) {
		super();
		this.id = id;
		this.userName = username;
		this.description = description;
		this.image = image;
		this.timeStamp = timestamp;
	}
	
	
	public Boolean getLiked() {
		return liked;
	}
	public void setLiked(Boolean liked) {
		this.liked = liked;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public List<User> getLikedUsers() {
		return likedUsers;
	}
	public void setLikedUsers(List<User> likedUsers) {
		this.likedUsers = likedUsers;
	}
	public String getUserProfileImage() {
		return userProfileImage;
	}
	public void setUserProfileImage(String userProfileImage) {
		this.userProfileImage = userProfileImage;
	}
	public int getLikeCount() {
		return likeCount;
	}
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userid) {
		this.userId = userid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUsername(String userName) {
		this.userName = userName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTimestamp() {
		return timeStamp;
	}
	public void setTimestamp(String timestamp) {
		this.timeStamp = timestamp;
	}
	
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
    

}
