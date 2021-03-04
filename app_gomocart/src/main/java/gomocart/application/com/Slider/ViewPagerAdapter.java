package gomocart.application.com.Slider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.gallery_list;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by mohamedzakaria on 8/11/16.
 */
public class ViewPagerAdapter extends PagerAdapter {

    Activity activity;
    LayoutInflater mLayoutInflater;
    ArrayList<String> images;
    PhotoViewAttacher mPhotoViewAttacher;
    private boolean isShowing = true;
    private RecyclerView imagesHorizontalList;

    public ViewPagerAdapter(Activity activity, ArrayList<String> images, RecyclerView imagesHorizontalList) {
        this.activity = activity;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.images = images;
        this.imagesHorizontalList = imagesHorizontalList;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.z_pager_item, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.iv);
        Glide.with(activity).load(images.get(position)).listener(new RequestListener<Drawable>() {

            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                mPhotoViewAttacher = new PhotoViewAttacher(imageView);

                mPhotoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        /*if (isShowing) {
                            isShowing = false;
                            imagesHorizontalList.animate().translationY(imagesHorizontalList.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                        } else {
                            isShowing = true;
                            imagesHorizontalList.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                        }*/

                        ArrayList<gallery_list> gallery_lists = new ArrayList<>();
                        //gallery_lists.add(new gallery_list(gallerylist));
                        /*Intent inten = new Intent(activity, DetailSlideGalleryActivity.class);
                        inten.putExtra("gallery", gallery_lists);
                        inten.putExtra("position", String.valueOf(position));
                        activity.startActivity(inten);*/
                    }

                    @Override
                    public void onOutsidePhotoTap() {

                    }
                });

                return false;
            }

        }).into(imageView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
