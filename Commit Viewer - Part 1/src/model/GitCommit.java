package model;

import java.util.Date;

public class GitCommit {

	private String hash;
	private String author;
	private String message;
	private Date time;
	
	public GitCommit(String hash, String author, String message, Date time) {
		super();
		this.hash = hash;
		this.author = author;
		this.message = message;
		this.time = time;
	}
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

}
