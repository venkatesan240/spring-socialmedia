package com.chainsys.socialmedia.model;

public class Message {

	private int id;
    private int senderId;
    private int receiverId;
    private String message;
    private String timeStamp;
    
	public Message() {
		super();
	}
	public Message(int id, int senderId, int receiverId, String message, String timeStamp) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.message = message;
		this.timeStamp = timeStamp;
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
	public int getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(int receiverId) {
		this.receiverId = receiverId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimestamp() {
		return timeStamp;
	}
	public void setTimestamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
    
    
}
