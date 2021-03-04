package kamoncust.application.com.data.toko;

import kamoncust.application.com.model.ResponseProvince;
import kamoncust.application.com.model.ResponseCity;
import kamoncust.application.com.model.ResponseSubdistrict;

import kamoncust.application.com.model.ResponseAlamatDelete;
import kamoncust.application.com.model.ResponseAlamatSave;
import kamoncust.application.com.model.ResponseAlamatStore;
import kamoncust.application.com.model.ResponseAlamatUtama;

import kamoncust.application.com.model.ResponseAddToCart;
import kamoncust.application.com.model.ResponseBankStore;
import kamoncust.application.com.model.ResponseCheckVoucher;

import kamoncust.application.com.model.ResponseProdukStore;
import kamoncust.application.com.model.ResponseProdukTerkait;
import kamoncust.application.com.model.ResponseHapusProduk;

import kamoncust.application.com.model.ResponseOrderToko;
import kamoncust.application.com.model.ResponseSubmitTransaksi;
import kamoncust.application.com.model.ResponseUpdateTransaksi;

import kamoncust.application.com.model.ResponseDetailPesananToko;
import kamoncust.application.com.model.ResponseKeranjangStore;
import kamoncust.application.com.model.ResponseOngkirStore;



import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RestApi {
    
    @FormUrlEncoded
    @POST("androidCartStore.php")
    Call<ResponseKeranjangStore> postCartList(
            @Field("id_user") String userId,
            @Field("kode_trx") String kodeTrx
    );

    @FormUrlEncoded
    @POST("androidCartUpdate.php")
    Call<ResponseKeranjangStore> postCartUpdate(
            @Field("id_user") String userId,
            @Field("kode_trx") String kodeTrx,
            //DETAIL ORDERS
            @Field("orders") String orders
    );
    
    @FormUrlEncoded
    @POST("androidDeleteProductFromCart.php")
    Call<ResponseHapusProduk> postDeleteCart(
            @Field("kode_trx") String kodeTrx,
            @Field("items") String items
    );

    @FormUrlEncoded
    @POST("androidProdukStore.php")
    Call<ResponseProdukStore> postProdukList(
            @Field("id_user") String userId,
            @Field("page") String page,
            @Field("toko") String asToko,
            @Field("kode_grup") String kodeGrup,

            @Field("query") String query,
            @Field("filter_kategori") String filterKategori,
            @Field("filter_brand") String filterBrand,
            @Field("filter_ukuran") String filterUkuran,
            @Field("filter_harga_min") String filterHargaMin,
            @Field("filter_harga_max") String filterHargaMax,
            @Field("filter_diskon_min") String filterDiskonMin,
            @Field("filter_diskon_max") String filterDiskonMax,
            @Field("sort_by") String sortBy
            );

    @FormUrlEncoded
    @POST("androidBankStore.php")
    Call<ResponseBankStore> postBankList(
            @Field("id_user") String userId
    );

    @FormUrlEncoded
    @POST("androidOngkirStore.php")
    Call<ResponseOngkirStore> postOngkirList(
            @Field("id_user") String userId,
            @Field("orgn_city_id") String originCityId,
            @Field("dest_city_id") String destCityId,
            @Field("dest_subdistrict_id") String destSubdistrictId,
            @Field("berat") String berat,
            @Field("total") String total
    );

    @FormUrlEncoded
    @POST("androidProdukTerkait.php")
    Call<ResponseProdukTerkait> postProdukTerkait(
            @Field("id_user") String userId,
            @Field("id_produk") String produkId,
            @Field("kode_grup") String kodeGrup
    );

    @FormUrlEncoded
    @POST("androidAddToCart.php")
    Call<ResponseAddToCart> postAddToCart(
            @Field("id_user") String userId,
            @Field("id_produk") String produkId,
            @Field("kode_trx") String kodeTrx,
            @Field("items") String items
    );

    @FormUrlEncoded
    @POST("androidVoucherCheck.php")
    Call<ResponseCheckVoucher> postCheckVoucher(
            @Field("id_user") String userId,
            @Field("kode_voucher") String kode_voucher
    );

    @FormUrlEncoded
    @POST("androidOrderStore.php")
    Call<ResponseOrderToko> postOrderList(
            @Field("id_user") String userId,
            @Field("page") String page,
            @Field("status") String status,
            @Field("tipe") String tipe
    );

    @FormUrlEncoded
    @POST("androidOrderDetail.php")
    Call<ResponseDetailPesananToko> postDetailPesanan(
            @Field("id_user") String userId,
            @Field("id_order") String orderId
    );

    @FormUrlEncoded
    @POST("androidOrderSubmit.php")
    Call<ResponseSubmitTransaksi> postSubmitTransaksi(
            @Field("id_user") String userId,
            @Field("id_mitra") String mitraId,
            @Field("kode_trx") String kodeTrx,
            @Field("kode_grup") String kodeGrup,

            @Field("keyUserId") String keyUserId,
            @Field("keyEntityCd") String keyEntityCd,
            @Field("keyEntityCdMarket") String keyEntityCdMarket,
            @Field("keyTotalBelanja") String keyTotalBelanja,

            //ALAMAT PENGIRIMAN
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("propinsi") String propinsi,
            @Field("nama_propinsi") String nama_propinsi,
            @Field("kota") String kota,
            @Field("nama_kota") String nama_kota,
            @Field("kecamatan") String kecamatan,
            @Field("nama_kecamatan") String nama_kecamatan,
            @Field("kode_pos") String kode_pos,
            @Field("no_hp") String no_hp,

            //KURIR PENGIRIMAN
            @Field("kurir_id") String kurir_id,
            @Field("kode_ongkir") String kode_ongkir,
            @Field("nama_ongkir") String nama_ongkir,
            @Field("kode_layanan") String kode_layanan,
            @Field("layanan") String layanan,
            @Field("nominal") String nominal,
            @Field("etd") String etd,
            @Field("tarif") String tarif,

            //METODE PEMBAYARAN
            @Field("metode_pembayaran") String metode_pembayaran,
            @Field("bank_id") String bank_id,
            @Field("bank_no_rekening") String bank_no_rekening,
            @Field("bank_nama_pemilik_rekening") String bank_nama_pemilik_rekening,
            @Field("bank_nama") String bank_nama,
            @Field("bank_cabang") String bank_cabang,
            @Field("bank_logo") String bank_logo,

            //DATA VOUCHER
            @Field("voucher") String voucher,

            //DETAIL ORDERS
            @Field("orders") String orders
    );

    @FormUrlEncoded
    @POST("androidOrderUpdate.php")
    Call<ResponseUpdateTransaksi> postUpdateTransaksi(
            @Field("id_user") String userId,
            @Field("id_trx") String trxId,
            @Field("status") String statusTrx
    );

    @FormUrlEncoded
    @POST("androidAlamatStore.php")
    Call<ResponseAlamatStore> postAlamatList(
            @Field("user_id") String userId,
            @Field("sort_by") String sortBy
    );

    @FormUrlEncoded
    @POST("androidAlamatDelete.php")
    Call<ResponseAlamatDelete> postDeleteAlamat(
            @Field("user_id") String userId,
            @Field("id") String sortBy
    );

    @FormUrlEncoded
    @POST("androidAlamatSave.php")
    Call<ResponseAlamatSave> postSaveAlamat(
            @Field("id") String id,
            @Field("id_member") String idMember,
            @Field("nama") String nama,
            @Field("alamat") String alamat,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("id_propinsi") String idPropinsi,
            @Field("nama_propinsi") String namaPropinsi,
            @Field("id_kota") String idKota,
            @Field("nama_kota") String namaKota,
            @Field("id_kecamatan") String idKecamatan,
            @Field("nama_kecamatan") String namaKecamatan,
            @Field("kode_pos") String kodePos,
            @Field("no_hp") String noHp,
            @Field("as_default") String asDefault
    );

    @FormUrlEncoded
    @POST("androidAlamatUtama.php")
    Call<ResponseAlamatUtama> postUtamakanAlamat(
            @Field("user_id") String userId,
            @Field("alamat_id") String alamatId
    );

    @FormUrlEncoded
    @POST("androidPropinsiStore.php")
    Call<ResponseProvince> postProvinceList(
            @Field("keyUserId") String userId
    );

    @FormUrlEncoded
    @POST("androidCityStore.php")
    Call<ResponseCity> postCityList(
            @Field("province_id") String provinceId
    );

    @FormUrlEncoded
    @POST("androidSubdistrictStore.php")
    Call<ResponseSubdistrict> postSubdistrictList(
            @Field("city_id") String cityId
    );
}
