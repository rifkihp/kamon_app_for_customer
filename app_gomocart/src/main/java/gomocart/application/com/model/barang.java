package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class barang implements Serializable {

    private static final long serialVersionUID = 1L;

    /*{
        "id_barang":"B000002",
        "barcode":"8997035111110",
        "nm_barang":"POCARY",
        "id_kategori":"K002",
        "nm_kategori":"MINUMAN",
        "id_satuan":"0005",
        "nm_satuan":"Kaleng",
        "harga_beli":"4851",
        "harga_jual":"6000",
        "stock":"0",
        "stock_palsu":"0",
        "keterangan":"ADA",
        "type":"ALL",
        "photo":"B000002.jpg",
        "entity_cd":"001"
    }*/

    @SerializedName("id_barang")
    String id_barang;

    @SerializedName("barcode")
    String barcode;

    @SerializedName("nm_barang")
    String nm_barang;

    @SerializedName("id_kategori")
    String id_kategori;

    @SerializedName("nm_kategori")
    String nm_kategori;

    @SerializedName("id_satuan")
    String id_satuan;

    @SerializedName("nm_satuan")
    String nm_satuan;

    @SerializedName("harga_beli")
    String harga_beli;

    @SerializedName("harga_jual")
    String harga_jual;

    @SerializedName("stock")
    String stock;

    @SerializedName("stock_palsu")
    String stock_palsu;

    @SerializedName("keterangan")
    String keterangan;
    
    @SerializedName("type")
    String type;
    
    @SerializedName("photo")
    String photo;
    
    @SerializedName("entity_cd")
    String entity_cd;
    
    public barang(String id_barang, String barcode, String nm_barang, String id_kategori, String nm_kategori, String id_satuan, String nm_satuan, String harga_beli, String harga_jual, String stock, String stock_palsu, String keterangan, String type, String photo, String entity_cd) {
        this.id_barang = id_barang;
        this.barcode = barcode;
        this.nm_barang = nm_barang;
        this.id_kategori = id_kategori;
        this.nm_kategori = nm_kategori;
        this.id_satuan = id_satuan;
        this.nm_satuan = nm_satuan;
        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.stock = stock;
        this.stock_palsu = stock_palsu;
        this.keterangan = keterangan;
        this.type = type;
        this.photo = photo;
        this.entity_cd = entity_cd;
    }

    public String getId_barang() {
        return this.id_barang;
    }

    public String getBarcode() {
        return this.barcode;
    }

    public String getNm_barang() {
        return this.nm_barang;
    }

    public String getId_kategori() {
        return this.id_kategori;
    }

    public String getNm_kategori() {
        return this.nm_kategori;
    }

    public String getId_satuan() {
        return this.id_satuan;
    }

    public String getNm_satuan() {
        return this.nm_satuan;
    }

    public String getHarga_jual() {
        return this.harga_jual;
    }

    public String getHarga_beli() {
        return this.harga_beli;
    }

    public String getStock() {
        return this.stock;
    }

    public String getStock_palsu() {
        return this.stock_palsu;
    }

    public String getKeterangan() {
        return this.keterangan;
    }

    public String getType() {
        return this.type;
    }

    public String getPhoto() {
        return this.photo;
    }

    public String getEntity_cd() {
        return this.entity_cd;
    }

}
