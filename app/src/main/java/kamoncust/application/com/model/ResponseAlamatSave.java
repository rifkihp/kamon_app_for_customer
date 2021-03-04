package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseAlamatSave {

	@SerializedName("id")
	private int id;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean getSuccess() {
		return success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}