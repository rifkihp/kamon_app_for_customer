package kamoncust.application.com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class EditProfileFragment extends Fragment {

    public static ImageView back;
    public static EditText edit_nama;
    public static EditText edit_phone;
    public static EditText edit_email;
    public static EditText edit_username;
    public static  TextView save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        back          = rootView.findViewById(R.id.back);
        edit_nama     = rootView.findViewById(R.id.edit_nama);
        edit_phone    = rootView.findViewById(R.id.edit_phone);
        edit_email    = rootView.findViewById(R.id.edit_email);
        edit_username = rootView.findViewById(R.id.edit_username);
        save          = rootView.findViewById(R.id.save);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(11);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).simpanDataProfile();
            }
        });

		((MainActivity) getActivity()).loadDataEditProfile();
		return rootView;
    }
}
