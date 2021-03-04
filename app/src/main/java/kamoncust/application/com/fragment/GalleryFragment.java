package kamoncust.application.com.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.TouchImageView;
import kamoncust.application.com.model.gallery;

public class GalleryFragment extends Fragment {

    Context context;
    gallery data_gallery;
    TouchImageView touchImageView;

    ImageLoader imageLoader;
    DisplayImageOptions imageOptionsGallery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.list_image_gallery, container, false);

        touchImageView = (TouchImageView) rootView.findViewById(R.id.imgDisplay);

        return rootView;
    }

    public void setDataGallery(Context context, gallery data_gallery) {
        this.context = context;
        this.data_gallery = data_gallery;

        CommonUtilities.initImageLoader(context);

        imageLoader = ImageLoader.getInstance();
        imageOptionsGallery = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageLoader.loadImage(CommonUtilities.SERVER_URL + "/uploads/produk/" + data_gallery.getGambar(), imageOptionsGallery, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
                touchImageView.setImageBitmap(loadedImage);
            }
        });
    }
}
