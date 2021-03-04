package kamoncust.application.com.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;

import kamoncust.application.com.kamoncust.R;

public class DonasiFragment extends Fragment {

    public static EditText edit_nominal;
    public static TextView lanjutkan;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_donasi, container, false);

        edit_nominal = (EditText) rootView.findViewById(R.id.edit_nominal);
        lanjutkan = (TextView) rootView.findViewById(R.id.next);

        lanjutkan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

		return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
