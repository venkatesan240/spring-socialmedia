package com.chainsys.socialmedia.model;

import java.util.Arrays;

public class Post {
	
	private int id;
	private int userId;
    private String userName;
    private String description;
    private String contentType;
    private byte[] image;
    private String timeStamp;
    private int likeCount; 
    
	public Post() {
		super();
	}
	public Post(int id, String username, String description, byte[] image, String timestamp) {
		super();
		this.id = id;
		this.userName = username;
		this.description = description;
		this.image = image;
		this.timeStamp = timestamp;
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
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
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
	@Override
	public String toString() {
		return "Post [id=" + id + ", username=" + userName + ", description=" + description + ", image="
				+ Arrays.toString(image) + ", timestamp=" + timeStamp + "]";
	}
    

}
