package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

public class Body {

	@SerializedName("acquirementId")
	private String acquirementId;

	@SerializedName("merchantTransId")
	private String merchantTransId;

	@SerializedName("resultInfo")
	private ResultInfo resultInfo;

	@SerializedName("checkoutUrl")
	private String checkoutUrl;

	public void setAcquirementId(String acquirementId){
		this.acquirementId = acquirementId;
	}

	public String getAcquirementId(){
		return acquirementId;
	}

	public void setMerchantTransId(String merchantTransId){
		this.merchantTransId = merchantTransId;
	}

	public String getMerchantTransId(){
		return merchantTransId;
	}

	public void setResultInfo(ResultInfo resultInfo){
		this.resultInfo = resultInfo;
	}

	public ResultInfo getResultInfo(){
		return resultInfo;
	}

	public void setCheckoutUrl(String checkoutUrl){
		this.checkoutUrl = checkoutUrl;
	}

	public String getCheckoutUrl(){
		return checkoutUrl;
	}
}