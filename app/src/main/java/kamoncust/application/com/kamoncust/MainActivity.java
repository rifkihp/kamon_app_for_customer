package kamoncust.application.com.kamoncust;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.alexzh.circleimageview.CircleImageView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import android.widget.TextView;

import kamoncust.application.com.adapter.ListProdukAdapter;
import kamoncust.application.com.fragment.BekamFragment;
import kamoncust.application.com.libs.MyImageDownloader;
import kamoncust.application.com.model.ResponseAlamat;
import kamoncust.application.com.model.ResponseBatalkanPesanan;
import kamoncust.application.com.model.ResponseCity;
import kamoncust.application.com.model.ResponseDefaultAlamat;
import kamoncust.application.com.model.ResponseEditProfil;
import kamoncust.application.com.model.ResponseGantiPhoto;
import kamoncust.application.com.model.ResponseOrder;
import kamoncust.application.com.model.ResponseProvince;
import kamoncust.application.com.model.ResponseSubdistrict;
import kamoncust.application.com.model.ResponseUtamakanAlamat;
import kamoncust.application.com.model.mitra;
import kamoncust.application.com.model.order_list;
import kamoncust.application.com.model.subdistrict;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import kamoncust.application.com.adapter.AlamatAdapter;
import kamoncust.application.com.adapter.CartlistAdapter;
import kamoncust.application.com.adapter.KategoriallAdapter;
import kamoncust.application.com.adapter.MoreMenuAdapter;
import kamoncust.application.com.adapter.PerpesananAdapter;
import kamoncust.application.com.adapter.InformasiAdapter;
import kamoncust.application.com.adapter.ListOngkirAdapter;
import kamoncust.application.com.adapter.ListOrderAdapter;
import kamoncust.application.com.adapter.NotifikasiAdapter;
import kamoncust.application.com.adapter.WishlistAdapter;
import kamoncust.application.com.data.RestApi;
import kamoncust.application.com.data.RetroFit;
import kamoncust.application.com.fragment.AlamatFragment;
import kamoncust.application.com.fragment.DaftarPesananBatalFragment;
import kamoncust.application.com.fragment.DaftarPesananProsesFragment;
import kamoncust.application.com.fragment.DaftarPesananSelesaiFragment;
import kamoncust.application.com.fragment.DashboardFragment;
import kamoncust.application.com.fragment.KeranjangFragment;
import kamoncust.application.com.fragment.OngkosKirimFragment;
import kamoncust.application.com.fragment.MoreFragment;
import kamoncust.application.com.fragment.EditProfileFragment;
import kamoncust.application.com.fragment.GantiPasswordFragment;
import kamoncust.application.com.fragment.JenisUserFragment;
import kamoncust.application.com.fragment.ProdukFragment;
import kamoncust.application.com.fragment.ProfileFragment;
import kamoncust.application.com.fragment.SaldoUserFragment;
import kamoncust.application.com.fragment.SettingFragment;
import kamoncust.application.com.fragment.InformasiFragment;
import kamoncust.application.com.fragment.KategoriFragment;
import kamoncust.application.com.fragment.PerpesananFragment;
import kamoncust.application.com.fragment.NotifikasiFragment;
import kamoncust.application.com.fragment.DaftarPesananFragment;
import kamoncust.application.com.fragment.DaftarPesananMenungguFragment;
import kamoncust.application.com.fragment.WishlistFragment;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.DatabaseHandler;
import kamoncust.application.com.libs.DatabaseHandlerToko;
import kamoncust.application.com.libs.GalleryFilePath;
import kamoncust.application.com.libs.JSONParser;
import kamoncust.application.com.libs.MCrypt;
import kamoncust.application.com.libs.ServerUtilities;
import kamoncust.application.com.model.ResponseDashboard;
import kamoncust.application.com.model.ResponseDeleteAlamat;
import kamoncust.application.com.model.ResponseDeleteProduk;
import kamoncust.application.com.model.ResponseKategori;
import kamoncust.application.com.model.ResponseProduk;
import kamoncust.application.com.model.alamat;
import kamoncust.application.com.model.bank;
import kamoncust.application.com.model.city;
import kamoncust.application.com.model.grandtotal;
import kamoncust.application.com.model.produkgrup;
import kamoncust.application.com.model.informasi;
import kamoncust.application.com.model.informasi_list;
import kamoncust.application.com.model.kategori;
import kamoncust.application.com.model.moremenu;
import kamoncust.application.com.model.perpesanan;
import kamoncust.application.com.model.perpesanan_list;
import kamoncust.application.com.model.notifikasi;
import kamoncust.application.com.model.notifikasi_list;
import kamoncust.application.com.model.ongkir;
import kamoncust.application.com.model.order;
import kamoncust.application.com.model.produk;
import kamoncust.application.com.model.produk_list;
import kamoncust.application.com.model.province;
import kamoncust.application.com.model.setting;
import kamoncust.application.com.model.shortcut;
import kamoncust.application.com.model.stok;
import kamoncust.application.com.model.user;
import kamoncust.application.com.model.banner;

import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.soundcloud.android.crop.Crop;

