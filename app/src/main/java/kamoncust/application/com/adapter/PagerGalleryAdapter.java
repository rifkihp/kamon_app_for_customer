package kamoncust.application.com.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import kamoncust.application.com.fragment.GalleryFragment;
import kamoncust.application.com.model.gallery;

public class PagerGalleryAdapter extends FragmentStatePagerAdapter {

    Context context;
    ArrayList<gallery> list_gallery;

    public PagerGalleryAdapter(Context context, FragmentManager fm, ArrayList<gallery> list_gallery) {
        super(fm);
        this.context = context;
        this.list_gallery = list_gallery;
    }

    @Override
    public Fragment getItem(int position) {
        GalleryFragment tab_gallery = new GalleryFragment();
        tab_gallery.setDataGallery(context, list_gallery.get(position));

        return tab_gallery;
    }

    @Override
    public int getCount() {
        return list_gallery.size();
    }
}