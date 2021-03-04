package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
/**
 * Created by apple on 21/05/16.
 */
public class order implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("no_transaksi")
    String no_transaksi;

    @SerializedName("tgl_transaksi")
    String tgl_transaksi;

    @SerializedName("nama")
    String nama;

    @SerializedName("qty")
    int qty;

    @SerializedName("jumlah")
    double jumlah;

    @SerializedName("estimasi")
    String estimasi;

    @SerializedName("kurir")
    String kurir;

    @SerializedName("noresi")
    String noresi;

    @SerializedName("status")
    int status;

    @SerializedName("gambar")
    String gambar;

    @SerializedName("checkout_url")
    String checkout_url;

    @SerializedName("pembayaran")
    int pembayaran;

    @SerializedName("market")
    market grup_market;

    public int getId() {
        return this.id;
    }

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

    public String getCheckout_url() {
        return this.checkout_url;
    }

    public market getMarket() {
        return this.grup_market;
    }
}


