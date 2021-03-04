package kamoncust.application.com.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import kamoncust.application.com.fragment.TabKategoriFragment;

public class PagerKategoriAdapter extends FragmentStatePagerAdapter {

    Context context;
    int tab_length;

    public PagerKategoriAdapter(Context context, FragmentManager fm, int tab_length) {
        super(fm);
        this.context = context;
        this.tab_length = tab_length;
    }

    @Override
    public Fragment getItem(int position) {
        TabKategoriFragment tab_kategori = new TabKategoriFragment();
        tab_kategori.setDataProduk(context, position);

        return tab_kategori;
    }

    @Override
    public int getCount() {
        return tab_length;
    }
}