package gomocart.application.com.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class damayUser  implements Serializable {

	@SerializedName("provinsi")
	private String provinsi;

	@SerializedName("entity_name")
	private String entityName;

	@SerializedName("nama_bagian")
	private String namaBagian;

	@SerializedName("tgl_masuk")
	private String tglMasuk;

	@SerializedName("jenis_nama")
	private String jenisNama;

	@SerializedName("id_bagian")
	private String idBagian;

	@SerializedName("merchant_id")
	private String merchantId;

	@SerializedName("tmp_lahir")
	private String tmpLahir;

	@SerializedName("client_id")
	private String clientId;

	@SerializedName("jns_kelamin")
	private String jnsKelamin;

	@SerializedName("nik")
	private String nik;

	@SerializedName("password")
	private String password;

	@SerializedName("alamat_ktp")
	private String alamatKtp;

	@SerializedName("status_karyawan_nama")
	private String statusKaryawanNama;

	@SerializedName("no_ktp")
	private String noKtp;

	@SerializedName("limit")
	private String limit;

	@SerializedName("tgl_regist")
	private String tglRegist;

	@SerializedName("id_bank")
	private String idBank;

	@SerializedName("client_secret")
	private String clientSecret;

	@SerializedName("email")
	private String email;

	@SerializedName("kota")
	private String kota;

	@SerializedName("no_rek")
	private String noRek;

	@SerializedName("entity_cd")
	private String entityCd;

	@SerializedName("photo")
	private String photo;

	@SerializedName("id_user")
	private String idUser;

	@SerializedName("saldo")
	private String saldo;

	@SerializedName("tgl_keluar")
	private Object tglKeluar;

	@SerializedName("tgl_lahir")
	private String tglLahir;

	@SerializedName("alamat")
	private String alamat;

	@SerializedName("simpanan_wajib")
	private String simpananWajib;

	@SerializedName("nama")
	private String nama;

	@SerializedName("loc_file")
	private String locFile;

	@SerializedName("jenis")
	private String jenis;

	@SerializedName("simpanan_pokok")
	private String simpananPokok;

	@SerializedName("kota_cd")
	private String kotaCd;

	@SerializedName("no_telp")
	private String noTelp;

	@SerializedName("status_karyawan")
	private String statusKaryawan;

	@SerializedName("photo_entity")
	private String photoEntity;

	@SerializedName("id_anggota")
	private String idAnggota;

	@SerializedName("simpanan_sukarela")
	private String simpananSukarela;

	@SerializedName("provinsi_cd")
	private String provinsiCd;

	@SerializedName("atas_nama")
	private String atasNama;

	@SerializedName("status")
	private String status;

	@SerializedName("main_entity_cd")
	private String mainEntityCd;

	@SerializedName("main_entity_name")
	private String mainEntityName;

	@SerializedName("satuan_cd")
	private String satuanCd;

	@SerializedName("satuan_nama")
	private String satuanName;
	
	public void setProvinsi(String provinsi){
		this.provinsi = provinsi;
	}

	public String getProvinsi(){
		return provinsi;
	}

	public void setEntityName(String entityName){
		this.entityName = entityName;
	}

	public String getEntityName(){
		return entityName;
	}

	public void setNamaBagian(String namaBagian){
		this.namaBagian = namaBagian;
	}

	public String getNamaBagian(){
		return namaBagian;
	}

	public void setTglMasuk(String tglMasuk){
		this.tglMasuk = tglMasuk;
	}

	public String getTglMasuk(){
		return tglMasuk;
	}

	public void setJenisNama(String jenisNama){
		this.jenisNama = jenisNama;
	}

	public String getJenisNama(){
		return jenisNama;
	}

	public void setIdBagian(String idBagian){
		this.idBagian = idBagian;
	}

	public String getIdBagian(){
		return idBagian;
	}

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setTmpLahir(String tmpLahir){
		this.tmpLahir = tmpLahir;
	}

	public String getTmpLahir(){
		return tmpLahir;
	}

	public void setClientId(String clientId){
		this.clientId = clientId;
	}

	public String getClientId(){
		return clientId;
	}

	public void setJnsKelamin(String jnsKelamin){
		this.jnsKelamin = jnsKelamin;
	}

	public String getJnsKelamin(){
		return jnsKelamin;
	}

	public void setNik(String nik){
		this.nik = nik;
	}

	public String getNik(){
		return nik;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setAlamatKtp(String alamatKtp){
		this.alamatKtp = alamatKtp;
	}

	public String getAlamatKtp(){
		return alamatKtp;
	}

	public void setStatusKaryawanNama(String statusKaryawanNama){
		this.statusKaryawanNama = statusKaryawanNama;
	}

	public String getStatusKaryawanNama(){
		return statusKaryawanNama;
	}

	public void setNoKtp(String noKtp){
		this.noKtp = noKtp;
	}

	public String getNoKtp(){
		return noKtp;
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

	public String getLimit(){
		return limit;
	}

	public void setTglRegist(String tglRegist){
		this.tglRegist = tglRegist;
	}

	public String getTglRegist(){
		return tglRegist;
	}

	public void setIdBank(String idBank){
		this.idBank = idBank;
	}

	public String getIdBank(){
		return idBank;
	}

	public void setClientSecret(String clientSecret){
		this.clientSecret = clientSecret;
	}

	public String getClientSecret(){
		return clientSecret;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setKota(String kota){
		this.kota = kota;
	}

	public String getKota(){
		return kota;
	}

	public void setNoRek(String noRek){
		this.noRek = noRek;
	}

	public String getNoRek(){
		return noRek;
	}

	public void setEntityCd(String entityCd){
		this.entityCd = entityCd;
	}

	public String getEntityCd(){
		return entityCd;
	}

	public void setPhoto(String photo){
		this.photo = photo;
	}

	public String getPhoto(){
		return photo;
	}

	public void setIdUser(String idUser){
		this.idUser = idUser;
	}

	public String getIdUser(){
		return idUser;
	}

	public void setSaldo(String saldo){
		this.saldo = saldo;
	}

	public String getSaldo(){
		return saldo;
	}

	public void setTglKeluar(Object tglKeluar){
		this.tglKeluar = tglKeluar;
	}

	public Object getTglKeluar(){
		return tglKeluar;
	}

	public void setTglLahir(String tglLahir){
		this.tglLahir = tglLahir;
	}

	public String getTglLahir(){
		return tglLahir;
	}

	public void setAlamat(String alamat){
		this.alamat = alamat;
	}

	public String getAlamat(){
		return alamat;
	}

	public void setSimpananWajib(String simpananWajib){
		this.simpananWajib = simpananWajib;
	}

	public String getSimpananWajib(){
		return simpananWajib;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setLocFile(String locFile){
		this.locFile = locFile;
	}

	public String getLocFile(){
		return locFile;
	}

	public void setJenis(String jenis){
		this.jenis = jenis;
	}

	public String getJenis(){
		return jenis;
	}

	public void setSimpananPokok(String simpananPokok){
		this.simpananPokok = simpananPokok;
	}

	public String getSimpananPokok(){
		return simpananPokok;
	}

	public void setKotaCd(String kotaCd){
		this.kotaCd = kotaCd;
	}

	public String getKotaCd(){
		return kotaCd;
	}

	public void setNoTelp(String noTelp){
		this.noTelp = noTelp;
	}

	public String getNoTelp(){
		return noTelp;
	}

	public void setStatusKaryawan(String statusKaryawan){
		this.statusKaryawan = statusKaryawan;
	}

	public String getStatusKaryawan(){
		return statusKaryawan;
	}

	public void setPhotoEntity(String photoEntity){
		this.photoEntity = photoEntity;
	}

	public String getPhotoEntity(){
		return photoEntity;
	}

	public void setIdAnggota(String idAnggota){
		this.idAnggota = idAnggota;
	}

	public String getIdAnggota(){
		return idAnggota;
	}

	public void setSimpananSukarela(String simpananSukarela){
		this.simpananSukarela = simpananSukarela;
	}

	public String getSimpananSukarela(){
		return simpananSukarela;
	}

	public void setProvinsiCd(String provinsiCd){
		this.provinsiCd = provinsiCd;
	}

	public String getProvinsiCd(){
		return provinsiCd;
	}

	public void setAtasNama(String atasNama){
		this.atasNama = atasNama;
	}

	public String getAtasNama(){
		return atasNama;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setMainEntityCd(String mainEntityCd){
		this.mainEntityCd = mainEntityCd;
	}

	public String getMainEntityCd(){
		return mainEntityCd;
	}

	public void setMainEntityName(String mainEntityName){
		this.mainEntityName = mainEntityName;
	}

	public String getMainEntityName(){
		return mainEntityName;
	}

	public void setSatuanCd(String satuanCd){
		this.satuanCd = satuanCd;
	}

	public String getSatuanCd(){
		return satuanCd;
	}

	public void setSatuanName(String satuanName){
		this.satuanName = satuanName;
	}

	public String getSatuanName(){
		return satuanName;
	}
}