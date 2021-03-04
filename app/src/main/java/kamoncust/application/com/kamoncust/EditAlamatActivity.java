package kamoncust.application.com.kamoncust;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import android.widget.EditText;
import android.widget.TextView;

import com.shivtechs.maplocationpicker.LocationPickerActivity;
import com.shivtechs.maplocationpicker.MapUtility;

import kamoncust.application.com.data.RestApi;
import kamoncust.application.com.data.RetroFit;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.DatabaseHandler;
import kamoncust.application.com.model.ResponseCity;
import kamoncust.application.com.model.ResponseProvince;
import kamoncust.application.com.model.ResponseSaveAlamat;
import kamoncust.application.com.model.ResponseSubdistrict;
import kamoncust.application.com.model.alamat;
import kamoncust.application.com.model.city;
import kamoncust.application.com.model.province;
import kamoncust.application.com.model.subdistrict;
import kamoncust.application.com.model.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAlamatActivity extends AppCompatActivity {

    Context context;

    int province_id = 0;
    int city_id = 0;
    int subdistrict_id = 0;
    double latitude;
    double longitude;

    EditText edit_nama;
    EditText edit_alamat;
    TextView select_location;
    
    EditText edit_province;
    EditText edit_city;
    EditText edit_state;
    EditText edit_kodepos;
    EditText edit_nohp;
    CheckBox chbox_default;
    
    TextView simpan;

    ArrayList<province> listProvince = new ArrayList<>();
    ArrayList<city> listCity = new ArrayList<>();
    ArrayList<subdistrict> listSubDistrict = new ArrayList<>();

    float downX = 0, downY = 0, upX, upY;
    Dialog dialog_listview;
    ListView listview;
    String action;
    DatabaseHandler dh;
    user data;
    alamat alamatSelected;

    ImageView back;
    Dialog dialog_loading;

    Dialog dialog_informasi;
    TextView btn_ok;
    TextView text_title;
    TextView text_informasi;

    public static final int ADDRESS_PICKER_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        MapUtility.apiKey = getResources().getString(R.string.google_maps_key);

        context = EditAlamatActivity.this;
        data = CommonUtilities.getSettingUser(context);
        dh = new DatabaseHandler(context);

        back          = findViewById(R.id.back);
        
        edit_nama     = findViewById(R.id.edit_nama);
        edit_alamat   = findViewById(R.id.edit_alamat);
        select_location = findViewById(R.id.select_location);
        edit_province = findViewById(R.id.edit_provice);
        edit_city     = findViewById(R.id.edit_city);
        edit_state    = findViewById(R.id.edit_kecamatan);
        edit_kodepos  = findViewById(R.id.edit_kodepos);
        edit_nohp     = findViewById(R.id.edit_phone);
        chbox_default = findViewById(R.id.checkboxdefault);
        
        simpan        = findViewById(R.id.save);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLocation();
            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = checkedBeforeNext();
                if(message.length()==0) {
                    new prosesSave().execute();
                } else {
                    openDialogMessage(message, false);
                }

            }
        });
        
        edit_alamat.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
                            Intent intent = new Intent(context, LocationPickerActivity.class);
                            startActivityForResult(intent, ADDRESS_PICKER_REQUEST);    
                        }

                        break;
                }

                return false;
            }
        });
        
        edit_province.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
                            action = "province";
                            loadListArray();
                            dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog_listview.show();
                        }

                        break;
                }

                return false;
            }
        });

        edit_city.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
                            if(edit_province.getText().toString().length()==0) {
                                openDialogMessage("Propinsi tujuan harus diisi!", false);
                            } else {
                                loadDialogListView("city");
                            }
                        }

                        break;
                }

                return false;
            }
        });

        edit_state.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = event.getX();
                        downY = event.getY();

                        break;

                    case MotionEvent.ACTION_UP:
                        upX = event.getX();
                        upY = event.getY();
                        float deltaX = downX - upX;
                        float deltaY = downY - upY;

                        if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
                            if(edit_city.getText().toString().length()==0) {
                                openDialogMessage("Kabupaten / kota tujuan harus diisi!", false);
                            } else {
                                loadDialogListView("subdistrict");
                            }
                        }

                        break;
                }

                return false;
            }
        });

        dialog_listview = new Dialog(context);
        dialog_listview.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_listview.setCancelable(true);
        dialog_listview.setContentView(R.layout.list_dialog);

        listview = (ListView) dialog_listview.findViewById(R.id.listViewDialog);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog_listview.dismiss();
                if(action.equalsIgnoreCase("province")) {
                    edit_province.setText(listProvince.get(position).getProvince());
                    edit_city.setText("");
                    edit_state.setText("");

                    province_id = listProvince.get(position).getProvince_id();
                    city_id = 0;
                    subdistrict_id = 0;

                    new loadCity().execute();
                    new loadSubdistrict().execute();

                } else if(action.equalsIgnoreCase("city")) {
                    edit_city.setText(listCity.get(position).getCity_name());
                    edit_state.setText("");

                    city_id = listCity.get(position).getCity_id();
                    subdistrict_id = 0;

                    new loadSubdistrict().execute();

                } else if(action.equalsIgnoreCase("subdistrict")) {
                    edit_state.setText(listSubDistrict.get(position).getSubdistrict_name());
                    subdistrict_id = listSubDistrict.get(position).getSubdistrict_id();
                }
                action = "";
            }
        });

        new loadProvince().execute();
        
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

        if(savedInstanceState==null) {
            alamatSelected = (alamat) getIntent().getSerializableExtra("alamat");
            if(alamatSelected!=null) {
                loadFieldAlamat(alamatSelected);
            }

            TextView title = findViewById(R.id.title);
            title.setText(alamatSelected==null?"TAMBAH ALAMAT":"EDIT ALAMAT");
        }
    }

    void selectLocation() {
        Intent intent = new Intent(context, LocationPickerActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        intent.putExtra("userAddress", edit_alamat.getText().toString());
        intent.putExtra("currentLatitude", latitude);
        intent.putExtra("currentLongitude", longitude);
        startActivityForResult(intent, ADDRESS_PICKER_REQUEST);
    }

    public void openDialogMessage(String message, boolean status) {
        text_informasi.setText(message);
        text_title.setText(status?"BERHASIL":"KESALAHAN");
        dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_informasi.show();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadEkspedisiReceiver);
            unregisterReceiver(mHandleProsesSaveAlamatReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadEkspedisiReceiver);
            unregisterReceiver(mHandleProsesSaveAlamatReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadEkspedisiReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST"));
        registerReceiver(mHandleProsesSaveAlamatReceiver, new IntentFilter("kamoncust.application.com.kamoncust.PROSES_SAVE_ALAMAT"));
        super.onResume();
    }

    private final BroadcastReceiver mHandleProsesSaveAlamatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context contx, Intent intn) {

            Boolean success = intn.getBooleanExtra("success", false);
            String message = intn.getStringExtra("message");

            dialog_loading.dismiss();
            if (success) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                i.putExtra("alamat", alamatSelected);
                ((EditAlamatActivity) context).setResult(RESULT_OK, i);
                finish();

            } else {
                text_informasi.setText(message);
                text_title.setText("KESALAHAN");
                dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_informasi.show();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data_intent);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADDRESS_PICKER_REQUEST: {

                    String address = data_intent.getStringExtra(MapUtility.ADDRESS);
                    latitude = data_intent.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                    longitude = data_intent.getDoubleExtra(MapUtility.LONGITUDE, 0.0);
                    edit_alamat.setText(address);

                    break;
                }
            }
        }
    }
    public void loadDialogListView(String act) {
        action = act;
        if(action.equalsIgnoreCase("province") && listProvince.size()==0) {
            openDialogLoading(true);
        } else if(action.equalsIgnoreCase("city") && listCity.size()==0) {
            openDialogLoading(true);
        } else if(action.equalsIgnoreCase("subdistrict") && listSubDistrict.size()==0) {
            openDialogLoading(true);
        } else {
            loadListArray();
            dialog_listview.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog_listview.show();
        }
    }


    public void openDialogLoading(boolean cancelable) {
        dialog_loading.setCancelable(cancelable);
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }

    private String checkedBeforeNext() {
        
        if(edit_nama.getText().toString().length()==0) {
            return "Nama harus diisi.";
        }

        if(edit_alamat.getText().toString().length()==0) {
            return "Alamat harus diisi.";
        }

        if(edit_nohp.getText().toString().length()==0) {
            return "No HP harus diisi.";
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

        return "";
    }

    private void loadFieldAlamat(alamat selected_alamat) {

        edit_nama.setText(selected_alamat.getNama());
        edit_alamat.setText(selected_alamat.getAlamat());
        edit_province.setText(selected_alamat.getProvince());
        province_id = selected_alamat.getProvince_id();
        edit_city.setText(selected_alamat.getCity_name());
        city_id = selected_alamat.getCity_id();
        edit_state.setText(selected_alamat.getSubdistrict_name());
        subdistrict_id = selected_alamat.getSubdistrict_id();
        edit_kodepos.setText(selected_alamat.getKode_pos());
        edit_nohp.setText(selected_alamat.getNo_hp());
        chbox_default.setChecked(selected_alamat.getAsDefaultAlamat());

        latitude = selected_alamat.getLatitude();
        longitude = selected_alamat.getLongitude();

        new loadCity().execute();
        new loadSubdistrict().execute();
    }

    public class prosesSave extends AsyncTask<String, Void, Void> {

        int id;
        boolean success;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading(false);
        }

        @Override
        protected Void doInBackground(String... urls) {

            id = 0;
            success = false;
            message = "Proses simpan data gagal. Silahkan coba lagi!";

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseSaveAlamat> saveAlamatCall = api.postSaveAlamat(
                alamatSelected!=null?alamatSelected.getId()+"":"0",
                data.getId()+"",
                edit_nama.getText().toString(),
                edit_alamat.getText().toString(),
                String.valueOf(latitude),
                String.valueOf(longitude),
                province_id+"",
                edit_province.getText().toString(),
                city_id+"",
                edit_city.getText().toString(),
                subdistrict_id+"",
                edit_state.getText().toString(),
                edit_kodepos.getText().toString(),
                edit_nohp.getText().toString(),
                chbox_default.isChecked()?"1":"0"
            );
            saveAlamatCall.enqueue(new Callback<ResponseSaveAlamat>() {
                @Override
                public void onResponse(@NonNull Call<ResponseSaveAlamat> call, @NonNull Response<ResponseSaveAlamat> response) {

                    id = Objects.requireNonNull(response.body()).getId();
                    success = Objects.requireNonNull(response.body()).getSuccess();
                    message = Objects.requireNonNull(response.body()).getMessage();

                    alamatSelected = new alamat(
                            id,
                            edit_nama.getText().toString(),
                            edit_alamat.getText().toString(),
                            latitude,
                            longitude,
                            province_id,
                            edit_province.getText().toString(),
                            city_id,
                            edit_city.getText().toString(),
                            subdistrict_id,
                            edit_state.getText().toString(),
                            edit_kodepos.getText().toString(),
                            edit_nohp.getText().toString(),
                            chbox_default.isChecked(),
                            false, "", "", ""
                    );

                    Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_SAVE_ALAMAT");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);

                }

                @Override
                public void onFailure(@NonNull Call<ResponseSaveAlamat> call, @NonNull Throwable t) {

                    Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_SAVE_ALAMAT");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);
                }
            });

            return null;
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

    private void loadListArray() {
        String[] from = new String[] { getResources().getString(R.string.list_dialog_title) };
        int[] to = new int[] { R.id.txt_title };

        List<HashMap<String, String>> fillMaps = new ArrayList<HashMap<String, String>>();
        if(action.equalsIgnoreCase("province")) {
            for (province data : listProvince) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getProvince());

                fillMaps.add(map);
            }
        } else if(action.equalsIgnoreCase("city")) {
            for (city data : listCity) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getCity_name());

                fillMaps.add(map);
            }
        } else if(action.equalsIgnoreCase("subdistrict")) {
            for (subdistrict data : listSubDistrict) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(getResources().getString(R.string.list_dialog_title), data.getSubdistrict_name());

                fillMaps.add(map);
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(context, fillMaps, R.layout.item_list_dialog, from, to);
        listview.setAdapter(adapter);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();

            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
