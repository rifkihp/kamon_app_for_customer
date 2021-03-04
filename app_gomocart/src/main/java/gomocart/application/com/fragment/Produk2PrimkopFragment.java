package gomocart.application.com.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Objects;

import gomocart.application.com.adapter.GridProdukAdapter;
import gomocart.application.com.data.RestApi;
import gomocart.application.com.data.RetroFit;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.EndlessScrollListener;
import gomocart.application.com.model.ResponseBarangKoperasi;
import gomocart.application.com.model.produk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Produk2PrimkopFragment extends Fragment {

    TextView filter;
    TextView sortby;
    GridView gridview;

    EditText edit_search;
    ImageButton btn_close;

    SwipeRefreshLayout swipeRefreshLayout_gridview;
    AppBarLayout app_bar;

    static ProgressBar loading;
    static LinearLayout retry;
    Button btnReload;

    static boolean is_search = false;
    static String search_keyword = "";

    static int page = 1;
    static ArrayList<produk> produklist;
    static GridProdukAdapter produkAdapter;
    int kode_grup = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_produk_gomocart, container, false);

        app_bar  = rootView.findViewById(R.id.id_appbar);
        gridview = rootView.findViewById(R.id.gridview);
        filter   = rootView.findViewById(R.id.filter);
        sortby   = rootView.findViewById(R.id.sortby);
        edit_search = rootView.findViewById(R.id.searchtext);
        btn_close   = rootView.findViewById(R.id.btn_close_add);

        swipeRefreshLayout_gridview = rootView.findViewById(R.id.swipe_container_gridview);

        loading   = rootView.findViewById(R.id.pgbarLoading);
        retry     = rootView.findViewById(R.id.loadMask);
        btnReload = rootView.findViewById(R.id.btnReload);

        app_bar.setVisibility(View.GONE);
        gridview.setNumColumns(3);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                new loadDataProduk().execute();
            }
        });

        swipeRefreshLayout_gridview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipeRefreshLayout_gridview.setRefreshing(false);
                page = 1;
                produklist = new ArrayList<>();
                produkAdapter.UpdateGridProdukAdapter(produklist);
                new loadDataProduk().execute();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openFilterProduk(kode_grup);
            }
        });

        sortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).openDialogSortBy(kode_grup);
            }
        });

        gridview.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                new loadDataProduk().execute();
                return true;
            }
        });



        edit_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (edit_search.getText().toString().length() > 0) {
                        is_search = true;
                        search_keyword = edit_search.getText().toString();
                        btn_close.setVisibility(View.VISIBLE);
                    } else {
                        is_search = false;
                        search_keyword = "";
                    }

                    closeSoftKeyboard();
                    loadDataProduk();

                    return true;
                }

                return false;
            }
        });

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_search.getText().clear();
                btn_close.setVisibility(View.GONE);

                is_search = false;
                search_keyword = "";

                loadDataProduk();
            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Context context = getActivity().getApplicationContext();
        page = 1;
        is_search = false;
        search_keyword = "";
        produklist = new ArrayList<>();
        produkAdapter = new GridProdukAdapter(context, new DatabaseHandler(context), produklist, kode_grup);
        gridview.setAdapter(produkAdapter);
        new loadDataProduk().execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    static class loadDataProduk extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {
            search_keyword = is_search?search_keyword:"";

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseBarangKoperasi> produkListCall = api.postBarangKoperasiList(
                    MainActivity.data.getKeyUserId(),
                    MainActivity.data.getKeyEntityCd(),
                    "",
                    page+"",
                    search_keyword,
                    ""
            );

            produkListCall.enqueue(new Callback<ResponseBarangKoperasi>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBarangKoperasi> call, @NonNull Response<ResponseBarangKoperasi> response) {

                    try {
                        page = Objects.requireNonNull(response.body()).getNext_page();
                        ArrayList<produk> temp = Objects.requireNonNull(response.body()).getProduklist();

                        for (produk flist : temp) {
                            produklist.add(flist);
                        }
                        produkAdapter.UpdateGridProdukAdapter(produklist);
                        loading.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        loading.setVisibility(View.GONE);
                        retry.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBarangKoperasi> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    loading.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
                }
            });

            return null;
        }

    }

    public static void loadDataProduk() {

        page = 1;
        produklist = new ArrayList<>();
        produkAdapter.UpdateGridProdukAdapter(produklist);

        new loadDataProduk().execute();
    }

    void closeSoftKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
