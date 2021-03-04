package gomocart.application.com.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.ExpandableHeightListView;

public class ProsesCheckoutKurirPengirimanFragment extends Fragment {

	public static ExpandableHeightListView listview;
	public static ProgressBar loading;
	public static LinearLayout retry;
	public static TextView btnReload;
	public static TextView lanjutkan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_kurir_pengiriman_gomocart, container, false);

		listview  = rootView.findViewById(R.id.listview);
		loading   = rootView.findViewById(R.id.pgbarLoading);
		retry     = rootView.findViewById(R.id.loadMask);
		btnReload = rootView.findViewById(R.id.btnReload);
		lanjutkan = rootView.findViewById(R.id.next);

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				((ProsesCheckoutActivity) getActivity()).loadDataOngkir();
			}
		});

		lanjutkan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String message = ((ProsesCheckoutActivity) getActivity()).checkedKurirPengirimanBeforeNext();
				if(message.length()==0) {
					((ProsesCheckoutActivity) getActivity()).displayView(2);
				} else {
					((ProsesCheckoutActivity) getActivity()).openDialogMessage(message, false);
				}

			}
		});

		((ProsesCheckoutActivity) getActivity()).setInitialKurirPengiruman();
		((ProsesCheckoutActivity) getActivity()).loadDataOngkir();

		return rootView;
	}
	
}
