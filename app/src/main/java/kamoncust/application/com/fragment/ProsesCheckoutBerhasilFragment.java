
package kamoncust.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import kamoncust.application.com.kamoncust.ProsesCheckoutActivity;
import kamoncust.application.com.kamoncust.R;


public class ProsesCheckoutBerhasilFragment extends Fragment {



	TextView konfirmasi_pembayaran;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_berhasil, container, false);

		konfirmasi_pembayaran = (TextView) rootView.findViewById(R.id.selesai);

		konfirmasi_pembayaran.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((ProsesCheckoutActivity) getActivity()).prosesKonfirmasi();
			}
		});

		return rootView;
	}
	
}
