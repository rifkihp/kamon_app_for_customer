package kamoncust.application.com.model;

import java.io.Serializable;

public class voucher_game implements Serializable {

    private static final long serialVersionUID = 1L;
    String code, nama;
    double harga;

    public voucher_game(String code, String nama, double harga) {
        
        this.code = code;
        this.nama = nama;
        this.harga = harga;
    }

    public String getCode() {
        return this.code;
    }

    public String getNama() {
        return this.nama;
    }

    public double getHarga() {
        return this.harga;
    }

}
