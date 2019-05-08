package sk.tomsik68.mclauncher.impl.login.yggdrasil;

public class YDServiceAuthenticationException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7648783527451750219L;
	
	private Exception thrown;
	private String reason;
	
	public YDServiceAuthenticationException(String msg) {
		super(msg);
	}
	
	public YDServiceAuthenticationException(String msg, Exception e) {
		super(msg);
		this.thrown = e;
	}

	public YDServiceAuthenticationException(String msg, String reason, Exception e) {
		super(msg);
		this.thrown = e;
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public Exception getThrown() {
		return thrown;
	}

}
