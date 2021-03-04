package kamoncust.application.com.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import kamoncust.application.com.kamoncust.AlamatActivity;
import kamoncust.application.com.kamoncust.EditAlamatActivity;
import kamoncust.application.com.kamoncust.ProsesCheckoutActivity;
import kamoncust.application.com.kamoncust.R;

public class ProsesCheckoutJadwalPengirimanFragment extends Fragment {

	public static EditText edit_tanggal_terapi;
	public static EditText edit_jam_terapi;
	public static EditText edit_riwayat_kesehatan;
	public static TextView lanjutkan;

	float downX = 0, downY = 0, upX, upY;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_jadwal_pengiriman, container, false);

		edit_tanggal_terapi = rootView.findViewById(R.id.edit_tanggal_terapi);
		edit_jam_terapi     = rootView.findViewById(R.id.edit_jam_terapi);
		edit_riwayat_kesehatan = rootView.findViewById(R.id.edit_riwayat_kesehatan);
		lanjutkan           = rootView.findViewById(R.id.next);

		lanjutkan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String message = ((ProsesCheckoutActivity) getActivity()).checkedJadwalKirimBeforeNext();
				if(message.length()==0) {
					((ProsesCheckoutActivity) getActivity()).saveJadwalKirim();
					((ProsesCheckoutActivity) getActivity()).validasiJadwal();
				} else {
					((ProsesCheckoutActivity) getActivity()).openDialogMessage(message, false);
				}
			}
		});

		edit_tanggal_terapi.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						downX = event.getX();
						downY = event.getY();

						break;

					case MotionEvent.ACTION_UP:
						upX = event.getX();
						upY = event.getY();
						float deltaX = downX - upX;
						float deltaY = downY - upY;

						if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
							((ProsesCheckoutActivity) getActivity()).loadDialogListView("tanggal_terapi");
						}

						break;
				}

				return false;
			}
		});

		edit_jam_terapi.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						downX = event.getX();
						downY = event.getY();

						break;

					case MotionEvent.ACTION_UP:
						upX = event.getX();
						upY = event.getY();
						float deltaX = downX - upX;
						float deltaY = downY - upY;

						if(Math.abs(deltaX)<50 && Math.abs(deltaY)<50) {
							((ProsesCheckoutActivity) getActivity()).loadDialogListView("jam_terapi");
						}

						break;
				}

				return false;
			}
		});

		((ProsesCheckoutActivity) getActivity()).loadDefaultJadwalKirim();

		return rootView;
	}

}
