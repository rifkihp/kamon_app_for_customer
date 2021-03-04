package kamoncust.application.com.model;

import java.io.Serializable;

public class tagihan implements Serializable {

    private static final long serialVersionUID = 1L;
    String id_pelanggan, customer, lembar, pemakaian, periode;
    double tagihan, denda, admin, total;

    public tagihan(String id_pelanggan, String customer, String lembar, String pemakaian, String periode, double tagihan, double denda, double admin, double total) {
        
        this.id_pelanggan = id_pelanggan;
        this.customer = customer;
        this.lembar = lembar;
        this.pemakaian = pemakaian;
        this.periode = periode;
        this.tagihan = tagihan;
        this.denda = denda;
        this.admin = admin;
        this.total = total;
    }

    public String getId_pelanggan() {
        return this.id_pelanggan;
    }

    public String getCustomer() {
        return this.customer;
    }

    public String getLembar() {
        return this.lembar;
    }

    public String getPemakaian() {
        return this.pemakaian;
    }

    public String getPeriode() {
        return this.periode;
    }

    public double getTagihan() {
        return this.tagihan;
    }

    public double getDenda() {
        return this.denda;
    }

    public double getAdmin() {
        return this.admin;
    }

    public double getTotal() {
        return this.total;
    }

}
