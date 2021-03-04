package kamoncust.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class MoreFragment extends Fragment {

	public static TextView detail_menu;

	public static LinearLayout retry;
	public static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_more, container, false);

		detail_menu = (TextView) rootView.findViewById(R.id.detail_menu);
		retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload = (Button) rootView.findViewById(R.id.btnReload);
		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).prosesLoadDetailMenu();
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		((MainActivity) getActivity()).prosesLoadDetailMenu();
	}
}
