package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gomocart.application.com.adapter.PagerAdapter;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;

public class DaftarPesanan0Fragment extends Fragment {

	public static TabLayout tabLayout;
	public static ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_daftar_pesanan_gomocart, container, false);

		tabLayout = rootView.findViewById(R.id.tab_layout);
		viewPager = rootView.findViewById(R.id.pager);

		tabLayout.addTab(tabLayout.newTab().setText("Belum Bayar"));
		tabLayout.addTab(tabLayout.newTab().setText("Dalam Proses"));
		tabLayout.addTab(tabLayout.newTab().setText("Sedang Kirim"));
		tabLayout.addTab(tabLayout.newTab().setText("Selesai"));
		tabLayout.addTab(tabLayout.newTab().setText("Batal"));

		PagerAdapter pagerAdapter = new PagerAdapter(((MainActivity) getActivity()).getSupportFragmentManager(), 5);
		viewPager.setAdapter(pagerAdapter);
		viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
		tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				viewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});

		((MainActivity) getActivity()).updateBottomNavigationView("MyOrder");
		return rootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

}
