package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseUpdateTransaksi {

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

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