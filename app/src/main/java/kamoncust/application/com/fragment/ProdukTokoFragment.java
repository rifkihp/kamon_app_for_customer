package kamoncust.application.com.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.Objects;

import kamoncust.application.com.adapter.GridProdukTokoAdapter;
import kamoncust.application.com.data.toko.RestApi;
import kamoncust.application.com.data.toko.RetroFit;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.DatabaseHandlerToko;
import kamoncust.application.com.libs.EndlessScrollListener;
import kamoncust.application.com.model.ResponseProdukStore;
import kamoncust.application.com.model.produk;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukTokoFragment extends Fragment {

    TextView filter;
    TextView sortby;

    EditText edit_search;
    ImageButton btn_close;

    GridView gridview;
    SwipeRefreshLayout swipeRefreshLayout_gridview;
    AppBarLayout app_bar;

    static ProgressBar loading;
    static LinearLayout retry;
    Button btnReload;

    static int page = 1;
    static int as_toko = 1;
    static int kode_grup = 2;

    static boolean is_search = false;
    static String search_keyword = "";

    static boolean is_filter = false;

    static String filter_kategori;
    static String filter_brand;
    static String filter_ukuran;
    static String filter_harga_min;
    static String filter_harga_max;
    static String filter_diskon_min;
    static String filter_diskon_max;

    static boolean is_sort = false;
    static String sort_produk_by;

    static ArrayList<produk> produklist;
    static GridProdukTokoAdapter produkAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_produk_toko, container, false);

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
        gridview.setNumColumns(GridView.AUTO_FIT);

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
                produkAdapter.UpdateGridProdukTokoAdapter(produklist);
                new loadDataProduk().execute();
            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) getActivity()).openFilterProdukToko(kode_grup);
            }
        });

        sortby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //((MainActivity) getActivity()).openDialogProdukTokoSortBy(kode_grup);

            }
        });

        gridview.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                new loadDataProduk().execute();
                return true;
            }
        });

        edit_search.setSelected(false);
        edit_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edit_search.setHint(getResources().getString(R.string.search_label));
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
        produkAdapter = new GridProdukTokoAdapter(context, new DatabaseHandlerToko(context), produklist, kode_grup);
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

            filter_kategori   = is_filter?filter_kategori:"";
            filter_brand      = is_filter?filter_brand:"";
            filter_ukuran     = is_filter?filter_ukuran:"";
            filter_harga_min  = is_filter?filter_harga_min:"";
            filter_harga_max  = is_filter?filter_harga_max:"";
            filter_diskon_min = is_filter?filter_diskon_min:"";
            filter_diskon_max = is_filter?filter_diskon_max:"";

            sort_produk_by    = is_sort?sort_produk_by:"";

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseProdukStore> produkListCall = api.postProdukList(
                    MainActivity.data.getId()+"",
                    page+"",
                    as_toko+"",
                    kode_grup+"",
                    search_keyword,
                    filter_kategori,
                    filter_brand,
                    filter_ukuran,
                    filter_harga_min,
                    filter_harga_max,
                    filter_diskon_min,
                    filter_diskon_max,

                    sort_produk_by
            );

            produkListCall.enqueue(new Callback<ResponseProdukStore>() {
                @Override
                public void onResponse(@NonNull Call<ResponseProdukStore> call, @NonNull Response<ResponseProdukStore> response) {

                    try {
                        page = Objects.requireNonNull(response.body()).getNextpage();
                        ArrayList<produk> temp = Objects.requireNonNull(response.body()).getProduk();
                        Log.e("KARURU", temp.size()+"");

                        for (produk flist : temp) {
                            produklist.add(flist);
                        }
                        produkAdapter.UpdateGridProdukTokoAdapter(produklist);

                        loading.setVisibility(View.GONE);

                    } catch (Exception e) {
                        e.printStackTrace();
                        loading.setVisibility(View.GONE);
                        retry.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseProdukStore> call, @NonNull Throwable t) {
                    loading.setVisibility(View.GONE);
                    retry.setVisibility(View.VISIBLE);
                }
            });

            return null;
        }

    }

    public static void loadDataProduk(int sortProdukBy) {
        is_sort = true;

        page = 1;
        sort_produk_by = String.valueOf(sortProdukBy);
        produklist = new ArrayList<>();
        produkAdapter.UpdateGridProdukTokoAdapter(produklist);

        new loadDataProduk().execute();
    }

    public static void loadDataProduk() {
        is_sort = false;

        page = 1;
        produklist = new ArrayList<>();
        produkAdapter.UpdateGridProdukTokoAdapter(produklist);

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
