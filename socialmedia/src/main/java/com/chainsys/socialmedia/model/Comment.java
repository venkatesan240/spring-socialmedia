package com.chainsys.socialmedia.model;

public class Comment {

	private int commentId;
	private int postId;
	private int userId;
	private String commentContent;
	private String createdAt;
	
	
	public Comment() {
		super();
	}


	public Comment(int commentid, int postid, int userid, String comment, String createdat) {
		super();
		this.commentId = commentid;
		this.postId = postid;
		this.userId = userid;
		this.commentContent = comment;
		this.createdAt = createdat;
	}


	public int getCommentId() {
		return commentId;
	}


	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}


	public int getPostId() {
		return postId;
	}


	public void setPostId(int postId) {
		this.postId = postId;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserid(int userId) {
		this.userId = userId;
	}


	public String getComment() {
		return commentContent;
	}


	public void setComment(String comment) {
		this.commentContent = comment;
	}


	public String getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
