package com.chainsys.socialmedia.model;

public class ReportPost {
	
	private int id;
	private int postId;
	private int userId;
	private String reportDate;
	private byte[] image;
	private String content;
	private String reason;
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public ReportPost(int id, int postId, int userId, String reportDate, byte[] image) {
		super();
		this.id = id;
		this.postId = postId;
		this.userId = userId;
		this.reportDate = reportDate;
		this.image = image;
	}
	public ReportPost() {
		super();
	}

}
