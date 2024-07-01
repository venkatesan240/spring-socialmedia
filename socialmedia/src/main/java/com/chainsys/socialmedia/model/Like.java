package com.chainsys.socialmedia.model;

public class Like {
	
	private int likeId;
	private int postId;
	private int userId;
	private String createdAt;
	private int likeCount;
	
	public Like() {
		super();
	}

	public Like(int likeId, int postId, int userId, String createdAt) {
		super();
		this.likeId = likeId;
		this.postId = postId;
		this.userId = userId;
		this.createdAt = createdAt;
	}

	public int getLikecount() {
		return likeCount;
	}

	public void setLikecount(int likecount) {
		this.likeCount = likecount;
	}

	public int getLikeid() {
		return likeId;
	}

	public void setLikeid(int likeid) {
		this.likeId = likeid;
	}

	public int getPostid() {
		return postId;
	}

	public void setPostid(int postid) {
		this.postId = postid;
	}

	public int getUserid() {
		return userId;
	}

	public void setUserid(int userid) {
		this.userId = userid;
	}

	public String getCreatedat() {
		return createdAt;
	}

	public void setCreatedat(String createdat) {
		this.createdAt = createdat;
	}

	public boolean isLiked() {
		return false;
	}
	
}
