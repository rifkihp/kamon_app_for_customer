package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class market implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("kode_trx")
    String kode_trx;

    @SerializedName("nama_market")
    String nama_market;

    @SerializedName("mitra")
    mitra simitra;

    @SerializedName("total_berat")
    double total_berat;

    @SerializedName("total_qty")
    double total_qty;

    @SerializedName("total_jumlah")
    double total_jumlah;

    public market(int id, String kode_trx, String nama_market, mitra simitra, double total_berat, double total_qty, double total_jumlah) {
        this.id           = id;
        this.kode_trx     = kode_trx;
        this.nama_market  = nama_market;
        this.simitra      = simitra;
        this.total_berat  = total_berat;
        this.total_qty    = total_qty;
        this.total_jumlah = total_jumlah;
    }

    public int getId() {
        return this.id;
    }

    public String getKode_trx() {
        return this.kode_trx;
    }

    public String getNama_market() {
        return this.nama_market;
    }

    public mitra getSimitra() {
        return this.simitra;
    }

    public double getTotal_berat() {
        return this.total_berat;
    }

    public double getTotal_qty() {
        return this.total_qty;
    }

    public double getTotal_jumlah() {
        return this.total_jumlah;
    }

}


