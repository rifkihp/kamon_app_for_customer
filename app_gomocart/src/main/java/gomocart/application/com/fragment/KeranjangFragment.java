package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class KeranjangFragment extends Fragment {

    SwipeRefreshLayout swipe_container;

    public static CheckBox chk_pilihsemua;
    public static TextView title;
    public static ImageView img_hapus;
    public static LinearLayout linear_cart;

    public static FrameLayout frame_tab;
    public static ListView listview;
    public static TextView checkout;
    public static TextView edit_qty;
    public static TextView edit_jumlah;

    public static LinearLayout retry;
    public static Button btnReload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_checkout_gomocart, container, false);

        chk_pilihsemua = rootView.findViewById(R.id.chk_box_pilih_all);
        img_hapus = rootView.findViewById(R.id.img_hapus);
        title = rootView.findViewById(R.id.title);
        swipe_container = rootView.findViewById(R.id.swipe_container);
        linear_cart = rootView.findViewById(R.id.linear_cart);
        listview = rootView.findViewById(R.id.listview);
        edit_qty = rootView.findViewById(R.id.edit_qty);
        edit_jumlah = rootView.findViewById(R.id.edit_jumlah);
        checkout = rootView.findViewById(R.id.checkout);
        btnReload = rootView.findViewById(R.id.btnReload);
        retry = rootView.findViewById(R.id.loadMask);

        chk_pilihsemua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((MainActivity) getActivity()).pilihSemuaChartlist(isChecked);
            }
        });

        img_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).hapusCartlistTerpilih();
            }
        });

        swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                swipe_container.setRefreshing(false);
                ((MainActivity) getActivity()).loadCartlist();
            }
        });

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                ((MainActivity) getActivity()).loadCartlist();
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).prosesCheckout();
            }
        });

        ((MainActivity) getActivity()).loadCartlist();

        return rootView;
    }
}
