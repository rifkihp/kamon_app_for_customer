package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Objects;

import android.widget.EditText;
import android.widget.TextView;
import gomocart.application.com.adapter.ListAlamatAdapter;
import gomocart.application.com.data.RestApi;
import gomocart.application.com.data.RetroFit;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.ResponseAlamatStore;
import gomocart.application.com.model.ResponseAlamatDelete;
import gomocart.application.com.model.ResponseAlamatUtama;
import gomocart.application.com.model.alamat;
import gomocart.application.com.model.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlamatActivity extends AppCompatActivity {

    Context context;
    user data;

    ListView listview;
    ImageView back, search, sortby, tambah;

    LinearLayout linear_utama;
    RelativeLayout linear_search;

    EditText edit_search;
    ImageButton btn_close;

    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar loading;
    LinearLayout retry;
    Button btnReload;

    Dialog dialog_sort_by_alamat;
    ImageView radioAZ, radioZA;
    LinearLayout linearAZ, linearZA;
    public static String sort_by_alamat;

    alamat alamatSelected;

    Dialog konfirmasi_dialog;
    TextView konfirmasi_dialog_title;
    TextView konfirmasi_dialog_text;
    TextView konfirmasi_dialog_yes;
    TextView konfirmasi_dialog_no;

    Dialog dialog_loading;

    final int RESULT_FROM_EDIT_ALAMAT = 13;

    public static ArrayList<alamat> alamatlist = new ArrayList<>();
    public static ArrayList<alamat> alamatlist_display = new ArrayList<>();
    public static ListAlamatAdapter alamatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamat_gomocart);
        context = AlamatActivity.this;
        data = CommonUtilities.getSettingUser(context);

        search = findViewById(R.id.search);
        sortby = findViewById(R.id.orderby);
        tambah = findViewById(R.id.tambah);
        back = findViewById(R.id.back);
        listview = findViewById(R.id.listview);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
        loading = findViewById(R.id.pgbarLoading);
        retry = findViewById(R.id.loadMask);
        btnReload = findViewById(R.id.btnReload);

        linear_utama  = findViewById(R.id.linear_utama);
        linear_search = findViewById(R.id.cardview_search);
        edit_search   = findViewById(R.id.searchtext);
        btn_close     = findViewById(R.id.btn_close);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                loadAlamatlist();
            }
        });

        sortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogSortByAlamat();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                linear_search.setVisibility(View.VISIBLE);
                linear_utama.setVisibility(View.GONE);
                edit_search.requestFocus();

                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                edit_search.setText("");
                linear_search.setVisibility(View.GONE);
                linear_utama.setVisibility(View.VISIBLE);

                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                showListAlamat();
            }
        });

        edit_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    showListAlamat();
                }
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                loadAlamatlist();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewAlamat();
            }
        });

        konfirmasi_dialog = new Dialog(context);
        konfirmasi_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        konfirmasi_dialog.setCancelable(true);
        konfirmasi_dialog.setContentView(R.layout.konfirmasi_dialog_gomocart);

        konfirmasi_dialog_yes = konfirmasi_dialog.findViewById(R.id.btn_yes);
        konfirmasi_dialog_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                konfirmasi_dialog.dismiss();
                new prosesDeleteAlamat().execute();
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

        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);

        alamatlist = new ArrayList<>();
        alamatAdapter = new ListAlamatAdapter(context, alamatlist);
        listview.setAdapter(alamatAdapter);
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadListAlamatReceiver);
            unregisterReceiver(mHandleDeleteAlamatReceiver);
            unregisterReceiver(mHandleUtamakanAlamatReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadListAlamatReceiver);
            unregisterReceiver(mHandleDeleteAlamatReceiver);
            unregisterReceiver(mHandleUtamakanAlamatReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadListAlamatReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_ALAMAT"));
        registerReceiver(mHandleDeleteAlamatReceiver, new IntentFilter("gomocart.application.com.gomocart.PROSES_DELETE_ALAMAT"));
        registerReceiver(mHandleUtamakanAlamatReceiver, new IntentFilter("gomocart.application.com.gomocart.PROSES_UTAMAKAN_ALAMAT"));

        loadAlamatlist();
        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadListAlamatReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean success = intent.getBooleanExtra("success", false);

            loading.setVisibility(View.GONE);
            if(!success) retry.setVisibility(View.VISIBLE);

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

                alamatAdapter.UpdateSelectAlamatAdapter(alamatlist);
            } else {
                showInformationDialog(message, success);
            }
        }
    };

    private void sortAlamtBy(int sortby) {
        sort_by_alamat = String.valueOf(sortby);

        alamatlist = new ArrayList<>();
        alamatlist_display = new ArrayList<>();

        showListAlamat();
        loadAlamatlist();
    }

    public void selectedAlamat(alamat data_selected_alamat) {
        Intent intent = new Intent();
        intent.putExtra("alamat", data_selected_alamat);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void openDialogSortByAlamat() {
        dialog_sort_by_alamat.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_sort_by_alamat.show();
    }

    public void showListAlamat() {

        String keyword = edit_search.getText().toString();
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

        alamatAdapter.UpdateSelectAlamatAdapter(alamatlist_display);
    }

    public void loadAlamatlist() {
        new loadAlamatlist().execute();
    }

    public class loadAlamatlist extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {

            alamatlist = new ArrayList<>();
            alamatlist_display = new ArrayList<>();

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseAlamatStore> alamatListCall = api.postAlamatList(data.getId()+"", sort_by_alamat+"");
            alamatListCall.enqueue(new Callback<ResponseAlamatStore>() {
                @Override
                public void onResponse(@NonNull Call<ResponseAlamatStore> call, @NonNull Response<ResponseAlamatStore> response) {

                    alamatlist   = Objects.requireNonNull(response.body()).getAlamatlist();
                    alamatlist_display  = Objects.requireNonNull(response.body()).getAlamatlist();

                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ALAMAT");
                    i.putExtra("success", true);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseAlamatStore> call, @NonNull Throwable t) {
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ALAMAT");
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

    public void deleteSelectedAlamat(alamat data_alamat) {
        alamatSelected = data_alamat;

        konfirmasi_dialog_title.setText("Hapus Alamat?");
        konfirmasi_dialog_text.setText("Alamat akan hilang dari daftar alamat.");
        konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        konfirmasi_dialog.show();
    }

    public void utamakanSelectedAlamat(alamat data_alamat) {
        alamatSelected = data_alamat;
        new prosesUtamakanAlamat().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data_intent);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_FROM_EDIT_ALAMAT:
                    alamat select_alamat = (alamat) data_intent.getSerializableExtra("alamat");

                    if(select_alamat!=null) {
                        for (alamat data_alamat : alamatlist) {
                            if (data_alamat.getId()==select_alamat.getId()) {
                                if(select_alamat.getAsDefaultAlamat()) {
                                    for(int i=0; i<alamatlist.size(); i++) {
                                        alamatlist.get(i).setAs_default(false);
                                    }
                                }

                                alamatlist.set(alamatlist.indexOf(data_alamat), select_alamat);
                                showListAlamat();

                                return;
                            }
                        }

                        if(select_alamat.getAsDefaultAlamat()) {
                            for(int i=0; i<alamatlist.size(); i++) {
                                alamatlist.get(i).setAs_default(false);
                            }
                        }

                        alamatlist.add(select_alamat);
                        showListAlamat();
                    }

                    break;
            }
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
            Call<ResponseAlamatUtama> utamakanAlamatCall = api.postUtamakanAlamat(data.getId()+"", alamatSelected.getId()+"");
            utamakanAlamatCall.enqueue(new Callback<ResponseAlamatUtama>() {
                @Override
                public void onResponse(@NonNull Call<ResponseAlamatUtama> call, @NonNull Response<ResponseAlamatUtama> response) {
                    Intent i = new Intent("gomocart.application.com.gomocart.PROSES_UTAMAKAN_ALAMAT");
                    i.putExtra("success", Objects.requireNonNull(response.body()).getSuccess());
                    i.putExtra("message", Objects.requireNonNull(response.body()).getMessage());
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseAlamatUtama> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.PROSES_UTAMAKAN_ALAMAT");
                    i.putExtra("success", false);
                    i.putExtra("message", "Proses utamakan alamat gagal.");
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
            Call<ResponseAlamatDelete> deleteAlamatCall = api.postDeleteAlamat(data.getId()+"", alamatSelected.getId()+"");
            deleteAlamatCall.enqueue(new Callback<ResponseAlamatDelete>() {
                @Override
                public void onResponse(@NonNull Call<ResponseAlamatDelete> call, @NonNull Response<ResponseAlamatDelete> response) {

                    Intent i = new Intent("gomocart.application.com.gomocart.PROSES_DELETE_ALAMAT");
                    i.putExtra("success", Objects.requireNonNull(response.body()).getSuccess());
                    i.putExtra("message", Objects.requireNonNull(response.body()).getMessage());
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseAlamatDelete> call, @NonNull Throwable t) {

                    Intent i = new Intent("gomocart.application.com.gomocart.PROSES_DELETE_ALAMAT");
                    i.putExtra("success", false);
                    i.putExtra("message", "Error koneksi server.");
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
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
