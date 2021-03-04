package kamoncust.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.widget.EditText;
import android.widget.TextView;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class JenisUserFragment extends Fragment {

    //public static ImageView back;
    public static TextView text_jenis_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_jenis_user, container, false);

        //back = (ImageView) rootView.findViewById(R.id.back);
        text_jenis_user = (TextView) rootView.findViewById(R.id.text_jenis_user);

        /*back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(11);
            }
        });*/

        ((MainActivity) getActivity()).loadDataJenisUser();
		return rootView;
    }
}
