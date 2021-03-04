package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class grandtotal implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("total")
    double total;

    @SerializedName("diskon")
    double diskon;

    @SerializedName("subtotal")
    double subtotal;

    @SerializedName("voucher")
    double voucher;

    @SerializedName("ongkir")
    double ongkir;

    @SerializedName("grandtotal")
    double grandtotal;

    public grandtotal(double total, double diskon, double subtotal, double voucher, double ongkir, double grandtotal) {
        this.total = total;
        this.diskon = diskon;
        this.subtotal = subtotal;
        this.voucher = voucher;
        this.ongkir = ongkir;
        this.grandtotal = grandtotal;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return this.total;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public double getDiskon() {
        return this.diskon;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getSubtotal() {
        return this.subtotal;
    }

    public void setVoucher(double voucher) {
        this.voucher = voucher;
    }

    public double getVoucher() {
        return this.voucher;
    }

    public void setOngkir(double ongkir) {
        this.ongkir = ongkir;
    }

    public double getOngkir() {
        return this.ongkir;
    }

    public void setGrandtotal(double grandtotal) {
        this.grandtotal = grandtotal;
    }

    public double getGrandtotal() {
        return this.grandtotal;
    }

}
