package gomocart.application.com.gomocart;

import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
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

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import android.widget.TextView;
import gomocart.application.com.adapter.PesananAdapter;
import gomocart.application.com.adapter.SelectBankAdapter;
import gomocart.application.com.adapter.SelectOngkirAdapter;
import gomocart.application.com.data.RestApi;
import gomocart.application.com.data.RetroFit;
import gomocart.application.com.fragment.ProsesCheckoutAlamatKirimFragment;
import gomocart.application.com.fragment.ProsesCheckoutBerhasilFragment;
import gomocart.application.com.fragment.ProsesCheckoutKonfirmasiPemesananFragment;
import gomocart.application.com.fragment.ProsesCheckoutKurirPengirimanFragment;
import gomocart.application.com.fragment.ProsesCheckoutMetodePembayaranFragment;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.MyImageDownloader;
import gomocart.application.com.model.ResponseBankStore;
import gomocart.application.com.model.ResponseCheckVoucher;
import gomocart.application.com.model.ResponseCity;
import gomocart.application.com.model.ResponseDanaCreateOrder;
import gomocart.application.com.model.ResponseOngkirStore;
import gomocart.application.com.model.ResponseProvince;
import gomocart.application.com.model.ResponseSubdistrict;
import gomocart.application.com.model.ResponseSubmitTransaksi;
import gomocart.application.com.model.alamat;
import gomocart.application.com.model.bank;
import gomocart.application.com.model.city;
import gomocart.application.com.model.grandtotal;
import gomocart.application.com.model.ongkir;
import gomocart.application.com.model.market;
import gomocart.application.com.model.produk;
import gomocart.application.com.model.produk_list;
import gomocart.application.com.model.province;
import gomocart.application.com.model.subdistrict;
import gomocart.application.com.model.user;
import gomocart.application.com.model.voucher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static gomocart.application.com.libs.CommonUtilities.getOptionsImage;

public class ProsesCheckoutActivity extends FragmentActivity {

    Context context;
    user data;
    DatabaseHandler dh;
    market marketSelected;

    public static ImageLoader imageLoader;
    public static DisplayImageOptions imageOptionDefault;

    int province_id    = 0;
    int city_id        = 0;
    int subdistrict_id = 0;
    int ongkir_index   = -1;
    int bank_index     = -1;

    ImageView back;
    TextView title;
    TextView step;

    ArrayList<province> listProvince = new ArrayList<>();
    ArrayList<city> listCity = new ArrayList<>();
    ArrayList<subdistrict> listSubDistrict = new ArrayList<>();

    //ONGKIR
    ArrayList<ongkir> ongkirlist = new ArrayList<>();
    SelectOngkirAdapter selectOngkirAdapter;
    
    //PAYMENT
    ArrayList<bank> banklist = new ArrayList<>();
    SelectBankAdapter selectBankAdapter;

    //ORDER
    List<produk> pesananlist = new ArrayList<>();
    PesananAdapter pesananAdapter;

    public static final int RESULT_FROM_ALAMAT = 1;
    //public static final int RESULT_FROM_KURIR = 2;
    public static final int ADDRESS_PICKER_REQUEST = 2;
    public static String action;

    Dialog dialog_listview;
    ListView listview;

    Dialog dialog_loading;

    grandtotal gtotal;
    alamat data_alamat;

    ongkir data_ongkir;
    bank data_bank;
    voucher data_voucher;

    int menu_selected = 0;
    boolean subdistrictChanged = true;

    int metode_pembayaran_id = 1;

    LinearLayout linear_indikator;
    ImageView image_step_2, image_step_3, image_step_4;
    View line_step_2_aktif;
    View line_step_3_aktif;
    View line_step_4_aktif;
    View line_step_2_inaktif;
    View line_step_3_inaktif;
    View line_step_4_inaktif;

