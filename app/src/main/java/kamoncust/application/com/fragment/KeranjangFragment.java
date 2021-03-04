package kamoncust.application.com.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.TextView;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class KeranjangFragment extends Fragment {

	SwipeRefreshLayout swipe_container;

	public static CheckBox chk_pilihsemua;
	public static TextView pilihsemua;
	public static TextView hapus;
	public static ImageView img_hapus;

	public static LinearLayout linear_cart;
	public static ListView listview;
	public static TextView checkout;
	public static TextView edit_qty;
	public static TextView edit_jumlah;

	public static LinearLayout retry;
	public static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_checkout, container, false);

		chk_pilihsemua = rootView.findViewById(R.id.chk_pilih_semua);
		pilihsemua     = rootView.findViewById(R.id.text_pilih_semua);
		hapus          = rootView.findViewById(R.id.text_hapus);
		img_hapus      = rootView.findViewById(R.id.img_hapus);

		swipe_container = rootView.findViewById(R.id.swipe_container);
		linear_cart     = rootView.findViewById(R.id.linear_cart);
		listview        = rootView.findViewById(R.id.listview);
		edit_qty        = rootView.findViewById(R.id.edit_qty);
		edit_jumlah     = rootView.findViewById(R.id.edit_jumlah);
		checkout        = rootView.findViewById(R.id.checkout);
		btnReload       = rootView.findViewById(R.id.btnReload);
		retry           = rootView.findViewById(R.id.loadMask);

		chk_pilihsemua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				((MainActivity) getActivity()).pilihSemuaChartlist(isChecked);
			}
		});

		hapus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).hapusCartlistTerpilih();
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
				//((MainActivity) getActivity()).loadCartlist();
			}
		});

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//((MainActivity) getActivity()).loadCartlist();
			}
		});

		checkout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).cekOrder();
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//((MainActivity) getActivity()).loadCartlist();
	}
}
