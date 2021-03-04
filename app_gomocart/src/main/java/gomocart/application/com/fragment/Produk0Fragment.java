package gomocart.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import gomocart.application.com.adapter.PagerIndukProdukAdapter;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.produk_kategori;

public class Produk0Fragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_induk_produk_gomocart, container, false);

        tabLayout = rootView.findViewById(R.id.tab_layout);
        viewPager = rootView.findViewById(R.id.pager);

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

        //tab kategori
        tabLayout.removeAllTabs();
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        for(produk_kategori item: MainActivity.dashboard_list_tab_kategori) {
            tabLayout.addTab(tabLayout.newTab().setText(item.getNama()));
        }

        PagerIndukProdukAdapter pagerIndukProdukAdapter = new PagerIndukProdukAdapter(getActivity().getApplicationContext(), ((MainActivity) getActivity()).getSupportFragmentManager());
        viewPager.setAdapter(pagerIndukProdukAdapter);

        ((MainActivity) getActivity()).updateBottomNavigationView("Home");

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    public static void loadDataProduk() {
        int index = viewPager.getCurrentItem();
        if(tabLayout.getTabCount()==4) {
            switch (index) {

                case 0: {
                    Produk1PuskopFragment.loadDataProduk();
                    Produk2PrimkopFragment.loadDataProduk();

                    break;
                }

                case 1: {
                    Produk1PuskopFragment.loadDataProduk();
                    Produk2PrimkopFragment.loadDataProduk();
                    Produk3UmkmFragment.loadDataProduk();

                    break;
                }

                case 2: {
                    Produk2PrimkopFragment.loadDataProduk();
                    Produk3UmkmFragment.loadDataProduk();

                    break;
                }

                case 3: {
                    Produk3UmkmFragment.loadDataProduk();

                    break;
                }
            }
        } else
        if(tabLayout.getTabCount()==3) {
            if(index<=1) {
                Produk1PuskopFragment.loadDataProduk();
                Produk3UmkmFragment.loadDataProduk();
            } else {
                Produk3UmkmFragment.loadDataProduk();
            }
        }
    }
}
