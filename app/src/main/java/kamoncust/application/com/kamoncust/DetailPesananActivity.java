package kamoncust.application.com.kamoncust;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.ContactsContract;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Objects;

import android.widget.TextView;
import kamoncust.application.com.adapter.PesananAdapter;
import kamoncust.application.com.data.RestApi;
import kamoncust.application.com.data.RetroFit;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.DatabaseHandler;
import kamoncust.application.com.libs.ExpandableHeightListView;
import kamoncust.application.com.libs.MyImageDownloader;
import kamoncust.application.com.model.ResponseDetailPesanan;
import kamoncust.application.com.model.alamat;
import kamoncust.application.com.model.grandtotal;
import kamoncust.application.com.model.jadwal;
import kamoncust.application.com.model.mitra;
import kamoncust.application.com.model.order;
import kamoncust.application.com.model.produk;
import kamoncust.application.com.model.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static kamoncust.application.com.libs.CommonUtilities.getOptionsImage;

public class DetailPesananActivity extends AppCompatActivity {

    Context context;
    DatabaseHandler dh;

    alamat data_alamat;
    jadwal data_jadwal;
    mitra  data_mitra;
    grandtotal gtotal;

    //CART LIST
    public static ArrayList<produk> pesananlist = new ArrayList<>();
    public static PesananAdapter pesananAdapter;

    ImageView back;
    ScrollView detail_view;

    TextView detail_pasien;
    TextView jadwal_terapi;
    ImageView image_terapis;
    TextView detail_terapis;
    ExpandableHeightListView listview;

    TextView subtotal;
    TextView voucher;
    TextView total;

    ProgressBar loading;
    LinearLayout retry;
    TextView btnReload;

    user data;
    order data_order;

    ImageLoader imageLoader;
    DisplayImageOptions imageOptionTerapis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);

        context = DetailPesananActivity.this;
        data = CommonUtilities.getSettingUser(context);
        dh = new DatabaseHandler(context);

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

        imageOptionTerapis = getOptionsImage(R.drawable.userdefault, R.drawable.userdefault);

        back        = findViewById(R.id.back);
        detail_view = findViewById(R.id.detail_view);

        detail_pasien  = findViewById(R.id.detail_pasien);
        jadwal_terapi  = findViewById(R.id.jadwal_terapi);
        image_terapis  = findViewById(R.id.image_terapis);
        detail_terapis = findViewById(R.id.detail_terapis);
        listview       = findViewById(R.id.lisview);

        subtotal = findViewById(R.id.subtotal);
        voucher  = findViewById(R.id.voucher);
        total    = findViewById(R.id.total);

        loading   = findViewById(R.id.pgbarLoading);
        retry     = findViewById(R.id.loadMask);
        btnReload = findViewById(R.id.btnReload);

        pesananAdapter = new PesananAdapter(context, pesananlist);
        listview.setAdapter(pesananAdapter);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                loadDataPesanan();
            }
        });
        loading.setVisibility(View.INVISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        if(savedInstanceState==null) {
            data_order = (order) getIntent().getSerializableExtra("order");
        }

        loadDataPesanan();
    }

    public void loadDataPesanan() {
        new loadDataPesanan().execute();
    }

    public class loadDataPesanan extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            detail_view.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseDetailPesanan> detailPesananCall = api.postDetailPesanan(data.getId()+"", data_order.getNo_transaksi());
            detailPesananCall.enqueue(new Callback<ResponseDetailPesanan>() {
                @Override
                public void onResponse(@NonNull Call<ResponseDetailPesanan> call, @NonNull Response<ResponseDetailPesanan> response) {

                    data_alamat = Objects.requireNonNull(response.body()).getDetail_pasien();
                    data_jadwal = Objects.requireNonNull(response.body()).getJadwal_terapi();
                    data_mitra  = Objects.requireNonNull(response.body()).getDetail_mitra();
                    pesananlist = Objects.requireNonNull(response.body()).getProduklist();
                    gtotal      = Objects.requireNonNull(response.body()).getGtotal();

                    Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_PESANAN");
                    i.putExtra("success", true);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseDetailPesanan> call, @NonNull Throwable t) {

                    Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_DATA_PESANAN");
                    i.putExtra("success", false);
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadDataPesananReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadDataPesananReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadDataPesananReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_DATA_PESANAN"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadDataPesananReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Boolean success = intent.getBooleanExtra("success", false);

            //DETAIL PASIEN
            detail_pasien.setText(data_alamat.getNama()+"\n"+data_alamat.getAlamat()+ " "+data_alamat.getKode_pos()+"\n"+data_alamat.getSubdistrict_name()+", "+data_alamat.getCity_name()+"\n"+data_alamat.getProvince()+"\nTelepon: "+data_alamat.getNo_hp());

            //JADWAL TERAPI
            jadwal_terapi.setText(
                    "Tanggal: "+data_jadwal.getTanggal_terapi()+"\n"+
                    "Jam: "+data_jadwal.getJam_terapi()+"\n"+
                    "Riwayat Kesehatan: "+data_jadwal.getRiwayat_kesehatan()
            );

            //DETAIL MITRA
            loadMitraLogo(data_mitra.getPhoto());
            detail_terapis.setText(
                data_mitra.getNama() + "\n" +
                data_mitra.getAlamat() +" \n" +
                "No. HP: " + data_mitra.getNo_hp()
            );

            pesananAdapter.UpdatePesananAdapter(pesananlist);
            subtotal.setText(CommonUtilities.getCurrencyFormat(gtotal.getTotal(), ""));
            voucher.setText(CommonUtilities.getCurrencyFormat(gtotal.getVoucher(), ""));
            total.setText(CommonUtilities.getCurrencyFormat(gtotal.getGrandtotal(), ""));

            loading.setVisibility(View.GONE);
            if(success) {
                detail_view.setVisibility(View.VISIBLE);
            } else {
                retry.setVisibility(View.VISIBLE);
            }
        }
    };

    public void loadMitraLogo(String gambar) {
        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/member/"+gambar;
        imageLoader.displayImage(url, image_terapis, imageOptionTerapis);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
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
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