    String checkoutUrl = "";
    String id_trx = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proses_checkout_gomocart);
        MapUtility.apiKey = getResources().getString(R.string.google_maps_key);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#308c8e"));
        }
        context = ProsesCheckoutActivity.this;
        data = CommonUtilities.getSettingUser(context);

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
        imageOptionDefault = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);

        dh = new DatabaseHandler(context);

        linear_indikator = findViewById(R.id.linear_indikator);
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
                if (menu_selected == 4) {
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    menu_selected--;
                    if (menu_selected < 0) {
                        finish();
                    } else {
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
                if (action.equalsIgnoreCase("province")) {
                    ProsesCheckoutAlamatKirimFragment.edit_province.setText(listProvince.get(position).getProvince());
                    ProsesCheckoutAlamatKirimFragment.edit_city.setText("");
                    ProsesCheckoutAlamatKirimFragment.edit_state.setText("");

                    ongkir_index = -1;

                    province_id = listProvince.get(position).getProvince_id();
                    city_id = 0;
                    subdistrict_id = 0;
                    subdistrictChanged = true;

                    new loadCity().execute();
                    new loadSubdistrict().execute();

                } else if (action.equalsIgnoreCase("city")) {
                    ProsesCheckoutAlamatKirimFragment.edit_city.setText(listCity.get(position).getCity_name());
                    ProsesCheckoutAlamatKirimFragment.edit_state.setText("");

                    ongkir_index = -1;

                    city_id = listCity.get(position).getCity_id();
                    subdistrict_id = 0;
                    subdistrictChanged = true;

                    new loadSubdistrict().execute();

                } else if (action.equalsIgnoreCase("subdistrict")) {
                    ProsesCheckoutAlamatKirimFragment.edit_state.setText(listSubDistrict.get(position).getSubdistrict_name());

                    ongkir_index = -1;

                    subdistrict_id = listSubDistrict.get(position).getSubdistrict_id();
                    subdistrictChanged = true;
                }
                action = "";
            }
        });

        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);

        listProvince = new ArrayList<>();
        listCity = new ArrayList<>();
        listSubDistrict = new ArrayList<>();
        new loadProvince().execute();

        if (savedInstanceState == null) {
            marketSelected = (market) getIntent().getSerializableExtra("market");
            data_alamat    = (alamat) getIntent().getSerializableExtra("data_alamat");
            ArrayList<produk_list> temp = getIntent().getParcelableArrayListExtra("cartlist");
            pesananlist = temp.get(0).getListData();
        }

        double total    = 0;
        for(produk data: pesananlist) {
            total+=data.getGrandtotal();
        }
        double diskon   = 0;
        double subtotal = total-diskon;
        double voucher  = 0;
        double ongkir   = 0;
        double grandtot = subtotal-voucher+ongkir;

        gtotal               = new grandtotal(total, diskon, subtotal, voucher, ongkir, grandtot);

        selectOngkirAdapter  = new SelectOngkirAdapter(context, ongkirlist);
        selectBankAdapter    = new SelectBankAdapter(context, banklist);
        pesananAdapter       = new PesananAdapter(context, dh, pesananlist, imageLoader, imageOptionDefault, marketSelected.getId());

        displayView(0);
    }

    /*public void openKurirDialog(kurir data, int index) {
        index_kurir = index;
        Log.e("BERATTT", data.getBerat()+"aaaa");
        Intent intent = new Intent(context, KurirActivity.class);
        intent.putExtra("ongkir", data.getEkspedisi());
        intent.putExtra("tujuan", data_alamat);
        intent.putExtra("asal", data.getSimitra().getCity_id());
        intent.putExtra("berat", data.getBerat());
        intent.putExtra("total", data.getTotal());
        startActivityForResult(intent, RESULT_FROM_KURIR);
    }*/

    public void pickAlamat() {
        Intent intent = new Intent(context, AlamatActivity.class);
        startActivityForResult(intent, RESULT_FROM_ALAMAT);
    }

    public void editAlamat() {
        Intent intent = new Intent(context, EditAlamatActivity.class);
        startActivityForResult(intent, RESULT_FROM_ALAMAT);
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

    public void loadDialogListView(String act) {
        action = act;
        if (action.equalsIgnoreCase("province") && listProvince.size() == 0) {
            openDialogLoading(true);
        } else if (action.equalsIgnoreCase("city") && listCity.size() == 0) {
            openDialogLoading(true);
        } else if (action.equalsIgnoreCase("subdistrict") && listSubDistrict.size() == 0) {
            openDialogLoading(true);
        } else {
            loadListArray();
            dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog_listview.show();
        }
    }

    public void openDialogMessage(String message, boolean status) {

        CommonUtilities.showSnackbar(message, status, this);
    }

    public void openDialogLoading(boolean cancelable) {
        dialog_loading.setCancelable(cancelable);
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }
    
    public void loadListArray() {
        String[] from = new String[]{getResources().getString(R.string.list_dialog_title)};
        int[] to = new int[]{R.id.txt_title};

        List<HashMap<String, String>> fillMaps = new ArrayList<>();
        if (action.equalsIgnoreCase("province")) {
            for (province data : listProvince) {
                HashMap<String, String> map = new HashMap<>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getProvince());

                fillMaps.add(map);
            }
        } else if (action.equalsIgnoreCase("city")) {
            for (city data : listCity) {
                HashMap<String, String> map = new HashMap<>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getCity_name());

                fillMaps.add(map);
            }
        } else if (action.equalsIgnoreCase("subdistrict")) {
            for (subdistrict data : listSubDistrict) {
                HashMap<String, String> map = new HashMap<>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getSubdistrict_name());

                fillMaps.add(map);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
        listview.setAdapter(adapter);
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

    private void closeHandler() {
        try {
            unregisterReceiver(mHandleLoadEkspedisiReceiver);
            unregisterReceiver(mHandleLoadListOngkirReceiver);
            unregisterReceiver(mHandleLoadListBankReceiver);
            unregisterReceiver(mHandleLoadListSubmitOrderReceiver);
            unregisterReceiver(mHandleLoadListCekVoucherReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadEkspedisiReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST"));
        registerReceiver(mHandleLoadListOngkirReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_CHECKOUT_ONGKIR"));
        registerReceiver(mHandleLoadListBankReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_CHECKOUT_BANK"));
        registerReceiver(mHandleLoadListCekVoucherReceiver, new IntentFilter("gomocart.application.com.gomocart.CEK_VOUCHER"));
        registerReceiver(mHandleLoadListSubmitOrderReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_SUBMIT_ORDER"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadListCekVoucherReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            boolean success = intent.getBooleanExtra("success", false);
            String message = intent.getStringExtra("message");

            dialog_loading.dismiss();
            if (success) {
                updateGrandTotal();
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
            if (!success) {
                openDialogMessage(message, false);
            } else {
                displayView(4);
            }

        }
    };

    private final BroadcastReceiver mHandleLoadListBankReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean success = intent.getBooleanExtra("success", false);

            ProsesCheckoutMetodePembayaranFragment.loading.setVisibility(View.GONE);
            if (!success) {
                ProsesCheckoutMetodePembayaranFragment.retry.setVisibility(View.VISIBLE);
            } else {
                selectBankAdapter.UpdateBankAdapter(banklist);
            }

        }
    };

    private final BroadcastReceiver mHandleLoadListOngkirReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean success = intent.getBooleanExtra("success", false);

            ProsesCheckoutKurirPengirimanFragment.loading.setVisibility(View.GONE);
            if (!success) {
                ProsesCheckoutKurirPengirimanFragment.retry.setVisibility(View.VISIBLE);
            } else {
                selectOngkirAdapter.UpdateSelectOngkirAdapter(ongkirlist);
                subdistrictChanged = false;
            }
        }
    };

    private final BroadcastReceiver mHandleLoadEkspedisiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (dialog_loading.isShowing()) {
                loadListArray();
                dialog_loading.dismiss();
                dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_listview.show();
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
                    subdistrictChanged = true;
                    alamat select_alamat = (alamat) data_intent.getSerializableExtra("alamat");
                    loadFieldAlamat(select_alamat, false);

                    break;
                }
            }
        }
    }

    public void setInitialKurirPengiruman() {
        ProsesCheckoutKurirPengirimanFragment.listview.setAdapter(selectOngkirAdapter);
    }

    public void setInitialMetodePembayaran() {
        ProsesCheckoutMetodePembayaranFragment.total_tagihan.setText(CommonUtilities.getCurrencyFormat(gtotal.getGrandtotal(), "Rp. "));
        ProsesCheckoutMetodePembayaranFragment.listview.setAdapter(selectBankAdapter);
    }

    public void setInitialKonfirmasiPesanan() {
        ProsesCheckoutKonfirmasiPemesananFragment.listview_order.setAdapter(pesananAdapter);
    }

    public void loadDefaultAlamat() {
        if (data_alamat != null) loadFieldAlamat(data_alamat, true);
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

        if (load_dropship) {
            ProsesCheckoutAlamatKirimFragment.checkbox_isdropship.setChecked(selected_alamat.getIs_dropship());
            ProsesCheckoutAlamatKirimFragment.edit_dropship_name.setText(selected_alamat.getDropship_name());
            ProsesCheckoutAlamatKirimFragment.edit_dropship_phone.setText(selected_alamat.getDropship_phone());
            ProsesCheckoutAlamatKirimFragment.linear_dropship_name.setVisibility(selected_alamat.getIs_dropship() ? View.VISIBLE : View.GONE);
            ProsesCheckoutAlamatKirimFragment.linear_dropship_phone.setVisibility(selected_alamat.getIs_dropship() ? View.VISIBLE : View.GONE);

            ProsesCheckoutAlamatKirimFragment.edit_email_notifikasi.setText(selected_alamat.getEmail_notifikasi());
            ProsesCheckoutAlamatKirimFragment.linear_email_notifikasi.setVisibility(data.getId() == 0 ? View.VISIBLE : View.GONE);
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
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseProvince> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
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
                        Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
                        sendBroadcast(i);
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseCity> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
                        sendBroadcast(i);
                    }
                });
            } else {
                Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
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
                        Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
                        sendBroadcast(i);
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseSubdistrict> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
                        sendBroadcast(i);
                    }
                });

            } else {
                Intent i = new Intent("gomocart.application.com.gomocart.LOAD_EXPEDISI_LIST");
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

                fragment = new ProsesCheckoutKurirPengirimanFragment();
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

                    title.setText(getResources().getString(R.string.delivery_add));
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

                    title.setText(getResources().getString(R.string.shipping_method));
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

                    title.setText(getResources().getString(R.string.payment_method));
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

                    title.setText(getResources().getString(R.string.order_confirmation));
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

        if (ProsesCheckoutAlamatKirimFragment.edit_nama.getText().toString().length() == 0) {
            return "Nama harus diisi.";
        }

        if (ProsesCheckoutAlamatKirimFragment.edit_alamat.getText().toString().length() == 0) {
            return "Alamat harus diisi.";
        }

        if (province_id == 0) {
            return "Propinsi harus harus diisi.";
        }

        if (city_id == 0) {
            return "Kota harus diisi.";
        }

        /*if (subdistrict_id == 0) {
            return "Kecamatan harus diisi.";
        }*/

        if (ProsesCheckoutAlamatKirimFragment.edit_nohp.getText().toString().length() == 0) {
            return "No HP harus diisi.";
        }

        if (ProsesCheckoutAlamatKirimFragment.edit_nohp.getText().toString().length() < 10) {
            return "No HP harus diisi dengan benar.";
        }

        if (ProsesCheckoutAlamatKirimFragment.edit_kodepos.getText().toString().length() < 5) {
            return "Kode pos harus diisi dengan benar.";
        }

        return "";
    }

    public String checkedKurirPengirimanBeforeNext() {
        if (data_ongkir == null || data_ongkir.getId_kurir() == 0) {
            return "Kurir pengiriman belum dipilih.";
        }

        updateGrandTotal();

        return "";
    }

    public String checkedMetodePembayaranBeforeNext() {
        if (data_bank == null || data_bank.getId() == 0) {
            metode_pembayaran_id = 1;
            return "Pilih salah bank pembayaran.";
        }

        return "";
    }

    public void saveAlamat() {

        String nama = ProsesCheckoutAlamatKirimFragment.edit_nama.getText().toString();
        String alamat = ProsesCheckoutAlamatKirimFragment.edit_alamat.getText().toString();
        String province = ProsesCheckoutAlamatKirimFragment.edit_province.getText().toString();
        String city_name = ProsesCheckoutAlamatKirimFragment.edit_city.getText().toString();
        String subdistrict_name = ProsesCheckoutAlamatKirimFragment.edit_state.getText().toString();
        String kode_pos = ProsesCheckoutAlamatKirimFragment.edit_kodepos.getText().toString();
        String no_hp = ProsesCheckoutAlamatKirimFragment.edit_nohp.getText().toString();

        boolean is_dropship = ProsesCheckoutAlamatKirimFragment.checkbox_isdropship.isChecked();
        String dropship_name = ProsesCheckoutAlamatKirimFragment.edit_dropship_name.getText().toString().trim();
        String dropship_phone = ProsesCheckoutAlamatKirimFragment.edit_dropship_phone.getText().toString().trim();
        String email_notifikasi = ProsesCheckoutAlamatKirimFragment.edit_email_notifikasi.getText().toString().trim();

        data_alamat = new alamat(0, nama, alamat, data_alamat.getLatitude(),data_alamat.getLongitude(), province_id, province, city_id, city_name, subdistrict_id, subdistrict_name, kode_pos, no_hp, false, is_dropship, dropship_name, dropship_phone, email_notifikasi);
    }

    public void loadDataOngkir() {

        if (subdistrictChanged) {
            ongkirlist = new ArrayList<>();
            selectOngkirAdapter.UpdateSelectOngkirAdapter(ongkirlist);
            new loadDataOngkir().execute();
        } else {
            ProsesCheckoutKurirPengirimanFragment.loading.setVisibility(View.GONE);
        }
    }
    public class loadDataOngkir extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ProsesCheckoutKurirPengirimanFragment.loading.setVisibility(View.VISIBLE);
            ProsesCheckoutKurirPengirimanFragment.retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {

            Log.e("KURUR", data.getId() + "\n" +
                    marketSelected.getSimitra().getCity_id()+ "\n" +
                    data_alamat.getCity_id()+ "\n" +
                    data_alamat.getSubdistrict_id()+ "\n" +
                    marketSelected.getTotal_berat()+ "\n" +
                    marketSelected.getTotal_jumlah()+"");

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseOngkirStore> ongkirStoreCall = api.postOngkirList(
                    data.getId()+"",
                    marketSelected.getSimitra().getCity_id()+"",
                    data_alamat.getCity_id()+"",
                    data_alamat.getSubdistrict_id()+"",
                    marketSelected.getTotal_berat()+"",
                    marketSelected.getTotal_jumlah()+""
            );
            ongkirStoreCall.enqueue(new Callback<ResponseOngkirStore>() {
                @Override
                public void onResponse(@NonNull Call<ResponseOngkirStore> call, @NonNull Response<ResponseOngkirStore> response) {

                    ongkirlist  = Objects.requireNonNull(response.body()).getOngkirlist();
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_CHECKOUT_ONGKIR");
                    i.putExtra("success", true);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseOngkirStore> call, @NonNull Throwable t) {
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_CHECKOUT_ONGKIR");
                    i.putExtra("success", false);
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    public void loadDataBank() {

        if (banklist.size() == 0) {
            new loadDataBank().execute();
        } else {
            ProsesCheckoutMetodePembayaranFragment.loading.setVisibility(View.GONE);
        }
    }

    public class loadDataBank extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ProsesCheckoutMetodePembayaranFragment.loading.setVisibility(View.VISIBLE);
            ProsesCheckoutMetodePembayaranFragment.retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseBankStore> bankStoreCall = api.postBankList(data.getId()+"");
            bankStoreCall.enqueue(new Callback<ResponseBankStore>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBankStore> call, @NonNull Response<ResponseBankStore> response) {

                    banklist = Objects.requireNonNull(response.body()).getBanklist();
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_CHECKOUT_BANK");
                    i.putExtra("success", true);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBankStore> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_CHECKOUT_BANK");
                    i.putExtra("success", false);
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    public int getOngkir_index() {
        return this.ongkir_index;
    }

    public void setOngkir(int index, ongkir dataongkir) {
        ongkir_index = index;
        data_ongkir = dataongkir;
    }
    
    public int getBank_index() {
        return this.bank_index;
    }

    public void setBank(int index, bank databank) {
        bank_index = index;
        data_bank = databank;
    }

    String getStringCartList() {
        String result = "";

        for(produk item: pesananlist) {
            result+=(result.length()>0?"\n":"")+
                    item.getId() + "\t" +
                    item.getKode() + "\t" +
                    item.getNama() + "\t" +
                    item.getFoto1_produk() + "\t" +
                    item.getUkuran() + "\t" +
                    item.getWarna() + "\t" +
                    item.getQty() + "\t" +
                    item.getBerat() + "\t" +
                    item.getHarga_beli() + "\t" +
                    item.getHarga_jual() + "\t" +
                    item.getHarga_grosir() + "\t" +
                    item.getHarga_diskon() + "\t" +
                    item.getPersen_diskon() + "\t" +
                    item.getSubtotal() + "\t" +
                    item.getGrandtotal() + "\t" +
                    item.getMitra().getId();
        }


        return result;
    }

    public void submitOrder() {
        new prosesSubmitOrder().execute();
    }

    public class prosesSubmitOrder extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading(false);
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseSubmitTransaksi> submitTransaksiCall = api.postSubmitTransaksi(
                    data.getId()+"",
                    marketSelected.getSimitra().getId()+"",
                    marketSelected.getKode_trx()+"",
                    marketSelected.getId()+"",

                    data.getKeyUserId()+"",
                    data.getKeyEntityCd()+"",
                    (marketSelected.getId()==0?"140000000":(marketSelected.getId()==1?data.getKeyEntityCd():""))+"",

                    gtotal.getGrandtotal()+"",

                    //ALAMAT PENGIRIMAN
                    data_alamat.getNama()+"",
                    data_alamat.getAlamat()+"",
                    data_alamat.getLatitude()+"",
                    data_alamat.getLongitude()+"",
                    data_alamat.getProvince_id()+"",
                    data_alamat.getProvince()+"",
                    data_alamat.getCity_id()+"",
                    data_alamat.getCity_name()+"",
                    data_alamat.getSubdistrict_id() + "",
                    data_alamat.getSubdistrict_name()+"",
                    data_alamat.getKode_pos()+"",
                    data_alamat.getNo_hp()+"",

                    //KURIR PENGIRIMAN
                    data_ongkir.getId_kurir() + "",
                    data_ongkir.getKode_kurir()+"",
                    data_ongkir.getNama_kurir()+"",
                    data_ongkir.getKode_service()+"",
                    data_ongkir.getNama_service()+"",
                    data_ongkir.getNominal() + "",
                    data_ongkir.getEtd()+"",
                    data_ongkir.getTarif()+"",

                    //METODE PEMBAYARAN
                    metode_pembayaran_id + "",
                    (metode_pembayaran_id == 1 ? data_bank.getId() : "") + "",
                    (metode_pembayaran_id == 1 ? data_bank.getNo_rekening() : "") + "",
                    (metode_pembayaran_id == 1 ? data_bank.getNama_pemilik_rekening() : "") + "",
                    (metode_pembayaran_id == 1 ? data_bank.getNama_bank() : "") + "",
                    (metode_pembayaran_id == 1 ? data_bank.getCabang() : "") + "",
                    (metode_pembayaran_id == 1 ? data_bank.getGambar() : "") + "",

                    //DATA VOUCHER
                    (data_voucher != null?(data_voucher.getKode() + "\t" + data_voucher.getNominal() + "\t" + data_voucher.getTipe_voucher() + "\t" + data_voucher.getJenis_voucher()):"") + "",

                    //DETAIL ORDERS
                    getStringCartList()+""
            );
            submitTransaksiCall.enqueue(new Callback<ResponseSubmitTransaksi>() {
                @Override
                public void onResponse(@NonNull Call<ResponseSubmitTransaksi> call, @NonNull Response<ResponseSubmitTransaksi> response) {

                    boolean success = Objects.requireNonNull(response.body()).getSuccess();
                    String message  = Objects.requireNonNull(response.body()).getMessage();
                    checkoutUrl     = Objects.requireNonNull(response.body()).getCheckoutUrl();
                    id_trx          = Objects.requireNonNull(response.body()).getId_trx();

                    if (success) {
                        dh.deleteCartlist(marketSelected.getKode_trx(), marketSelected.getId(), marketSelected.getSimitra().getId());
                    }

                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_SUBMIT_ORDER");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseSubmitTransaksi> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_SUBMIT_ORDER");
                    i.putExtra("success", false);
                    i.putExtra("message", "Proses checkout gagal. Silahkan coba lagi.");
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    /*public void prosesPembayaran() {
        new danaCreateOrder().execute();
    }

    public class danaCreateOrder extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading(false);
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseDanaCreateOrder> danaCreateOrderCall = api.postDanaCreateOrder(
                    id_trx
            );
            danaCreateOrderCall.enqueue(new Callback<ResponseDanaCreateOrder>() {
                @Override
                public void onResponse(@NonNull Call<ResponseDanaCreateOrder> call, @NonNull Response<ResponseDanaCreateOrder> response) {

                    boolean success      = Objects.requireNonNull(response.body()).getSuccess();
                    String message       = Objects.requireNonNull(response.body()).getMessage();
                    String acquirementId = Objects.requireNonNull(response.body()).getAcquirementId();
                    String checkoutUrl   = Objects.requireNonNull(response.body()).getCheckoutUrl();

                    Intent i = new Intent("gomocart.application.com.gomocart.DANA_CREATE_ORDER_CHECKOUT");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    i.putExtra("acquirementId", acquirementId);
                    i.putExtra("checkoutUrl", checkoutUrl);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseDanaCreateOrder> call, @NonNull Throwable t) {

                    Intent i = new Intent("gomocart.application.com.gomocart.DANA_CREATE_ORDER_CHECKOUT");
                    i.putExtra("success", false);
                    i.putExtra("message", "Tidak bisa terhubung dengan server.");
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }*/


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

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseCheckVoucher> checkVoucherCall = api.postCheckVoucher(
                    data.getId()+"",
                    ProsesCheckoutKonfirmasiPemesananFragment.kode_voucher.getText().toString()+""
            );
            checkVoucherCall.enqueue(new Callback<ResponseCheckVoucher>() {
                @Override
                public void onResponse(@NonNull Call<ResponseCheckVoucher> call, @NonNull Response<ResponseCheckVoucher> response) {

                    boolean success = Objects.requireNonNull(response.body()).getSuccess();
                    String message  = Objects.requireNonNull(response.body()).getMessage();
                    if(success) {
                        data_voucher = Objects.requireNonNull(response.body()).getDetail_voucher();
                    }

                    Intent i = new Intent("gomocart.application.com.gomocart.CEK_VOUCHER");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseCheckVoucher> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.CEK_VOUCHER");
                    i.putExtra("success", false);
                    i.putExtra("message", "Check Voucher gagal. Silahkan coba lagi.");
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    void updateGrandTotal() {

        double subtotal = gtotal.getSubtotal();
        double ongkir   = data_ongkir.getNominal();

        double voucher = 0;
        if (data_voucher != null) {
            voucher = (data_voucher.getTipe_voucher().equalsIgnoreCase("persentase") ? ((data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") ? ongkir : subtotal) * (data_voucher.getNominal() * 0.01)) : data_voucher.getNominal());
            if (data_voucher.getJenis_voucher().equalsIgnoreCase("ongkir") && voucher > ongkir) {
                voucher = ongkir;
            }

            if (data_voucher.getJenis_voucher().equalsIgnoreCase("belanja") && voucher > subtotal) {
                voucher = subtotal;
            }
        }

        double grandtot = subtotal - voucher + ongkir;
        gtotal = new grandtotal(gtotal.getTotal(), gtotal.getDiskon(), subtotal, voucher, ongkir, grandtot);
    }

    public void loadDataPesanan() {

        //ALAMAT PENGIRIMAN
        ProsesCheckoutKonfirmasiPemesananFragment.alamat.setText(data_alamat.getNama() + "\n" + data_alamat.getAlamat() + " " + data_alamat.getKode_pos() + "\n" + data_alamat.getSubdistrict_name() + ", " + data_alamat.getCity_name() + "\n" + data_alamat.getProvince() + "\nTelepon: " + data_alamat.getNo_hp());

        //EXPEDISI PENGIRIMAN
        loadOngkirLogo(data_ongkir.getGambar());
        ProsesCheckoutKonfirmasiPemesananFragment.ekspedisi.setText(data_ongkir.getNama_kurir() + "\n" + data_ongkir.getNama_service()+(data_ongkir.getEtd().trim().length()>0?" ("+data_ongkir.getEtd().trim()+" hari)":"") +" "+data_ongkir.getTarif());

        //PEMBAYARAN
        loadPembayaranLogo(data_bank.getGambar());
        ProsesCheckoutKonfirmasiPemesananFragment.pembayaran.setText(data_bank.getNama_bank() + "\n" + data_bank.getNo_rekening() + " an. " + data_bank.getNama_pemilik_rekening());

        //DETAIL ORDER
        pesananAdapter.UpdatePesananAdapter(pesananlist);

        //GRAND TOTAL
        loadFieldGrandTotal();
    }

    public void loadFieldGrandTotal() {
        ProsesCheckoutKonfirmasiPemesananFragment.total.setText(CommonUtilities.getCurrencyFormat(gtotal.getTotal(), ""));
        ProsesCheckoutKonfirmasiPemesananFragment.ongkir.setText(CommonUtilities.getCurrencyFormat(gtotal.getOngkir(), ""));
        ProsesCheckoutKonfirmasiPemesananFragment.voucher.setText(CommonUtilities.getCurrencyFormat(gtotal.getVoucher(), ""));
        ProsesCheckoutKonfirmasiPemesananFragment.grandtotal.setText(CommonUtilities.getCurrencyFormat(gtotal.getGrandtotal(), ""));
    }

    public void loadOngkirLogo(String gambar) {
        String server = CommonUtilities.SERVER_URL;
        String url = server + "/uploads/ekspedisi/" + gambar;
        imageLoader.displayImage(url, ProsesCheckoutKonfirmasiPemesananFragment.image_ekspedisi, imageOptionDefault);
    }

    public void loadPembayaranLogo(String gambar) {
        String server = CommonUtilities.SERVER_URL;
        String url = server + "/uploads/bank/" + gambar;
        imageLoader.displayImage(url, ProsesCheckoutKonfirmasiPemesananFragment.image_pembayaran, imageOptionDefault);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (menu_selected == 4) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else {
                menu_selected--;
                if (menu_selected < 0) {
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