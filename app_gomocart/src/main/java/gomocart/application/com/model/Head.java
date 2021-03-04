package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

public class Head {

	@SerializedName("clientId")
	private String clientId;

	@SerializedName("function")
	private String function;

	@SerializedName("respTime")
	private String respTime;

	@SerializedName("version")
	private String version;

	@SerializedName("reqMsgId")
	private String reqMsgId;

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public String getClientId(){
		return clientId;
	}

	public void setFunction(String function){
		this.function = function;
	}

	public String getFunction(){
		return function;
	}

	public void setRespTime(String respTime){
		this.respTime = respTime;
	}

	public String getRespTime(){
		return respTime;
	}

	public void setVersion(String version){
		this.version = version;
	}

	public String getVersion(){
		return version;
	}

	public void setReqMsgId(String reqMsgId){
		this.reqMsgId = reqMsgId;
	}

	public String getReqMsgId(){
		return reqMsgId;
	}
}