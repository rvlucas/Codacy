package model;

public class GitCommit {

	private String hash;
	private String author;
	private String message;
	private String time;
	
	public GitCommit(String hash, String author, String message, String time) {
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
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString(){
		return "Commit: " + this.hash + "\n" + "Author: " + this.author + "\n" + "Date: " + this.time + "\n\t" + this.message + "\n" ;
		
	}
	
	

}
