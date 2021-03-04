package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseEditProfil {

	@SerializedName("success")
	boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("user")
	private user user_login;

	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public user getUser_login() {
		return user_login;
	}

}
