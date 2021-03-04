package kamoncust.application.com.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import kamoncust.application.com.kamoncust.DetailProdukTokoActivity;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;

public class ImageSliderAdapter extends PagerAdapter {
     
    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        // TODO Auto-generated method stub
         
        LayoutInflater inflater =  LayoutInflater.from(container.getContext());	
        
        View viewItem =  inflater.inflate(R.layout.layout_fullscreen_image, null);
        final ImageView imageView = (ImageView) viewItem.findViewById(R.id.imgDisplay);

        
        /*imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                 return DetailProdukTableStokActivity.mDetector.onTouchEvent(event);
            }
        });*/
        
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				//DetailProdukTableStokActivity.showDialogSaveAs();
				
				return false;
			}
		});
        
        ((ViewPager)container).addView(viewItem);
         
        return viewItem;
    }
 
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 0; //DetailProdukTableStokActivity.listGambarProduk.size();
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        // TODO Auto-generated method stub
         
        return view == ((View)object);
    }
 
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView((View) object);
    }
 
}