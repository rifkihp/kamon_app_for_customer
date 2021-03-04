package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

import gomocart.application.com.adapter.SelectKurirAdapter;
import gomocart.application.com.data.RestApi;
import gomocart.application.com.data.RetroFit;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.ResponseOngkirStore;
import gomocart.application.com.model.alamat;
import gomocart.application.com.model.ongkir;
import gomocart.application.com.model.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static gomocart.application.com.libs.CommonUtilities.SERVER_URL;

public class KurirActivity extends AppCompatActivity {

    Context context;
    user data;

    ImageView back;

    ListView listview;
    ProgressBar loading;
    LinearLayout retry;
    Button btnReload;
    
    ongkir ongkirSelected;
    alamat alamatTujuan;
    int kotaAsalId;
    double berat;
    double total;
    
    Dialog dialog_loading;

    ArrayList<ongkir> ongkirlist = new ArrayList<>();
    SelectKurirAdapter selectOngkirAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kurir_gomocart);
        
        context = KurirActivity.this;
        data = CommonUtilities.getSettingUser(context);

        back = findViewById(R.id.back);
        listview = findViewById(R.id.listview);
        loading = findViewById(R.id.pgbarLoading);
        retry = findViewById(R.id.loadMask);
        btnReload = findViewById(R.id.btnReload);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                loadPengirimanList();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        
        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);

        if (savedInstanceState == null) {
            ongkirSelected = (ongkir) getIntent().getSerializableExtra("ongkir");
            alamatTujuan   = (alamat) getIntent().getSerializableExtra("tujuan");
            kotaAsalId     = getIntent().getIntExtra("asal", 0);
            berat          = getIntent().getDoubleExtra("berat", 0);
            total          = getIntent().getDoubleExtra("total", 0);
        }
        
        ongkirlist = new ArrayList<>();
        selectOngkirAdapter = new SelectKurirAdapter(context, ongkirlist);
        listview.setAdapter(selectOngkirAdapter);

        loadPengirimanList();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadListOngkirReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadListOngkirReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadListOngkirReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_ONGKIR"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadListOngkirReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean success = intent.getBooleanExtra("success", false);

            loading.setVisibility(View.GONE);
            if(!success){
                retry.setVisibility(View.VISIBLE);
            } else {
                selectOngkirAdapter.UpdateSelectKurirAdapter(ongkirlist);
            }
        }
    };

    public void selectedOngkir(ongkir data_selected_ongkir) {
        Intent intent = new Intent();
        intent.putExtra("ongkir", data_selected_ongkir);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void loadPengirimanList() {
        new loadPengirimanList().execute();
    }

    public class loadPengirimanList extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseOngkirStore> ongkirStoreCall = api.postOngkirList(
                    data.getId()+"",
                    kotaAsalId+"",
                    alamatTujuan.getCity_id()+"",
                    alamatTujuan.getSubdistrict_id()+"",
                    berat+"",
                    total+""
                    );
            ongkirStoreCall.enqueue(new Callback<ResponseOngkirStore>() {
                @Override
                public void onResponse(@NonNull Call<ResponseOngkirStore> call, @NonNull Response<ResponseOngkirStore> response) {

                    ongkirlist  = Objects.requireNonNull(response.body()).getOngkirlist();
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ONGKIR");
                    i.putExtra("success", true);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseOngkirStore> call, @NonNull Throwable t) {
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ONGKIR");
                    i.putExtra("success", false);
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    public void openDialogLoading() {
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }
}