public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, NavigationView.OnNavigationItemSelectedListener {

	final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
	final private int RESULT_FROM_SIGN_IN = 1;
	final private int RESULT_FROM_SIGN_UP = 2;
	final private int RESULT_FROM_AKTIVASI = 15;
	final private int RESULT_FROM_PRODUK_DETAIL = 3;
	final private int REQUEST_FROM_GALLERY = 5;
	final private int REQUEST_FROM_CAMERA  = 6;
	final private int REQUEST_FROM_FILTER = 7;
	final private int RESULT_FROM_PROSES_CHECKOUT= 8;
	final private int RESULT_FROM_KIRIM_PESAN = 9;
	final private int RESULT_FROM_DETAIL_ORDER = 12;
	final private int RESULT_FROM_EDIT_ALAMAT = 13;

	//DASHBOARD
	public static boolean tampilkan_produk_induk = false;
	public static boolean tampilkan_shortcut = false;
	public static boolean tampilkan_mitra = false;
	public static boolean tampilkan_produk_home = false;

	public static ArrayList<banner>     dashboard_banner       = new ArrayList<>();
	public static ArrayList<shortcut>   dashboard_shortcut     = new ArrayList<>();
	public static ArrayList<mitra>      dashboard_mitra        = new ArrayList<>();
	public static ArrayList<produkgrup> dashboard_produk_induk = new ArrayList<>();
	public static ArrayList<produkgrup> dashboard_produk_home  = new ArrayList<>();

	//KATERGORI
	public static ArrayList<kategori> kategorialllist = new ArrayList<>();
	public static KategoriallAdapter kategorialladapter;

	//PRODUK
	public static mitra mitra_selected;
	public static int page_produk;
	public static ArrayList<produk> produklist = new ArrayList<>();
	public static ListProdukAdapter produkadapter;

	public static int province_id;
	public static int city_id;
	public static int subdistrict_id;
	public static int berat_barang;
	public static int page_id;

	public static ArrayList<province> listProvince = new ArrayList<>();
	public static ArrayList<city> listCity = new ArrayList<>();
	public static ArrayList<subdistrict> listSubDistrict = new ArrayList<>();

	Dialog dialog_listview;
	ListView listview;

	public static int image_produk_size_vertical=0;
	public static int image_produk_size_horizontal=0;

	public static Context context;
	public static user data;

	public static DatabaseHandler dh;
	public static DatabaseHandlerToko dht;

	int total_cart;
	int total_wishlist;

	LinearLayout lin_login;
	LinearLayout lin_register;

	ImageView image_menu_login;
	ImageView image_menu_profil;
	TextView nav_login;
	TextView nav_register;

	Dialog dialog_sort_by;
	ImageView radioTerbaru, radioRating, radioTermurah, radioTermahal;
	LinearLayout linearTerbaru, linearRating, linearTermurah, linearTermahal;
	public static String sort_produk_by;

	Dialog dialog_sort_by_alamat;
	ImageView radioAZ, radioZA;
	LinearLayout linearAZ, linearZA;
	public static String sort_by_alamat;

	Dialog dialog_setting_notifikasi;
	ImageView radioSuaraGetar, radioSuara, radioGetar;
	LinearLayout linearSuaraGetar, linearSuara, linearGetar;
	public static String setting_notifikasi;

	//CART LIST
	grandtotal gtotal_proses;
	ArrayList<produk> cartlist_proses = new ArrayList<>();
	public static ArrayList<produk> cartlist = new ArrayList<>();
	public static CartlistAdapter cartlistAdapter;

	//WISH LIST
	public static ArrayList<produk> wishlist = new ArrayList<>();
	public static WishlistAdapter wishlistAdapter;

	// ONGKIR LIST
	public static ArrayList<ongkir> ongkirlist = new ArrayList<>();
	public static ListOngkirAdapter ongkirAdapter;


	//ORDER LIST
	int[] orderlist_page = new int[4];
	ArrayList<order>[] orderlist = new ArrayList[4];
	ListOrderAdapter[] orderlist_adapter = new ListOrderAdapter[4];

	// INFORMASI
	int next_page_informasi;
	public static ArrayList<informasi> informasilist = new ArrayList<>();
	public static InformasiAdapter informasiAdapter;

	//PERPESANAN
	int next_page_perpesanan;
	public static ArrayList<perpesanan> perpesananlist = new ArrayList<>();
	public static PerpesananAdapter perpesananAdapter;

	//NOTIFIKASI
	public static int next_page_notifikasi;
	public static ArrayList<notifikasi> list_notifikasi = new ArrayList<>();
	public static NotifikasiAdapter notifikasi_adapter;

	//SLIDE MENU
	ArrayList<moremenu> moremenulist = new ArrayList<>();
	Map<moremenu, ArrayList<moremenu>> submoremenulist = new LinkedHashMap<>();
	MoreMenuAdapter moremenuAdapter;
	public static ExpandableListView moremenuListView;

	//ALAMAT SAYA
	public static ArrayList<alamat> alamatlist = new ArrayList<>();
	public static ArrayList<alamat> alamatlist_display = new ArrayList<>();
	public static AlamatAdapter alamatAdapter;

	public static int menu_selected = 0;
	public static ImageLoader imageLoader;
	public static DisplayImageOptions imageOptionsUser;
	public static DisplayImageOptions imageOptionProduk;
	public static DisplayImageOptions imageOptionKategori;
	public static DisplayImageOptions imageOptionBank;
	public static DisplayImageOptions imageOptionOngkir;
	public static DisplayImageOptions imageOptionInformasi;

	ImageView menu;
	DrawerLayout drawer;
	LinearLayout mDrawerPane;

	CircleImageView avatar;
	TextView name_avatar;

	produk produkSelected;
	alamat alamatSelected;
	order orderSelected;
	produk wistlistSelected;
	
	Dialog konfirmasi_dialog;
	TextView konfirmasi_dialog_title;
	TextView konfirmasi_dialog_text;
	TextView konfirmasi_dialog_yes;
	TextView konfirmasi_dialog_no;

	Dialog dialog_ukuran_warna;
	ListView listview_ukuran_warna;
	String action;
	int item_index;

	ArrayList<String> list_ukuran;
	ArrayList<String> list_warna;

	Dialog dialog_loading;

	Dialog dialog_informasi;
	TextView btn_ok;
	TextView text_title;
	TextView text_informasi;

	ArrayList<bank> list_bank;

	Dialog dialog_pilih_gambar;
	TextView from_camera, from_galery;

	private static Uri mImageCaptureUri;
	int count_close = 1;
	int current_click = 0;

	Handler mHandlerClose = new Handler();
	public static moremenu moremenu_select = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		context = MainActivity.this;
		data = CommonUtilities.getSettingUser(context);

		if (Build.VERSION.SDK_INT >= 23) {
			insertDummyContactWrapper();
		}

		if (Build.VERSION.SDK_INT >= 21) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.parseColor("#278CE3"));
		}

		dh = new DatabaseHandler(context);
		dh.createTable();

		dht = new DatabaseHandlerToko(context);
		dht.createTable();

		menu = findViewById(R.id.menu);
		drawer = findViewById(R.id.drawer_layout);
		mDrawerPane = findViewById(R.id.drawerPane);

		int width = getResources().getDisplayMetrics().widthPixels;
		width = width - (width / 3);
		DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mDrawerPane.getLayoutParams();
		params.width = width;
		mDrawerPane.setLayoutParams(params);

		moremenuListView = (ExpandableListView) findViewById(R.id.moremenulistview);

		menu.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View view) {
				if (drawer.isDrawerOpen(GravityCompat.START)) {
					drawer.closeDrawer(GravityCompat.START);
				} else {
					drawer.openDrawer(GravityCompat.START);
				}
			}
		});

		avatar = findViewById(R.id.banar1);
		name_avatar = findViewById(R.id.name);

		lin_login = findViewById(R.id.lin_login);
		lin_register = findViewById(R.id.lin_register);

		image_menu_login = findViewById(R.id.image_menu_login);
		image_menu_profil  = findViewById(R.id.image_menu_profil);

		nav_login = findViewById(R.id.nav_login);
		nav_register = findViewById(R.id.nav_register);

		lin_login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				if(data.getId()==0) {
					openPageLogin();
				} else {
					openDialogSignout();
				}
			}
		});

		lin_register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawer.closeDrawer(GravityCompat.START);
				if(data.getId()==0) {
					Intent intent = new Intent(context, DaftarActivity.class);
					startActivityForResult(intent, RESULT_FROM_SIGN_UP);
				} else {
					displayView(11);
				}
			}
		});

		moremenulist.add(new moremenu(1, "", getResources().getString(R.string.menu_beranda), "", R.drawable.menu_beranda, 0));
		moremenulist.add(new moremenu(2, "", getResources().getString(R.string.menu_order), "", R.drawable.menu_daftar_pesanan, 0));

		moremenulist.add(new moremenu(3, "", getResources().getString(R.string.menu_tentang_kami), "", R.drawable.menu_hubungi_pengembang, 0));
		moremenulist.add(new moremenu(4, "", getResources().getString(R.string.menu_cara_pemesanan), "", R.drawable.menu_keranjang, 0));
		moremenulist.add(new moremenu(5, "", getResources().getString(R.string.menu_syarat_ketentuan), "", R.drawable.menu_produk, 0));
		moremenulist.add(new moremenu(6, "", getResources().getString(R.string.menu_hubungi_pengembang), "", R.drawable.phone, 0));

		moremenulist.add(new moremenu(7, "", getResources().getString(R.string.menu_bagikan), "", R.drawable.button_share, 0));
		moremenulist.add(new moremenu(8, "", getResources().getString(R.string.menu_facebook), "", R.drawable.menu_informasi, 0));
		moremenulist.add(new moremenu(9, "", getResources().getString(R.string.menu_instagram), "", R.drawable.send_32, 0));
		moremenulist.add(new moremenu(10, "", getResources().getString(R.string.menu_youtube), "", R.drawable.signup_nohp, 0));

		moremenuAdapter = new MoreMenuAdapter(context, moremenulist, submoremenulist);
		moremenuListView.setAdapter(moremenuAdapter);

		moremenuListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v, int position, long id) {
				int menu_id = moremenulist.get(position).getId();
				switch (menu_id) {
					case 1:
						//KILIK BEKAM
						displayView(4);
						break;

					case 2:
						//HISTORI PESANA
						displayView(1);
						break;

					case 3:
						//TENTANG KAMI
						page_id = 1;
						displayView(6);
						break;

					case 4:
						//CARA PESAN
						page_id = 2;
						displayView(6);
						break;

					case 5:
						//SYARAT & KETENTUAN
						page_id = 3;
						displayView(6);
						break;

					case 6:
						//HUBUNGI KAMI
						page_id = 5;
						displayView(6);
						break;

					case 7:
						//BAGIKAN
						break;

					case 8:
						//FACEBOOK
						break;

					case 9:
						//INSTAGRAM
						break;

					case 10:
						//YOUTUBE
						break;

					default:
						break;

				}

				return false;
			}
		});

		LinearLayout nav_bekam    = findViewById(R.id.nav_bekam);
		nav_bekam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(4);
			}
		});
		LinearLayout nav_pesanan  = findViewById(R.id.nav_pesanan);
		nav_pesanan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(1);
			}
		});
		LinearLayout nav_home     = findViewById(R.id.nav_home);
		nav_home.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(0);
			}
		});
		LinearLayout nav_informasi = findViewById(R.id.nav_informasi);
		nav_informasi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(5);
			}
		});
		LinearLayout nav_profil   = findViewById(R.id.nav_profil);
		nav_profil.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				displayView(11);
			}
		});

		avatar.setLayerType(View.LAYER_TYPE_HARDWARE, null);
		dialog_listview = new Dialog(context);
		dialog_listview.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_listview.setCancelable(true);
		dialog_listview.setContentView(R.layout.list_dialog);

		listview = (ListView) dialog_listview.findViewById(R.id.listViewDialog);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog_listview.dismiss();
				if(action.equalsIgnoreCase("province") || action.equalsIgnoreCase("profile_province")) {
					if(action.equalsIgnoreCase("province")) {
						OngkosKirimFragment.edit_province.setText(listProvince.get(position).getProvince());
						OngkosKirimFragment.prop_ok.setVisibility(View.VISIBLE);
						OngkosKirimFragment.edit_city.setText("");
						OngkosKirimFragment.city_ok.setVisibility(View.GONE);
						OngkosKirimFragment.edit_state.setText("");
						OngkosKirimFragment.kecamatan_ok.setVisibility(View.GONE);
					}

					province_id = listProvince.get(position).getProvince_id();
					city_id = 0;
					subdistrict_id = 0;

					listCity = new ArrayList<>();
					new loadCity().execute();

					listSubDistrict = new ArrayList<>();
					new loadSubdistrict().execute();

				} else if(action.equalsIgnoreCase("city") || action.equalsIgnoreCase("profile_city")) {
					if(action.equalsIgnoreCase("city")) {
						OngkosKirimFragment.edit_city.setText(listCity.get(position).getCity_name());
						OngkosKirimFragment.city_ok.setVisibility(View.VISIBLE);
						OngkosKirimFragment.edit_state.setText("");
						OngkosKirimFragment.kecamatan_ok.setVisibility(View.GONE);
					}

					city_id = listCity.get(position).getCity_id();
					subdistrict_id = 0;

					listSubDistrict = new ArrayList<>();
					new loadSubdistrict().execute();

				} else if(action.equalsIgnoreCase("subdistrict") || action.equalsIgnoreCase("profile_subdistrict")) {
					if(action.equalsIgnoreCase("subdistrict")) {
						OngkosKirimFragment.edit_state.setText(listSubDistrict.get(position).getSubdistrict_name());
						OngkosKirimFragment.kecamatan_ok.setVisibility(View.VISIBLE);
					}

					subdistrict_id = listSubDistrict.get(position).getSubdistrict_id();
				}
			}
		});

		konfirmasi_dialog = new Dialog(context);
		konfirmasi_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		konfirmasi_dialog.setCancelable(true);
		konfirmasi_dialog.setContentView(R.layout.konfirmasi_dialog);

		konfirmasi_dialog_yes = konfirmasi_dialog.findViewById(R.id.btn_yes);
		konfirmasi_dialog_yes.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				konfirmasi_dialog.dismiss();

				if(action.equalsIgnoreCase("logout")) {
					new prosesSignOut().execute();
				} else 
				if(action.equalsIgnoreCase("delete_cartlist")) {
					prosesHapusCartlistTerpilih();
				} else
				if(action.equalsIgnoreCase("delete_produk")) {
					new prosesDeleteProduk().execute();
				} else
				if(action.equalsIgnoreCase("delete_alamat")) {
					new prosesDeleteAlamat().execute();
				} else
				if(action.equalsIgnoreCase("batalkan_pesanan")) {
					new prosesBatalkanPesanan().execute();
				} else
				if(action.equalsIgnoreCase("delete_wishlist")) {
					dh.deleteWishlist(wistlistSelected);
					for (produk wish_item : wishlist) {
						int index = wishlist.indexOf(wish_item);
						if(wish_item.getId()==wistlistSelected.getId()) {
							wishlist.remove(index);
							break;
						}
					}
					wishlistAdapter.UpdateWishlistAdapter(wishlist);
					updateTotalWishlist(wistlistSelected);
				}
			}
		});

		konfirmasi_dialog_no = konfirmasi_dialog.findViewById(R.id.btn_no);
		konfirmasi_dialog_no.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				konfirmasi_dialog.dismiss();

			}
		});

		konfirmasi_dialog_title = konfirmasi_dialog.findViewById(R.id.text_title);
		konfirmasi_dialog_text = konfirmasi_dialog.findViewById(R.id.text_dialog);

		dialog_loading = new Dialog(context);
		dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_loading.setCancelable(false);
		dialog_loading.setContentView(R.layout.loading_dialog);

		dialog_informasi = new Dialog(context);
		dialog_informasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_informasi.setCancelable(true);
		dialog_informasi.setContentView(R.layout.informasi_dialog);

		btn_ok = dialog_informasi.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog_informasi.dismiss();
			}
		});

		text_title = dialog_informasi.findViewById(R.id.text_title);
		text_informasi = dialog_informasi.findViewById(R.id.text_dialog);

		int memoryCacheSize;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
			int memClass = ((ActivityManager)
					context.getSystemService(Context.ACTIVITY_SERVICE))
					.getMemoryClass();
			memoryCacheSize = (memClass / 8) * 1024 * 1024;
		} else {
			memoryCacheSize = 2 * 1024 * 1024;
		}

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.memoryCacheSize(memoryCacheSize)
				.memoryCache(new FIFOLimitedMemoryCache(memoryCacheSize - 1000000))
				.denyCacheImageMultipleSizesInMemory()
				//.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.imageDownloader(new MyImageDownloader(context))
				.build();

		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);

		imageOptionsUser     = CommonUtilities.getOptionsImage(R.drawable.userdefault, R.drawable.userdefault);
		imageOptionProduk    = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageOptionKategori  = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageOptionBank      = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageOptionOngkir 	 = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
		imageOptionInformasi = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);

		menu_selected = 0;

		if(savedInstanceState==null) {
			checkGcmRegid();
			menu_selected = getIntent().getIntExtra("menu_select", 0);
		}
		setSignIn();

		dialog_ukuran_warna = new Dialog(context);
		dialog_ukuran_warna.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_ukuran_warna.setCancelable(true);
		dialog_ukuran_warna.setContentView(R.layout.list_dialog);

		listview_ukuran_warna = (ListView) dialog_ukuran_warna.findViewById(R.id.listViewDialog);
		listview_ukuran_warna.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog_ukuran_warna.dismiss();
				if(action.equalsIgnoreCase("ukuran")) {
					cartlist.get(item_index).setUkuran(list_ukuran.get(position));
					cartlist.get(item_index).setWarna("");
				} else {
					cartlist.get(item_index).setWarna(list_warna.get(position));
				}
				cartlistAdapter.UpdateCartlistAdapter();
			}
		});

		dialog_pilih_gambar = new Dialog(context);
		dialog_pilih_gambar.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_pilih_gambar.setCancelable(true);
		dialog_pilih_gambar.setContentView(R.layout.pilih_gambar_dialog);

		from_galery = dialog_pilih_gambar.findViewById(R.id.txtFromGalley);
		from_galery.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_pilih_gambar.dismiss();
				fromGallery();
			}
		});

		from_camera = dialog_pilih_gambar.findViewById(R.id.txtFromCamera);
		from_camera.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_pilih_gambar.dismiss();
				fromCamera();
			}
		});

		dialog_setting_notifikasi = new Dialog(context);
		dialog_setting_notifikasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_setting_notifikasi.setCancelable(true);
		dialog_setting_notifikasi.setContentView(R.layout.setting_notifikasi_dialog);

		radioSuaraGetar = dialog_setting_notifikasi.findViewById(R.id.radioSuaraGetar);
		linearSuaraGetar = dialog_setting_notifikasi.findViewById(R.id.linearSuaraGetar);
		linearSuaraGetar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_setting_notifikasi.dismiss();
				setting_notifikasi = "Suara dan Getar";

				radioSuaraGetar.setImageResource(R.drawable.radioblack);
				radioSuara.setImageResource(R.drawable.radiouncheked);
				radioGetar.setImageResource(R.drawable.radiouncheked);

				SettingFragment.edit_notifikasi.setText(setting_notifikasi);
			}
		});

		radioSuara = dialog_setting_notifikasi.findViewById(R.id.radioSuara);
		linearSuara = dialog_setting_notifikasi.findViewById(R.id.linearSuara);
		linearSuara.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_setting_notifikasi.dismiss();
				setting_notifikasi = "Suara";

				radioSuaraGetar.setImageResource(R.drawable.radiouncheked);
				radioSuara.setImageResource(R.drawable.radioblack);
				radioGetar.setImageResource(R.drawable.radiouncheked);

				SettingFragment.edit_notifikasi.setText(setting_notifikasi);
			}
		});

		radioGetar = dialog_setting_notifikasi.findViewById(R.id.radioGetar);
		linearGetar = dialog_setting_notifikasi.findViewById(R.id.linearGetar);
		linearGetar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_setting_notifikasi.dismiss();
				setting_notifikasi = "Getar";

				radioSuaraGetar.setImageResource(R.drawable.radiouncheked);
				radioGetar.setImageResource(R.drawable.radiouncheked);
				radioGetar.setImageResource(R.drawable.radioblack);

				SettingFragment.edit_notifikasi.setText(setting_notifikasi);
			}
		});


		dialog_sort_by_alamat = new Dialog(context);
		dialog_sort_by_alamat.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_sort_by_alamat.setCancelable(true);
		dialog_sort_by_alamat.setContentView(R.layout.sortby_alamat_dialog);

		radioAZ = dialog_sort_by_alamat.findViewById(R.id.radioAZ);
		linearAZ = dialog_sort_by_alamat.findViewById(R.id.linearAZ);
		linearAZ.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by_alamat.dismiss();
				sortAlamtBy(1);

				radioAZ.setImageResource(R.drawable.radioblack);
				radioZA.setImageResource(R.drawable.radiouncheked);
			}
		});

		radioZA = dialog_sort_by_alamat.findViewById(R.id.radioZA);
		linearZA = dialog_sort_by_alamat.findViewById(R.id.linearZA);
		linearZA.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by_alamat.dismiss();
				sortAlamtBy(1);

				radioAZ.setImageResource(R.drawable.radiouncheked);
				radioZA.setImageResource(R.drawable.radioblack);
			}
		});

		dialog_sort_by = new Dialog(context);
		dialog_sort_by.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_sort_by.setCancelable(true);
		dialog_sort_by.setContentView(R.layout.sortby_produk_dialog);

		radioTerbaru = dialog_sort_by.findViewById(R.id.radioTerbaru);
		linearTerbaru = dialog_sort_by.findViewById(R.id.linearTerbaru);
		linearTerbaru.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by.dismiss();
				sortProdukBy(1);

				radioTerbaru.setImageResource(R.drawable.radioblack);
				radioRating.setImageResource(R.drawable.radiouncheked);
				radioTermurah.setImageResource(R.drawable.radiouncheked);
				radioTermahal.setImageResource(R.drawable.radiouncheked);
			}
		});

		radioRating = dialog_sort_by.findViewById(R.id.radioRatting);
		linearRating = dialog_sort_by.findViewById(R.id.linearRating);
		linearRating.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by.dismiss();
				sortProdukBy(2);

				radioTerbaru.setImageResource(R.drawable.radiouncheked);
				radioRating.setImageResource(R.drawable.radioblack);
				radioTermurah.setImageResource(R.drawable.radiouncheked);
				radioTermahal.setImageResource(R.drawable.radiouncheked);
			}
		});

		radioTermurah = dialog_sort_by.findViewById(R.id.radioTermurah);
		linearTermurah = dialog_sort_by.findViewById(R.id.linearTermurah);
		linearTermurah.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by.dismiss();
				sortProdukBy(3);

				radioTerbaru.setImageResource(R.drawable.radiouncheked);
				radioRating.setImageResource(R.drawable.radiouncheked);
				radioTermurah.setImageResource(R.drawable.radioblack);
				radioTermahal.setImageResource(R.drawable.radiouncheked);
			}
		});

		radioTermahal = dialog_sort_by.findViewById(R.id.radioTermahal);
		linearTermahal = dialog_sort_by.findViewById(R.id.linearTermahal);
		linearTermahal.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_sort_by.dismiss();
				sortProdukBy(4);

				radioTerbaru.setImageResource(R.drawable.radiouncheked);
				radioRating.setImageResource(R.drawable.radiouncheked);
				radioTermurah.setImageResource(R.drawable.radiouncheked);
				radioTermahal.setImageResource(R.drawable.radioblack);
			}
		});
	}

	private void sortProdukBy(int sortby) {
		sort_produk_by = String.valueOf(sortby);
		loadDataProduk(true);
	}

	private void sortAlamtBy(int sortby) {
		sort_by_alamat = String.valueOf(sortby);

		alamatlist = new ArrayList<>();
		alamatlist_display = new ArrayList<>();

		showListAlamat();
		loadAlamatlist();
	}

	public void openDialogSettingNotifikasi() {
		dialog_setting_notifikasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_setting_notifikasi.show();

		radioSuaraGetar.setImageResource(setting_notifikasi.equalsIgnoreCase("Suara dan Getar")?R.drawable.radioblack:R.drawable.radiouncheked);
		radioSuara.setImageResource(setting_notifikasi.equalsIgnoreCase("Suara")?R.drawable.radioblack:R.drawable.radiouncheked);
		radioGetar.setImageResource(setting_notifikasi.equalsIgnoreCase("Getar")?R.drawable.radioblack:R.drawable.radiouncheked);
	}

	public void openDialogSortByAlamat() {
		dialog_sort_by_alamat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_sort_by_alamat.show();
	}

	public void openDialogSortBy() {
		dialog_sort_by.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_sort_by.show();
	}

	public void selectImage() {
		dialog_pilih_gambar.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_pilih_gambar.show();
	}

	private void beginCrop(Uri source) {
		File file= new File(source.getPath());
		Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped_"+file.getName()));
		Crop.of(source, destination).asSquare().start(this);
	}

	private void fromGallery() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("image/*");
		startActivityForResult(intent, REQUEST_FROM_GALLERY);
	}

	private void fromCamera() {

		Intent intent = new Intent(context, AmbilFotoActivity.class);
		startActivityForResult(intent, REQUEST_FROM_CAMERA);
	}


	public void openDialogUkuran(ArrayList<stok> list_stok, int index) {
		item_index = index;
		list_ukuran = new ArrayList<>();
		boolean add_ukuran;

		for(stok data_: list_stok) {
			add_ukuran = true;
			for(String data__: list_ukuran) {
				if(data_.getUkuran().equalsIgnoreCase(data__)) {
					add_ukuran = false;
					break;
				}
			}
			if(add_ukuran) {
				list_ukuran.add(data_.getUkuran());
			}
		}

		dialog_ukuran_warna.show();
		loadListArray(list_ukuran);
		action = "ukuran";
	}

	public void openDialogWarna(ArrayList<stok> list_stok, String ukuran, int index) {
		item_index = index;
		list_warna = new ArrayList<>();
		boolean add_warna;
		for(stok data_: list_stok) {
			if(data_.getUkuran().equalsIgnoreCase(ukuran)) {
				add_warna = true;
				for(String data__: list_warna) {
					if(data_.getWarna().equalsIgnoreCase(data__)) {
						add_warna = false;
						break;
					}
				}
				if(add_warna) {
					list_warna.add(data_.getWarna());
				}
			}
		}

		dialog_ukuran_warna.show();
		loadListArray(list_warna);
		action = "warna";
	}

	private void loadListArray(ArrayList<String> list_data) {
		String[] from = new String[] { getResources().getString(R.string.list_dialog_title) };
		int[] to = new int[] { R.id.txt_title };

		List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
		for (String data : list_data) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put(getResources().getString(R.string.list_dialog_title), data);

			fillMaps.add(map);
		}

		SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
		listview_ukuran_warna.setAdapter(adapter);
	}

	public void openDialogSignout() {
		action = "logout";
		konfirmasi_dialog_title.setText("Keluar");
		konfirmasi_dialog_text.setText("Yakin untuk keluar?");
		konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		konfirmasi_dialog.show();
	}

	public void openPageLogin() {
		Intent intent = new Intent(context, LoginActivity.class);
		intent.putExtra("menu_selected", menu_selected);
		startActivityForResult(intent, RESULT_FROM_SIGN_IN);
	}

	public void prosesBatalkanPesanan(order data_order) {
		orderSelected = data_order;
		action = "batalkan_pesanan";
		konfirmasi_dialog_title.setText("Batalkan Pesanan?");
		konfirmasi_dialog_text.setText("Pesanan yang dipilih akan dibatalkan.");
		konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		konfirmasi_dialog.show();
	}

	public void openDetailOrder(order data) {
		Intent intent = new Intent(context, DetailPesananActivity.class);
		intent.putExtra("order", data);
		startActivityForResult(intent, RESULT_FROM_DETAIL_ORDER);
	}

	class prosesBatalkanPesanan extends AsyncTask<String, Void, Void> {
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseBatalkanPesanan> batalkanPesananCall = api.postBatalkanPesanan(orderSelected.getNo_transaksi());
			batalkanPesananCall.enqueue(new Callback<ResponseBatalkanPesanan>() {
				@Override
				public void onResponse(@NonNull Call<ResponseBatalkanPesanan> call, @NonNull Response<ResponseBatalkanPesanan> response) {

					Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_BATALKAN_PESANAN");
					i.putExtra("success", Objects.requireNonNull(response.body()).getSuccess());
					i.putExtra("message", Objects.requireNonNull(response.body()).getMessage());
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseBatalkanPesanan> call, @NonNull Throwable t) {

					Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_BATALKAN_PESANAN");
					i.putExtra("success", false);
					i.putExtra("message", "Error koneksi server.");
					sendBroadcast(i);
				}
			});
			
			return null;
		}
	}

	class prosesDeleteProduk extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseDeleteProduk> deleteProdukCall = api.postDeleteProduk(data.getId()+"", produkSelected.getId()+"");
			deleteProdukCall.enqueue(new Callback<ResponseDeleteProduk>() {
				@Override
				public void onResponse(@NonNull Call<ResponseDeleteProduk> call, @NonNull Response<ResponseDeleteProduk> response) {

					Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_DELETE_PRODUK");
					i.putExtra("success", Objects.requireNonNull(response.body()).getSuccess());
					i.putExtra("message", Objects.requireNonNull(response.body()).getMessage());
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseDeleteProduk> call, @NonNull Throwable t) {
					
					Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_DELETE_PRODUK");
					i.putExtra("success", false);
					i.putExtra("message", "Error koneksi server.");
					sendBroadcast(i);
				}
			});

			return null;
		}
	}


	class prosesDeleteAlamat extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseDeleteAlamat> deleteAlamatCall = api.postDeleteAlamat(data.getId()+"", alamatSelected.getId()+"");
			deleteAlamatCall.enqueue(new Callback<ResponseDeleteAlamat>() {
				@Override
				public void onResponse(@NonNull Call<ResponseDeleteAlamat> call, @NonNull Response<ResponseDeleteAlamat> response) {

					Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_DELETE_ALAMAT");
					i.putExtra("success", Objects.requireNonNull(response.body()).getSuccess());
					i.putExtra("message", Objects.requireNonNull(response.body()).getMessage());
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseDeleteAlamat> call, @NonNull Throwable t) {

					Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_DELETE_ALAMAT");
					i.putExtra("success", false);
					i.putExtra("message", "Error koneksi server.");
					sendBroadcast(i);
				}
			});

			return null;
		}
	}

	class prosesUtamakanAlamat extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseUtamakanAlamat> utamakanAlamatCall = api.postUtamakanAlamat(data.getId()+"", alamatSelected.getId()+"");
			utamakanAlamatCall.enqueue(new Callback<ResponseUtamakanAlamat>() {
				@Override
				public void onResponse(@NonNull Call<ResponseUtamakanAlamat> call, @NonNull Response<ResponseUtamakanAlamat> response) {
					Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_UTAMAKAN_ALAMAT");
					i.putExtra("success", Objects.requireNonNull(response.body()).getSuccess());
					i.putExtra("message", Objects.requireNonNull(response.body()).getMessage());
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseUtamakanAlamat> call, @NonNull Throwable t) {
					t.printStackTrace();
					Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_UTAMAKAN_ALAMAT");
					i.putExtra("success", false);
					i.putExtra("message", "Proses utamakan alamat gagal.");
					sendBroadcast(i);
				}
			});

			return null;
		}
	}

	class prosesSignOut extends AsyncTask<String, Void, JSONObject> {

		boolean success;
		String message;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			String url = CommonUtilities.SERVER_URL + "/store/androidSignout.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			return json;
		}

		@Deprecated
		@Override
		protected void onPostExecute(JSONObject result) {

			dialog_loading.dismiss();

			success = false;
			message = "Gagal melakukan sign out. Silahkan coba lagi!";
			if(result!=null) {
				try {
					success = !result.isNull("success") && result.getBoolean("success");
					message = result.isNull("message")?message:result.getString("message");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(success) {
				data = new user(0, "", "", "", "", "", "", "", "", "", 0, 0);
				CommonUtilities.setSettingUser(context, data);
				checkGcmRegid();
				dh.clearOrderlist();
				int landing_page = CommonUtilities.getLandingPage(context);
				if(landing_page==0) {
					menu_selected = 0;
					setSignIn();
				} else {
					Intent i = new Intent(context, SplashActivity.class);
					startActivity(i);
					finish();
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			if(menu_selected==0) {
				if (current_click == count_close) {
					finish();
				} else {
					current_click++;
					Toast.makeText(context, "Tekan dua kali untuk keluar.", Toast.LENGTH_SHORT).show();
					mHandlerClose.postDelayed(mUpdateTimeTask, 1000);
					return false;
				}
			}

			if (menu_selected > 0) {
				current_click=0;
				if(menu_selected==12) {
					displayView(11);
				} else if(menu_selected==15) {
					displayView(11);
				} else if(menu_selected==16) {
					displayView(11);
				} else if(menu_selected==17) {
					displayView(11);
				} else if(menu_selected==19) {
					displayView(11);
				} else {
					displayView(0);
				}
				return false;
			}
		}

		return super.onKeyDown(keyCode, event);
	}

	public void displayView(int position) {
		drawer.closeDrawer(GravityCompat.START);

		menu_selected = position;
		Fragment fragment = null;

		switch (position) {
			case 0: {
				fragment = new DashboardFragment();
				break;
			}
			case 1: {
				fragment = new DaftarPesananFragment();
				break;
			}
			case 2: {
				fragment = new ProdukFragment();
				break;
			}
			case 3: {
				fragment = new KeranjangFragment();
				break;
			}
			case 4: {
				fragment = new BekamFragment();
				break;
			}
			case 5: {

				fragment = new InformasiFragment();
				break;
			}
			case 6: {
				Intent i = new Intent(context, TermKondisiActivity.class);
				i.putExtra("page_id", page_id);
				startActivity(i);
				//fragment = new PageFragment();
				break;
			}

			case 11: {
				fragment = new ProfileFragment();
				break;
			}
			case 12: {
				fragment = new SettingFragment();
				break;
			}
			case 15: {
				alamatlist = new ArrayList<>();
				alamatAdapter = new AlamatAdapter(context, alamatlist);
				fragment = new AlamatFragment();
				break;
			}
			case 16: {
				fragment = new EditProfileFragment();
				break;
			}
			case 17: {
				fragment = new GantiPasswordFragment();
				break;
			}
			case 19: {
				fragment = new SaldoUserFragment();
				break;
			}
			default: {
				break;
			}
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager(); // getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
		}
	}


	private void closeHandler() {
		try {
			unregisterReceiver(mHandleLoadMoreMenuReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadDetailMenuReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadDashboardReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadKategoriReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadListProdukReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadDefaultAlamatReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadListWishReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadListOngkirReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadListOrderReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleEditDataProfileReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadListInformasiReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleReloadInformasiReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadDataPerpesananReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleReloadDataPerpesananReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleUpdateWishlistReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadNotifikasiReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadListAlamatReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleDeleteAlamatReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleUtamakanAlamatReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadEkspedisiReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleProsesBatalkanPesananReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			mHandlerClose.removeCallbacks(mUpdateTimeTask);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onDestroy() {
		closeHandler();

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		closeHandler();

		super.onPause();
	}

	@Override
	protected void onResume() {
		registerReceiver(mHandleLoadNotifikasiReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_NOTIFIKASI"));
		registerReceiver(mHandleLoadMoreMenuReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_MORE_MENU"));
		registerReceiver(mHandleLoadDetailMenuReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_DETAIL_MENU"));

		registerReceiver(mHandleLoadDashboardReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_DASHBOARD"));
		registerReceiver(mHandleLoadKategoriReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_KATEGORI"));

		registerReceiver(mHandleLoadListProdukReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_PRODUK"));
		registerReceiver(mHandleLoadDefaultAlamatReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_DEF_ALAMAT"));

		registerReceiver(mHandleLoadListWishReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_WISH"));
		registerReceiver(mHandleLoadListOngkirReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_ONGKIR"));
		registerReceiver(mHandleLoadListOrderReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_ORDER"));

		registerReceiver(mHandleEditDataProfileReceiver, new IntentFilter("kamoncust.application.com.kamoncust.EDIT_DATA_PROFILE"));
		registerReceiver(mHandleLoadListInformasiReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_INFORMASI"));
		registerReceiver(mHandleReloadInformasiReceiver, new IntentFilter("kamoncust.application.com.kamoncust.RELOAD_DATA_INFORMASI"));
		registerReceiver(mHandleLoadDataPerpesananReceiver,  new IntentFilter("kamoncust.application.com.kamoncust.LOAD_PERPESANAN_LIST"));
		registerReceiver(mHandleReloadDataPerpesananReceiver,  new IntentFilter("kamoncust.application.com.kamoncust.RELOAD_PERPESANAN_LIST"));
		registerReceiver(mHandleUpdateWishlistReceiver, new IntentFilter("kamoncust.application.com.kamoncust.UPDATE_WISHLIST"));

		registerReceiver(mHandleLoadListAlamatReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_ALAMAT"));
		registerReceiver(mHandleDeleteAlamatReceiver, new IntentFilter("kamoncust.application.com.kamoncust.PROSES_DELETE_ALAMAT"));
		registerReceiver(mHandleUtamakanAlamatReceiver, new IntentFilter("kamoncust.application.com.kamoncust.PROSES_UTAMAKAN_ALAMAT"));

		registerReceiver(mHandleLoadEkspedisiReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST"));
		registerReceiver(mHandleProsesBatalkanPesananReceiver, new IntentFilter("kamoncust.application.com.kamoncust.PROSES_BATALKAN_PESANAN"));

		super.onResume();
	}

	private final BroadcastReceiver mHandleLoadDetailMenuReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (menu_selected == 10) {
				Boolean success = intent.getBooleanExtra("success", false);
				if (!success) {
					MoreFragment.retry.setVisibility(View.VISIBLE);
				} else {
					String detail = intent.getStringExtra("detail");
					MoreFragment.detail_menu.setText(Html.fromHtml(detail));
				}
			}
		}
	};

	private final BroadcastReceiver mHandleLoadMoreMenuReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			moremenuAdapter.UpdateMoreMenuAdapter(moremenulist, submoremenulist);
		}
	};

	private final BroadcastReceiver mHandleLoadDashboardReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(final Context cntx, Intent intent) {
			if(menu_selected==0) {
				Boolean success = intent.getBooleanExtra("success", false);
				DashboardFragment.resultLoadDashboard(context, success);
			} else if(menu_selected==4) {
				Boolean success = intent.getBooleanExtra("success", false);
				BekamFragment.resultLoadBekam(context, success);
			}
		}
	};

	private final BroadcastReceiver mHandleLoadKategoriReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context cntx, Intent intent) {
			if(menu_selected==1) {
				Boolean success = intent.getBooleanExtra("success", false);
				KategoriFragment.loading.setVisibility(View.GONE);
				if(success) {
					kategorialladapter = new KategoriallAdapter(context, kategorialllist);
					KategoriFragment.gridview.setAdapter(kategorialladapter);
				} else {
					KategoriFragment.retry.setVisibility(View.VISIBLE);
				}
			}
		}
	};

	private final BroadcastReceiver mHandleProsesBatalkanPesananReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context ctx, Intent intent) {

			if(menu_selected==1) {
				Boolean success = intent.getBooleanExtra("success", false);
				String message = intent.getStringExtra("message");

				dialog_loading.dismiss();
				if(success) {
					int index = DaftarPesananFragment.viewPager.getCurrentItem();
					if(index>0) {
						loadOrderlist(index-1, true);
					}
					loadOrderlist(index, true);
					if(index<3) {
						loadOrderlist(index+1, true);
					}
				} else {
					openDialogMessage(message, success);
				}
			}

		}
	};



	private final BroadcastReceiver mHandleLoadListProdukReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context ctx, Intent intent) {

			if(menu_selected==2) {
				Boolean success = intent.getBooleanExtra("success", false);
				ProdukFragment.resultLoadProduk(context, success);
			}

		}
	};

	private final BroadcastReceiver mHandleLoadDefaultAlamatReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			Boolean success = intent.getBooleanExtra("success", false);
			dialog_loading.dismiss();
			if(success) {
				Intent inten = new Intent(context, ProsesCheckoutActivity.class);

				alamat data_alamat = (alamat) intent.getSerializableExtra("data_alamat");
				if (data_alamat != null) {
					inten.putExtra("data_alamat", data_alamat);
				}

				double total = 0;
				cartlist_proses = new ArrayList<>();
				for(produk item:produklist) {
					if(item.getQty()>0) {
						cartlist_proses.add(item);
						total+=item.getQty()*item.getSubtotal();
					}
				}
				double diskon     = 0; //jika ada diskon tambahan seperti voucher
				double subtotal   = total-diskon;
				double voucher    = 0;
				double ongkir     = 0;
				double grandtotal = subtotal-voucher+ongkir;

				gtotal_proses = new grandtotal(total, diskon, subtotal, voucher, ongkir, grandtotal);
				ArrayList<produk_list> temp_cartlist = new ArrayList<>();
				temp_cartlist.add(new produk_list(cartlist_proses));

				inten.putExtra("cartlist", temp_cartlist);
				inten.putExtra("grandtotal", gtotal_proses);
				inten.putExtra("mitra", mitra_selected);

				startActivityForResult(inten, RESULT_FROM_PROSES_CHECKOUT);
			} else {
				String message  = intent.getStringExtra("message");
				openDialogMessage(message, success);
			}
		}
	};

	private final BroadcastReceiver mHandleLoadListWishReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if(menu_selected==4) {
				Boolean success = intent.getBooleanExtra("success", false);

				if(success) {
					wishlistAdapter = new WishlistAdapter(context, wishlist);
					WishlistFragment.listview.setAdapter(wishlistAdapter);
					WishlistFragment.listview.setVisibility(View.VISIBLE);

					produk data_produk = (produk) intent.getSerializableExtra("produk");
					updateTotalWishlist(data_produk);
				} else {
					WishlistFragment.retry.setVisibility(View.VISIBLE);
				}
			}

		}
	};

	private final BroadcastReceiver mHandleLoadListOngkirReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==6) {
				Boolean success = intent.getBooleanExtra("success", false);

				if (success) {
					ongkirAdapter = new ListOngkirAdapter(context, ongkirlist);
					OngkosKirimFragment.listViewOngkir.setAdapter(ongkirAdapter);

					OngkosKirimFragment.linear_ongkir.setVisibility(View.VISIBLE);
				} else {
					OngkosKirimFragment.retry.setVisibility(View.VISIBLE);
				}
				dialog_loading.dismiss();
			}
		}
	};

	private final BroadcastReceiver mHandleLoadListOrderReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==1) {

				Boolean success = intent.getBooleanExtra("success", false);
				int index = intent.getIntExtra("index", 0);

				if (success) {
					ArrayList<order_list> temp = intent.getParcelableArrayListExtra("order_list");
					ArrayList<order> result = temp.get(0).getListData();

					for (order flist : result) {
						orderlist[index].add(flist);
					}
					orderlist_adapter[index].UpdateListOrderAdapter(orderlist[index]);
				}

				switch (index) {
					case 0: {
						DaftarPesananMenungguFragment.loading.setVisibility(View.GONE);
						DaftarPesananMenungguFragment.retry.setVisibility(!success?View.VISIBLE:View.GONE);
						break;
					}
					case 1: {
						DaftarPesananProsesFragment.loading.setVisibility(View.GONE);
						DaftarPesananProsesFragment.retry.setVisibility(!success?View.VISIBLE:View.GONE);
						break;
					}
					case 2: {
						DaftarPesananSelesaiFragment.loading.setVisibility(View.GONE);
						DaftarPesananSelesaiFragment.retry.setVisibility(!success?View.VISIBLE:View.GONE);
						break;
					}
					case 3: {
						DaftarPesananBatalFragment.loading.setVisibility(View.GONE);
						DaftarPesananBatalFragment.retry.setVisibility(!success?View.VISIBLE:View.GONE);
						break;
					}
				}

			}
		}
	};

	private final BroadcastReceiver mHandleLoadDataPerpesananReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==14) {
				Boolean success = intent.getBooleanExtra("success", false);

				if (!success) {
					PerpesananFragment.retry.setVisibility(View.VISIBLE);
				} else {
					ArrayList<perpesanan_list> temp = intent.getParcelableArrayListExtra("perpesanan_list");
					ArrayList<perpesanan> result = temp.get(0).getListData();

					for (perpesanan flist : result) {
						perpesananlist.add(flist);
					}

					perpesananAdapter.UpdateLaporanAdapter(perpesananlist);
				}
			}
		}
	};

	private final BroadcastReceiver mHandleReloadDataPerpesananReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==14) {
				loadDataPerpesanan(true);
			}
		}
	};

	private final BroadcastReceiver mHandleLoadListInformasiReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (menu_selected == 5) {
				Boolean success = intent.getBooleanExtra("success", false);

				if(!success) {
					InformasiFragment.retry.setVisibility(View.VISIBLE);
				} else {
					ArrayList<informasi_list> temp = intent.getParcelableArrayListExtra("data_informasi_list");
					ArrayList<informasi> result = temp.get(0).getListData();

					for (informasi flist : result) {
						informasilist.add(flist);
					}

					informasiAdapter.UpdateListInformasiAdapter(informasilist);
				}
			}
		}
	};

	private final BroadcastReceiver mHandleReloadInformasiReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (menu_selected == 5) {
				loadDataInformasi(true);
			}
		}
	};

	private final BroadcastReceiver mHandleLoadNotifikasiReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(menu_selected==13) {
				Boolean success = intent.getBooleanExtra("success", false);

				if (!success) {
					NotifikasiFragment.retry.setVisibility(View.VISIBLE);
				} else {
					ArrayList<notifikasi_list> temp = intent.getParcelableArrayListExtra("notifikasi_list");
					ArrayList<notifikasi> result = temp.get(0).getListData();

					for (notifikasi flist : result) {
						list_notifikasi.add(flist);
					}

					notifikasi_adapter.UpdateListNotifikasiAdapter(list_notifikasi);
				}
			}
		}
	};

	private final BroadcastReceiver mHandleUpdateWishlistReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			produk data_produk = (produk) intent.getSerializableExtra("produk");
			updateTotalWishlist(data_produk);
		}
	};

	private final BroadcastReceiver mHandleLoadEkspedisiReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			if(dialog_loading.isShowing()) {
				loadListArray();
				dialog_loading.dismiss();
				dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				dialog_listview.show();
			}
		}
	};

	private final BroadcastReceiver mHandleLoadListAlamatReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);

			if(AlamatFragment.loading!=null) AlamatFragment.loading.setVisibility(View.GONE);
			if(!success && AlamatFragment.retry!=null) AlamatFragment.retry.setVisibility(View.VISIBLE);

			showListAlamat();
		}
	};

	private final BroadcastReceiver mHandleDeleteAlamatReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);
			String message  = intent.getStringExtra("message");

			dialog_loading.dismiss();
			if(success) {
				for(int i=0; i<alamatlist.size(); i++) {
					if(alamatlist.get(i).getId()==alamatSelected.getId()) {
						alamatlist.remove(i);
						showListAlamat();

						break;
					}
				}
			} else {
				showInformationDialog(message, success);
			}
		}
	};

	private final BroadcastReceiver mHandleUtamakanAlamatReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);
			String message  = intent.getStringExtra("message");

			dialog_loading.dismiss();
			if(success) {
				for(int i=0; i<alamatlist.size(); i++) {
					alamatlist.get(i).setAs_default(alamatlist.get(i).getId()==alamatSelected.getId());
				}

				alamatAdapter.UpdateAlamatAdapter(alamatlist);
			} else {
				showInformationDialog(message, success);
			}
		}
	};

	public void showInformationDialog(String message, final boolean status) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(status?"BERHASIL":"GAGAL");
		builder.setMessage(message);
		builder.setCancelable(true);

		builder.setPositiveButton(
				"OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.dismiss();
						if(status) {
							Intent intent = new Intent();
							setResult(RESULT_OK, intent);
							finish();
						}
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void showListAlamat() {

		String keyword = AlamatFragment.edit_search.getText().toString();
		if(keyword.length()==0) {
			alamatlist_display = alamatlist;
		} else {
			alamatlist_display = new ArrayList<>();
			keyword = keyword.toLowerCase();
			for (alamat dt : alamatlist) {
				if(dt.getNama().toLowerCase().contains(keyword) || dt.getAlamat().toLowerCase().contains(keyword)) {
					alamatlist_display.add(dt);
				}
			}
		}

		alamatAdapter.UpdateAlamatAdapter(alamatlist_display);
	}

	public void openDialogMessage(String message, boolean status) {
		text_informasi.setText(message);
		text_title.setText(status?"BERHASIL":"KESALAHAN");
		dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_informasi.show();
	}

	private final BroadcastReceiver mHandleEditDataProfileReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Boolean success = intent.getBooleanExtra("success", false);
			String message = intent.getStringExtra("message");
			dialog_loading.dismiss();
			if(success) {
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
				setSignIn();
			} else {
				openDialogMessage(message,success);
			}
		}
	};

	public void openProdukMitra(mitra mit) {
		mitra_selected = mit;
		displayView(2);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);

		savedInstanceState.putInt("menu_selected", menu_selected);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		menu_selected = savedInstanceState.getInt("menu_selected");
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data_intent);

		if (resultCode == RESULT_OK) {

			switch (requestCode) {
				case RESULT_FROM_PRODUK_DETAIL: {
					updateTotalCartlist();

					String detail_go_to = data_intent.getStringExtra("goto");
					if (detail_go_to != null) {
						if (detail_go_to.equalsIgnoreCase("cart_list")) {
							displayView(2);
							return;
						}
					}

					boolean cekongkir = data_intent.getBooleanExtra("cekongkir", false);
					if (cekongkir) {
						displayView(6);
					}

					boolean opendetail = data_intent.getBooleanExtra("opendetail", false);
					if (opendetail) {
						produk data = (produk) data_intent.getSerializableExtra("produk");
						int kode_grup = data_intent.getIntExtra("kode_grup", 0);
						openDetailProdukToko(data, kode_grup);
					}
					break;
				}
				case Crop.REQUEST_CROP: {
					mImageCaptureUri = Crop.getOutput(data_intent);
					new savePhotoProfil().execute();

					break;
				}
				case REQUEST_FROM_CAMERA: {
					String imageUri = data_intent.getStringExtra("path");
					String extension = imageUri.substring(imageUri.lastIndexOf("."));
					String fileName = new SimpleDateFormat("yyyyMMddhhmmss'"+extension+"'").format(new Date());
					String dest = CommonUtilities.getOutputPath(context, "images")+File.separator+fileName;

					//Log.e("ANALISA_FILE", dest);
					CommonUtilities.compressImage(context, imageUri, dest);
					mImageCaptureUri = Uri.fromFile(new File(dest));
					beginCrop(mImageCaptureUri);

					break;
				}
				case REQUEST_FROM_GALLERY: {
					Uri selectedUri = data_intent.getData();
					String imageUri = GalleryFilePath.getPath(context, selectedUri);
					String extension = imageUri.substring(imageUri.lastIndexOf("."));
					String fileName = new SimpleDateFormat("yyyyMMddhhmmss'"+extension+"'").format(new Date());
					String dest = CommonUtilities.getOutputPath(context, "images")+File.separator+fileName;

					//Log.e("ANALISA_FILE", dest);
					CommonUtilities.compressImage(context, imageUri, dest);
					mImageCaptureUri = Uri.fromFile(new File(dest));
					beginCrop(mImageCaptureUri);

					break;
				}
				case REQUEST_FROM_FILTER: {
					loadDataProduk(true);
					break;
				}
				case RESULT_FROM_SIGN_IN: {
					data = CommonUtilities.getSettingUser(context);
					//Toast.makeText(context, data.getLast_name(), Toast.LENGTH_LONG).show();
					menu_selected = data_intent.getIntExtra("menu_selected", 0);

					boolean is_login = data_intent.getBooleanExtra("is_login", false);
					if (is_login) {
						setSignIn();
					}

					boolean from_checkout = data_intent.getBooleanExtra("from_checkout", false);
					if (from_checkout) {
						//new prosesCekOrder().execute();
					}

					checkGcmRegid();

					break;
				}
				case RESULT_FROM_SIGN_UP: {
					String go_to_login = data_intent.getStringExtra("go_to");
					if (go_to_login != null && go_to_login.equalsIgnoreCase("login")) {
						openPageLogin();
					} else {
						Intent intent = new Intent(context, AktivasiSmsActivity.class);
						startActivityForResult(intent, RESULT_FROM_AKTIVASI);
					}

					break;
				}
				case RESULT_FROM_AKTIVASI: {
					Intent intent_ = new Intent(context, LoginActivity.class);
					startActivityForResult(intent_, RESULT_FROM_SIGN_IN);

					break;
				}
				case RESULT_FROM_PROSES_CHECKOUT: {
					updateTotalCartlist();
					displayView(0);

					break;
				}

				case RESULT_FROM_EDIT_ALAMAT: {
					alamat select_alamat = (alamat) data_intent.getSerializableExtra("alamat");

					if (select_alamat != null) {
						for (alamat data_alamat : alamatlist) {
							if (data_alamat.getId() == select_alamat.getId()) {
								if (select_alamat.getAsDefaultAlamat()) {
									for (int i = 0; i < alamatlist.size(); i++) {
										alamatlist.get(i).setAs_default(false);
									}
								}

								alamatlist.set(alamatlist.indexOf(data_alamat), select_alamat);
								showListAlamat();

								return;
							}
						}

						if (select_alamat.getAsDefaultAlamat()) {
							for (int i = 0; i < alamatlist.size(); i++) {
								alamatlist.get(i).setAs_default(false);
							}
						}

						alamatlist.add(select_alamat);
						showListAlamat();
					}

					break;
				}

				case RESULT_FROM_DETAIL_ORDER: {
					int index = DaftarPesananFragment.viewPager.getCurrentItem();
					if(index>0) {
						loadOrderlist(index-1, true);
					}
					loadOrderlist(index, true);
					if(index<3) {
						loadOrderlist(index+1, true);
					}

					break;
				}
			}
		}
	}

	public void prosesHapusCartlist(int index) {
		cartlist.get(index).setChecked(true);
		hapusCartlistTerpilih();
	}

	public void updateTotalCartlist() {
		total_cart = dh.getTotalCart();
		moremenulist.get(1).setTotalcount(total_cart);
		moremenuAdapter.UpdateMoreMenuAdapter(moremenulist, submoremenulist);
	}

	public void updateTotalWishlist() {
		total_wishlist = dh.getTotalWishlist();

		//moremenulist.get(4).setTotalcount(total_wishlist);
		//moremenuAdapter.UpdateMoreMenuAdapter(moremenulist, submoremenulist);
	}

	public void updateSummaryCart() {
		double total_qty = 0;
		double jumlah = 0;

		for(produk data: cartlist) {
			total_qty+=data.getQty();
			jumlah+=data.getGrandtotal();
		}

		KeranjangFragment.edit_qty.setText(CommonUtilities.getNumberFormat(total_qty));
		KeranjangFragment.edit_jumlah.setText(CommonUtilities.getCurrencyFormat(jumlah, "Rp. "));
	}

	public void updateTotalWishlist(produk data_produk) {
		total_wishlist = dh.getTotalWishlist();
		moremenulist.get(4).setTotalcount(total_wishlist);
		moremenuAdapter.UpdateMoreMenuAdapter(moremenulist, submoremenulist);

		/*if(menu_selected==0) {
			for(produk terbaru: listProdukTerbaru) {
				if(terbaru.getId()==data_produk.getId()) {
					listProdukTerbaru.get(listProdukTerbaru.indexOf(terbaru)).setWishlist(dh.getIdWishlist(terbaru.getId())>0);
					//produkTerbaruAdapter.notifyDataSetChanged();
					break;
				}
			}

			for(produk promo: listProdukPromo) {
				if(promo.getId()==data_produk.getId()) {
					listProdukPromo.get(listProdukPromo.indexOf(promo)).setWishlist(dh.getIdWishlist(promo.getId())>0);
					//produkPromoAdapter.notifyDataSetChanged();
					break;
				}
			}
		}*/

		if(menu_selected==2) {
			for(produk prod: produklist) {
				if(prod.getId()==data_produk.getId()) {
					produklist.get(produklist.indexOf(prod)).setWishlist(dh.getIdWishlist(prod.getId())>0);
					//produklistAdapter.notifyDataSetChanged();
					break;
				}
			}

			for(produk prod: produklist) {
				if(prod.getId()==data_produk.getId()) {
					produklist.get(produklist.indexOf(prod)).setWishlist(dh.getIdWishlist(prod.getId())>0);
					//produklistAdapter.notifyDataSetChanged();
					break;
				}
			}
		}
	}

	public void loadDataDashboard() {
		new loadDataDashboard().execute();
	}

	public class loadDataDashboard extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {

			tampilkan_produk_induk = false;
			tampilkan_shortcut     = false;
			tampilkan_mitra        = false;
			tampilkan_produk_home  = false;

			dashboard_produk_induk = new ArrayList<>();
			dashboard_banner       = new ArrayList<>();
			dashboard_shortcut     = new ArrayList<>();
			dashboard_mitra        = new ArrayList<>();
			dashboard_produk_home  = new ArrayList<>();

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseDashboard> dashboardListCall = api.postDashboardList(data.getId()+"");
			dashboardListCall.enqueue(new Callback<ResponseDashboard>() {
				@Override
				public void onResponse(@NonNull Call<ResponseDashboard> call, @NonNull Response<ResponseDashboard> response) {
					try {

						tampilkan_produk_induk = Objects.requireNonNull(response.body()).getTampilkan_produk_induk();
						tampilkan_shortcut     = Objects.requireNonNull(response.body()).getTampilkan_shortcut();
						tampilkan_mitra        = Objects.requireNonNull(response.body()).getTampilkan_mitra();
						tampilkan_produk_home  = Objects.requireNonNull(response.body()).getTampilkan_produk_home();

						dashboard_banner = Objects.requireNonNull(response.body()).getBannerlist();
						dashboard_shortcut = Objects.requireNonNull(response.body()).getShortcutlist();
						dashboard_mitra = Objects.requireNonNull(response.body()).getMitralist();
						dashboard_produk_home = Objects.requireNonNull(response.body()).getProdukhomelist();

						dashboard_produk_induk.add(new produkgrup(0,"HOME", new ArrayList<produk>(), 0));
						if(tampilkan_produk_induk) {
							for(produkgrup temp: Objects.requireNonNull(response.body()).getProdukinduklist()) {
								dashboard_produk_induk.add(temp);
							}
						}

						Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_DASHBOARD");
						i.putExtra("success", true);
						sendBroadcast(i);

					} catch (Exception e) {
						Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_DASHBOARD");
						i.putExtra("success", false);
						sendBroadcast(i);
					}
				}

				@Override
				public void onFailure(@NonNull Call<ResponseDashboard> call, @NonNull Throwable t) {
					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_DASHBOARD");
					i.putExtra("success", false);
					sendBroadcast(i);
				}
			});

			return null;
		}
	}

	public void loadDataInformasi(boolean starting) {
		if(starting) {
			next_page_informasi = 1;
			informasilist = new ArrayList<>();
			informasiAdapter = new InformasiAdapter(context, informasilist);
			InformasiFragment.listview.setAdapter(informasiAdapter);
		}
		new loadDataInformasi().execute();
	}

	public class loadDataInformasi extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			InformasiFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			ArrayList<informasi> result = null;
			String url = CommonUtilities.SERVER_URL + "/store/androidInformasiDataStore.php";

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("page", next_page_informasi+""));

			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {
					result = new ArrayList<>();
					next_page_informasi = json.isNull("next_page") ? next_page_informasi : json.getInt("next_page");
					JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

						int id = rec.isNull("id")?0:rec.getInt("id");
						String tanggal = rec.isNull("tanggal")?"":rec.getString("tanggal");
						String judul = rec.isNull("judul")?"":rec.getString("judul");
						String header = rec.isNull("header")?"":rec.getString("header");
						String konten = rec.isNull("konten")?"":rec.getString("konten");
						String gambar = rec.isNull("gambar")?"":rec.getString("gambar");

						result.add(new informasi(id, tanggal, judul, header, konten, gambar));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Boolean success = result!=null;
			if(result==null) result = new ArrayList<>();
			ArrayList<informasi_list> temp = new ArrayList<>();
			temp.add(new informasi_list(result));

			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_INFORMASI");
			i.putExtra("data_informasi_list", temp);
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void prosesLoadDetailMenu() {
		new loadDetailMenu().execute();
	}

	public class loadDetailMenu extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			MoreFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("id_menu", moremenu_select.getId()+""));

			String detail = "";
			String url = CommonUtilities.SERVER_URL + "/store/androidDetailMenuStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {
					detail = json.isNull("detail")?"":json.getString("detail");

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_DETAIL_MENU");
			i.putExtra("detail", detail);
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void pilihSemuaChartlist(boolean isChecked) {
		for (int i=0; i<cartlist.size(); i++) {
			cartlist.get(i).setChecked(isChecked);
		}
		cartlistAdapter.UpdateCartlistAdapter();
	}

	public void hapusCartlistTerpilih() {
		action = "delete_cartlist";
		konfirmasi_dialog_title.setText("Hapus Barang?");
		konfirmasi_dialog_text.setText("Barang yang dipilih akan hilang dari keranjang.");
		konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		konfirmasi_dialog.show();
	}


	public void prosesHapusCartlistTerpilih() {

		String items = "";
		for(int i=cartlist.size()-1; i>=0; i--) {
			if(cartlist.get(i).getChecked()) {
                items+=(items.length()>0?"\n":"")+cartlist.get(i).getId()+"\t"+cartlist.get(i).getUkuran()+"\t"+cartlist.get(i).getWarna()+"\t"+cartlist.get(i).getQty();

                //dh.deleteCartlist(cartlist.get(i));
				//cartlist.remove(cartlist.get(i));
			}
		}

		//cartlistAdapter.UpdateCartlistAdapter();
		//updateTotalCartlist();
		//updateSummaryCart();

        if(items.length()>0) {
		    new deleteCartList(items).execute();
        }
	}


	public class deleteCartList extends AsyncTask<String, Void, Void> {

		String items;

		deleteCartList(String items) {
			this.items = items;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Tidak bisa kontak ke server.";

			String url = CommonUtilities.SERVER_URL + "/store/androidDeleteProductFromCart.php";

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("kode_trx", CommonUtilities.getKodeTransaksi(context)));
			params.add(new BasicNameValuePair("items", items));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if(json!=null) {
				try {
					success = !json.isNull("success") && json.getBoolean("success");
					message = json.isNull("message")?message:json.getString("message");

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.REMOVE_FROM_CART");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadWishlist() {
		new loadWishlist().execute();
	}

	public class loadWishlist extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			WishlistFragment.listview.setVisibility(View.GONE);
			WishlistFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			wishlist = new ArrayList<>();
			ArrayList<produk> temp_cartlist = dh.getWishlist();
			boolean success = false;

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("cart", dh.getStringWishlist()));

			String url = CommonUtilities.SERVER_URL + "/store/androidCartDataStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			if(json!=null) {
				try {

					JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics.length(); i++) {
						JSONObject rec = topics.getJSONObject(i);

                        int id = rec.isNull("id")?0:rec.getInt("id");
                        String ukuran = rec.isNull("ukuran")?"":rec.getString("ukuran");
                        String warna = rec.isNull("warna")?"":rec.getString("warna");
                        int jumlah = rec.isNull("jumlah")?0:rec.getInt("jumlah");
						int berat = rec.isNull("berat")?0:rec.getInt("berat");
						double harga_beli = rec.isNull("harga_beli")?0:rec.getDouble("harga_beli");
						double harga_jual = rec.isNull("harga_jual")?0:rec.getDouble("harga_jual");
						double harga_diskon = rec.isNull("harga_diskon")?0:rec.getDouble("harga_diskon");
						int persen_diskon = rec.isNull("persen_diskon")?0:rec.getInt("persen_diskon");
						double subtotal = rec.isNull("subtotal")?0:rec.getDouble("subtotal");
						double grandtotal = rec.isNull("grandtotal")?0:rec.getDouble("grandtotal");

						for(produk data: temp_cartlist) {
                            if(data.getId()==id && data.getUkuran().equalsIgnoreCase(ukuran) && data.getWarna().equalsIgnoreCase(warna)) {
								data.setQty(jumlah);
								data.setBerat(berat);
								data.setHarga_beli(harga_beli);
								data.setHarga_jual(harga_jual);
								data.setHarga_diskon(harga_diskon);
								data.setPersen_diskon(persen_diskon);
								data.setSubtotal(subtotal);
								data.setGrandtotal(grandtotal);

								temp_cartlist.remove(data);
								wishlist.add(data);
								break;
							}
						}
					}

					dh.deleteWishlist();
					dh.insertWishlists(wishlist);

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_WISH");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadAlamatlist() {
		new loadAlamatlist().execute();
	}

	public class loadAlamatlist extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			AlamatFragment.loading.setVisibility(View.VISIBLE);
			AlamatFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			alamatlist = new ArrayList<>();
			alamatlist_display = new ArrayList<>();

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseAlamat> alamatListCall = api.postAlamatList(data.getId()+"", sort_by_alamat+"");
			alamatListCall.enqueue(new Callback<ResponseAlamat>() {
				@Override
				public void onResponse(@NonNull Call<ResponseAlamat> call, @NonNull Response<ResponseAlamat> response) {

					alamatlist   = Objects.requireNonNull(response.body()).getAlamatlist();
					alamatlist_display  = Objects.requireNonNull(response.body()).getAlamatlist();

					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_ALAMAT");
					i.putExtra("success", true);
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseAlamat> call, @NonNull Throwable t) {
					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_ALAMAT");
					i.putExtra("success", false);
					sendBroadcast(i);
				}
			});

			return null;
		}
	}

	public void addNewAlamat() {
		Intent intent = new Intent(context, EditAlamatActivity.class);
		startActivityForResult(intent, RESULT_FROM_EDIT_ALAMAT);
	}

	public void editSelectedAlamat(alamat data_alamat) {
		Intent intent = new Intent(context, EditAlamatActivity.class);
		intent.putExtra("alamat", data_alamat);
		startActivityForResult(intent, RESULT_FROM_EDIT_ALAMAT);
	}

	public void utamakanSelectedAlamat(alamat data_alamat) {
		alamatSelected = data_alamat;
		new prosesUtamakanAlamat().execute();
	}

	public void deleteSelectedAlamat(alamat data_alamat) {
		alamatSelected = data_alamat;

		action = "delete_alamat";
		konfirmasi_dialog_title.setText("Hapus Alamat?");
		konfirmasi_dialog_text.setText("Alamat akan hilang dari daftar alamat.");
		konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		konfirmasi_dialog.show();
	}
	
	public void deleteSelectedWistlist(produk data_produk) {
		wistlistSelected = data_produk;

		action = "delete_wishlist";
		konfirmasi_dialog_title.setText("Hapus Kesukaan?");
		konfirmasi_dialog_text.setText("Produk akan hilang dari daftar kesukaan.");
		konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		konfirmasi_dialog.show();
	}


	@Override
	public void onSliderClick(BaseSliderView slider) {

	}

	@Override
	public void onBackPressed() {
		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {

		DrawerLayout drawer = findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	public void loadOrderlist(int index, boolean is_first) {
		if(is_first) {
			orderlist_page[index] = 1;
			orderlist[index] = new ArrayList<>();
			orderlist_adapter[index] = new ListOrderAdapter(context, orderlist[index]);
			switch (index) {
				case 0:
					DaftarPesananMenungguFragment.listViewOrder.setAdapter(orderlist_adapter[index]);
					break;
				case 1:
					DaftarPesananProsesFragment.listViewOrder.setAdapter(orderlist_adapter[index]);
					break;
				case 2:
					DaftarPesananSelesaiFragment.listViewOrder.setAdapter(orderlist_adapter[index]);
					break;
				case 3:
					DaftarPesananBatalFragment.listViewOrder.setAdapter(orderlist_adapter[index]);
					break;
			}
		}
		new loadOrderlist(index).execute();
	}

	public class loadOrderlist extends AsyncTask<String, Void, Void> {

		int index = 0;
		loadOrderlist(int index) {
			this.index = index;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			switch (index) {
				case 0: {
					DaftarPesananMenungguFragment.loading.setVisibility(View.VISIBLE);
					DaftarPesananMenungguFragment.retry.setVisibility(View.GONE);
					break;
				}
				case 1: {
					DaftarPesananProsesFragment.loading.setVisibility(View.VISIBLE);
					DaftarPesananProsesFragment.retry.setVisibility(View.GONE);
					break;
				}
				case 2: {
					DaftarPesananSelesaiFragment.loading.setVisibility(View.VISIBLE);
					DaftarPesananSelesaiFragment.retry.setVisibility(View.GONE);
					break;
				}
				case 3: {
					DaftarPesananBatalFragment.loading.setVisibility(View.VISIBLE);
					DaftarPesananBatalFragment.retry.setVisibility(View.GONE);
					break;
				}
			}
		}

		@Override
		protected Void doInBackground(String... urls) {

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseOrder> orderListCall = api.postOrderList(data.getId()+"", orderlist_page[index]+"", index+"", data.getTipe()+"");
			orderListCall.enqueue(new Callback<ResponseOrder>() {
				@Override
				public void onResponse(@NonNull Call<ResponseOrder> call, @NonNull Response<ResponseOrder> response) {

					orderlist_page[index] = Objects.requireNonNull(response.body()).getNextpage();
					ArrayList<order> result = Objects.requireNonNull(response.body()).getOrder();

					boolean success = result!=null;
					if(result==null) result = new ArrayList<>();
					ArrayList<order_list> temp = new ArrayList<>();
					temp.add(new order_list(result));

					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_ORDER");
					i.putExtra("index", index);
					i.putExtra("order_list", temp);
					i.putExtra("success", success);
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseOrder> call, @NonNull Throwable t) {

					ArrayList<order>  result = new ArrayList<>();
					ArrayList<order_list> temp = new ArrayList<>();
					temp.add(new order_list(result));

					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_ORDER");
					i.putExtra("index", index);
					i.putExtra("order_list", temp);
					i.putExtra("success", false);
					sendBroadcast(i);
				}
			});

			return null;
		}
	}

	public void loadDataOngkir() {
		new loadDataOngkir().execute();
	}

	public class loadDataOngkir extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			openDialogLoading();
			OngkosKirimFragment.retry.setVisibility(View.GONE);
			OngkosKirimFragment.linear_ongkir.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			ongkirlist = new ArrayList<>();
			String url = CommonUtilities.SERVER_URL + "/store/androidAllLayananDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("city_id", city_id+""));
			params.add(new BasicNameValuePair("subdistrict_id", subdistrict_id+""));
			params.add(new BasicNameValuePair("berat", (berat_barang*1000)+""));
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);
			boolean success = false;
			if(json!=null) {
				try {
					JSONArray topics_induk = json.isNull("topics")?null:json.getJSONArray("topics");
					for (int i=0; i<topics_induk.length(); i++) {
						JSONObject rec = topics_induk.getJSONObject(i);

						int id_kurir = rec.isNull("id_kurir")?0:rec.getInt("id_kurir");
						String kode_kurir = rec.isNull("kode_kurir")?null:rec.getString("kode_kurir");
						String nama_kurir = rec.isNull("nama_kurir")?null:rec.getString("nama_kurir");
						String kode_service = rec.isNull("kode_service")?null:rec.getString("kode_service");
						String nama_service = rec.isNull("nama_service")?null:rec.getString("nama_service");
						int nominal = rec.isNull("nominal")?0:rec.getInt("nominal");
						String tarif = rec.isNull("tarif")?null:rec.getString("tarif");
						String etd = rec.isNull("etd")?null:rec.getString("etd");
						String gambar = rec.isNull("gambar_kurir")?null:rec.getString("gambar_kurir");
						ongkirlist.add(new ongkir(id_kurir, kode_kurir, nama_kurir, kode_service, nama_service, nominal, etd, tarif, gambar));
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_ONGKIR");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}
	
	public void loadDataKategori() {
		new loadDataKategori().execute();
	}

	public class loadDataKategori extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			KategoriFragment.loading.setVisibility(View.VISIBLE);
			KategoriFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			kategorialllist = new ArrayList<>();

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseKategori> dashboardListCall = api.postKategori(data.getId()+"");
			dashboardListCall.enqueue(new Callback<ResponseKategori>() {
				@Override
				public void onResponse(@NonNull Call<ResponseKategori> call, @NonNull Response<ResponseKategori> response) {

					kategorialllist = Objects.requireNonNull(response.body()).getKategorilist();
					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_KATEGORI");
					i.putExtra("success", true);
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseKategori> call, @NonNull Throwable t) {
					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_KATEGORI");
					i.putExtra("success", false);
					sendBroadcast(i);
				}
			});

			return null;
		}
	}

	public void loadMitraSelected() {
		ProdukFragment.nama_mitra.setText(mitra_selected.getNama());
		ProdukFragment.alamat_mitra.setText(mitra_selected.getAlamat());
		loadMitraLogo(mitra_selected.getPhoto());
	}

	public void loadMitraLogo(String gambar) {
		String server = CommonUtilities.SERVER_URL;
		String url = server+"/uploads/member/"+gambar;
		imageLoader.displayImage(url, ProdukFragment.image_mitra, imageOptionsUser);
	}

	public void loadDataProduk(boolean firstime) {
		if(firstime) {
			page_produk = 1;
			produklist = new ArrayList<>();
		}

		new loadDataProduk().execute();
	}

	public class loadDataProduk extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseProduk> produkListCall = api.postProdukList(
					data.getId()+"",
					mitra_selected.getId()+"",
					page_produk+"",
					""
			);
			produkListCall.enqueue(new Callback<ResponseProduk>() {
				@Override
				public void onResponse(@NonNull Call<ResponseProduk> call, @NonNull Response<ResponseProduk> response) {

					try {
						page_produk = Objects.requireNonNull(response.body()).getNextpage();
						produklist = Objects.requireNonNull(response.body()).getProduk();
						produkadapter = new ListProdukAdapter(context, produklist);

						Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_PRODUK");
						i.putExtra("success", true);
						sendBroadcast(i);

					} catch (Exception e) {
						e.printStackTrace();
						Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_PRODUK");
						i.putExtra("success", false);
						sendBroadcast(i);
					}
				}

				@Override
				public void onFailure(@NonNull Call<ResponseProduk> call, @NonNull Throwable t) {
					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_PRODUK");
					i.putExtra("success", false);
					sendBroadcast(i);
				}
			});

			return null;
		}

	}

	public void lacak_pengiriman(String kurir, String no_resi) {
		Intent i = new Intent(context, DetailPengirimanActivity.class);
		i.putExtra("kurir", kurir);
		i.putExtra("no_resi", no_resi);
		startActivity(i);
	}

	public void updateWishlistGrid(int index, boolean is_value, produk data) {
		if(menu_selected==0) {
			updateTotalWishlist(data);
		} else {
			if(index<produklist.size()) {
				produklist.get(index).setWishlist(is_value);
				//produklistAdapter.UpdateGridProdukAdapter(produklist);
				updateTotalWishlist(produklist.get(index));
			}
		}
	}

	public void updateWishlistList(int index, boolean is_value) {
		if(index<produklist.size()) {
			produklist.get(index).setWishlist(is_value);
			//produklistAdapter.UpdateListProdukAdapter(produklist);
			updateTotalWishlist(produklist.get(index));
		}
	}
	
	public void openDetailProdukToko(produk data_produk, int kode_grup) {

		Intent intent = new Intent(context, DetailProdukTokoActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("kode_grup", kode_grup);
		intent.putExtra("produk", data_produk);
		intent.putExtra("damayUserLogin", data);

		startActivityForResult(intent, RESULT_FROM_PRODUK_DETAIL);
	}

	public void cekOrder() {
		cartlist_proses = new ArrayList<>();
		for(produk item:produklist) {
			if(item.getQty()>0) {
				cartlist_proses.add(item);
			}
		}

		if(cartlist_proses.size()==0) {
			openDialogMessage("Tidak ada produk yang dipesan.", false);
		} else {
			new getDefaultAlamat().execute();
		}
	}

	public class getDefaultAlamat extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseDefaultAlamat> defaultAlamatCall = api.postDefaultAlamat(data.getId()+"");
			defaultAlamatCall.enqueue(new Callback<ResponseDefaultAlamat>() {
				@Override
				public void onResponse(@NonNull Call<ResponseDefaultAlamat> call, @NonNull Response<ResponseDefaultAlamat> response) {

					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_DEF_ALAMAT");
					i.putExtra("success", true);
					i.putExtra("data_alamat", Objects.requireNonNull(response.body()).getDef_alamat());
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseDefaultAlamat> call, @NonNull Throwable t) {

					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_DEF_ALAMAT");
					i.putExtra("success", false);
					i.putExtra("message", "Error koneksi server.");
					sendBroadcast(i);
				}
			});

			return null;
		}
	}

	public void loadDataEditProfile() {
		EditProfileFragment.edit_nama.setText(data.getFirst_name()+" "+data.getLast_name());
		EditProfileFragment.edit_phone.setText(data.getPhone());
		EditProfileFragment.edit_email.setText(data.getEmail());
		EditProfileFragment.edit_username.setText(data.getUsername());
	}

	public void loadDataProfile() {

		mImageCaptureUri = null;
		ProfileFragment.name.setText(data.getFirst_name() + " " + data.getLast_name());
		imageLoader.displayImage(CommonUtilities.SERVER_URL+"/uploads/member/"+data.getPhoto(), ProfileFragment.image_profile, imageOptionsUser);
	}

	public void loadDataJenisUser() {
		JenisUserFragment.text_jenis_user.setText(data.getJenis_user());
	}
	public void loadDataSaldoUser() {
		SaldoUserFragment.text_saldo_user.setText(CommonUtilities.getCurrencyFormat(data.getSaldo(), "Rp. "));
	}

	public void loadDataSetting() {
		setting data_setting = CommonUtilities.getSettingApplikasi(context);
		SettingFragment.edit_notifikasi.setText(data_setting.getSet_notifikasi());
		SettingFragment.checkboxdefault_update_pesanan.setChecked(data_setting.getUpdate_pesanan());
		SettingFragment.checkboxdefault_informasi.setChecked(data_setting.getInformasi());
		SettingFragment.checkboxdefault_notifikasi.setChecked(data_setting.getNotifikasi());
		SettingFragment.checkboxdefault_chat.setChecked(data_setting.getChat());

		setting_notifikasi = data_setting.getSet_notifikasi();
	}

	public void simpanDataProfile() {
		new simpanDataProfile().execute();
	}

	public class simpanDataProfile extends AsyncTask<String, Void, Void> {
		boolean success;
		String message;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {
			success = false;
			message = "Proses edit profil gagal. Coba lagi.";

			String nama_lengkap = EditProfileFragment.edit_nama.getText().toString().trim();
			String[] temps = nama_lengkap.split(" ");
			String firstname = temps[0];
			String lastname  = "";
			for(int i=1; i<temps.length; i++) {
				lastname+=temps[i]+" ";
			}
			lastname = lastname.trim();

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseEditProfil> editProfilCall = api.postEditProfil(
					data.getId()+"",
					firstname,
					lastname,
					EditProfileFragment.edit_phone.getText().toString(),
					EditProfileFragment.edit_email.getText().toString(),
					EditProfileFragment.edit_username.getText().toString()
			);
			editProfilCall.enqueue(new Callback<ResponseEditProfil>() {
				@Override
				public void onResponse(@NonNull Call<ResponseEditProfil> call, @NonNull Response<ResponseEditProfil> response) {

					success = response.body().getSuccess();
					message = response.body().getMessage();
					if(success) {
						data =  response.body().getUser_login();
						CommonUtilities.setSettingUser(context, data);
					}

					Intent i = new Intent("kamoncust.application.com.kamoncust.EDIT_DATA_PROFILE");
					i.putExtra("success", success);
					i.putExtra("message", message);
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseEditProfil> call, @NonNull Throwable t) {
					t.printStackTrace();

					Intent i = new Intent("kamoncust.application.com.kamoncust.EDIT_DATA_PROFILE");
					i.putExtra("success", success);
					i.putExtra("message", message);
					sendBroadcast(i);

				}
			});

			return null;
		}

	}


	public void simpanDataPassword() {
		new simpanDataPassword().execute();
	}

	public class simpanDataPassword extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {

			JSONParser token_json = new JSONParser();
			JSONObject token = token_json.getJSONFromUrl(CommonUtilities.SERVER_URL + "/store/token.php", null, null);
			String cookies = token_json.getCookies();

			String security_code = "";
			try {
				security_code = token.isNull("security_code") ? "" : token.getString("security_code");
				MCrypt mCrypt = new MCrypt();
				security_code = new String(mCrypt.decrypt(security_code));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			boolean success = false;
			String message = "Proses ganti password gagal silahkan coba lagi.";
			if (security_code.length() > 0) {

				String url = CommonUtilities.SERVER_URL + "/store/androidSaveGantiPassword.php";
				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("id_user", data.getId() + ""));
				params.add(new BasicNameValuePair("security_code", security_code));

				params.add(new BasicNameValuePair("password_lama", GantiPasswordFragment.edit_old_password.getText().toString()));
				params.add(new BasicNameValuePair("password_baru", GantiPasswordFragment.edit_password.getText().toString()));
				params.add(new BasicNameValuePair("password_konf", GantiPasswordFragment.edit_konfirmasi.getText().toString()));

				JSONObject json = new JSONParser().getJSONFromUrl(url, params, cookies);
				if (json != null) {
					try {
						success = !json.isNull("success") && json.getBoolean("success");
						message = json.isNull("message") ? "" : json.getString("message");
						if (success) {

							data.setUsername(json.isNull("username") ? "" : json.getString("username"));
							data.setFirst_name(json.isNull("first_name") ? "" : json.getString("first_name"));
							data.setLast_name(json.isNull("last_name") ? "" : json.getString("last_name"));
							data.setEmail(json.isNull("email") ? "" : json.getString("email"));
							data.setPhone(json.isNull("phone") ? "" : json.getString("phone"));
							data.setSaldo(json.isNull("saldo") ? 0 : json.getDouble("saldo"));

							CommonUtilities.setSettingUser(context, data);

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.EDIT_DATA_PROFILE");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}
	}

	public class savePhotoProfil extends AsyncTask<String, Void, Void> {
		boolean success;
		String message;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading();
		}

		@Override
		protected Void doInBackground(String... urls) {
			success = false;
			message = "Proses ganti photo gagal. Coba lagi.";

			MultipartBody.Part body = null;
			if(mImageCaptureUri!=null) {
				File file = new File(mImageCaptureUri.getPath());
				// create RequestBody instance from file
				RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

				// MultipartBody.Part is used to send also the actual file name
				body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
			}

			RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), data.getId()+"");
			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseGantiPhoto> gantiPhotoCall = api.postGantiPhoto(userId, body);
			gantiPhotoCall.enqueue(new Callback<ResponseGantiPhoto>() {
				@Override
				public void onResponse(@NonNull Call<ResponseGantiPhoto> call, @NonNull Response<ResponseGantiPhoto> response) {

					success = response.body().getSuccess();
					message = response.body().getMessage();
					if(success) {
						data.setPhoto(response.body().getPhoto());
						CommonUtilities.setSettingUser(context, data);
					}

					mImageCaptureUri = null;
					Intent i = new Intent("kamoncust.application.com.kamoncust.EDIT_DATA_PROFILE");
					i.putExtra("success", success);
					i.putExtra("message", message);
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseGantiPhoto> call, @NonNull Throwable t) {
					t.printStackTrace();
					mImageCaptureUri = null;
					Intent i = new Intent("kamoncust.application.com.kamoncust.EDIT_DATA_PROFILE");
					i.putExtra("success", success);
					i.putExtra("message", message);
					sendBroadcast(i);

				}
			});

			return null;
		}

	}

	public void updateSetting() {
		setting data_setting = new setting(
			SettingFragment.edit_notifikasi.getText().toString(),
			SettingFragment.checkboxdefault_update_pesanan.isChecked(),
			SettingFragment.checkboxdefault_informasi.isChecked(),
			SettingFragment.checkboxdefault_notifikasi.isChecked(),
			SettingFragment.checkboxdefault_chat.isChecked()
		);

		CommonUtilities.setSettingAplikasi(context, data_setting);
		Toast.makeText(context, "Update setting berhasil.", Toast.LENGTH_LONG).show();
		displayView(11);
	}

	public void loadDataNotifikasi(boolean starting) {
		if(starting) {
			next_page_notifikasi = 1;
			list_notifikasi = new ArrayList<>();
			notifikasi_adapter = new NotifikasiAdapter(context, list_notifikasi);
			NotifikasiFragment.listview.setAdapter(notifikasi_adapter);
		}
		new loadDataNotifikasi().execute();
	}

	public class loadDataNotifikasi extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			NotifikasiFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			ArrayList<notifikasi> result = dh.getListNotifikasi(next_page_notifikasi);
			next_page_notifikasi += result.size() > 0 ? 1 : 0;

			ArrayList<notifikasi_list> temp = new ArrayList<>();
			temp.add(new notifikasi_list(result));
			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_NOTIFIKASI");
			i.putExtra("notifikasi_list", temp);
			i.putExtra("success", true);
			sendBroadcast(i);

			return null;
		}
	}

	public void openDialogLoading() {
		dialog_loading.setCancelable(false);
		dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_loading.show();
	}

	public void openDialogLoadingEkspedisi() {
		dialog_loading.setCancelable(true);
		dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_loading.show();
	}


	public void openFilterProduk() {
		Intent intent = new Intent(context, FilterProdukActivity.class);
		startActivityForResult(intent, REQUEST_FROM_FILTER);
	}

	public void loadDialogListView(String act) {
		action = act;
		if((action.equalsIgnoreCase("province") || action.equalsIgnoreCase("profile_province")) && listProvince.size()==0) {
			openDialogLoadingEkspedisi();
		} else if((action.equalsIgnoreCase("city") || action.equalsIgnoreCase("profile_city")) && listCity.size()==0) {
			openDialogLoadingEkspedisi();
		} else if((action.equalsIgnoreCase("subdistrict") || action.equalsIgnoreCase("profile_subdistrict")) && listSubDistrict.size()==0) {
			openDialogLoadingEkspedisi();
		} else {
			loadListArray();
			dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog_listview.show();
		}
	}

	private void loadListArray() {
		String[] from = new String[] { getResources().getString(R.string.list_dialog_title) };
		int[] to = new int[] { R.id.txt_title };

		List<HashMap<String, String>> fillMaps = new ArrayList<>();
		if(action.equalsIgnoreCase("province") || action.equalsIgnoreCase("profile_province")) {
			for (province data : listProvince) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getProvince());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("city") || action.equalsIgnoreCase("profile_city")) {
			for (city data : listCity) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getCity_name());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("subdistrict") || action.equalsIgnoreCase("profile_subdistrict")) {
			for (subdistrict data : listSubDistrict) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getSubdistrict_name());

				fillMaps.add(map);
			}
		}

		SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
		listview.setAdapter(adapter);
	}

	public void loadDataProvince() {
		listCity = new ArrayList<>();
		listSubDistrict = new ArrayList<>();
		if(listProvince.size()==0) {
			new loadProvince().execute();
		}
	}

	public class loadProvince extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {
			listProvince = new ArrayList<>();
			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseProvince> provinceCall = api.postProvinceList("");
			provinceCall.enqueue(new Callback<ResponseProvince>() {
				@Override
				public void onResponse(@NonNull Call<ResponseProvince> call, @NonNull Response<ResponseProvince> response) {

					listProvince = Objects.requireNonNull(response.body()).getTopics();
					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST");
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseProvince> call, @NonNull Throwable t) {
					t.printStackTrace();
					Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST");
					sendBroadcast(i);
				}
			});

			return null;
		}
	}

	public class loadCity extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

		}

		@Override
		protected Void doInBackground(String... urls) {
			listCity = new ArrayList<>();
			if(province_id>0) {
				RestApi api = RetroFit.getInstanceRetrofit();
				Call<ResponseCity> cityCall = api.postCityList(province_id+"");
				cityCall.enqueue(new Callback<ResponseCity>() {
					@Override
					public void onResponse(@NonNull Call<ResponseCity> call, @NonNull Response<ResponseCity> response) {

						listCity = Objects.requireNonNull(response.body()).getTopics();
						Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST");
						sendBroadcast(i);
					}

					@Override
					public void onFailure(@NonNull Call<ResponseCity> call, @NonNull Throwable t) {
						t.printStackTrace();
						Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST");
						sendBroadcast(i);
					}
				});
			} else {
				Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST");
				sendBroadcast(i);
			}

			return null;
		}
	}

	public class loadSubdistrict extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {
			listSubDistrict = new ArrayList<>();
			if(city_id>0) {
				RestApi api = RetroFit.getInstanceRetrofit();
				Call<ResponseSubdistrict> subdistrictCall = api.postSubdistrictList(city_id+"");
				subdistrictCall.enqueue(new Callback<ResponseSubdistrict>() {
					@Override
					public void onResponse(@NonNull Call<ResponseSubdistrict> call, @NonNull Response<ResponseSubdistrict> response) {

						listSubDistrict = Objects.requireNonNull(response.body()).getTopics();
						Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST");
						sendBroadcast(i);
					}

					@Override
					public void onFailure(@NonNull Call<ResponseSubdistrict> call, @NonNull Throwable t) {
						t.printStackTrace();
						Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST");
						sendBroadcast(i);
					}
				});

			} else {
				Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST");
				sendBroadcast(i);
			}

			return null;
		}
	}

	private void setSignIn() {
		if(data.getId()>0) {
			imageLoader.displayImage(CommonUtilities.SERVER_URL+"/uploads/member/"+data.getPhoto(), avatar, imageOptionsUser);
		} else {
			avatar.setImageResource(R.drawable.userdefault);
		}

		name_avatar.setText(data.getFirst_name()+" "+data.getLast_name());
		nav_login.setText(data.getId()==0?"Masuk":"Keluar");
		image_menu_login.setImageResource(data.getId()==0?R.drawable.menu_login:R.drawable.menu_logout);

		nav_register.setText(data.getId()==0?"Daftar":"Profil");
		displayView(menu_selected);
	}

	private void insertDummyContactWrapper() {
		List<String> permissionsNeeded = new ArrayList<>();
		final List<String> permissionsList = new ArrayList<>();

		if (!addPermission(permissionsList, android.Manifest.permission.INTERNET))
			permissionsNeeded.add("INTERNET");
		if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_NETWORK_STATE))
			permissionsNeeded.add("ACCESS_NETWORK_STATE");
		if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
			permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
		if (!addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE))
			permissionsNeeded.add("READ_EXTERNAL_STORAGE");
		if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
			permissionsNeeded.add("CAMERA");
		//if (!addPermission(permissionsList, android.Manifest.permission.FLASHLIGHT))
			////permissionsNeeded.add("FLASHLIGHT");

		if (permissionsList.size() > 0) {
			if (permissionsNeeded.size() > 0) {
				// Need Rationale
				String message = "You need to grant access to " + permissionsNeeded.get(0);
				for (int i = 1; i < permissionsNeeded.size(); i++)
					message = message + ", " + permissionsNeeded.get(i);

				//showMessageOKCancel(message, new DialogInterface.OnClickListener() {
				//@Override
				//public void onClick(DialogInterface dialog, int which) {*/
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
				}
				//}
				//});
				return;
			}
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
			}
			return;
		}
	}

	private boolean addPermission(List<String> permissionsList, String permission) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
				permissionsList.add(permission);
				// Check for Rationale Option
				return shouldShowRequestPermissionRationale(permission);
			}
		}
		return true;
	}


		@Override
		public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

			switch (requestCode) {
				case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
					Map<String, Integer> perms = new HashMap<String, Integer>();
					// Initial
					perms.put(android.Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
					perms.put(android.Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
					perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
					perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
					perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
					perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
					perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);

					// Fill with results
					for (int i = 0; i < permissions.length; i++)
						perms.put(permissions[i], grantResults[i]);
					// Check for ACCESS_FINE_LOCATION
					if (perms.get(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
							&& perms.get(android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
							&& perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
							&& perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
							&& perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
							&& perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
							&& perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
						// All Permissions Granted

						menu_selected = 0;
						displayView(menu_selected);
						//Toast.makeText(context, "You Okay?", Toast.LENGTH_SHORT).show();

					} else {
						// Permission Denied
						Toast.makeText(context, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
					}
				}
				break;
				default:
					super.onRequestPermissionsResult(requestCode, permissions, grantResults);
			}
		}
	class prosesUpdateRegisterRegId extends AsyncTask<String, Void, JSONObject> {

		String registrationId;
		boolean success;
		String message;

		prosesUpdateRegisterRegId(String registrationId) {
			this.registrationId = registrationId;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			return ServerUtilities.register(context, registrationId, data.getId(), CommonUtilities.getGuestId(context));
		}

		@Deprecated
		@Override
		protected void onPostExecute(JSONObject result) {

			success = false;
			message = "Gagal melakukan proses take action. Cobalah lagi.";
			if(result!=null) {
				try {
					success = !result.isNull("success") && result.getBoolean("success");
					message = result.isNull("message")?message:result.getString("message");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(!success) {
				new prosesUpdateRegisterRegId(registrationId).execute();
			}
		}
	}

	private void checkGcmRegid() {
		//String registrationId = getString(R.string.msg_token_fmt, FirebaseApp.initializeApp(context).getToken(true));
		//registrationId = registrationId.equalsIgnoreCase("null") ? "" : registrationId;
		//Log.d("Registration id", registrationId);
		//Toast.makeText(context, registrationId, Toast.LENGTH_SHORT).show();
		//if (registrationId.length() > 0) {
			//new prosesUpdateRegisterRegId(registrationId).execute();
		//}
	}

	public void openDetailInformasi(informasi data) {
		Intent intent = new Intent(context, DetailInformasiActivity.class);
		intent.putExtra("informasi", data);
		startActivity(intent);
	}

	public void openDetailNotifikasi(notifikasi data) {
		//Intent intent = new Intent(context, DetailNotifikasiActivity.class);
		//intent.putExtra("notifikasi", data);
		//startActivity(intent);
	}

	public void openMessageActivity(perpesanan data) {
		//data_perpesanan = data;
		//Toast.makeText(context, data.getId_produk()+"", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(context, MessageActivity.class);
		i.putExtra("id_produk", data.getId_produk());
		startActivityForResult(i, RESULT_FROM_KIRIM_PESAN);
	}

	public void openMessageActivity(order data) {
		//data_perpesanan = data;
		Toast.makeText(context, "Kirim Pesan!", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(context, MessageActivity.class);
		i.putExtra("no_trx", data.getNo_transaksi());
		startActivity(i);
	}

	public void loadDataPerpesanan(boolean starting) {
		if(starting) {
			next_page_perpesanan = 1;
			perpesananlist = new ArrayList<>();
			perpesananAdapter = new PerpesananAdapter(context, perpesananlist);
			PerpesananFragment.listview.setAdapter(perpesananAdapter);
		}
		new loadDataPerpesanan().execute();
	}

	public class loadDataPerpesanan extends AsyncTask<String, Void, ArrayList<perpesanan>> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			PerpesananFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected ArrayList<perpesanan> doInBackground(String... urls) {
			JSONParser token_json = new JSONParser();
			JSONObject token = token_json.getJSONFromUrl(CommonUtilities.SERVER_URL + "/store/token.php", null, null);
			String cookies = token_json.getCookies();

			String security_code = "";
			try {
				security_code = token.isNull("security_code")?"":token.getString("security_code");
				MCrypt mCrypt = new MCrypt();
				security_code = new String(mCrypt.decrypt(security_code));
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			ArrayList<perpesanan> result = null;
			if(security_code.length()>0) {
				String url = CommonUtilities.SERVER_URL + "/store/androidLaporanDataStore.php";

				List<NameValuePair> params = new ArrayList<>();
				params.add(new BasicNameValuePair("page", next_page_perpesanan+""));
				params.add(new BasicNameValuePair("user_id", data.getId() + ""));
				params.add(new BasicNameValuePair("guest_id", CommonUtilities.getGuestId(context) + ""));
				params.add(new BasicNameValuePair("security_code", security_code));
				JSONObject json = new JSONParser().getJSONFromUrl(url, params, cookies);

				if(json!=null) {
					try {
						result = new ArrayList<>();

						next_page_perpesanan = json.isNull("next_page")?next_page_perpesanan:json.getInt("next_page");
						JSONArray topics = json.isNull("topics")?null:json.getJSONArray("topics");
						for (int i=0; i<topics.length(); i++) {
							JSONObject rec = topics.getJSONObject(i);

							int id            = rec.isNull("id")?0:rec.getInt("id");
							int id_produk     = rec.isNull("id_produk")?0:rec.getInt("id_produk");
							String kode       = rec.isNull("kode")?"":rec.getString("kode");
							String nama       = rec.isNull("nama")?"":rec.getString("nama");
							String gambar     = rec.isNull("gambar")?"":rec.getString("gambar");
							String pesan      = rec.isNull("pesan")?"":rec.getString("pesan");
							String tanggal    = rec.isNull("tanggal_jam")?"":rec.getString("tanggal_jam");
							int from_id       = rec.isNull("from_id")?0:rec.getInt("from_id");
							String from_nama  = rec.isNull("from_nama")?"":rec.getString("from_nama");
							String from_photo = rec.isNull("from_photo")?"":rec.getString("from_photo");
							int total_unread  = rec.isNull("total_unread")?0:rec.getInt("total_unread");

							result.add(new perpesanan(id, id_produk, kode, nama, gambar, tanggal, pesan, from_id, from_nama, from_photo, total_unread));
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return result;
		}

		@Override
		protected void onPostExecute(ArrayList<perpesanan> result) {

			Boolean success = result!=null;
			if(result==null) result = new ArrayList<>();
			ArrayList<perpesanan_list> temp = new ArrayList<>();
			temp.add(new perpesanan_list(result));

			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_PERPESANAN_LIST");
			i.putExtra("perpesanan_list", temp);
			i.putExtra("success", success);
			sendBroadcast(i);
		}
	}

	public Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			mHandlerClose.removeCallbacks(this);
			current_click = 0;
		}
	};

	void openSoftKeyboard() {
		View view = getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		}
	}

	void closeSoftKeyboard() {
		View view = getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
		int REQUEST_STORAGE_PERMISSION = 100;

		public void requestStoragePermission() {

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE_PERMISSION);
			} else {
				requestLocationPermission();
			}
		}


		int REQUEST_LOCATION_PERMISSION = 200;

		public void requestLocationPermission() {

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
			}
		}
}