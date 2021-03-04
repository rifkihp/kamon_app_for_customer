package kamoncust.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import android.widget.EditText;
import android.widget.TextView;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class OngkosKirimFragment extends Fragment {

    float downX = 0, downY = 0, upX, upY;

    public static EditText edit_province;
    public static EditText edit_city;
    public static EditText edit_state;
    public static TextView edit_berat;
    public static ImageView btn_plus;
    public static ImageView btn_minus;
    public static TextView cek_ongkir;
    public static ImageView prop_ok;
    public static ImageView city_ok;
    public static ImageView kecamatan_ok;

    public static ListView listViewOngkir;

    public static RelativeLayout linear_ongkir;
    public static LinearLayout retry;
    public static TextView btnReload;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_ongkos_kirim, container, false);

        edit_province = (EditText) rootView.findViewById(R.id.edit_provice);
        edit_city = (EditText) rootView.findViewById(R.id.edit_city);
        edit_state = (EditText) rootView.findViewById(R.id.edit_kecamatan);
        edit_berat = (TextView) rootView.findViewById(R.id.cartno);
        btn_plus = (ImageView) rootView.findViewById(R.id.plus);
        btn_minus = (ImageView) rootView.findViewById(R.id.minus);

        prop_ok      = (ImageView) rootView.findViewById(R.id.prop_ok);
        city_ok      = (ImageView) rootView.findViewById(R.id.city_ok);
        kecamatan_ok = (ImageView) rootView.findViewById(R.id.kecamatan_ok);

        prop_ok.setVisibility(View.INVISIBLE);
        city_ok.setVisibility(View.INVISIBLE);
        kecamatan_ok.setVisibility(View.INVISIBLE);

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
                            ((MainActivity) getActivity()).loadDialogListView("province");
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
                                ((MainActivity) getActivity()).openDialogMessage("Propinsi tujuan harus diisi!", false);
                            } else {
                                ((MainActivity) getActivity()).loadDialogListView("city");
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
                                ((MainActivity) getActivity()).openDialogMessage("Kabupaten / kota tujuan harus diisi!", false);
                            } else {
                                ((MainActivity) getActivity()).loadDialogListView("subdistrict");
                            }
                        }

                        break;
                }

                return false;
            }
        });

        edit_berat.setText("1");

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(edit_berat.getText().toString());
                number++;
                edit_berat.setText(String.valueOf(number));
                MainActivity.berat_barang = number;
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number = Integer.parseInt(edit_berat.getText().toString());
                if(number>1) {
                    number--;
                    edit_berat.setText(String.valueOf(number));
                    MainActivity.berat_barang = number;
                }
            }
        });

        cek_ongkir = (TextView) rootView.findViewById(R.id.cek_ongkir);
        cek_ongkir.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(edit_province.getText().toString().length()==0) {
                    ((MainActivity) getActivity()).openDialogMessage("Propinsi tujuan harus diisi!", false);
                    return;
                }

                if(edit_city.getText().toString().length()==0) {
                    ((MainActivity) getActivity()).openDialogMessage("Kabupaten / kota tujuan harus diisi!", false);
                    return;
                }

                if(edit_state.getText().toString().length()==0) {
                    ((MainActivity) getActivity()).openDialogMessage("Kecamatan tujuan harus diisi!", false);
                    return;
                }

                ((MainActivity) getActivity()).loadDataOngkir();
				
			}
		});

        linear_ongkir = (RelativeLayout) rootView.findViewById(R.id.linear2);
        listViewOngkir = (ListView) rootView.findViewById(R.id.lisview);
        retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
        btnReload = (TextView) rootView.findViewById(R.id.btnReload);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                ((MainActivity) getActivity()).loadDataOngkir();
            }
        });

		return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((MainActivity) getActivity()).loadDataProvince();
    }
}
