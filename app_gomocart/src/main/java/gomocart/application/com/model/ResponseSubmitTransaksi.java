package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseSubmitTransaksi {

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("checkoutUrl")
	private String checkoutUrl;

	@SerializedName("id_trx")
	private String id_trx;

	public boolean getSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public String getCheckoutUrl() {
		return checkoutUrl;
	}

	public String getId_trx() {
		return id_trx;
	}
}