
package kamoncust.application.com.fragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import android.widget.EditText;
import android.widget.TextView;
import kamoncust.application.com.kamoncust.ProsesCheckoutActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.ExpandableHeightListView;

public class ProsesCheckoutKonfirmasiPemesananFragment extends Fragment {

	public static ScrollView detail_view;
	public static ProgressBar loading;
	public static LinearLayout retry;
	public static TextView btnReload;

	public static TextView detail_pasien;
	public static TextView jadwal_terapi;

	public static ImageView image_terapis;
	public static TextView detail_terapis;

	public static EditText kode_voucher;
	public static TextView apply;
	public static ExpandableHeightListView listViewPesanan;

	public static TextView subtotal;
	public static TextView voucher;
	public static TextView total;

	public static TextView selesai;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_konfirmasi_pemesanan, container, false);

		detail_view = (ScrollView) rootView.findViewById(R.id.detail_view);
		loading = (ProgressBar) rootView.findViewById(R.id.pgbarLoading);
		retry = rootView.findViewById(R.id.loadMask);
		btnReload = rootView.findViewById(R.id.btnReload);

		detail_pasien = rootView.findViewById(R.id.detail_pasien);
		jadwal_terapi = rootView.findViewById(R.id.jadwal_terapi);

		image_terapis = rootView.findViewById(R.id.image_terapis);
		detail_terapis = rootView.findViewById(R.id.detail_terapis);

		kode_voucher = rootView.findViewById(R.id.edit_kode_voucher);
		apply = rootView.findViewById(R.id.apply_voucher);

		listViewPesanan = rootView.findViewById(R.id.lisview);

		subtotal   = rootView.findViewById(R.id.subtotal);
		voucher    = rootView.findViewById(R.id.voucher);
		total      = rootView.findViewById(R.id.total);

		selesai    = rootView.findViewById(R.id.selesai);


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
