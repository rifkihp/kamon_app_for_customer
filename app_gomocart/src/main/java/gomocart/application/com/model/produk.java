package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class produk implements Serializable {

    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    int id;

    @SerializedName("kode")
    String kode;

    @SerializedName("nama")
    String nama;

    @SerializedName("id_category")
    int id_category;

    @SerializedName("category_name")
    String category_name;

    @SerializedName("penjelasan")
    String penjelasan;

    @SerializedName("foto1_produk")
    String foto1_produk;

    @SerializedName("satuan")
    String satuan;

    @SerializedName("harga_beli")
    double harga_beli;

    @SerializedName("harga_jual")
    double  harga_jual;

    @SerializedName("harga_grosir")
    double  harga_grosir;

    @SerializedName("harga_diskon")
    double harga_diskon;

    @SerializedName("persen_diskon")
    int persen_diskon;

    @SerializedName("berat")
    int berat;

    @SerializedName("list_ukuran")
    String list_ukuran;

    @SerializedName("ukuran")
    String ukuran;

    @SerializedName("list_warna")
    String list_warna;

    @SerializedName("warna")
    String warna;

    @SerializedName("qty")
    int qty;

    @SerializedName("max_qty")
    int max_qty;

    @SerializedName("minimum_pesan")
    int minimum_pesan;

    @SerializedName("produk_promo")
    int produk_promo;

    @SerializedName("produk_featured")
    int produk_featured;

    @SerializedName("produk_terbaru")
    int produk_terbaru;

    @SerializedName("produk_preorder")
    int produk_preorder;

    @SerializedName("produk_soldout")
    int produk_soldout;

    @SerializedName("produk_grosir")
    int produk_grosir;

    @SerializedName("produk_freeongkir")
    int produk_freeongkir;

    @SerializedName("produk_cod")
    int produk_cod;

    @SerializedName("rating")
    int rating;

    @SerializedName("responden")
    int responden;

    @SerializedName("review")
    int review;

    @SerializedName("subtotal")
    double subtotal;

    @SerializedName("grandtotal")
    double grandtotal;

    @SerializedName("wishlist")
    boolean wishlist;

    @SerializedName("checked")
    boolean checked;

    @SerializedName("publish")
    boolean publish;

    @SerializedName("mitra")
    mitra mitra1;

    @SerializedName("periode_promo")
    String periode_promo;

    ArrayList<stok> list_stok;
    int jam;
    int menit;
    int detik;
    long expirationTime;
    int seqId = 0;

    public produk(int id, String kode, String nama, int id_category, String category_name, String penjelasan, String foto1_produk, String satuan, double harga_beli, double harga_jual, double harga_grosir, double harga_diskon, int persen_diskon, int berat, String list_ukuran, String ukuran, String list_warna, String warna, int qty, int max_qty, int minimum_pesan, int produk_promo, int produk_featured, int produk_terbaru, int produk_preorder, int produk_soldout, int produk_grosir, int produk_freeongkir, int produk_cod, int rating, int responden, int review, double subtotal, double grandtotal, mitra mitra1, boolean wishlist, boolean checked, boolean publish) {

        this.id = id;
        this.kode = kode;
        this.nama = nama;

        this.id_category = id_category;
        this.category_name = category_name;

        this.penjelasan = penjelasan;
        this.foto1_produk = foto1_produk;
        this.satuan = satuan;

        this.harga_beli = harga_beli;
        this.harga_jual = harga_jual;
        this.harga_grosir = harga_grosir;
        this.harga_diskon = harga_diskon;
        this.persen_diskon = persen_diskon;
        this.berat = berat;

        this.qty = qty;
        this.max_qty = max_qty;
        this.minimum_pesan = minimum_pesan;

        this.list_ukuran = list_ukuran;
        this.ukuran = ukuran;
        this.list_warna = list_warna;
        this.warna = warna;

        this.produk_promo = produk_promo;
        this.produk_featured = produk_featured;
        this.produk_terbaru = produk_terbaru;
        this.produk_preorder = produk_preorder;
        this.produk_soldout = produk_soldout;
        this.produk_grosir = produk_grosir;
        this.produk_freeongkir = produk_freeongkir;
        this.produk_cod = produk_cod;

        this.rating = rating;
        this.responden = responden;
        this.review = review;

        this.subtotal = subtotal;
        this.grandtotal = grandtotal;

        this.mitra1 = mitra1;

        this.wishlist = wishlist;
        this.checked = checked;
        this.publish = publish;
    }

    public int getId() {
        return this.id;
    }

    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public int getId_category() {
        return this.id_category;
    }

    public String getCategory_name() {
        return this.category_name;
    }

    public String getPenjelasan() {
        return this.penjelasan;
    }

    public String getFoto1_produk() {
        return this.foto1_produk;
    }

    public String getSatuan() {
        return this.satuan;
    }

    public void setHarga_beli(double harga_beli) {
        this.harga_beli = harga_beli;
    }

    public double getHarga_beli() {
        return this.harga_beli;
    }

    public void setHarga_jual(double harga_jual) {
        this.harga_jual = harga_jual;
    }

    public double getHarga_jual() {
        return this.harga_jual;
    }

    public void setHarga_grosir(double harga_grosir) {
        this.harga_grosir = harga_grosir;
    }

    public double getHarga_grosir() {
        return this.harga_grosir;
    }

    public void setHarga_diskon(double harga_diskon) {
        this.harga_diskon = harga_diskon;
    }

    public double getHarga_diskon() {
        return this.harga_diskon;
    }

    public void setPersen_diskon(int persen_diskon) {
        this.persen_diskon = persen_diskon;
    }

    public int getPersen_diskon() {
        return this.persen_diskon;
    }

    public String getList_ukuran() {
        return this.list_ukuran;
    }

    public void setList_ukuran(String list_ukuran) {
        this.list_ukuran = list_ukuran;
    }

    public String getUkuran() {
        return this.ukuran;
    }

    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }

    public String getList_warna() {
        return this.list_warna;
    }

    public void setList_warna(String list_warna) {
        this.list_warna = list_warna;
    }

    public String getWarna() {
        return this.warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public int getQty() {
        return this.qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getMax_qty() {
        return this.max_qty;
    }

    public int getMinimum_pesan() {
        return this.minimum_pesan;
    }

    public void setMax_qty(int max_qty) {
        this.max_qty = max_qty;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public int getBerat() {
        return this.berat;
    }

    public int getProduk_promo() {
        return this.produk_promo;
    }

    public int getProduk_featured() {
        return this.produk_featured;
    }

    public int getProduk_terbaru() {
        return this.produk_terbaru;
    }

    public int getProduk_preorder() {
        return this.produk_preorder;
    }

    public int getProduk_soldout() {
        return this.produk_soldout;
    }

    public int getProduk_grosir() {
        return this.produk_grosir;
    }

    public int getProduk_freeongkir() {
        return this.produk_freeongkir;
    }

    public int getProduk_cod() {
        return this.produk_cod;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getResponden() {
        return this.responden;
    }

    public void setResponden(int responden) {
        this.responden = responden;
    }

    public int getReview() {
        return this.review;
    }

    public void setReview(int review) {
        this.review = review;
    }

    public double getSubtotal() {
        return this.subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getGrandtotal() {
        return this.grandtotal;
    }

    public void setGrandtotal(double grandtotal) {
        this.grandtotal = grandtotal;
    }

    public boolean getWishlist() {
        return this.wishlist;
    }

    public void setWishlist(boolean wishlist) {
        this.wishlist = wishlist;
    }

    public boolean getChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getPublish() {
        return this.publish;
    }

    public String getPeriode_promo() {
        return this.periode_promo;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public mitra getMitra() {
        return this.mitra1;
    }

    public void setList_stok(ArrayList<stok> list_stok) {
        this.list_stok = list_stok;
    }

    public ArrayList<stok> getList_stok() {
        return this.list_stok;
    }

    public int getDetik() {
        return this.detik;
    }

    public void setDetik(int detik) {
        this.detik = detik;
    }

    public int getJam() {
        return this.jam;
    }

    public void setJam(int jam) {
        this.jam = jam;
    }

    public int getMenit() {
        return this.menit;
    }

    public void setMenit(int menit) {
        this.menit = menit;
    }

    public long getExpirationTime() {
        return this.expirationTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public int getSeqId() {
        return this.seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }
}
