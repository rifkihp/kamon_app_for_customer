package kamoncust.application.com.data;

import kamoncust.application.com.model.ResponseAlamat;
import kamoncust.application.com.model.ResponseBatalkanPesanan;
import kamoncust.application.com.model.ResponseCity;
import kamoncust.application.com.model.ResponseDashboard;
import kamoncust.application.com.model.ResponseDefaultAlamat;
import kamoncust.application.com.model.ResponseDeleteAlamat;
import kamoncust.application.com.model.ResponseDeleteProduk;
import kamoncust.application.com.model.ResponseDetailPesanan;
import kamoncust.application.com.model.ResponseEditProfil;
import kamoncust.application.com.model.ResponseGantiPhoto;
import kamoncust.application.com.model.ResponseKategori;
import kamoncust.application.com.model.ResponseOrder;
import kamoncust.application.com.model.ResponseProduk;
import kamoncust.application.com.model.ResponseProvince;
import kamoncust.application.com.model.ResponseSaveAlamat;
import kamoncust.application.com.model.ResponseSignUp;
import kamoncust.application.com.model.ResponseSubdistrict;
import kamoncust.application.com.model.ResponseUtamakanAlamat;
import kamoncust.application.com.model.ResponseValidasiJadwal;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import kamoncust.application.com.model.ResponseSignIn;
import kamoncust.application.com.model.ResponseSplash;
import retrofit2.http.Part;

public interface RestApi {

    @FormUrlEncoded
    @POST("androidSplashDataStore.php")
    Call<ResponseSplash> postSplash(
            @Field("keyUserId") String userId
    );

    @FormUrlEncoded
    @POST("androidDashboardDataStore.php")
    Call<ResponseDashboard> postDashboardList(
            @Field("keyUserId") String userId
    );

    @FormUrlEncoded
    @POST("androidValidasiJadwal.php")
    Call<ResponseValidasiJadwal> postValidasiJadwal(
            @Field("id_mitra") String mitraId,
            @Field("tanggal") String tanggal,
            @Field("jam") String jam
    );

    @FormUrlEncoded
    @POST("androidKategoriDataStore.php")
    Call<ResponseKategori> postKategori(
            @Field("keyUserId") String userId
    );

    @FormUrlEncoded
    @POST("androidProdukDataStore.php")
    Call<ResponseProduk> postProdukList(
            @Field("id_user") String userId,
            @Field("id_mitra") String mitraId,
            @Field("page") String page,
            @Field("query") String query
    );

    @FormUrlEncoded
    @POST("androidDeleteProduk.php")
    Call<ResponseDeleteProduk> postDeleteProduk(
            @Field("id_user") String userId,
            @Field("id_produk") String produkId
    );

    @FormUrlEncoded
    @POST("androidBatalkanPesanan.php")
    Call<ResponseBatalkanPesanan> postBatalkanPesanan(
            @Field("no_trx") String noTrx
    );


    @FormUrlEncoded
    @POST("androidAlamatDataStore.php")
    Call<ResponseAlamat> postAlamatList(
            @Field("user_id") String userId,
            @Field("sort_by") String sortBy
    );

    @FormUrlEncoded
    @POST("androidDeleteAlamat.php")
    Call<ResponseDeleteAlamat> postDeleteAlamat(
            @Field("user_id") String userId,
            @Field("id") String sortBy
    );

    @FormUrlEncoded
    @POST("androidSaveAlamat.php")
    Call<ResponseSaveAlamat> postSaveAlamat(
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
    @POST("androidDefaultAlamat.php")
    Call<ResponseDefaultAlamat> postDefaultAlamat(
            @Field("user_id") String userId
    );

    @FormUrlEncoded
    @POST("androidUtamakanAlamat.php")
    Call<ResponseUtamakanAlamat> postUtamakanAlamat(
            @Field("user_id") String userId,
            @Field("alamat_id") String alamatId
    );

    @FormUrlEncoded
    @POST("androidOrderDataStore.php")
    Call<ResponseOrder> postOrderList(
            @Field("id_member") String userId,
            @Field("page") String page,
            @Field("status") String status,
            @Field("tipe") String tipe
    );

    @FormUrlEncoded
    @POST("androidOrderDetail.php")
    Call<ResponseDetailPesanan> postDetailPesanan(
            @Field("id_user") String userId,
            @Field("no_transaksi") String noTransaksi
    );

    @FormUrlEncoded
    @POST("androidPropinsiDataStore.php")
    Call<ResponseProvince> postProvinceList(
            @Field("keyUserId") String userId
    );

    @FormUrlEncoded
    @POST("androidCityDataStore.php")
    Call<ResponseCity> postCityList(
            @Field("province_id") String provinceId
    );

    @FormUrlEncoded
    @POST("androidSubdistrictDataStore.php")
    Call<ResponseSubdistrict> postSubdistrictList(
            @Field("city_id") String cityId
    );

    @FormUrlEncoded
    @POST("androidSignin.php")
    Call<ResponseSignIn> postSignIn(
            @Field("email") String email,
            @Field("password") String password
    );

    @Multipart
    @POST("androidSignup.php")
    Call<ResponseSignUp> postSignup(
            @Part("first_name") RequestBody firstName,
            @Part("last_name") RequestBody lastName,
            @Part("no_hp") RequestBody nohp,
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("referensi") RequestBody referensi,
            @Part("password") RequestBody password,
            @Part("konfirmasi") RequestBody konfirmasi,
            @Part("alamat") RequestBody alamat,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("id_propinsi") RequestBody idPropinsi,
            @Part("nama_propinsi") RequestBody namaPropinsi,
            @Part("id_kota") RequestBody idKota,
            @Part("nama_kota") RequestBody namaKota,
            @Part("id_kecamatan") RequestBody idKecamatan,
            @Part("nama_kecamatan") RequestBody namaKecamatan,
            @Part("kode_pos") RequestBody kodePos,
            @Part("tipe") RequestBody tipe,
            @Part MultipartBody.Part imageFile
    );

    @Multipart
    @POST("androidSavePhoto.php")
    Call<ResponseGantiPhoto> postGantiPhoto(
            @Part("id_user") RequestBody userId,
            @Part MultipartBody.Part imageFile
    );

    @FormUrlEncoded
    @POST("androidSaveProfil.php")
    Call<ResponseEditProfil> postEditProfil(
            @Field("id_user") String userId,
            @Field("first_name") String firstName,
            @Field("last_name") String lastName,
            @Field("no_hp") String no_hp,
            @Field("email") String email,
            @Field("username") String username
    );
}
