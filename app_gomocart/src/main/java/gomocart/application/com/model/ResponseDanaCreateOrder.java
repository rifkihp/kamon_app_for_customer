package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseDanaCreateOrder {

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("acquirementId")
	private String acquirementId;

	@SerializedName("checkoutUrl")
	private String checkoutUrl;

	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public String getCheckoutUrl() {
		return checkoutUrl;
	}

	public String getAcquirementId() {
		return acquirementId;
	}

}