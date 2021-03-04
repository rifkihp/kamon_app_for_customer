package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

public class Response {

	@SerializedName("head")
	private Head head;

	@SerializedName("body")
	private Body body;

	public void setHead(Head head){
		this.head = head;
	}

	public Head getHead(){
		return head;
	}

	public void setBody(Body body){
		this.body = body;
	}

	public Body getBody(){
		return body;
	}
}