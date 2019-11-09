package exceptions;

public class GitCliException extends Exception {

	private static final long serialVersionUID = 1L;
	
	String message;
	
	public GitCliException(String message) {
		super(message);
	}
}
