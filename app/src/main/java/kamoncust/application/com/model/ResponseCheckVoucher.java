package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseCheckVoucher {

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("voucher")
	private voucher detail_voucher;

	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public voucher getDetail_voucher() {
		return detail_voucher;
	}
}