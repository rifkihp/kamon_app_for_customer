
package gomocart.application.com.fragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import java.io.IOException;
import java.io.InputStream;

import android.widget.TextView;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;


public class ProsesCheckoutMetodePembayaranFragment extends Fragment {
	
	public static TextView total_tagihan;
	public static ListView listview;
	
	public static ProgressBar loading;
	public static LinearLayout retry;
	public static TextView btnReload;
	public static TextView lanjutkan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_metode_pembayaran_gomocart, container, false);

		total_tagihan = rootView.findViewById(R.id.total_tagihan);
		listview      = rootView.findViewById(R.id.listview);
		loading       = rootView.findViewById(R.id.pgbarLoading);
		retry         = rootView.findViewById(R.id.loadMask);
		btnReload     = rootView.findViewById(R.id.btnReload);
		lanjutkan     = rootView.findViewById(R.id.next);

		lanjutkan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				String message = ((ProsesCheckoutActivity) getActivity()).checkedMetodePembayaranBeforeNext();
				if(message.length()==0) {
					((ProsesCheckoutActivity) getActivity()).displayView(3);
				} else {
					((ProsesCheckoutActivity) getActivity()).openDialogMessage(message, false);
				}
			}
		});

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				((ProsesCheckoutActivity) getActivity()).loadDataBank();
			}
		});

		((ProsesCheckoutActivity) getActivity()).setInitialMetodePembayaran();
		((ProsesCheckoutActivity) getActivity()).loadDataBank();

		return rootView;
	}
	
}
