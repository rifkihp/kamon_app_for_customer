package kamoncust.application.com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alexzh.circleimageview.CircleImageView;

import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class GantiPasswordFragment extends Fragment {

    public static ImageView back;
    public static EditText edit_old_password;
    public static EditText edit_password;
    public static EditText edit_konfirmasi;

    public static  TextView save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_ganti_password, container, false);

        edit_old_password = (EditText) rootView.findViewById(R.id.edit_old_password);
        edit_password = (EditText) rootView.findViewById(R.id.edit_password);
        edit_konfirmasi = (EditText) rootView.findViewById(R.id.edit_konfirmasi);
        save = (TextView) rootView.findViewById(R.id.save);
        back = (ImageView) rootView.findViewById(R.id.back);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).simpanDataPassword();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(11);
            }
        });

		return rootView;
    }
}
