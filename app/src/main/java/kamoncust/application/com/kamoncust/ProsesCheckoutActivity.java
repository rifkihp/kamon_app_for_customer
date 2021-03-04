package kamoncust.application.com.kamoncust;

import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.shivtechs.maplocationpicker.LocationPickerActivity;
import com.shivtechs.maplocationpicker.MapUtility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import android.widget.TextView;
import android.widget.TimePicker;

import kamoncust.application.com.adapter.PesananAdapter;
import kamoncust.application.com.adapter.SelectBankAdapter;
import kamoncust.application.com.adapter.SelectOngkirAdapter;
import kamoncust.application.com.data.RestApi;
import kamoncust.application.com.data.RetroFit;
import kamoncust.application.com.fragment.ProsesCheckoutAlamatKirimFragment;
import kamoncust.application.com.fragment.ProsesCheckoutBerhasilFragment;
import kamoncust.application.com.fragment.ProsesCheckoutJadwalPengirimanFragment;
import kamoncust.application.com.fragment.ProsesCheckoutKonfirmasiPemesananFragment;
import kamoncust.application.com.fragment.ProsesCheckoutKurirPengirimanFragment;
import kamoncust.application.com.fragment.ProsesCheckoutMetodePembayaranFragment;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.DatabaseHandler;
import kamoncust.application.com.libs.JSONParser;
import kamoncust.application.com.libs.MyImageDownloader;
import kamoncust.application.com.model.ResponseCity;
import kamoncust.application.com.model.ResponseProvince;
import kamoncust.application.com.model.ResponseSubdistrict;
import kamoncust.application.com.model.ResponseValidasiJadwal;
import kamoncust.application.com.model.alamat;
import kamoncust.application.com.model.bank;
import kamoncust.application.com.model.city;
import kamoncust.application.com.model.grandtotal;
import kamoncust.application.com.model.jadwal;
import kamoncust.application.com.model.mitra;
import kamoncust.application.com.model.ongkir;
import kamoncust.application.com.model.produk;
import kamoncust.application.com.model.produk_list;
import kamoncust.application.com.model.province;
import kamoncust.application.com.model.subdistrict;
import kamoncust.application.com.model.user;
import kamoncust.application.com.model.voucher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kamoncust.application.com.libs.CommonUtilities.getOptionsImage;

public class ProsesCheckoutActivity extends FragmentActivity {

	Context context;
	user data;
	DatabaseHandler dh;

	mitra mitra_selected;
	int province_id = 0;
	int city_id = 0;
	int subdistrict_id = 0;

	int kurir_index = -1;
	int bank_index = -1;

	ImageView back;
	TextView title;
	TextView step;
	
	ArrayList<String> listJamTerapi = new ArrayList<>();
	ArrayList<alamat> listAlamat = new ArrayList<>();
	ArrayList<province> listProvince = new ArrayList<>();
	ArrayList<city> listCity = new ArrayList<>();
	ArrayList<subdistrict> listSubDistrict = new ArrayList<>();

	ArrayList<ongkir> ongkirlist = new ArrayList<>();
	SelectOngkirAdapter selectOngkirAdapter;

	ArrayList<bank> banklist = new ArrayList<>();
	SelectBankAdapter selectBankAdapter;

	public static final int RESULT_FROM_ALAMAT = 1;
	public static final int ADDRESS_PICKER_REQUEST = 2;
	public static String action;

	Dialog dialog_listview;
	ListView listview;

	Dialog dialog_informasi;
	TextView btn_ok;
	TextView text_title;
	TextView text_informasi;

	Dialog dialog_loading;

	grandtotal gtotal;
	alamat data_alamat;
	jadwal data_jadwal;
	ongkir data_kurir;
	bank data_bank;
	voucher data_voucher;

	int menu_selected = 0;
	boolean change_subdistrict = true;

	int metode_pembayaran_id = 1;

	ArrayList<produk> pesananlist = new ArrayList<>();
	PesananAdapter pesananAdapter;

	ImageLoader imageLoader;
	DisplayImageOptions imageOptionMitra, imageOptionPembayaran;

	LinearLayout linear_indikator;
	ImageView image_step_2, image_step_3, image_step_4;
	View line_step_2_aktif;
	View line_step_3_aktif;
	View line_step_4_aktif;
	View line_step_2_inaktif;
	View line_step_3_inaktif;
	View line_step_4_inaktif;

	final Calendar myCalendar = Calendar.getInstance();
	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			// TODO Auto-generated method stub
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			String myFormat = "dd-MM-yyyy"; //In which you need put here
			SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

			if(action.equalsIgnoreCase("tanggal_terapi")) {
				ProsesCheckoutJadwalPengirimanFragment.edit_tanggal_terapi.setText(sdf.format(myCalendar.getTime()));
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_proses_checkout);
		MapUtility.apiKey = getResources().getString(R.string.google_maps_key);

