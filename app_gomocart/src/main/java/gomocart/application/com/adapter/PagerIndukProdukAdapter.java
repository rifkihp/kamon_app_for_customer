package gomocart.application.com.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import gomocart.application.com.fragment.Produk4KomoditiFragment;
import gomocart.application.com.fragment.Produk2PrimkopFragment;
import gomocart.application.com.fragment.Produk1PuskopFragment;
import gomocart.application.com.fragment.Produk3UmkmFragment;
import gomocart.application.com.gomocart.MainActivity;

public class PagerIndukProdukAdapter extends FragmentStatePagerAdapter {

    Context context;

    public PagerIndukProdukAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        int id = MainActivity.dashboard_list_tab_kategori.get(position).getId();
        switch (id) {
            case 0: {
                Produk1PuskopFragment produkPuskopFragment = new Produk1PuskopFragment();
                return produkPuskopFragment;
            }
            case 1: {
                Produk2PrimkopFragment produkPrimkopFragment = new Produk2PrimkopFragment();
                return produkPrimkopFragment;
            }
            case 2: {
                Produk3UmkmFragment produkUmkmFragment = new Produk3UmkmFragment();
                return produkUmkmFragment;
            }
            case 3: {
                Produk4KomoditiFragment produkKomoditiFragment = new Produk4KomoditiFragment();
                return produkKomoditiFragment;
            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return MainActivity.dashboard_list_tab_kategori.size();
    }
}