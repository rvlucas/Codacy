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
	
	/**
	 * Gets the hash of the commit
	 * @return hash
	 */
	public String getHash() {
		return hash;
	}
	
	/**
	 * Sets the hash of the commit
	 * @param hash
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	/**
	 * Gets the author of the commit
	 * @return author
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Sets the author of the commit
	 * @param author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * Gets the message of the commit
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message of the commit
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Gets the time of the commit
	 * @return time
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * Sets the time of the commit
	 * @param time
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Return a formatted string with all fields of the commit
	 */
	@Override
	public String toString(){
		return "Commit: " + this.hash + "\n" + "Author: " + this.author + "\n" + "Date: " + this.time + "\n\t" + this.message + "\n" ;
		
	}
	
	

}
