package business;

import java.io.Serializable;

public class CustomException extends Exception implements Serializable {

	public CustomException() {
		super();
	}
	public CustomException(String msg) {
		super(msg);
	}
	public CustomException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 8978723266036027364L;
	
}
