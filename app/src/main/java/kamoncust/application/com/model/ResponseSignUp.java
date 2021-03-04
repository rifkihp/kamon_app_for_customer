package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseSignUp {

	@SerializedName("success")
	boolean success;

	@SerializedName("message")
	private String message;

	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

}