		if (Build.VERSION.SDK_INT >= 21) {
			Window window = getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.parseColor("#278CE3"));
		}
		context = ProsesCheckoutActivity.this;


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

		imageOptionMitra 	  = getOptionsImage(R.drawable.userdefault, R.drawable.userdefault);
		imageOptionPembayaran = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);

		data = CommonUtilities.getSettingUser(context);
		dh = new DatabaseHandler(context);

		linear_indikator =  findViewById(R.id.linear_indikator);
		line_step_2_aktif = findViewById(R.id.line_step_2_aktif);
		line_step_3_aktif = findViewById(R.id.line_step_3_aktif);
		line_step_4_aktif = findViewById(R.id.line_step_4_aktif);

		line_step_2_inaktif = findViewById(R.id.line_step_2_inaktif);
		line_step_3_inaktif = findViewById(R.id.line_step_3_inaktif);
		line_step_4_inaktif = findViewById(R.id.line_step_4_inaktif);

		image_step_2 = findViewById(R.id.image_step_2);
		image_step_3 = findViewById(R.id.image_step_3);
		image_step_4 = findViewById(R.id.image_step_4);

		back = findViewById(R.id.back);
		title = findViewById(R.id.title);
		step = findViewById(R.id.step);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(menu_selected==4) {
					Intent intent = new Intent();
					setResult(RESULT_OK, intent);
					finish();
				} else {
					menu_selected-=menu_selected==3?2:1;
					if(menu_selected<0) {
						finish();
					}else {
						displayView(menu_selected);
					}
				}
			}
		});
		dialog_listview = new Dialog(context);
		dialog_listview.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_listview.setCancelable(true);
		dialog_listview.setContentView(R.layout.list_dialog);

		listview = dialog_listview.findViewById(R.id.listViewDialog);
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog_listview.dismiss();
				if(action.equalsIgnoreCase("province")) {
					ProsesCheckoutAlamatKirimFragment.edit_province.setText(listProvince.get(position).getProvince());
					ProsesCheckoutAlamatKirimFragment.edit_city.setText("");
					ProsesCheckoutAlamatKirimFragment.edit_state.setText("");

					kurir_index = -1;

					province_id = listProvince.get(position).getProvince_id();
					city_id = 0;
					subdistrict_id = 0;
					change_subdistrict = true;

					new loadCity().execute();
					new loadSubdistrict().execute();

				} else if(action.equalsIgnoreCase("city")) {
					ProsesCheckoutAlamatKirimFragment.edit_city.setText(listCity.get(position).getCity_name());
					ProsesCheckoutAlamatKirimFragment.edit_state.setText("");

					kurir_index = -1;

					city_id = listCity.get(position).getCity_id();
					subdistrict_id = 0;
					change_subdistrict = true;

					new loadSubdistrict().execute();

				} else if(action.equalsIgnoreCase("subdistrict")) {
					ProsesCheckoutAlamatKirimFragment.edit_state.setText(listSubDistrict.get(position).getSubdistrict_name());

					kurir_index = -1;

					subdistrict_id = listSubDistrict.get(position).getSubdistrict_id();
					change_subdistrict = true;
				} else if(action.equalsIgnoreCase("jam_terapi")) {
					ProsesCheckoutJadwalPengirimanFragment.edit_jam_terapi.setText(listJamTerapi.get(position));
				}

				action = "";
			}
		});

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

		dialog_loading = new Dialog(context);
		dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_loading.setCancelable(false);
		dialog_loading.setContentView(R.layout.loading_dialog);

		listProvince = new ArrayList<>();
		listCity = new ArrayList<>();
		listSubDistrict = new ArrayList<>();
		new loadProvince().execute();

		if(savedInstanceState==null) {
			data_alamat = dh.getAlamat();
			if(data_alamat.getLatitude()==0 && data_alamat.getLongitude()==0) {
				data_alamat = (alamat) getIntent().getSerializableExtra("data_alamat");
				data_alamat = data_alamat==null?dh.getAlamat():data_alamat;
			}
			data_jadwal = dh.getJadwalTerapi();
			ArrayList<produk_list> temp = getIntent().getParcelableArrayListExtra("cartlist");
			pesananlist = temp.get(0).getListData();
			gtotal = (grandtotal) getIntent().getSerializableExtra("grandtotal");
			mitra_selected = (mitra) getIntent().getSerializableExtra("mitra");

			data_kurir  = new ongkir(0, "", "", "", "", 0, "", "", "");
			//data_bank   = new bank(0, "", "", "", "", "");
		}

		listJamTerapi.add("08 s.d 10 WIB");
		listJamTerapi.add("10 s.d 12 WIB");
		listJamTerapi.add("12 s.d 14 WIB");
		listJamTerapi.add("14 s.d 16 WIB");
		listJamTerapi.add("16 s.d 18 WIB");
		listJamTerapi.add("19 s.d 20 WIB");

		selectOngkirAdapter = new SelectOngkirAdapter(context, ongkirlist);
		selectBankAdapter = new SelectBankAdapter(context, banklist);
		pesananAdapter = new PesananAdapter(context, pesananlist);
		displayView(0);
	}

	private void showTimeDialog() {

		Calendar mcurrentTime = Calendar.getInstance();
		int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
		int minute = mcurrentTime.get(Calendar.MINUTE);
		TimePickerDialog mTimePicker;
		mTimePicker = new TimePickerDialog(ProsesCheckoutActivity.this, new TimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
				String jam = (selectedHour<10?"0":"")+selectedHour;
				String menit = (selectedMinute<10?"0":"")+selectedMinute;

				if(action.equalsIgnoreCase("jam_terapi")) {
					ProsesCheckoutJadwalPengirimanFragment.edit_jam_terapi.setText(jam+":"+menit);
				}
			}
		}, hour, minute, true);//Yes 24 hour time
		mTimePicker.setTitle("Select Time");
		mTimePicker.show();
	}

	public void loadDialogListView(String act) {
		action = act;
		if(action.equalsIgnoreCase("province") && listProvince.size()==0) {
			openDialogLoading(true);
		} else if(action.equalsIgnoreCase("city") && listCity.size()==0) {
			openDialogLoading(true);
		} else if(action.equalsIgnoreCase("subdistrict") && listSubDistrict.size()==0) {
			openDialogLoading(true);
		} else if(action.equalsIgnoreCase("tanggal_terapi")) {
			selectDate();
		} else {
			loadListArray();
			dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog_listview.show();
		}
	}

	public void selectDate() {
		new DatePickerDialog(context, R.style.MySpinnerDatePickerStyle, date, myCalendar
				.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
				myCalendar.get(Calendar.DAY_OF_MONTH)).show();
	}

	public void openDialogMessage(String message, boolean status) {
		text_informasi.setText(message);
		text_title.setText(status?"BERHASIL":"KESALAHAN");
		dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_informasi.show();
	}

	public void openDialogLoading(boolean cancelabel) {
		dialog_loading.setCancelable(cancelabel);
		dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog_loading.show();
	}

	public void loadListArray() {
		String[] from = new String[] { getResources().getString(R.string.list_dialog_title) };
		int[] to = new int[] { R.id.txt_title };

		List<HashMap<String, String>> fillMaps = new ArrayList<>();
		if(action.equalsIgnoreCase("pilih_alamat")) {
			for (alamat data : listAlamat) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getNama());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("province")) {
			for (province data : listProvince) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getProvince());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("city")) {
			for (city data : listCity) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getCity_name());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("subdistrict")) {
			for (subdistrict data : listSubDistrict) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data.getSubdistrict_name());

				fillMaps.add(map);
			}
		} else if(action.equalsIgnoreCase("jam_terapi")) {
			for (String data : listJamTerapi) {
				HashMap<String, String> map = new HashMap<>();
				map.put(getResources().getString(R.string.list_dialog_title), data);

				fillMaps.add(map);
			}
		}

		SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
		listview.setAdapter(adapter);
	}

	void unregisterReceiver() {
		try {
			unregisterReceiver(mHandleLoadDataPesananReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadListSubmitOrderReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unregisterReceiver(mHandleLoadListCekVoucherReceiver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver();

		super.onDestroy();
	}

	@Override
	protected void onPause() {
		unregisterReceiver();

		super.onPause();
	}

	@Override
	protected void onResume() {
		registerReceiver(mHandleLoadDataPesananReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_PESANAN"));
		registerReceiver(mHandleLoadListSubmitOrderReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_SUBMIT_ORDER"));
		registerReceiver(mHandleLoadListCekVoucherReceiver, new IntentFilter("kamoncust.application.com.kamoncust.CEK_VOUCHER"));
		registerReceiver(mHandleLoadEkspedisiReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST"));
		registerReceiver(mHandleValidasiJadwalReceiver, new IntentFilter("kamoncust.application.com.kamoncust.VALIDASI_JADWAL_TERAPI"));

		super.onResume();
	}

	private final BroadcastReceiver mHandleValidasiJadwalReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			boolean success = intent.getBooleanExtra("success", false);
			String message = intent.getStringExtra("message");

			dialog_loading.dismiss();
			if(success) {
				displayView(3);
			} else {
				openDialogMessage(message, false);
			}
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

	private final BroadcastReceiver mHandleLoadListCekVoucherReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			boolean success = intent.getBooleanExtra("success", false);
			String message = intent.getStringExtra("message");

			dialog_loading.dismiss();
			if(success) {
				loadFieldGrandTotal();
			} else {
				openDialogMessage(message, false);
			}

		}
	};

	private final BroadcastReceiver mHandleLoadListSubmitOrderReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			boolean success = intent.getBooleanExtra("success", false);
			String message = intent.getStringExtra("message");

			dialog_loading.dismiss();
			if(!success) {
				openDialogMessage(message, false);
			} else {
				displayView(4);
			}

		}
	};

	private final BroadcastReceiver mHandleLoadDataPesananReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {

			Boolean success = intent.getBooleanExtra("success", false);

			//DETAIL PASIEN
			ProsesCheckoutKonfirmasiPemesananFragment.detail_pasien.setText(data_alamat.getNama()+"\n"+data_alamat.getAlamat()+ " "+data_alamat.getKode_pos()+"\n"+data_alamat.getSubdistrict_name()+", "+data_alamat.getCity_name()+"\n"+data_alamat.getProvince()+"\nTelepon: "+data_alamat.getNo_hp());

			//LIST PESANAN
			pesananAdapter.UpdatePesananAdapter(pesananlist);

			//VOUCHER DAN GRAND TOTAL
			loadFieldGrandTotal();

			ProsesCheckoutKonfirmasiPemesananFragment.loading.setVisibility(View.GONE);
			if(success) {
				ProsesCheckoutKonfirmasiPemesananFragment.detail_view.setVisibility(View.VISIBLE);
			} else {
				ProsesCheckoutKonfirmasiPemesananFragment.retry.setVisibility(View.VISIBLE);
			}
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data_intent);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case ADDRESS_PICKER_REQUEST: {

					String address = data_intent.getStringExtra(MapUtility.ADDRESS);
					data_alamat.setLatitude(data_intent.getDoubleExtra(MapUtility.LATITUDE, 0.0));
					data_alamat.setLongitude(data_intent.getDoubleExtra(MapUtility.LONGITUDE, 0.0));

					ProsesCheckoutAlamatKirimFragment.edit_alamat.setText(address);

					break;
				}
				case RESULT_FROM_ALAMAT: {
					change_subdistrict = true;
					alamat select_alamat = (alamat) data_intent.getSerializableExtra("alamat");
					loadFieldAlamat(select_alamat, false);

					break;
				}
			}
		}
	}

	public void setInitialAlamatKirim() {
		ProsesCheckoutAlamatKirimFragment.pickup_alamat.setVisibility(data.getId()>0?View.VISIBLE:View.INVISIBLE);
		ProsesCheckoutAlamatKirimFragment.add_alamat.setVisibility(data.getId()>0?View.VISIBLE:View.INVISIBLE);
	}

	public void setInitialKurirPengiruman() {
		ProsesCheckoutKurirPengirimanFragment.listViewOngkir.setAdapter(selectOngkirAdapter);
	}

	public void setInitialMetodePembayaran() {
		//Toast.makeText(context, data_kurir.getKode_kurir()+" TES", Toast.LENGTH_LONG).show();
		ProsesCheckoutMetodePembayaranFragment.linear_metode_cod.setVisibility(data_kurir.getKode_kurir().equalsIgnoreCase("COD")?View.VISIBLE:View.GONE);
		ProsesCheckoutMetodePembayaranFragment.linear_metode_transfer_bank.setVisibility(data_kurir.getKode_kurir().equalsIgnoreCase("COD")?View.GONE:View.VISIBLE);

		ProsesCheckoutMetodePembayaranFragment.listViewBank.setAdapter(selectBankAdapter);
	}

	public void setInitialKonfirmasiPesanan() {
		ProsesCheckoutKonfirmasiPemesananFragment.listViewPesanan.setAdapter(pesananAdapter);
	}

	public void loadDefaultAlamat() {
		if(data_alamat!=null) loadFieldAlamat(data_alamat, true);
	}

	public void loadFieldAlamat(alamat selected_alamat, boolean load_dropship) {

		province_id = selected_alamat.getProvince_id();
		city_id = selected_alamat.getCity_id();
		subdistrict_id = selected_alamat.getSubdistrict_id();

		new loadCity().execute();
		new loadSubdistrict().execute();

		ProsesCheckoutAlamatKirimFragment.edit_nama.setText(selected_alamat.getNama());
		ProsesCheckoutAlamatKirimFragment.edit_alamat.setText(selected_alamat.getAlamat());
		ProsesCheckoutAlamatKirimFragment.edit_province.setText(selected_alamat.getProvince());

		ProsesCheckoutAlamatKirimFragment.edit_city.setText(selected_alamat.getCity_name());
		ProsesCheckoutAlamatKirimFragment.edit_state.setText(selected_alamat.getSubdistrict_name());
		ProsesCheckoutAlamatKirimFragment.edit_kodepos.setText(selected_alamat.getKode_pos());
		ProsesCheckoutAlamatKirimFragment.edit_nohp.setText(selected_alamat.getNo_hp());

		if(load_dropship) {
			selected_alamat = dh.getAlamat();
			ProsesCheckoutAlamatKirimFragment.checkbox_isdropship.setChecked(selected_alamat.getIs_dropship());
			ProsesCheckoutAlamatKirimFragment.edit_dropship_name.setText(selected_alamat.getDropship_name().length() == 0 ? data.getDropship_name() : selected_alamat.getDropship_name());
			ProsesCheckoutAlamatKirimFragment.edit_dropship_phone.setText(selected_alamat.getDropship_phone().length() == 0 ? data.getDropship_phone() : selected_alamat.getDropship_phone());
			ProsesCheckoutAlamatKirimFragment.linear_dropship_name.setVisibility(selected_alamat.getIs_dropship() ? View.VISIBLE : View.GONE);
			ProsesCheckoutAlamatKirimFragment.linear_dropship_phone.setVisibility(selected_alamat.getIs_dropship() ? View.VISIBLE : View.GONE);

			ProsesCheckoutAlamatKirimFragment.edit_email_notifikasi.setText(selected_alamat.getEmail_notifikasi());
			ProsesCheckoutAlamatKirimFragment.linear_email_notifikasi.setVisibility(data.getId() == 0 ? View.VISIBLE : View.GONE);
		}
	}

	public void loadDefaultJadwalKirim() {
		ProsesCheckoutJadwalPengirimanFragment.edit_tanggal_terapi.setText(data_jadwal.getTanggal_terapi());
		ProsesCheckoutJadwalPengirimanFragment.edit_jam_terapi.setText(data_jadwal.getJam_terapi());
		ProsesCheckoutJadwalPengirimanFragment.edit_riwayat_kesehatan.setText(data_jadwal.getRiwayat_kesehatan());
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

	public void displayView(int position) {
		menu_selected = position;

		Fragment fragment = null;
		switch (position) {
			case 0:

				fragment = new ProsesCheckoutAlamatKirimFragment();
				break;

			case 1:

				fragment = new ProsesCheckoutJadwalPengirimanFragment();
				break;

			case 2:

				fragment = new ProsesCheckoutMetodePembayaranFragment();
				break;

			case 3:

				fragment = new ProsesCheckoutKonfirmasiPemesananFragment();
				break;

			case 4:

				fragment = new ProsesCheckoutBerhasilFragment();
				break;

			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

			switch (menu_selected) {
				case 0:

					title.setText("ALAMAT KIRIM");
					step.setText("Step 1/4");

					line_step_2_aktif.setVisibility(View.GONE);
					line_step_3_aktif.setVisibility(View.GONE);
					line_step_4_aktif.setVisibility(View.GONE);

					line_step_2_inaktif.setVisibility(View.VISIBLE);
					line_step_3_inaktif.setVisibility(View.VISIBLE);
					line_step_4_inaktif.setVisibility(View.VISIBLE);

					image_step_2.setImageResource(R.drawable.checkout_step_2);
					image_step_3.setImageResource(R.drawable.checkout_step_3);
					image_step_4.setImageResource(R.drawable.checkout_step_4);

					break;
				case 1:

					title.setText("JADWAL TERAPI");
					step.setText("Step 2/4");

					line_step_2_inaktif.setVisibility(View.GONE);
					line_step_3_aktif.setVisibility(View.GONE);
					line_step_4_aktif.setVisibility(View.GONE);

					line_step_2_aktif.setVisibility(View.VISIBLE);
					line_step_3_inaktif.setVisibility(View.VISIBLE);
					line_step_4_inaktif.setVisibility(View.VISIBLE);

					image_step_2.setImageResource(R.drawable.checkout_step_2_aktif);
					image_step_3.setImageResource(R.drawable.checkout_step_3);
					image_step_4.setImageResource(R.drawable.checkout_step_4);

					break;
				case 2:

					title.setText("METODE PEMBAYARAN");
					step.setText("Step 3/4");

					line_step_2_inaktif.setVisibility(View.GONE);
					line_step_3_inaktif.setVisibility(View.GONE);
					line_step_4_aktif.setVisibility(View.GONE);

					line_step_2_aktif.setVisibility(View.VISIBLE);
					line_step_3_aktif.setVisibility(View.VISIBLE);
					line_step_4_inaktif.setVisibility(View.VISIBLE);

					image_step_2.setImageResource(R.drawable.checkout_step_2_aktif);
					image_step_3.setImageResource(R.drawable.checkout_step_3_aktif);
					image_step_4.setImageResource(R.drawable.checkout_step_4);

					break;
				case 3:

					title.setText("KONFIRMASI PEMESANAN");
					step.setText("Step 4/4");

					line_step_2_inaktif.setVisibility(View.GONE);
					line_step_3_inaktif.setVisibility(View.GONE);
					line_step_4_inaktif.setVisibility(View.GONE);

					line_step_2_aktif.setVisibility(View.VISIBLE);
					line_step_3_aktif.setVisibility(View.VISIBLE);
					line_step_4_aktif.setVisibility(View.VISIBLE);

					image_step_2.setImageResource(R.drawable.checkout_step_2_aktif);
					image_step_3.setImageResource(R.drawable.checkout_step_3_aktif);
					image_step_4.setImageResource(R.drawable.checkout_step_4_aktif);

					break;
				case 4:

					title.setText("PESANAN BERHASIL");
					step.setText("");

					linear_indikator.setVisibility(View.INVISIBLE);
					break;
			}
		}
	}

	public String checkedAlamatKirimBeforeNext() {

		if(ProsesCheckoutAlamatKirimFragment.edit_nama.getText().toString().length()==0) {
			return "Nama harus diisi.";
		}

		if(ProsesCheckoutAlamatKirimFragment.edit_alamat.getText().toString().length()==0) {
			return "Alamat harus diisi.";
		}

		if(province_id==0) {
			return "Propinsi harus harus diisi.";
		}

		if(city_id==0) {
			return "Kota harus diisi.";
		}

		if(subdistrict_id==0) {
			return "Kecamatan harus diisi.";
		}

		if(ProsesCheckoutAlamatKirimFragment.edit_nohp.getText().toString().length()==0) {
			return "No HP harus diisi.";
		}

		return "";
	}

	public String checkedJadwalKirimBeforeNext() {

		if(ProsesCheckoutJadwalPengirimanFragment.edit_tanggal_terapi.getText().toString().length()==0) {
			return "Tanggal terapi harus diisi.";
		}

		if(ProsesCheckoutJadwalPengirimanFragment.edit_jam_terapi.getText().toString().length()==0) {
			return "Jam terapi harus diisi.";
		}

		return "";
	}
	
	public String checkedKurirPengirimanBeforeNext() {
		if(data_kurir==null || data_kurir.getId_kurir()==0) {
			return "Pilih salah satu kurir pengiriman.";
		}

		return "";
	}

	public String checkedMetodePembayaranBeforeNext() {
		if(data_kurir.getKode_kurir().equalsIgnoreCase("COD")) {
			metode_pembayaran_id = 3;
			return "";
		}

		if(data_bank==null || data_bank.getId()==0) {
			metode_pembayaran_id = 1;
			return "Pilih salah bank pembayaran.";
		}

		return "";
	}

	public void saveAlamat() {

		String nama = ProsesCheckoutAlamatKirimFragment.edit_nama.getText().toString();
		String alamat = ProsesCheckoutAlamatKirimFragment.edit_alamat.getText().toString();
		double latitude = data_alamat.getLatitude();
		double longitude = data_alamat.getLongitude();
		String province = ProsesCheckoutAlamatKirimFragment.edit_province.getText().toString();
		String city_name = ProsesCheckoutAlamatKirimFragment.edit_city.getText().toString();
		String subdistrict_name = ProsesCheckoutAlamatKirimFragment.edit_state.getText().toString();
		String kode_pos = ProsesCheckoutAlamatKirimFragment.edit_kodepos.getText().toString();
		String no_hp = ProsesCheckoutAlamatKirimFragment.edit_nohp.getText().toString();

		boolean is_dropship = ProsesCheckoutAlamatKirimFragment.checkbox_isdropship.isChecked();
		String dropship_name = ProsesCheckoutAlamatKirimFragment.edit_dropship_name.getText().toString().trim();
		String dropship_phone = ProsesCheckoutAlamatKirimFragment.edit_dropship_phone.getText().toString().trim();
		String email_notifikasi = ProsesCheckoutAlamatKirimFragment.edit_email_notifikasi.getText().toString().trim();

		data_alamat = new alamat(0, nama, alamat, latitude, longitude, province_id, province, city_id, city_name, subdistrict_id, subdistrict_name, kode_pos, no_hp, false, is_dropship, dropship_name, dropship_phone, email_notifikasi);
		dh.insertAlamat(data_alamat);
	}
	
	public void saveJadwalKirim() {

		String tanggal_terapi = ProsesCheckoutJadwalPengirimanFragment.edit_tanggal_terapi.getText().toString();
		String jam_terapi = ProsesCheckoutJadwalPengirimanFragment.edit_jam_terapi.getText().toString();
		String riwayat_kesehatan = ProsesCheckoutJadwalPengirimanFragment.edit_riwayat_kesehatan.getText().toString().trim();

		data_jadwal = new jadwal(0, tanggal_terapi, jam_terapi, riwayat_kesehatan);
		dh.insertJadwalTerapi(data_jadwal);
	}
	
	public void updateGrandtotal() {

		gtotal.setOngkir(data_kurir.getNominal());
		gtotal.setGrandtotal(gtotal.getTotal()-gtotal.getDiskon()-gtotal.getVoucher()+gtotal.getOngkir());
	}

	public void loadDataBank() {
		if(banklist.size()==0) {
			new loadDataBank().execute();
		} else {
			ProsesCheckoutMetodePembayaranFragment.loading.setVisibility(View.GONE);
			ProsesCheckoutMetodePembayaranFragment.detail_view.setVisibility(View.VISIBLE);
		}
	}




	public class loadDataBank extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			ProsesCheckoutMetodePembayaranFragment.detail_view.setVisibility(View.INVISIBLE);
			ProsesCheckoutMetodePembayaranFragment.loading.setVisibility(View.VISIBLE);
			ProsesCheckoutMetodePembayaranFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			String url = CommonUtilities.SERVER_URL + "/store/androidBankDataStore.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, null, null);
			boolean success = false;
			if (json != null) {
				try {
					JSONArray data = json.isNull("topics") ? null : json.getJSONArray("topics");
					for (int i = 0; i < data.length(); i++) {
						JSONObject rec = data.getJSONObject(i);

						int id = rec.isNull("id") ? 0 : rec.getInt("id");
						String no_rekening = rec.isNull("no_rekening") ? "" : rec.getString("no_rekening");
						String nama_pemilik_rekening = rec.isNull("nama_pemilik_rekening") ? "" : rec.getString("nama_pemilik_rekening");
						String nama_bank = rec.isNull("nama_bank") ? "" : rec.getString("nama_bank");
						String cabang = rec.isNull("cabang") ? "" : rec.getString("cabang");
						String gambar = rec.isNull("gambar") ? "" : rec.getString("gambar");

						//banklist.add(i, new bank(id, no_rekening, nama_pemilik_rekening, nama_bank, cabang, gambar));
						//banklist.get(i).setIs_selected(bank_index==1);
					}
					success = true;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_CHECKOUT_BANK");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadDataOngkir() {
		if(change_subdistrict) {
			new loadDataOngkir().execute();
		} else {
			ProsesCheckoutKurirPengirimanFragment.loading.setVisibility(View.GONE);
			ProsesCheckoutKurirPengirimanFragment.detail_view.setVisibility(View.VISIBLE);
		}
	}

	public class loadDataOngkir extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			ProsesCheckoutKurirPengirimanFragment.detail_view.setVisibility(View.GONE);
			ProsesCheckoutKurirPengirimanFragment.loading.setVisibility(View.VISIBLE);
			ProsesCheckoutKurirPengirimanFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			ongkirlist = new ArrayList<>();
			String url = CommonUtilities.SERVER_URL + "/store/androidAllLayananDataStore.php";
			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("city_id", data_alamat.getCity_id()+""));
			params.add(new BasicNameValuePair("subdistrict_id", data_alamat.getSubdistrict_id()+""));
			params.add(new BasicNameValuePair("berat", dh.getTotalBeratCart()+""));
			params.add(new BasicNameValuePair("cart", dh.getStringCartlist()));
			params.add(new BasicNameValuePair("total", dh.getGrandtotal().getTotal()+""));


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
						ongkirlist.add(i, new ongkir(id_kurir, kode_kurir, nama_kurir, kode_service, nama_service, nominal, etd, tarif, gambar));
						ongkirlist.get(i).setIs_selected(kurir_index==i);
					}

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_ONGKIR_CHECKOUT");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public int getKurir_index() {
		return this.kurir_index;
	}

	public void setKurirPengiriman(int index, ongkir datakurir) {

		kurir_index = index;
		data_kurir = datakurir;
	}

	public int getBank_index() {
		return this.bank_index;
	}

	public void setMetodePembayaranBank(int index, bank databank) {

		bank_index = index;
		data_bank = databank;
	}

	public void validasiJadwal() {
		new prosesValidasiJadwal().execute();
	}

	public class prosesValidasiJadwal extends AsyncTask<String, Void, Void> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading(false);
		}

		@Override
		protected Void doInBackground(String... urls) {

			RestApi api = RetroFit.getInstanceRetrofit();
			Call<ResponseValidasiJadwal> validasiJadwalCall = api.postValidasiJadwal(
					mitra_selected.getId()+"",
					data_jadwal.getTanggal_terapi(),
					data_jadwal.getJam_terapi()
			);
			validasiJadwalCall.enqueue(new Callback<ResponseValidasiJadwal>() {
				@Override
				public void onResponse(@NonNull Call<ResponseValidasiJadwal> call, @NonNull Response<ResponseValidasiJadwal> response) {

					boolean success = Objects.requireNonNull(response.body()).getSuccess();
					String message = Objects.requireNonNull(response.body()).getMessage();
					Intent i = new Intent("kamoncust.application.com.kamoncust.VALIDASI_JADWAL_TERAPI");
					i.putExtra("success", success);
					i.putExtra("message", message);
					sendBroadcast(i);
				}

				@Override
				public void onFailure(@NonNull Call<ResponseValidasiJadwal> call, @NonNull Throwable t) {
					t.printStackTrace();
					Intent i = new Intent("kamoncust.application.com.kamoncust.VALIDASI_JADWAL_TERAPI");
					i.putExtra("success", false);
					i.putExtra("message", "Pengecekan jadwal gagal. Silahkan coba lagi.");
					sendBroadcast(i);
				}
			});

			return null;
		}
	}

	public void submitOrder() {
	    new prosesSubmitOrder().execute();
		openDialogLoading(false);
	}

	public class prosesSubmitOrder extends AsyncTask<String, Void, Void> {


		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Kesalahan koneksi internet.";

			List<NameValuePair> params = new ArrayList<>();

			//DATA USER
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("id_mitra", mitra_selected.getId()+""));
			params.add(new BasicNameValuePair("kode_trx", CommonUtilities.getKodeTransaksi(context)));

			//ALAMAT PENGIRIMAN
			params.add(new BasicNameValuePair("nama", data_alamat.getNama()));
			params.add(new BasicNameValuePair("alamat", data_alamat.getAlamat()));
			params.add(new BasicNameValuePair("latitude", data_alamat.getLatitude()+""));
			params.add(new BasicNameValuePair("longitude", data_alamat.getLongitude()+""));
			params.add(new BasicNameValuePair("propinsi", data_alamat.getProvince_id()+""));
			params.add(new BasicNameValuePair("nama_propinsi", data_alamat.getProvince()));
			params.add(new BasicNameValuePair("kota", data_alamat.getCity_id()+""));
			params.add(new BasicNameValuePair("nama_kota", data_alamat.getCity_name()));
			params.add(new BasicNameValuePair("kecamatan", data_alamat.getSubdistrict_id()+""));
			params.add(new BasicNameValuePair("nama_kecamatan", data_alamat.getSubdistrict_name()));
			params.add(new BasicNameValuePair("kode_pos", data_alamat.getKode_pos()));
			params.add(new BasicNameValuePair("no_hp", data_alamat.getNo_hp()));

			//DROPSHIP
			params.add(new BasicNameValuePair("is_dropship", data_alamat.getIs_dropship()?"Y":"N"));
			params.add(new BasicNameValuePair("dropship_name", data_alamat.getIs_dropship()?data_alamat.getDropship_name():""));
			params.add(new BasicNameValuePair("dropship_phone", data_alamat.getIs_dropship()?data_alamat.getDropship_phone():""));
			params.add(new BasicNameValuePair("email_notifikasi", data.getId()>0?data_alamat.getEmail_notifikasi():""));

			//JADWAL PENGIRIMAN
			params.add(new BasicNameValuePair("tanggal_terapi", data_jadwal.getTanggal_terapi()));
			params.add(new BasicNameValuePair("jam_terapi", data_jadwal.getJam_terapi()));
			params.add(new BasicNameValuePair("riwayat_kesehatan", data_jadwal.getRiwayat_kesehatan()));

			//KURIR PENGIRIMAN
            params.add(new BasicNameValuePair("kurir_id", data_kurir.getId_kurir()+""));
			params.add(new BasicNameValuePair("kode_kurir", data_kurir.getKode_kurir()));
			params.add(new BasicNameValuePair("nama_kurir", data_kurir.getNama_kurir()));
			params.add(new BasicNameValuePair("kode_layanan", data_kurir.getKode_service()));
			params.add(new BasicNameValuePair("layanan", data_kurir.getNama_service()));
			params.add(new BasicNameValuePair("nominal", data_kurir.getNominal()+""));
            params.add(new BasicNameValuePair("etd", data_kurir.getEtd()));
            params.add(new BasicNameValuePair("tarif", data_kurir.getTarif()));

			//METODE PEMBAYARAN
			params.add(new BasicNameValuePair("metode_pembayaran", metode_pembayaran_id+""));
			params.add(new BasicNameValuePair("bank_id", (metode_pembayaran_id==1?data_bank.getId():"")+""));
			params.add(new BasicNameValuePair("bank_no_rekening", (metode_pembayaran_id==1?data_bank.getNo_rekening():"")));
			params.add(new BasicNameValuePair("bank_nama_pemilik_rekening", (metode_pembayaran_id==1?data_bank.getNama_pemilik_rekening():"")));
			params.add(new BasicNameValuePair("bank_nama", (metode_pembayaran_id==1?data_bank.getNama_bank():"")));
			params.add(new BasicNameValuePair("bank_cabang", (metode_pembayaran_id==1?data_bank.getCabang():"")));
			params.add(new BasicNameValuePair("bank_logo", (metode_pembayaran_id==1?data_bank.getGambar():"")));

			//DATA VOUCHER
			if(data_voucher!=null) {
				String voucher = data_voucher.getKode()+"\t"+data_voucher.getNominal()+"\t"+data_voucher.getTipe_voucher()+"\t"+data_voucher.getJenis_voucher();
				params.add(new BasicNameValuePair("voucher", voucher));
			}

			//DETAIL ORDERS
			params.add(new BasicNameValuePair("orders", getStringCartlist()));

			String url = CommonUtilities.SERVER_URL + "/store/androidSubmitOrder.php";
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

			if(success) {
				dh.deleteCartlist();
				dh.deleteAlamat();
				dh.deleteJadwalTerapi();
				dh.deleteGrandtotal();
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_SUBMIT_ORDER");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}
	}

	public void prosesCekVoucher() {
		new prosesCekVoucher().execute();
	}

	public class prosesCekVoucher extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			openDialogLoading(false);
		}

		@Override
		protected Void doInBackground(String... urls) {

			boolean success = false;
			String message = "Kesalahan koneksi internet.";

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("kode_voucher", ProsesCheckoutKonfirmasiPemesananFragment.kode_voucher.getText().toString()));

			String url = CommonUtilities.SERVER_URL + "/store/androidCekVoucher.php";
			JSONObject json = new JSONParser().getJSONFromUrl(url, params, null);

			if(json!=null) {
				try {
					success = !json.isNull("success") && json.getBoolean("success");
					message = json.isNull("message")?message:json.getString("message");

					String kode = json.isNull("kode_voucher")?"":json.getString("kode_voucher");
					double nominal = json.isNull("nominal")?0:json.getDouble("nominal");
					String tipe_voucher = json.isNull("tipe_voucher")?"":json.getString("tipe_voucher");
					String jenis_voucher = json.isNull("jenis_voucher")?"":json.getString("jenis_voucher");

					//data_voucher = new voucher(kode, nominal, tipe_voucher, jenis_voucher);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.CEK_VOUCHER");
			i.putExtra("success", success);
			i.putExtra("message", message);
			sendBroadcast(i);

			return null;
		}
	}

	public void loadFieldTotalTransfer() {

		double total_belanja = gtotal.getTotal();
		double total_ongkir = gtotal.getOngkir();

		double total_voucher = 0;
		if(data_voucher!=null) {
			total_voucher = (data_voucher.getTipe_voucher().equalsIgnoreCase("persentase") ? ((data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") ? total_ongkir : total_belanja) * (data_voucher.getNominal() * 0.01)) : data_voucher.getNominal());
			if (data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") && total_voucher > total_ongkir) {
				total_voucher = total_ongkir;
			}

			if (data_voucher.getJenis_voucher().equalsIgnoreCase("belanja") && total_voucher > total_belanja) {
				total_voucher = total_belanja;
			}
		}

		double grand = total_belanja + gtotal.getOngkir() - total_voucher;
		ProsesCheckoutMetodePembayaranFragment.total_tagihan.setText(CommonUtilities.getCurrencyFormat(grand, "Rp. "));
	}

	private void loadFieldGrandTotal() {

		double total_belanja = gtotal.getTotal();
		double total_ongkir = gtotal.getOngkir();

		double total_voucher = 0;
		if(data_voucher!=null) {
			total_voucher = (data_voucher.getTipe_voucher().equalsIgnoreCase("persentase") ? ((data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") ? total_ongkir : total_belanja) * (data_voucher.getNominal() * 0.01)) : data_voucher.getNominal());
			if (data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") && total_voucher > total_ongkir) {
				total_voucher = total_ongkir;
			}

			if (data_voucher.getJenis_voucher().equalsIgnoreCase("belanja") && total_voucher > total_belanja) {
				total_voucher = total_belanja;
			}

			ProsesCheckoutKonfirmasiPemesananFragment.kode_voucher.setText(data_voucher.getKode());
		}

		double total = total_belanja - total_voucher + gtotal.getOngkir();
		ProsesCheckoutKonfirmasiPemesananFragment.subtotal.setText(CommonUtilities.getCurrencyFormat(total_belanja, ""));
		ProsesCheckoutKonfirmasiPemesananFragment.voucher.setText(CommonUtilities.getCurrencyFormat(total_voucher, ""));
		ProsesCheckoutKonfirmasiPemesananFragment.total.setText(CommonUtilities.getCurrencyFormat(total, ""));
	}


	public void loadDataPesanan() {
		if(pesananlist.size()==0) {
			new loadDataPesanan().execute();
		} else {
			//DETAIL PASIEN
			ProsesCheckoutKonfirmasiPemesananFragment.detail_pasien.setText(data_alamat.getNama()+"\n"+data_alamat.getAlamat()+ " "+data_alamat.getKode_pos()+"\n"+data_alamat.getSubdistrict_name()+", "+data_alamat.getCity_name()+"\n"+data_alamat.getProvince()+"\nTelepon: "+data_alamat.getNo_hp());

			//JADWAL
			ProsesCheckoutKonfirmasiPemesananFragment.jadwal_terapi.setText(
				"Tanggal: "+data_jadwal.getTanggal_terapi()+"\n"+
				"Jam: "+data_jadwal.getJam_terapi()+"\n"+
				"Riwayat Kesehatan: "+data_jadwal.getRiwayat_kesehatan()
			);

			//DETAIL MITRA
			loadMitraLogo(mitra_selected.getPhoto());
			ProsesCheckoutKonfirmasiPemesananFragment.detail_terapis.setText(
					mitra_selected.getNama() + "\n" +
					mitra_selected.getAlamat()
			);

			//VOUCHER DAN GRAND TOTAL
			loadFieldGrandTotal();

			ProsesCheckoutKonfirmasiPemesananFragment.loading.setVisibility(View.GONE);
			ProsesCheckoutKonfirmasiPemesananFragment.detail_view.setVisibility(View.VISIBLE);

		}
	}

	private String getStringCartlist() {
		String result = "";

		for(produk data:pesananlist){
			result+=(result.length()>0?"\n":"")+data.getId() + "\t" + data.getUkuran() + "\t" + data.getWarna() + "\t" + data.getQty();
		}

		return result;
	}

	public class loadDataPesanan extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			ProsesCheckoutKonfirmasiPemesananFragment.detail_view.setVisibility(View.INVISIBLE);
			ProsesCheckoutKonfirmasiPemesananFragment.loading.setVisibility(View.VISIBLE);
			ProsesCheckoutKonfirmasiPemesananFragment.retry.setVisibility(View.GONE);
		}

		@Override
		protected Void doInBackground(String... urls) {

			String cart = getStringCartlist();
			ArrayList<produk> temp_pesananlist = pesananlist; //dh.getCartlist();
			pesananlist = new ArrayList<>();
			boolean success = false;

			List<NameValuePair> params = new ArrayList<>();
			params.add(new BasicNameValuePair("id_user", data.getId()+""));
			params.add(new BasicNameValuePair("cart", cart));
			params.add(new BasicNameValuePair("kurir", data_kurir.getId_kurir()+""));

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

						for(produk data: temp_pesananlist) {
							if(data.getId()==id && data.getUkuran().equalsIgnoreCase(ukuran) && data.getWarna().equalsIgnoreCase(warna)) {
								data.setQty(jumlah);
								data.setBerat(berat);
								data.setHarga_beli(harga_beli);
								data.setHarga_jual(harga_jual);
								data.setHarga_diskon(harga_diskon);
								data.setPersen_diskon(persen_diskon);
								data.setSubtotal(subtotal);
								data.setGrandtotal(grandtotal);

								temp_pesananlist.remove(data);
								pesananlist.add(data);
								break;
							}
						}
					}

					//dh.deleteCartlist();
					//dh.insertCartlists(pesananlist);

					success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_PESANAN");
			i.putExtra("success", success);
			sendBroadcast(i);

			return null;
		}
	}

	public void selectLocation() {
		Intent intent = new Intent(context, LocationPickerActivity.class);
		intent.putExtra("latitude", data_alamat.getLatitude());
		intent.putExtra("longitude", data_alamat.getLongitude());
		intent.putExtra("userAddress", ProsesCheckoutAlamatKirimFragment.edit_alamat.getText().toString());
		intent.putExtra("currentLatitude", data_alamat.getLatitude());
		intent.putExtra("currentLongitude", data_alamat.getLongitude());
		startActivityForResult(intent, ADDRESS_PICKER_REQUEST);
	}

	public void loadMitraLogo(String gambar) {
		String server = CommonUtilities.SERVER_URL;
		String url = server+"/uploads/member/"+gambar;
		imageLoader.displayImage(url, ProsesCheckoutKonfirmasiPemesananFragment.image_terapis, imageOptionMitra);
	}

	public void prosesKonfirmasi() {
		Intent intent = new Intent();
		intent.putExtra("goto", "konfirmasi");
		setResult(RESULT_OK, intent);
		finish();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			if(menu_selected==4) {
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				finish();
			} else {
				menu_selected-=menu_selected==3?2:1;
				if(menu_selected<0) {
					finish();
				} else {
					displayView(menu_selected);
				}
			}

			return false;
		}

		return super.onKeyDown(keyCode, event);
	}
}