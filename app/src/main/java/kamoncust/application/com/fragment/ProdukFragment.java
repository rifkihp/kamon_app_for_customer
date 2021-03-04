package kamoncust.application.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class ProdukFragment extends Fragment {

	public static ImageView image_mitra;
	public static TextView nama_mitra;
	public static TextView alamat_mitra;

	static ListView listview;
	static ProgressBar loading;
	static LinearLayout retry;
	static Button btnReload;
	static TextView checkout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_produk, container, false);

		image_mitra   = rootView.findViewById(R.id.image_mitra);
		nama_mitra   = rootView.findViewById(R.id.nama_mitra);
		alamat_mitra = rootView.findViewById(R.id.alamat_mitra);

		listview     = rootView.findViewById(R.id.listview);
		loading      = rootView.findViewById(R.id.pgbarLoading);
		retry        = rootView.findViewById(R.id.loadMask);
		btnReload    = rootView.findViewById(R.id.btnReload);
		checkout     = rootView.findViewById(R.id.checkout);

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				loading.setVisibility(View.VISIBLE);
				retry.setVisibility(View.GONE);

				((MainActivity) getActivity()).loadDataProduk(false);
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
		loading.setVisibility(View.VISIBLE);
		retry.setVisibility(View.GONE);

		((MainActivity) getActivity()).loadMitraSelected();
		((MainActivity) getActivity()).loadDataProduk(true);
	}

	public static void resultLoadProduk(Context context, boolean success) {
		loading.setVisibility(View.GONE);
		if(!success) {
			retry.setVisibility(View.VISIBLE);
		} else {
			listview.setAdapter(MainActivity.produkadapter);
		}
	}

}
