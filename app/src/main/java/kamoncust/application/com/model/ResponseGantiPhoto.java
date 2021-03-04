package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseGantiPhoto {

	@SerializedName("success")
	boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("photo")
	private String photo;

	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public String getPhoto() {
		return photo;
	}

}
