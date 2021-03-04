package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResponseDana {

	@SerializedName("signature")
	private String signature;

	@SerializedName("response")
	private Response response;

	public void setSignature(String signature){
		this.signature = signature;
	}

	public String getSignature(){
		return signature;
	}

	public void setResponse(Response response){
		this.response = response;
	}

	public Response getResponse(){
		return response;
	}
}