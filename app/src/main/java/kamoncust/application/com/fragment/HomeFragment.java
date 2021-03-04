package kamoncust.application.com.fragment;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import kamoncust.application.com.adapter.MitraAdapter;
import kamoncust.application.com.adapter.PagerKategoriAdapter;
import kamoncust.application.com.adapter.ShortcutAdapter;
import kamoncust.application.com.libs.CustomTabLayout;
import kamoncust.application.com.libs.StickyScrollView;
import kamoncust.application.com.model.produkgrup;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.ChildAnimationExample;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.ExpandableHeightGridView;
import kamoncust.application.com.libs.SliderLayout;
import kamoncust.application.com.model.banner;

public class HomeFragment extends Fragment {

	static StickyScrollView scrollView;
	static SliderLayout slider;
	static ExpandableHeightGridView shortcut;
	static ExpandableHeightGridView mitra;
	static CustomTabLayout tabLayout;
	static ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);

		scrollView  = rootView.findViewById(R.id.scroll);
		slider      = rootView.findViewById(R.id.slider);
		shortcut    = rootView.findViewById(R.id.shortcut);
		mitra       = rootView.findViewById(R.id.mitra);
		tabLayout   = rootView.findViewById(R.id.tab_layout);
		viewPager   = rootView.findViewById(R.id.pager);

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

		slider.setPresetTransformer(SliderLayout.Transformer.Default);
		slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
		slider.setCustomAnimation(new ChildAnimationExample());
		slider.setDuration(4000);
		slider.addOnPageChangeListener((MainActivity) getActivity());

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//banner
		slider.removeAllSliders();
		for (banner data_banner : MainActivity.dashboard_banner) {
			TextSliderView textSliderView = new TextSliderView(getActivity());
			textSliderView
					.image(CommonUtilities.SERVER_URL + "/uploads/banner/" + data_banner.getUrl_image())
					.setScaleType(BaseSliderView.ScaleType.Fit);

			slider.addSlider(textSliderView);
		}

		//shortcut
		shortcut.setVisibility(MainActivity.tampilkan_shortcut? View.VISIBLE : View.GONE);
		shortcut.setAdapter(new ShortcutAdapter(getActivity(), MainActivity.dashboard_shortcut));

		//mitra
		mitra.setVisibility(MainActivity.tampilkan_mitra? View.VISIBLE : View.GONE);
		mitra.setAdapter(new MitraAdapter(getActivity(), MainActivity.dashboard_mitra));

		//PRODUK HOME
		tabLayout.setVisibility(MainActivity.tampilkan_produk_home? View.VISIBLE : View.GONE);
		viewPager.setVisibility(MainActivity.tampilkan_produk_home? View.VISIBLE : View.GONE);
		if(MainActivity.tampilkan_produk_home) {
			tabLayout.removeAllTabs();
			tabLayout.setTabMode(MainActivity.dashboard_produk_home.size() < 5 ? TabLayout.MODE_FIXED : TabLayout.MODE_SCROLLABLE);
			for (produkgrup tab_produk_home : MainActivity.dashboard_produk_home) {
				tabLayout.addTab(tabLayout.newTab().setText(tab_produk_home.getNama()));
			}

			PagerKategoriAdapter pagerKategoriAdapter = new PagerKategoriAdapter(getActivity(), getChildFragmentManager(), MainActivity.dashboard_produk_home.size());
			viewPager.setAdapter(pagerKategoriAdapter);
		}
	}

	@Override
	public void onStop() {
		slider.stopAutoCycle();
		super.onStop();
	}
}
