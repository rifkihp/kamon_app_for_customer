package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;

public class ProsesCheckoutAlamatKirimFragment extends Fragment {

	public static TextView pickup_alamat;
	public static TextView add_alamat;
	public static EditText edit_nama;
	public static EditText edit_alamat;
	public static EditText edit_province;
	public static EditText edit_city;
	public static EditText edit_state;
	public static EditText edit_kodepos;
	public static EditText edit_nohp;

	public static CheckBox checkbox_isdropship;
	public static LinearLayout linear_dropship_name;
	public static LinearLayout linear_dropship_phone;
	public static EditText edit_dropship_name;
	public static EditText edit_dropship_phone;

	public static LinearLayout linear_email_notifikasi;
	public static EditText edit_email_notifikasi;
	public static TextView lanjutkan;
	public static TextView select_location;

	float downX = 0, downY = 0, upX, upY;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_proses_checkout_alamat_kirim_gomocart, container, false);

		pickup_alamat = rootView.findViewById(R.id.pilih);
		add_alamat    = rootView.findViewById(R.id.tambah);
		edit_nama     = rootView.findViewById(R.id.edit_nama);
		edit_alamat   = rootView.findViewById(R.id.edit_alamat);
		edit_province = rootView.findViewById(R.id.edit_provice);
		edit_city     = rootView.findViewById(R.id.edit_city);
		edit_state    = rootView.findViewById(R.id.edit_kecamatan);
		edit_kodepos  = rootView.findViewById(R.id.edit_kodepos);
		edit_nohp     = rootView.findViewById(R.id.edit_phone);

		checkbox_isdropship   = rootView.findViewById(R.id.checkbox_isdropship);
		linear_dropship_name  = rootView.findViewById(R.id.linear_dropship_name);
		edit_dropship_name    = rootView.findViewById(R.id.edit_dropship_name);
		linear_dropship_phone = rootView.findViewById(R.id.linear_dropship_phone);
		edit_dropship_phone   = rootView.findViewById(R.id.edit_dropship_phone);

		linear_email_notifikasi = rootView.findViewById(R.id.linear_email_notifikasi);
		edit_email_notifikasi   = rootView.findViewById(R.id.edit_email_notifikasi);

		lanjutkan               = rootView.findViewById(R.id.next);
		select_location         = rootView.findViewById(R.id.select_location);

		checkbox_isdropship.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				boolean isChecked = ((CheckBox)v).isChecked();
				linear_dropship_name.setVisibility(isChecked?View.VISIBLE:View.GONE);
				linear_dropship_phone.setVisibility(isChecked?View.VISIBLE:View.GONE);
			}
		});

		select_location.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ProsesCheckoutActivity) getActivity()).selectLocation();
			}
		});

		lanjutkan.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String message = ((ProsesCheckoutActivity) getActivity()).checkedAlamatKirimBeforeNext();
				if(message.length()==0) {
					((ProsesCheckoutActivity) getActivity()).saveAlamat();
					((ProsesCheckoutActivity) getActivity()).displayView(1);
				} else {
					((ProsesCheckoutActivity) getActivity()).openDialogMessage(message, false);
				}
			}
		});

		pickup_alamat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ProsesCheckoutActivity) getActivity()).pickAlamat();
			}
		});

		add_alamat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((ProsesCheckoutActivity) getActivity()).editAlamat();
			}
		});

		edit_province.setOnTouchListener(new View.OnTouchListener() {

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
							((ProsesCheckoutActivity) getActivity()).loadDialogListView("province");
						}

						break;
				}

				return false;
			}
		});

		edit_city.setOnTouchListener(new View.OnTouchListener() {

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
							if(edit_province.getText().toString().length()==0) {
								((ProsesCheckoutActivity) getActivity()).openDialogMessage("Propinsi tujuan harus diisi!", false);
							} else {
								((ProsesCheckoutActivity) getActivity()).loadDialogListView("city");
							}
						}

						break;
				}

				return false;
			}
		});

		edit_state.setOnTouchListener(new View.OnTouchListener() {

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
							if(edit_city.getText().toString().length()==0) {
								((ProsesCheckoutActivity) getActivity()).openDialogMessage("Kabupaten / kota tujuan harus diisi!", false);
							} else {
								((ProsesCheckoutActivity) getActivity()).loadDialogListView("subdistrict");
							}
						}

						break;
				}

				return false;
			}
		});

		((ProsesCheckoutActivity) getActivity()).loadDefaultAlamat();

		return rootView;
	}

}