package kamoncust.application.com.kamoncust;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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

import com.alexzh.circleimageview.CircleImageView;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.shivtechs.maplocationpicker.LocationPickerActivity;
import com.shivtechs.maplocationpicker.MapUtility;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kamoncust.application.com.data.RestApi;
import kamoncust.application.com.data.RetroFit;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.GalleryFilePath;
import kamoncust.application.com.libs.MyImageDownloader;
import kamoncust.application.com.model.ResponseCity;
import kamoncust.application.com.model.ResponseProvince;
import kamoncust.application.com.model.ResponseSignUp;
import kamoncust.application.com.model.ResponseSubdistrict;
import kamoncust.application.com.model.city;
import kamoncust.application.com.model.province;
import kamoncust.application.com.model.subdistrict;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarActivity extends AppCompatActivity {

    Context context;
    final int ADDRESS_PICKER_REQUEST = 2;
    final int REQUEST_FROM_GALLERY = 5;
    final int REQUEST_FROM_CAMERA  = 6;

    ImageView back;
    CircleImageView image;
    TextView upload;
    EditText edit_nama;
    EditText edit_nohp;
    EditText edit_email;
    EditText edit_username;
    EditText edit_referensi;
    EditText edit_password;
    EditText edit_konfirmasi;
    EditText edit_alamat;
    TextView select_location;
    EditText edit_province;
    EditText edit_city;
    EditText edit_state;
    EditText edit_kodepos;
    CheckBox checkbox_setuju;
    TextView termc;
    TextView submit;

    Dialog dialog_loading;

    Dialog dialog_informasi;
    TextView btn_ok;
    TextView text_title;
    TextView text_informasi;

    int province_id = 0;
    int city_id = 0;
    int subdistrict_id = 0;
    double latitude;
    double longitude;

    ArrayList<province> listProvince = new ArrayList<>();
    ArrayList<city> listCity = new ArrayList<>();
    ArrayList<subdistrict> listSubDistrict = new ArrayList<>();

    float downX = 0, downY = 0, upX, upY;
    Dialog dialog_listview;
    ListView listview;
    String action;

    ImageLoader imageLoader;
    DisplayImageOptions imageOptions;
    Dialog dialog_pilih_gambar;
    TextView from_camera, from_galery;
    Uri mImageCaptureUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        MapUtility.apiKey = getResources().getString(R.string.google_maps_key);

        context = DaftarActivity.this;
        image           = findViewById(R.id.image);
        upload          = findViewById(R.id.upload);
        edit_nama       = findViewById(R.id.edit_nama);
        edit_nohp       = findViewById(R.id.edit_phone);
        edit_email      = findViewById(R.id.edit_email);
        edit_username   = findViewById(R.id.edit_username);
        edit_referensi  = findViewById(R.id.edit_referensi);
        edit_password   = findViewById(R.id.edit_password);
        edit_konfirmasi = findViewById(R.id.edit_konfirmasi);

        edit_alamat     = findViewById(R.id.edit_alamat);
        select_location = findViewById(R.id.select_location);
        edit_province   = findViewById(R.id.edit_provice);
        edit_city       = findViewById(R.id.edit_city);
        edit_state      = findViewById(R.id.edit_kecamatan);
        edit_kodepos    = findViewById(R.id.edit_kodepos);
        checkbox_setuju = findViewById(R.id.checkbox_setuju);
        termc           = findViewById(R.id.termc);
        submit          = findViewById(R.id.submit);
        back            = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectLocation();
            }
        });

        termc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TermKondisiActivity.class);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = checkedBeforeNext();
                if(message.length()==0) {
                    new prosesSingUp().execute();
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
                            selectLocation();
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
                .denyCacheImageMultipleSizesInMemory()
                //.discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new MyImageDownloader(context))
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        imageOptions = CommonUtilities.getOptionsImage(R.drawable.userdefault, R.drawable.userdefault);

        new loadProvince().execute();
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

    public void openDialogMessage(String message, boolean status) {
        text_informasi.setText(message);
        text_title.setText(status?"BERHASIL":"KESALAHAN");
        dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_informasi.show();
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

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadEkspedisiReceiver);
            unregisterReceiver(mHandleSignUpReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadEkspedisiReceiver);
            unregisterReceiver(mHandleSignUpReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadEkspedisiReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_EXPEDISI_LIST"));
        registerReceiver(mHandleSignUpReceiver, new IntentFilter("kamoncust.application.com.kamoncust.PROSES_SIGN_UP"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleSignUpReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctx, Intent intent) {
            Boolean success = intent.getBooleanExtra("success", false);
            String message = intent.getStringExtra("message");

            dialog_loading.dismiss();
            if(success) {
                mImageCaptureUri = null;
                CommonUtilities.setNoHpAktivasi(context, edit_nohp.getText().toString());
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                DaftarActivity.this.setResult(RESULT_OK, new Intent());
                finish();
            } else {
                openDialogMessage(message, success);
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

    private String checkedBeforeNext() {

        if(edit_nama.getText().toString().length()==0) {
            return "Nama harus diisi.";
        }

        if(edit_nohp.getText().toString().length()==0) {
            return "No. HP harus diisi.";
        }

        if(edit_email.getText().toString().length()==0) {
            return "Email harus diisi.";
        }

        if(edit_username.getText().toString().length()==0) {
            return "Username harus diisi.";
        }

        if (edit_password.getText().toString().length() == 0) {
            return "Password harus diisi.";
        }

        if(!edit_konfirmasi.getText().toString().equalsIgnoreCase(edit_password.getText().toString())) {
            return "Konfirmasi password tidak sesuai.";
        }

        if(edit_alamat.getText().toString().length()==0) {
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

        if(edit_kodepos.getText().toString().length()==0) {
            return "Kode pos harus diisi.";
        }

        return "";
    }

    class prosesSingUp extends AsyncTask<String, Void, Void> {

        boolean success;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading(true);
        }

        @Override
        protected Void doInBackground(String... urls) {
            success = false;
            message = "Proses daftar gagal. Coba lagi.";

            MultipartBody.Part body = null;
            if(mImageCaptureUri!=null) {
                File file = new File(mImageCaptureUri.getPath());
                //Log.d("FILE SEND", mImageCaptureUri.getPath());

                // create RequestBody instance from file
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

                // MultipartBody.Part is used to send also the actual file name
                body = MultipartBody.Part.createFormData("photo", file.getName(), requestFile);
            }

            String nama_lengkap = edit_nama.getText().toString().trim();
            String[] temps = nama_lengkap.split(" ");
            String firstname = temps[0];
            String lastname  = "";
            for(int i=1; i<temps.length; i++) {
                lastname+=temps[i]+" ";
            }
            lastname = lastname.trim();

            RequestBody firstName     = RequestBody.create(MediaType.parse("text/plain"), firstname);
            RequestBody lastName      = RequestBody.create(MediaType.parse("text/plain"), lastname);
            RequestBody no_hp         = RequestBody.create(MediaType.parse("text/plain"), edit_nohp.getText().toString());
            RequestBody email         = RequestBody.create(MediaType.parse("text/plain"), edit_email.getText().toString());
            RequestBody username      = RequestBody.create(MediaType.parse("text/plain"), edit_username.getText().toString());
            RequestBody referensi     = RequestBody.create(MediaType.parse("text/plain"), edit_referensi.getText().toString());
            RequestBody password      = RequestBody.create(MediaType.parse("text/plain"), edit_password.getText().toString());
            RequestBody konfirmasi    = RequestBody.create(MediaType.parse("text/plain"), edit_konfirmasi.getText().toString());
            RequestBody alamat        = RequestBody.create(MediaType.parse("text/plain"), edit_alamat.getText().toString());
            RequestBody lat           = RequestBody.create(MediaType.parse("text/plain"), latitude+"");
            RequestBody lng           = RequestBody.create(MediaType.parse("text/plain"), longitude+"");
            RequestBody idPropinsi    = RequestBody.create(MediaType.parse("text/plain"), province_id+"");
            RequestBody namaPropinsi  = RequestBody.create(MediaType.parse("text/plain"), edit_province.getText().toString());
            RequestBody idKota        = RequestBody.create(MediaType.parse("text/plain"), city_id+"");
            RequestBody namaKota      = RequestBody.create(MediaType.parse("text/plain"), edit_city.getText().toString());
            RequestBody idKecamatan   = RequestBody.create(MediaType.parse("text/plain"), subdistrict_id+"");
            RequestBody namaKecamatan = RequestBody.create(MediaType.parse("text/plain"), edit_state.getText().toString());
            RequestBody kodePos       = RequestBody.create(MediaType.parse("text/plain"), edit_kodepos.getText().toString());
            RequestBody tipe          = RequestBody.create(MediaType.parse("text/plain"), "0");

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseSignUp> signUpCall = api.postSignup(
                firstName,
                lastName,
                no_hp,
                email,
                username,
                referensi,
                password,
                konfirmasi,
                alamat,
                lat,
                lng,
                idPropinsi,
                namaPropinsi,
                idKota,
                namaKota,
                idKecamatan,
                namaKecamatan,
                kodePos,
                tipe,
                body
            );

            signUpCall.enqueue(new Callback<ResponseSignUp>() {
                @Override
                public void onResponse(@NonNull Call<ResponseSignUp> call, @NonNull Response<ResponseSignUp> response) {

                    boolean success = response.body().getSuccess();
                    String message = response.body().getMessage();

                    Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_SIGN_UP");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseSignUp> call, @NonNull Throwable t) {
                    Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_SIGN_UP");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data_intent);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ADDRESS_PICKER_REQUEST: {

                    latitude = data_intent.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                    longitude = data_intent.getDoubleExtra(MapUtility.LONGITUDE, 0.0);

                    String address = data_intent.getStringExtra(MapUtility.ADDRESS);
                    edit_alamat.setText(address);

                    break;
                }
                case Crop.REQUEST_CROP: {
                    mImageCaptureUri = Crop.getOutput(data_intent);
                    image.setImageURI(Crop.getOutput(data_intent));
                    //String imageUri = GalleryFilePath.getPath(context, mImageCaptureUri);
                    //Log.e("ANALISA_FILE", imageUri);

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
            }
        }
    }
}
