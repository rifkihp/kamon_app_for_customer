package kamoncust.application.com.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import kamoncust.application.com.fragment.KategoriIndukFragment;
import kamoncust.application.com.fragment.KategoriLevel2Fragment;
import kamoncust.application.com.fragment.KategoriLevel3Fragment;

public class PagerKategoriLevelAdapter extends FragmentStatePagerAdapter {

    FragmentManager fm;
    Context context;

    public PagerKategoriLevelAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new KategoriIndukFragment();
            case 1:
                return new KategoriLevel2Fragment();
            case 2:
                return new KategoriLevel3Fragment();
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}