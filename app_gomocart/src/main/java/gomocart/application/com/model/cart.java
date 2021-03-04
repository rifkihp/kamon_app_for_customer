package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("kode_trx")
    String kode_trx;

    @SerializedName("id_produk")
    int id_produk;

    @SerializedName("ukuran")
    String ukuran;

    @SerializedName("warna")
    String warna;

    @SerializedName("qty")
    int qty;

    @SerializedName("hari")
    int hari;

    @SerializedName("jam")
    int jam;

    @SerializedName("menit")
    int menit;

    @SerializedName("detik")
    int detik;

    @SerializedName("timeover")
    String timeover;

    public int getId() {
        return this.id;
    }

    public String getKode_trx() {
        return this.kode_trx;
    }

    public int getId_produk() {
        return this.id_produk;
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

    public int getHari() {
        return this.hari;
    }

    public int getJam() {
        return this.jam;
    }

    public int getMenit() {
        return this.menit;
    }

    public int getDetik() {
        return this.detik;
    }

    public String getTimeover() {
        return this.timeover;
    }

}
