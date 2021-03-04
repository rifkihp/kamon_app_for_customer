package gomocart.application.com.gomocart;

import android.app.ActivityManager;
import android.app.Dialog;
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
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.alexzh.circleimageview.CircleImageView;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Objects;

import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import gomocart.application.com.adapter.PesananAdapter;
import gomocart.application.com.data.RestApi;
import gomocart.application.com.data.RetroFit;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.ExpandableHeightListView;
import gomocart.application.com.libs.MyImageDownloader;
import gomocart.application.com.model.ResponseDetailPesanan;
import gomocart.application.com.model.ResponseUpdateTransaksi;
import gomocart.application.com.model.alamat;
import gomocart.application.com.model.bank;
import gomocart.application.com.model.grandtotal;
import gomocart.application.com.model.mitra;
import gomocart.application.com.model.ongkir;
import gomocart.application.com.model.order;
import gomocart.application.com.model.produk;
import gomocart.application.com.model.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static gomocart.application.com.libs.CommonUtilities.SERVER_URL;
import static gomocart.application.com.libs.CommonUtilities.getOptionsImage;

public class DetailPesananActivity extends AppCompatActivity {

    Context context;
    DatabaseHandler dh;

    alamat data_alamat;
    mitra data_mitra;
    ongkir data_ekspedisi;
    bank data_pembayaran;
    grandtotal gtotal;

    //CART LIST
    public static ArrayList<produk> pesananlist = new ArrayList<>();
    public static PesananAdapter pesananAdapter;

    ImageView back;

    ScrollView detail_view;

    TextView no_transaksi;
    TextView tanggal;
    TextView status;

    TextView alamat;

    CircleImageView image_mitra;
    TextView mitra;

    ImageView image_ekspedisi;
    TextView ekspedisi;

    ImageView image_pembayaran;
    TextView pembayaran;

    ExpandableHeightListView listview;

    TextView total;
    TextView ongkir;
    TextView voucher;
    TextView grandtotal;

    TextView batal;
    TextView bayar;
    TextView lacak;
    TextView terima;

    ProgressBar loading;
    LinearLayout retry;
    TextView btnReload;

    user data;
    order orderSelected;
    
    ImageLoader imageLoader;
    DisplayImageOptions imageOptions;
    DisplayImageOptions imageOptionsMitra;

    Dialog dialog_loading;

    Dialog dialog_konfirmasi;
    TextView btn_konfirmasi_no;
    TextView btn_konfirmasi_yes;
    TextView konfirmasi_title;
    TextView konfirmasi_keterangan;

