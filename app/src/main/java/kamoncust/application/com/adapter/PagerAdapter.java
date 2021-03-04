package kamoncust.application.com.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import kamoncust.application.com.fragment.DaftarPesananBatalFragment;
import kamoncust.application.com.fragment.DaftarPesananMenungguFragment;
import kamoncust.application.com.fragment.DaftarPesananProsesFragment;
import kamoncust.application.com.fragment.DaftarPesananSelesaiFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {


    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new DaftarPesananMenungguFragment();
            case 1:
                return new DaftarPesananProsesFragment();
            case 2:
                return new DaftarPesananSelesaiFragment();
            case 3:
                return new DaftarPesananBatalFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}