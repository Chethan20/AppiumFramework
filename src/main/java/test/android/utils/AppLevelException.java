package test.android.utils;

@SuppressWarnings("serial")
public class AppLevelException extends Exception {

	public AppLevelException(String message) {
		super(message);
	}

	public String toString() {
		return ("User Defined Exception");
	}
}
