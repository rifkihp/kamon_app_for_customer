package gomocart.application.com.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import android.widget.TextView;

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
import gomocart.application.com.model.kategori;
import gomocart.application.com.model.produk;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Produk4KomoditiFragment extends Fragment {

    ArrayList<produk> produklist;
    GridProdukAdapter produkAdapter;
    int kode_grup = 3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_produk_gomocart, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Context context = getActivity().getApplicationContext();

        produklist = new ArrayList<>();
        produkAdapter = new GridProdukAdapter(context, new DatabaseHandler(context), produklist, kode_grup);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
