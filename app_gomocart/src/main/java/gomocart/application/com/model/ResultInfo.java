package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

public class ResultInfo {

	@SerializedName("resultStatus")
	private String resultStatus;

	@SerializedName("resultCode")
	private String resultCode;

	@SerializedName("resultCodeId")
	private String resultCodeId;

	@SerializedName("resultMsg")
	private String resultMsg;

	public void setResultStatus(String resultStatus){
		this.resultStatus = resultStatus;
	}

	public String getResultStatus(){
		return resultStatus;
	}

	public void setResultCode(String resultCode){
		this.resultCode = resultCode;
	}

	public String getResultCode(){
		return resultCode;
	}

	public void setResultCodeId(String resultCodeId){
		this.resultCodeId = resultCodeId;
	}

	public String getResultCodeId(){
		return resultCodeId;
	}

	public void setResultMsg(String resultMsg){
		this.resultMsg = resultMsg;
	}

	public String getResultMsg(){
		return resultMsg;
	}
}