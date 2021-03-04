package kamoncust.application.com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.alexzh.circleimageview.CircleImageView;

import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class ProfileFragment extends Fragment {

    LinearLayout menu_edit_profile, menu_alamat, menu_kelola_notifikasi, menu_katasandi, menu_saldo_user;
    public static TextView name;
    public static CircleImageView image_profile;
    public static  TextView upload;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        menu_edit_profile = rootView.findViewById(R.id.menu_edit_profil);
        menu_alamat = rootView.findViewById(R.id.menu_alamat);
        menu_kelola_notifikasi = rootView.findViewById(R.id.menu_kelola_notifikasi);
        menu_katasandi = rootView.findViewById(R.id.menu_katasandi);
        menu_saldo_user = rootView.findViewById(R.id.menu_saldo_user);
        image_profile = (CircleImageView) rootView.findViewById(R.id.image_user_profile);
        name = (TextView) rootView.findViewById(R.id.name);
        upload = (TextView) rootView.findViewById(R.id.upload);

        image_profile.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).selectImage();
            }
        });

        menu_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(16);
            }
        });

        menu_alamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(15);
            }
        });

        menu_kelola_notifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(12);
            }
        });

        menu_katasandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(17);
            }
        });

        menu_saldo_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(19);
            }
        });

		((MainActivity) getActivity()).loadDataProfile();
		return rootView;
    }
}
