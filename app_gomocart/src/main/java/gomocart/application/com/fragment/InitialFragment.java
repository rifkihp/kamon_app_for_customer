package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.EndlessScrollListener;

public class InitialFragment extends Fragment {

	public static ListView listview;
	public static LinearLayout retry;
	public static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_initial_gomocart, container, false);

		listview  = rootView.findViewById(R.id.listview);
		retry     =  rootView.findViewById(R.id.loadMask);
		btnReload = rootView.findViewById(R.id.btnReload);


		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				((MainActivity) getActivity()).loadDataDashboard();
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((MainActivity) getActivity()).loadDataDashboard();
	}

}
