package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class cartstok implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("ukuran")
    String ukuran;

    @SerializedName("warna")
    String warna;

    @SerializedName("qty")
    int qty;

    @SerializedName("harga_grosir")
    double harga_grosir;

    public int getId() {
        return this.id;
    }

    public String getUkuran() {
        return this.ukuran;
    }

    public String getWarna() {
        return this.warna;
    }

    public int getQty() {
        return this.qty;
    }

    public double getHarga_grosir() {
        return this.harga_grosir;
    }

}
