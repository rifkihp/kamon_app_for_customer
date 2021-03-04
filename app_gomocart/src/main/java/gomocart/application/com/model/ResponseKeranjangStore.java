package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseKeranjangStore {

	@SerializedName("success")
	boolean success;

	@SerializedName("alamat")
	alamat alamatSelected;

	@SerializedName("cart")
	ArrayList<cart> dataCart;

	@SerializedName("message")
	String message;

	public boolean getSuccess() {
		return success;
	}

	public alamat getAlamatSelected() {
		return alamatSelected;
	}

	public ArrayList<cart> getDataCart() {
		return dataCart;
	}

	public String getMessage() {
		return message;
	}
}