package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseBarangList {
	
	@SerializedName("DataBarang")
	private List<barang> databarang;

	public void setDataBarang(List<barang> databarang) {
		this.databarang = databarang;
	}

	public List<barang> getDataBarang() {
		return databarang;
	}
}