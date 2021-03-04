package gomocart.application.com.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import gomocart.application.com.fragment.DaftarPesanan5BatalFragment;
import gomocart.application.com.fragment.DaftarPesanan1BelumBayarFragment;
import gomocart.application.com.fragment.DaftarPesanan3SedangKirimFragment;
import gomocart.application.com.fragment.DaftarPesanan2SedangProsesFragment;
import gomocart.application.com.fragment.DaftarPesanan4SelesaiFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new DaftarPesanan1BelumBayarFragment();
            case 1:
                return new DaftarPesanan2SedangProsesFragment();
            case 2:
                return new DaftarPesanan3SedangKirimFragment();
            case 3:
                return new DaftarPesanan4SelesaiFragment();
            case 4:
                return new DaftarPesanan5BatalFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}