package com.chainsys.socialmedia.model;

public class UserReport {
	
	private int id;
    private int senderId;
    private int reportedId;
    private String reportDate;
    private String reason;
    private String content;
    
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
	public int getSenderId() {
		return senderId;
	}
	public void setSenderId(int senderId) {
		this.senderId = senderId;
	}
	public int getReportedId() {
		return reportedId;
	}
	public void setReportedId(int reportedId) {
		this.reportedId = reportedId;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public UserReport(int id, int senderId, int reportedId, String reportDate, String reason) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.reportedId = reportedId;
		this.reportDate = reportDate;
		this.reason = reason;
	}
	public UserReport() {
		super();
	}
	@Override
	public String toString() {
		return "UserReport [id=" + id + ", senderId=" + senderId + ", reportedId=" + reportedId + ", reportDate="
				+ reportDate + ", reason=" + reason + "]";
	}  
    	

}
