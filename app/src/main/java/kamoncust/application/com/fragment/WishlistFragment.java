package kamoncust.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class WishlistFragment extends Fragment {

	SwipeRefreshLayout swipe_container;

	public static ListView listview;
	public static LinearLayout retry;
	public static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_wishlist, container, false);

		swipe_container  = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
		listview = (ListView) rootView.findViewById(R.id.listview);
		retry = (LinearLayout) rootView.findViewById(R.id.loadMask);
		btnReload = (Button) rootView.findViewById(R.id.btnReload);

		swipe_container.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				swipe_container.setRefreshing(false);
				((MainActivity) getActivity()).loadWishlist();
			}
		});

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((MainActivity) getActivity()).loadWishlist();
			}
		});

		return rootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		((MainActivity) getActivity()).loadWishlist();
	}
}
