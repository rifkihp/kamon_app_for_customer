package kamoncust.application.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import kamoncust.application.com.adapter.MitraAdapter;
import kamoncust.application.com.adapter.PagerIndukKategoriAdapter;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CustomTabLayout;
import kamoncust.application.com.libs.ExpandableHeightGridView;
import kamoncust.application.com.model.produkgrup;

public class BekamFragment extends Fragment {

	ImageView back;
	static ExpandableHeightGridView mitra;
	static ProgressBar loading;
	static LinearLayout retry;
	static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_bekam, container, false);

		back      = rootView.findViewById(R.id.back);
		mitra     = rootView.findViewById(R.id.mitra);
		loading   = rootView.findViewById(R.id.pgbarLoading);
		retry     = rootView.findViewById(R.id.loadMask);
		btnReload = rootView.findViewById(R.id.btnReload);

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				loading.setVisibility(View.VISIBLE);
				retry.setVisibility(View.GONE);
				((MainActivity) getActivity()).loadDataDashboard();
			}
		});

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).displayView(0);
			}
		});

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		loading.setVisibility(View.VISIBLE);
		retry.setVisibility(View.GONE);
		((MainActivity) getActivity()).loadDataDashboard();
	}

	public static void resultLoadBekam(Context context, boolean success) {
		loading.setVisibility(View.GONE);
		if(!success) {
			retry.setVisibility(View.VISIBLE);
		} else {

			mitra.setAdapter(new MitraAdapter(context, MainActivity.dashboard_mitra));
		}

	}
}
