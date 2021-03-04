package kamoncust.application.com.fragment;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import kamoncust.application.com.adapter.PagerIndukKategoriAdapter;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CustomTabLayout;
import kamoncust.application.com.model.produk_kategori;
import kamoncust.application.com.model.produkgrup;

public class DashboardFragment extends Fragment {

	static CustomTabLayout tabLayout;
	static ViewPager viewPager;
	static ProgressBar loading;
	static LinearLayout retry;
	static Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

		tabLayout       = rootView.findViewById(R.id.tab_layout);
		viewPager       = rootView.findViewById(R.id.pager);
		loading         = rootView.findViewById(R.id.pgbarLoading);
		retry           = rootView.findViewById(R.id.loadMask);
		btnReload       = rootView.findViewById(R.id.btnReload);

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				loading.setVisibility(View.VISIBLE);
				retry.setVisibility(View.GONE);
				((MainActivity) getActivity()).loadDataDashboard();
			}
		});

		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				final int position = tab.getPosition();
				viewPager.setCurrentItem(position, true);
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

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

	public static void resultLoadDashboard(Context context, boolean success) {
		loading.setVisibility(View.GONE);
		if(!success) {
			retry.setVisibility(View.VISIBLE);
		} else {

			//tabLayout.setVisibility(MainActivity.tampilkan_produk_induk?View.VISIBLE:View.GONE);

			//tabLayout.removeAllTabs();
			//tabLayout.setTabMode(MainActivity.dashboard_produk_induk.size()<5? TabLayout.MODE_FIXED: TabLayout.MODE_SCROLLABLE);
			//for (produkgrup tab_produk_induk: MainActivity.dashboard_produk_induk) {
				//tabLayout.addTab(tabLayout.newTab().setText(tab_produk_induk.getNama()));
			//}

			tabLayout.removeAllTabs();
			tabLayout.addTab(tabLayout.newTab().setText("HOME"));
			tabLayout.addTab(tabLayout.newTab().setText("TOKO ONLINE"));

			tabLayout.setVisibility(View.VISIBLE);


			//PagerIndukKategoriAdapter pagerIndukKategoriAdapter = new PagerIndukKategoriAdapter(context, ((MainActivity) context).getSupportFragmentManager(), MainActivity.dashboard_produk_induk.size());
			PagerIndukKategoriAdapter pagerIndukKategoriAdapter = new PagerIndukKategoriAdapter(context, ((MainActivity) context).getSupportFragmentManager(), 2);
			viewPager.setAdapter(pagerIndukKategoriAdapter);
		}

	}
}
