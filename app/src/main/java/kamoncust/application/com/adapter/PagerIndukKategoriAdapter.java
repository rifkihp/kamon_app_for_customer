package kamoncust.application.com.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import kamoncust.application.com.fragment.HomeFragment;
import kamoncust.application.com.fragment.ProdukTokoFragment;
import kamoncust.application.com.fragment.TabIndukKategoriFragment;

public class PagerIndukKategoriAdapter extends FragmentStatePagerAdapter {

    Context context;
    int tab_length;

    public PagerIndukKategoriAdapter(Context context, FragmentManager fm, int tab_length) {
        super(fm);
        this.context = context;
        this.tab_length = tab_length;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0) {

            HomeFragment homeFragment = new HomeFragment();
            return homeFragment;
        } else if(position==1) {

            ProdukTokoFragment produkTokoFragment = new ProdukTokoFragment();
            return produkTokoFragment;

        } else {

            TabIndukKategoriFragment tab_induk_kategori = new TabIndukKategoriFragment();
            tab_induk_kategori.setDataProduk(context, position);

            return tab_induk_kategori;
        }
    }

    @Override
    public int getCount() {
        return tab_length;
    }
}