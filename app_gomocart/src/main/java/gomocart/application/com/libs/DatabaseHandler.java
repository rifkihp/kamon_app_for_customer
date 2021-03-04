package gomocart.application.com.libs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;

import gomocart.application.com.model.mitra;
import gomocart.application.com.model.market;
import gomocart.application.com.model.produk;

public class DatabaseHandler extends SQLiteOpenHelper {

	Context context;
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAMA = "gomocart";

	public static final String TABLE_CARTLIST = "cartlist";
	public static final String TABLE_BADGE_NT = "badge_nt";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAMA, null, DATABASE_VERSION);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public void createTable() {
		SQLiteDatabase db = this.getWritableDatabase();

		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARTLIST);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_CARTLIST + "(" +

				"id INTEGER, " +
				"kode TEXT, " +
				"nama TEXT, " +

				"id_category INTEGER, " +
				"category_name TEXT, " +

				"penjelasan TEXT, " +
				"foto1_produk TEXT," +
				"satuan TEXT," +

				"harga_beli DOUBLE," +
				"harga_jual DOUBLE," +
				"harga_grosir DOUBLE," +
				"harga_diskon DOUBLE," +
				"persen_diskon INTEGER," +
				"berat INTEGER," +

				"list_ukuran TEXT," +
				"ukuran TEXT," +
				"list_warna TEXT," +
				"warna TEXT," +

				"qty INTEGER," +
				"max_qty INTEGER," +
				"minimum_pesan INTEGER," +

				"produk_promo INTEGER," +
				"produk_featured INTEGER," +
				"produk_terbaru INTEGER," +
				"produk_preorder INTEGER," +
				"produk_soldout INTEGER," +
				"produk_grosir INTEGER," +
				"produk_freeongkir INTEGER," +
				"produk_cod INTEGER," +

				"rating INTEGER," +
				"responden INTEGER," +
				"review INTEGER," +

				"subtotal DOUBLE," +
				"grandtotal DOUBLE," +

				"wishlist INTEGER," +
				"checked INTEGER," +
				"publish INTEGER," +

				"mitra_id INTEGER," +
				"mitra_nama TEXT," +
				"mitra_no_hp TEXT," +
				"mitra_email TEXT," +
				"mitra_username TEXT," +
				"mitra_password TEXT," +
				"mitra_alamat TEXT," +
				"mitra_latitude DOUBLE," +
				"mitra_longitude DOUBLE," +
				"mitra_province_id INTEGER," +
				"mitra_province TEXT," +
				"mitra_city_id INTEGER," +
				"mitra_city_name TEXT," +
				"mitra_subdistrict_id INTEGER," +
				"mitra_subdistrict_name TEXT," +
				"mitra_kode_pos TEXT," +
				"mitra_photo TEXT," +
				"mitra_aktif INTEGER," +

				"kode_trx TEXT," +
				"kode_grup INTEGER)"
		);

		//db.execSQL("DROP TABLE IF EXISTS " + TABLE_BADGE_NT);
		db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_BADGE_NT + "(" +
				"id INTEGER, " +
				"id_badge INTEGER, " +
				"kategori TEXT, " +
				"qty INTEGER," +

				"kode_grup INTEGER)"
		);

		db.close();
	}

	public void insertCartlist(produk cart_item, String kode_trx, int kode_grup) {
		int total_qty = getTotalCartItem(cart_item.getId(), cart_item.getUkuran(), cart_item.getWarna(), kode_trx, kode_grup);

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("id", cart_item.getId());
		values.put("kode", cart_item.getKode());
		values.put("nama", cart_item.getNama());

		values.put("id_category", cart_item.getId_category());
		values.put("category_name", cart_item.getCategory_name());

		values.put("penjelasan", cart_item.getPenjelasan());
		values.put("foto1_produk", cart_item.getFoto1_produk());
		values.put("satuan", cart_item.getSatuan());

		values.put("harga_beli", cart_item.getHarga_beli());
		values.put("harga_jual", cart_item.getHarga_jual());
		values.put("harga_grosir", cart_item.getHarga_grosir());
		values.put("harga_diskon", cart_item.getHarga_diskon());
		values.put("persen_diskon", cart_item.getPersen_diskon());
		values.put("berat", cart_item.getBerat());

		values.put("list_ukuran", cart_item.getList_ukuran());
		values.put("ukuran", cart_item.getUkuran());
		values.put("list_warna", cart_item.getList_warna());
		values.put("warna", cart_item.getWarna());

		values.put("qty", cart_item.getQty());
		values.put("max_qty", cart_item.getMax_qty());
		values.put("minimum_pesan", cart_item.getMinimum_pesan());

		values.put("produk_promo", cart_item.getProduk_promo());
		values.put("produk_featured", cart_item.getProduk_featured());
		values.put("produk_terbaru", cart_item.getProduk_terbaru());
		values.put("produk_preorder", cart_item.getProduk_preorder());
		values.put("produk_soldout", cart_item.getProduk_soldout());
		values.put("produk_grosir", cart_item.getProduk_grosir());
		values.put("produk_freeongkir", cart_item.getProduk_freeongkir());
		values.put("produk_cod", cart_item.getProduk_cod());

		values.put("rating", cart_item.getRating());
		values.put("responden", cart_item.getResponden());
		values.put("review", cart_item.getReview());

		values.put("subtotal", cart_item.getSubtotal());
		values.put("grandtotal", cart_item.getGrandtotal());

		values.put("mitra_id", cart_item.getMitra().getId());
		values.put("mitra_nama", cart_item.getMitra().getNama());
		values.put("mitra_no_hp", cart_item.getMitra().getNo_hp());
		values.put("mitra_email", cart_item.getMitra().getEmail());
		values.put("mitra_username", cart_item.getMitra().getUsername());
		values.put("mitra_password", cart_item.getMitra().getPassword());
		values.put("mitra_alamat", cart_item.getMitra().getAlamat());
		values.put("mitra_latitude", cart_item.getMitra().getLatitude());
		values.put("mitra_longitude", cart_item.getMitra().getLongitude());
		values.put("mitra_province_id", cart_item.getMitra().getProvince_id());
		values.put("mitra_province", cart_item.getMitra().getProvince());
		values.put("mitra_city_id", cart_item.getMitra().getCity_id());
		values.put("mitra_city_name", cart_item.getMitra().getCity_name());
		values.put("mitra_subdistrict_id", cart_item.getMitra().getSubdistrict_id());
		values.put("mitra_subdistrict_name", cart_item.getMitra().getSubdistrict_name());
		values.put("mitra_kode_pos", cart_item.getMitra().getKode_pos());
		values.put("mitra_photo", cart_item.getMitra().getPhoto());
		values.put("mitra_aktif", cart_item.getMitra().getAktif()?1:0);

		values.put("wishlist", cart_item.getWishlist()?1:0);
		values.put("checked", cart_item.getChecked()?1:0);
		values.put("publish", cart_item.getPublish()?1:0);

		values.put("kode_trx", kode_trx);
		values.put("kode_grup", kode_grup);

		if(total_qty==0){
			db.insert(TABLE_CARTLIST, null, values);
		} else {
			values.put("qty", cart_item.getQty()+total_qty);
			values.put("grandtotal", cart_item.getSubtotal()*(cart_item.getQty()+total_qty));
			db.update(TABLE_CARTLIST, values, "id=? AND ukuran=? AND warna=? AND kode_trx=? AND kode_grup=?", new String[]{ String.valueOf(cart_item.getId()), cart_item.getUkuran(), cart_item.getWarna(), kode_trx, String.valueOf(kode_grup) });
		}
		db.close();
	}

	public void updateCartlist(produk cart_item, int id, String ukuran, String warna, String kode_trx, int kode_grup) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("id", cart_item.getId());
		values.put("kode", cart_item.getKode());
		values.put("nama", cart_item.getNama());

		values.put("id_category", cart_item.getId_category());
		values.put("category_name", cart_item.getCategory_name());

		values.put("penjelasan", cart_item.getPenjelasan());
		values.put("foto1_produk", cart_item.getFoto1_produk());
		values.put("satuan", cart_item.getSatuan());

		values.put("harga_beli", cart_item.getHarga_beli());
		values.put("harga_jual", cart_item.getHarga_jual());
		values.put("harga_grosir", cart_item.getHarga_grosir());
		values.put("harga_diskon", cart_item.getHarga_diskon());
		values.put("persen_diskon", cart_item.getPersen_diskon());
		values.put("berat", cart_item.getBerat());

		values.put("list_ukuran", cart_item.getList_ukuran());
		values.put("ukuran", cart_item.getUkuran());
		values.put("list_warna", cart_item.getList_warna());
		values.put("warna", cart_item.getWarna());

		values.put("qty", cart_item.getQty());
		values.put("max_qty", cart_item.getMax_qty());
		values.put("minimum_pesan", cart_item.getMinimum_pesan());

		values.put("produk_promo", cart_item.getProduk_promo());
		values.put("produk_featured", cart_item.getProduk_featured());
		values.put("produk_terbaru", cart_item.getProduk_terbaru());
		values.put("produk_preorder", cart_item.getProduk_preorder());
		values.put("produk_soldout", cart_item.getProduk_soldout());
		values.put("produk_grosir", cart_item.getProduk_grosir());
		values.put("produk_freeongkir", cart_item.getProduk_freeongkir());
		values.put("produk_cod", cart_item.getProduk_cod());

		values.put("rating", cart_item.getRating());
		values.put("responden", cart_item.getResponden());
		values.put("review", cart_item.getReview());

		values.put("subtotal", cart_item.getSubtotal());
		values.put("grandtotal", cart_item.getGrandtotal());

		values.put("mitra_id", cart_item.getMitra().getId());
		values.put("mitra_nama", cart_item.getMitra().getNama());
		values.put("mitra_no_hp", cart_item.getMitra().getNo_hp());
		values.put("mitra_email", cart_item.getMitra().getEmail());
		values.put("mitra_username", cart_item.getMitra().getUsername());
		values.put("mitra_password", cart_item.getMitra().getPassword());
		values.put("mitra_alamat", cart_item.getMitra().getAlamat());
		values.put("mitra_latitude", cart_item.getMitra().getLatitude());
		values.put("mitra_longitude", cart_item.getMitra().getLongitude());
		values.put("mitra_province_id", cart_item.getMitra().getProvince_id());
		values.put("mitra_province", cart_item.getMitra().getProvince());
		values.put("mitra_city_id", cart_item.getMitra().getCity_id());
		values.put("mitra_city_name", cart_item.getMitra().getCity_name());
		values.put("mitra_subdistrict_id", cart_item.getMitra().getSubdistrict_id());
		values.put("mitra_subdistrict_name", cart_item.getMitra().getSubdistrict_name());
		values.put("mitra_kode_pos", cart_item.getMitra().getKode_pos());
		values.put("mitra_photo", cart_item.getMitra().getPhoto());
		values.put("mitra_aktif", cart_item.getMitra().getAktif()?1:0);

		values.put("wishlist", cart_item.getWishlist()?1:0);
		values.put("checked", cart_item.getChecked()?1:0);
		values.put("publish", cart_item.getPublish()?1:0);

		values.put("kode_trx", kode_trx);
		values.put("kode_grup", kode_grup);

		db.update(TABLE_CARTLIST, values, "id=? AND ukuran=? AND warna=? AND kode_trx=? AND kode_grup=?", new String[]{ String.valueOf(id), ukuran, warna, kode_trx, String.valueOf(kode_grup) });

		db.close();
	}

	public void deleteCartlist(String kode_trx, int kode_grup, int mitra_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CARTLIST, "kode_trx=? AND kode_grup=? AND mitra_id=?", new String[]{ kode_trx, String.valueOf(kode_grup), String.valueOf(mitra_id) });
		db.close();
	}

	public void deleteCartlist(String kode_trx, int kode_grup, produk item) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CARTLIST, "id=? AND ukuran=? AND warna=? AND kode_trx=? AND kode_grup=?", new String[]{ String.valueOf(item.getId()), item.getUkuran(), item.getWarna(), kode_trx, String.valueOf(kode_grup) });
		db.close();
	}

	public int getTotalCart() {
		int result = 0;

		try {
			String sql = "SELECT SUM(qty) FROM " + TABLE_CARTLIST;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int getTotalCartItem(int id, String ukuran, String warna, String kode_trx, int kode_grup) {
		int result = 0;

		try {
			String sql = "SELECT SUM(qty) FROM " + TABLE_CARTLIST + " WHERE id="+id+ " AND ukuran='"+ukuran+"' AND warna='"+warna+"' AND kode_trx='"+kode_trx+"' AND kode_grup="+kode_grup;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<produk> getCartlist(String kode_trx, int kode_grup) {
		ArrayList<produk> result = new ArrayList<>();

		try {
			String sql = "SELECT " +

					"id, " +
					"kode, " +
					"nama, " +

					"id_category, " +
					"category_name, " +

					"penjelasan, " +
					"foto1_produk, " +
					"satuan, " +

					"harga_beli, " +
					"harga_jual, " +
					"harga_grosir, " +
					"harga_diskon, " +
					"persen_diskon, " +
					"berat, " +

					"list_ukuran, " +
					"ukuran, " +
					"list_warna, " +
					"warna, " +

					"qty, " +
					"max_qty, " +
					"minimum_pesan, " +

					"produk_promo, " +
					"produk_featured, " +
					"produk_terbaru, " +
					"produk_preorder, " +
					"produk_soldout, " +
					"produk_grosir, " +
					"produk_freeongkir, " +
					"produk_cod, " +

					"rating, " +
					"responden, " +
					"review, " +

					"subtotal, " +
					"grandtotal, " +

					"mitra_id," +
					"mitra_nama," +
					"mitra_no_hp," +
					"mitra_email," +
					"mitra_username," +
					"mitra_password," +
					"mitra_alamat," +
					"mitra_latitude," +
					"mitra_longitude," +
					"mitra_province_id," +
					"mitra_province," +
					"mitra_city_id," +
					"mitra_city_name," +
					"mitra_subdistrict_id," +
					"mitra_subdistrict_name," +
					"mitra_kode_pos," +
					"mitra_photo," +
					"mitra_aktif," +

					"wishlist," +
					"checked," +
					"publish FROM " + TABLE_CARTLIST +" WHERE kode_trx='" + kode_trx + "' AND kode_grup="+kode_grup;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				for(int i=0; i<cursor.getCount(); i++) {
					produk prod = new produk(
							cursor.getInt(0),
							cursor.getString(1),
							cursor.getString(2),

							cursor.getInt(3),
							cursor.getString(4),

							cursor.getString(5),
							cursor.getString(6),
							cursor.getString(7),

							cursor.getDouble(8),
							cursor.getDouble(9),
							cursor.getDouble(10),
							cursor.getDouble(11),
							cursor.getInt(12),
							cursor.getInt(13),

							cursor.getString(14),
							cursor.getString(15),
							cursor.getString(16),
							cursor.getString(17),

							cursor.getInt(18),
							cursor.getInt(19),
							cursor.getInt(20),

							cursor.getInt(21),
							cursor.getInt(22),
							cursor.getInt(23),
							cursor.getInt(24),
							cursor.getInt(25),
							cursor.getInt(26),
							cursor.getInt(27),
							cursor.getInt(28),

							cursor.getInt(29),
							cursor.getInt(30),
							cursor.getInt(31),

							cursor.getDouble(32),
							cursor.getDouble(33),

							new mitra(
									cursor.getInt(34),
									cursor.getString(35),
									cursor.getString(36),
									cursor.getString(37),
									cursor.getString(38),
									cursor.getString(39),
									cursor.getString(40),
									cursor.getDouble(41),
									cursor.getDouble(42),
									cursor.getInt(43),
									cursor.getString(44),
									cursor.getInt(45),
									cursor.getString(46),
									cursor.getInt(47),
									cursor.getString(48),
									cursor.getString(49),
									cursor.getString(50),
									cursor.getInt(51)==1
							),

							cursor.getInt(52)==1,
							cursor.getInt(53)==1,
							cursor.getInt(54)==1
					);

					result.add(prod);
					cursor.moveToNext();
				}
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public market getGroupOrderList(String kode_grup) {
		ArrayList<market> result = new ArrayList<>();
		String[] nama = new String[] {"Toko PUSKOP", "Toko PRIMKOP", "Toko UMKM", "Toko KOMODITI"};

		String sql = "SELECT " +
				"kode_grup AS id, " +
				"kode_trx, " +

				"mitra_id," +
				"mitra_nama," +
				"mitra_no_hp," +
				"mitra_email," +
				"mitra_username," +
				"mitra_password," +
				"mitra_alamat," +
				"mitra_latitude," +
				"mitra_longitude," +
				"mitra_province_id," +
				"mitra_province," +
				"mitra_city_id," +
				"mitra_city_name," +
				"mitra_subdistrict_id," +
				"mitra_subdistrict_name," +
				"mitra_kode_pos," +
				"mitra_photo," +
				"mitra_aktif," +

				"SUM(berat*qty) AS total_berat,"+
				"SUM(qty) AS total_qty, " +
				"SUM(grandtotal) AS total_jumlah FROM " + TABLE_CARTLIST + " WHERE kode_grup='"+kode_grup+"' GROUP BY kode_grup, kode_trx, mitra_id  ORDER BY kode_grup, mitra_id";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.getCount() > 0) {
			cursor.moveToFirst();
			for(int i=0; i<cursor.getCount(); i++) {
				market data = new market(
						cursor.getInt(0),
						cursor.getString(1),
						nama[cursor.getInt(0)],
						new mitra(
								cursor.getInt(2),
								cursor.getString(3),
								cursor.getString(4),
								cursor.getString(5),
								cursor.getString(6),
								cursor.getString(7),
								cursor.getString(8),
								cursor.getDouble(9),
								cursor.getDouble(10),
								cursor.getInt(11),
								cursor.getString(12),
								cursor.getInt(13),
								cursor.getString(14),
								cursor.getInt(15),
								cursor.getString(16),
								cursor.getString(17),
								cursor.getString(18),
								cursor.getInt(19)==1
						),
						cursor.getDouble(20),
						cursor.getDouble(21),
						cursor.getDouble(22)
				);
				result.add(data);

				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		return result.size()>0?result.get(0):null;
	}

	public String getKodeTrx(int kode_grup, int mitra_id) {
		String result= "";

		String sql = "SELECT kode_trx FROM " + TABLE_CARTLIST + " WHERE kode_grup=" + kode_grup + " AND mitra_id=" + mitra_id + " GROUP BY kode_grup, mitra_id";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(sql, null);
		if(cursor.getCount() > 0) {
			cursor.moveToFirst();
			for(int i=0; i<cursor.getCount(); i++) {
				result = cursor.getString(0);
				cursor.moveToNext();
			}
		}
		cursor.close();
		db.close();

		return result;
	}

	public void deleteBadge(int id_badge, String kategori, int kode_grup) {
		SQLiteDatabase db = this.getWritableDatabase();
		Log.e("DELETE", id_badge+" "+kategori);
		db.delete(TABLE_BADGE_NT, "id_badge=? AND kategori=? AND kode_grup=?", new String[]{ String.valueOf(id_badge), kategori, String.valueOf(kode_grup) });
		db.close();
	}

	public void insertBadge(int id_badge, int qty, String kategori, int kode_grup) {
		int total_qty = getTotalBadge(id_badge, kategori, kode_grup);

		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("id_badge", id_badge);
		values.put("kategori", kategori);
		values.put("qty", qty);

		if(total_qty==0){
			db.insert(TABLE_BADGE_NT, null, values);
		} else {
			values.put("qty", qty);
			db.update(TABLE_BADGE_NT, values, "id_badge=? AND kategori=? AND kode_grup=?", new String[]{ String.valueOf(id_badge), kategori, String.valueOf(kode_grup) });
		}
		db.close();
	}

	public int getTotalBadge(String kategori) {
		int result = 0;

		try {
			String sql = "SELECT SUM(qty) FROM " + TABLE_BADGE_NT + " WHERE kategori='" + kategori + "'";
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public int getTotalBadge(int id_badge, String kategori, int kode_grup) {
		int result = 0;

		try {
			String sql = "SELECT SUM(qty) FROM " + TABLE_BADGE_NT + " WHERE id_badge='" + id_badge + "' AND kategori='" + kategori + "' AND kode_grup=" + kode_grup;
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(sql, null);
			if(cursor.getCount() > 0) {
				cursor.moveToFirst();
				result = cursor.getInt(0);
			}
			cursor.close();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}
}
