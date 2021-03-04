package kamoncust.application.com.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import kamoncust.application.com.fragment.KategoriLevel2Fragment;
import kamoncust.application.com.fragment.KategoriLevel3Fragment;

public class PagerKategoriLevel2Adapter extends FragmentStatePagerAdapter {

    public PagerKategoriLevel2Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new KategoriLevel2Fragment();
            case 1:
                return new KategoriLevel3Fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}