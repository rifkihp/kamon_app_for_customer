package kamoncust.application.com.fragment;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kamoncust.application.com.adapter.PagerAdapter;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;

public class DaftarPesananFragment extends Fragment {

	TabLayout tabLayout;
	public static ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_daftar_pesanan, container, false);

		tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
		viewPager = (ViewPager) rootView.findViewById(R.id.pager);

		tabLayout.addTab(tabLayout.newTab().setText("MENUNGGU"));
		tabLayout.addTab(tabLayout.newTab().setText("PROSES"));
		tabLayout.addTab(tabLayout.newTab().setText("SELESAI"));
		tabLayout.addTab(tabLayout.newTab().setText("BATAL"));

		PagerAdapter pagerAdapter = new PagerAdapter(((MainActivity) getActivity()).getSupportFragmentManager());
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

		return rootView;
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