    String orderStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan_gomocart);

        context = DetailPesananActivity.this;
        dh = new DatabaseHandler(context);
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
        imageOptions = getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
        imageOptionsMitra = getOptionsImage(R.drawable.userdefault, R.drawable.userdefault);

        back             =  findViewById(R.id.back);
        detail_view      =  findViewById(R.id.detail_view);
        no_transaksi     = findViewById(R.id.no_transaksi);
        tanggal          = findViewById(R.id.tanggal);
        status           = findViewById(R.id.status);
        alamat           = findViewById(R.id.alamat);
        image_mitra      = findViewById(R.id.image_mitra);
        mitra            = findViewById(R.id.mitra);
        image_ekspedisi  = findViewById(R.id.image_ekspedisi);
        ekspedisi        = findViewById(R.id.ekspedisi);
        image_pembayaran = findViewById(R.id.image_pembayaran);
        pembayaran       = findViewById(R.id.pembayaran);
        listview         = findViewById(R.id.listview);
        total            = findViewById(R.id.total);
        ongkir           = findViewById(R.id.ongkir);
        voucher          = findViewById(R.id.voucher);
        grandtotal       = findViewById(R.id.grandtotal);
        batal            = findViewById(R.id.batal);
        bayar            = findViewById(R.id.bayar);
        lacak            = findViewById(R.id.lacak);
        terima           = findViewById(R.id.terima);
        loading          = findViewById(R.id.pgbarLoading);
        retry            = findViewById(R.id.loadMask);
        btnReload        = findViewById(R.id.btnReload);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                loadDataPesanan();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent();
                //setResult(RESULT_OK, intent);
                finish();

            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus = "4";
                konfirmasi_title.setText("Batal Pesanan");
                konfirmasi_keterangan.setText("Pesanan akan dibatalkan?");

                dialog_konfirmasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_konfirmasi.show();
            }
        });

        terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderStatus = "5";
                konfirmasi_title.setText("Terima Pesanan");
                konfirmasi_keterangan.setText("Pesanan sudah diterima?");

                dialog_konfirmasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_konfirmasi.show();
            }
        });

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesPembayaran(orderSelected.getCheckout_url());
            }
        });

        lacak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtilities.showSnackbar("Maaf Fitur Lacak Pesanan Belum Bisa.", false, DetailPesananActivity.this);
            }
        });

        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);

        dialog_konfirmasi = new Dialog(context);
        dialog_konfirmasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_konfirmasi.setCancelable(true);
        dialog_konfirmasi.setContentView(R.layout.konfirmasi_dialog_gomocart);

        konfirmasi_title = dialog_konfirmasi.findViewById(R.id.text_title);
        konfirmasi_keterangan = dialog_konfirmasi.findViewById(R.id.text_dialog);

        btn_konfirmasi_yes = dialog_konfirmasi.findViewById(R.id.btn_yes);
        btn_konfirmasi_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog_konfirmasi.dismiss();
                new updateTransaksi().execute();
            }
        });

        btn_konfirmasi_no = dialog_konfirmasi.findViewById(R.id.btn_no);
        btn_konfirmasi_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog_konfirmasi.dismiss();

            }
        });

        // Wait until my scrollView is ready
        detail_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Ready, move up
                detail_view.fullScroll(View.FOCUS_UP);
            }
        });


        if(savedInstanceState==null) {
            orderSelected = (order) getIntent().getSerializableExtra("order");
        }

        Log.e("IDORDER", orderSelected.getId()+"");
        loadDataPesanan();

    }

    public void prosesPembayaran(String checkoutUrl) {
        /*Intent intent = new Intent(context, DanaPaymentActivity.class);
        intent.putExtra("checkoutUrl", checkoutUrl);
        startActivity(intent);*/
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
            Call<ResponseDetailPesanan> detailPesananCall = api.postDetailPesanan(data.getId()+"", orderSelected.getId()+"");
            detailPesananCall.enqueue(new Callback<ResponseDetailPesanan>() {
                @Override
                public void onResponse(@NonNull Call<ResponseDetailPesanan> call, @NonNull Response<ResponseDetailPesanan> response) {

                    boolean success = Objects.requireNonNull(response.body()).getSuccess();
                    if(success) {
                        data_alamat = Objects.requireNonNull(response.body()).getPengiriman();
                        data_mitra = Objects.requireNonNull(response.body()).getSimitra();
                        data_ekspedisi = Objects.requireNonNull(response.body()).getEkspedisi();
                        data_pembayaran = Objects.requireNonNull(response.body()).getPembayaran();
                        pesananlist = Objects.requireNonNull(response.body()).getProduklist();
                        gtotal = Objects.requireNonNull(response.body()).getGtotal();
                    }
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_PESANAN");
                    i.putExtra("success", success);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseDetailPesanan> call, @NonNull Throwable t) {

                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_PESANAN");
                    i.putExtra("success", false);
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    public void openDialogLoading() {
        dialog_loading.setCancelable(false);
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }

    class updateTransaksi extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            openDialogLoading();
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseUpdateTransaksi> updateTransaksiCall = api.postUpdateTransaksi(data.getId()+"", orderSelected.getId()+"", orderStatus);
            updateTransaksiCall.enqueue(new Callback<ResponseUpdateTransaksi>() {
                @Override
                public void onResponse(@NonNull Call<ResponseUpdateTransaksi> call, @NonNull Response<ResponseUpdateTransaksi> response) {

                    Boolean success = Objects.requireNonNull(response.body()).getSuccess();
                    String message = Objects.requireNonNull(response.body()).getMessage();

                    Intent i = new Intent("gomocart.application.com.gomocart.UPDATE_STATUS_TRANSAKSI");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseUpdateTransaksi> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.UPDATE_STATUS_TRANSAKSI");
                    i.putExtra("success", false);
                    i.putExtra("message", "Proses update transaksi gagal.");
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
            unregisterReceiver(mHandleUpdateTransaksiReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadDataPesananReceiver);
            unregisterReceiver(mHandleUpdateTransaksiReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadDataPesananReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_PESANAN"));
        registerReceiver(mHandleUpdateTransaksiReceiver, new IntentFilter("gomocart.application.com.gomocart.UPDATE_STATUS_TRANSAKSI"));

        super.onResume();
    }


    private final BroadcastReceiver mHandleUpdateTransaksiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context contx, Intent intent) {

            dialog_loading.dismiss();

            Boolean success = intent.getBooleanExtra("success", false);
            String message  = intent.getStringExtra("message");

            if(success) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                Intent i = new Intent();
                DetailPesananActivity.this.setResult(RESULT_OK, i);
                finish();
            } else {
                CommonUtilities.showSnackbar(message, false, DetailPesananActivity.this);
            }

        }
    };

    private final BroadcastReceiver mHandleLoadDataPesananReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Boolean success = intent.getBooleanExtra("success", false);
            
            loading.setVisibility(View.GONE);
            if(success) {

                no_transaksi.setText("#"+orderSelected.getNo_transaksi());
                tanggal.setText(orderSelected.getTgl_transaksi());
                status.setText((
                    orderSelected.getStatus()==0?"Belum Bayar":(
                    orderSelected.getStatus()==2?"Sedang Diproses":(
                    orderSelected.getStatus()==6?"Sedang Dikirim":(
                    orderSelected.getStatus()==5?"Selesai":(
                    orderSelected.getStatus()==4?"Dibatalkan":""))))));

                status.setTextColor(getResources().getColor(
                    orderSelected.getStatus()==0?R.color.blue_light_holo:(
                    orderSelected.getStatus()==2?R.color.orange:(
                    orderSelected.getStatus()==6?R.color.orange:(
                    orderSelected.getStatus()==5?R.color.grrenonion:(
                    orderSelected.getStatus()==4?R.color.red_sober:R.color.Tex)))))
                );

                //ALAMAT PENGIRIMAN
                alamat.setText(data_alamat.getNama() + "\n" + data_alamat.getAlamat() + " " + data_alamat.getKode_pos() + "\n" + data_alamat.getSubdistrict_name() + ", " + data_alamat.getCity_name() + "\n" + data_alamat.getProvince() + "\nTelepon: " + data_alamat.getNo_hp());

                //MITRA
                loadMitraLogo(data_mitra.getPhoto());
                mitra.setText(
                        data_mitra.getNama() + "\n" +
                        data_mitra.getCity_name() + "\n" +
                        data_mitra.getProvince()
                );

                //EXPEDISI PENGIRIMAN
                loadLogoEkspedisi(data_ekspedisi.getGambar());
                ekspedisi.setText(data_ekspedisi.getNama_kurir() + "\n" + data_ekspedisi.getNama_service()+(data_ekspedisi.getEtd().trim().length()>0?" ("+data_ekspedisi.getEtd().trim()+" hari)":"") +" "+data_ekspedisi.getTarif());

                //PEMBAYARAN
                loadLogoPembayaran(data_pembayaran.getGambar());
                pembayaran.setText(data_pembayaran.getNama_bank() + "\n" + data_pembayaran.getNo_rekening() + " an. " + data_pembayaran.getNama_pemilik_rekening());

                //DETAIL ORDER
                pesananAdapter = new PesananAdapter(context, dh, pesananlist, imageLoader, imageOptions, orderSelected.getMarket().getId());
                listview.setAdapter(pesananAdapter);

                //GRAND TOTAL
                total.setText(CommonUtilities.getCurrencyFormat(gtotal.getTotal(), ""));
                ongkir.setText(CommonUtilities.getCurrencyFormat(gtotal.getOngkir(), ""));
                voucher.setText(CommonUtilities.getCurrencyFormat(gtotal.getVoucher(), ""));
                grandtotal.setText(CommonUtilities.getCurrencyFormat(gtotal.getGrandtotal(), ""));

                batal.setVisibility(orderSelected.getStatus()==0?View.VISIBLE:View.GONE);
                bayar.setVisibility(orderSelected.getStatus()==0?View.VISIBLE:View.GONE);
                lacak.setVisibility((orderSelected.getStatus()==2 || orderSelected.getStatus()==3)?View.VISIBLE:View.GONE);
                terima.setVisibility(orderSelected.getStatus()==6?View.VISIBLE:View.GONE);

                detail_view.setVisibility(View.VISIBLE);
            } else {
                retry.setVisibility(View.VISIBLE);
            }
        }
    };

    public void loadMitraLogo(String gambar) {
        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/member/"+gambar;
        imageLoader.displayImage(url, image_mitra, imageOptionsMitra);
    }

    public void loadLogoEkspedisi(String gambar) {
        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/ekspedisi/"+gambar;
        imageLoader.displayImage(url, image_ekspedisi, imageOptions);
    }

    public void loadLogoPembayaran(String gambar) {
        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/bank/"+gambar;
        imageLoader.displayImage(url, image_pembayaran, imageOptions);
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
