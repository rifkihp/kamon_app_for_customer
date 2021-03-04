package gomocart.application.com.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import java.io.IOException;
import java.io.InputStream;

import android.widget.EditText;
import android.widget.TextView;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.ExpandableHeightListView;

public class ProsesCheckoutKonfirmasiPemesananFragment extends Fragment {

	public static TextView alamat;

	public static ImageView image_ekspedisi;
	public static TextView ekspedisi;

	public static ImageView image_pembayaran;
	public static TextView pembayaran;
	
	public static EditText kode_voucher;
	public static TextView apply;
	
	public static ExpandableHeightListView listview_order;

	public static TextView total;
	public static TextView ongkir;
	public static TextView voucher;
	public static TextView grandtotal;

	public static TextView selesai;

	public static ProgressBar loading;
	public static LinearLayout retry;
	public static TextView btnReload;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_konfirmasi_pemesanan_gomocart, container, false);
		
		alamat = rootView.findViewById(R.id.alamat);

		image_ekspedisi  = rootView.findViewById(R.id.image_ekspedisi);
		ekspedisi = rootView.findViewById(R.id.ekspedisi);
		
		image_pembayaran  = rootView.findViewById(R.id.image_pembayaran);
		pembayaran = rootView.findViewById(R.id.pembayaran);

		kode_voucher = rootView.findViewById(R.id.edit_kode_voucher);
		apply        = rootView.findViewById(R.id.apply_voucher);

		listview_order = rootView.findViewById(R.id.listview_order);

		total      = rootView.findViewById(R.id.total);
		ongkir     = rootView.findViewById(R.id.ongkir);
		voucher    = rootView.findViewById(R.id.voucher);
		grandtotal = rootView.findViewById(R.id.grandtotal);

		selesai    = rootView.findViewById(R.id.selesai);

		loading = rootView.findViewById(R.id.pgbarLoading);
		retry = rootView.findViewById(R.id.loadMask);
		btnReload = rootView.findViewById(R.id.btnReload);
		
		apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ProsesCheckoutActivity) getActivity()).prosesCekVoucher();

			}
		});

		selesai.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ProsesCheckoutActivity) getActivity()).submitOrder();
			}
		});


		((ProsesCheckoutActivity) getActivity()).setInitialKonfirmasiPesanan();
		((ProsesCheckoutActivity) getActivity()).loadDataPesanan();

		return rootView;
	}
	
}
