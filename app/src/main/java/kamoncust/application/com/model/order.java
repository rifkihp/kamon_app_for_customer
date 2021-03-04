package kamoncust.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by apple on 21/05/16.
 */
public class order implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("no_transaksi")
    private String no_transaksi;

    @SerializedName("tgl_transaksi")
    private String tgl_transaksi;

    @SerializedName("nama")
    private String nama;

    @SerializedName("qty")
    private int qty;

    @SerializedName("jumlah")
    private double jumlah;

    @SerializedName("estimasi")
    private String estimasi;

    @SerializedName("kurir")
    private String kurir;

    @SerializedName("noresi")
    private String noresi;

    @SerializedName("status")
    private int status;

    @SerializedName("gambar")
    private String gambar;

    @SerializedName("user_id")
    private int user_id;

    @SerializedName("pembayaran")
    private int pembayaran;

    @SerializedName("mitra_id")
    private int mitra_id;

    @SerializedName("mitra_nama")
    private String mitra_nama;

    @SerializedName("riwayat_kesehatan")
    private String riwayat_kesehatan;

    @SerializedName("tanggal_terapi")
    private String tanggal_terapi;

    @SerializedName("jam_terapi")
    private String jam_terapi;

    public String getNo_transaksi() {
        return no_transaksi;
    }

    public String getTgl_transaksi() {
        return this.tgl_transaksi;
    }

    public int getPembayaran() {
        return this.pembayaran;
    }

    public String getNama() {
        return this.nama;
    }

    public int getQty() {
        return this.qty;
    }

    public double getJumlah() {
        return this.jumlah;
    }

    public String getEstimasi() {
        return this.estimasi;
    }

    public String getKurir() {
        return this.kurir;
    }

    public String getNoresi() {
        return this.noresi;
    }

    public int getStatus() {
        return this.status;
    }

    public String getGambar() {
        return this.gambar;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public int getMitra_id() {
        return this.mitra_id;
    }

    public String getMitra_nama() {
        return this.mitra_nama;
    }

    public String getRiwayat_kesehatan() {
        return this.riwayat_kesehatan;
    }

    public String getTanggal_terapi() {
        return this.tanggal_terapi;
    }

    public String getJam_terapi() {
        return this.jam_terapi;
    }

}