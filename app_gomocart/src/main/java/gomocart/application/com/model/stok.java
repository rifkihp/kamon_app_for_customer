package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class stok implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("ukuran")
    String ukuran;

    @SerializedName("warna")
    String warna;

    @SerializedName("harga")
    String harga;

    @SerializedName("jumlah")
    int qty;

    public stok(int id, String ukuran, String warna, String harga, int qty) {
        this.id = id;
        this.ukuran = ukuran;
        this.warna = warna;
        this.harga = harga;
        this.qty = qty;
    }

    public int getId() {
        return this.id;
    }

    public String getUkuran() {
        return this.ukuran;
    }

    public String getWarna() {
        return this.warna;
    }

    public String getHarga() {
        return this.harga;
    }

    public int getQty() {
        return this.qty;
    }

}
