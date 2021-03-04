package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseSignIn {

	@SerializedName("success")
	boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("user")
	private user datauser;

	@SerializedName("order")
	private ArrayList<order> data_order;
	
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

	public void setUser(user datauser) {
		this.datauser = datauser;
	}

	public user getUser() {
		return datauser;
	}

	public void setData_order(ArrayList<order> data_order) {
		this.data_order = data_order;
	}

	public ArrayList<order> getData_order() {
		return data_order;
	}

}
