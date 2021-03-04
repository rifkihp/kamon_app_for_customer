package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBeliBarang {

	@SerializedName("result")
	private String result;

	@SerializedName("msg")
	private String msg;

	@SerializedName("seq_penjualan")
	private int seq_penjualan;

	@SerializedName("sisa")
	private int sisa_stok;
	
	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setSeq_penjualan(int seq_penjualan) {
		this.seq_penjualan = seq_penjualan;
	}

	public int getSeq_penjualan() {
		return seq_penjualan;
	}

	public void setSisa(int sisa) {
		this.sisa_stok = sisa;
	}

	public int getSisa() {
		return sisa_stok;
	}
}