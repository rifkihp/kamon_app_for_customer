package kamoncust.application.com.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class SaldoUserFragment extends Fragment {

    public static ImageView back;
    public static TextView text_saldo_user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_saldo_user, container, false);

        back = rootView.findViewById(R.id.back);
        text_saldo_user = rootView.findViewById(R.id.text_saldo_user);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).displayView(11);
            }
        });

        ((MainActivity) getActivity()).loadDataSaldoUser();
		return rootView;
    }
}
