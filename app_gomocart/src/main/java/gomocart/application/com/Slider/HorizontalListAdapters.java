package gomocart.application.com.Slider;

import android.app.Activity;
import android.graphics.ColorMatrix;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import gomocart.application.com.gomocart.R;

/**
 * Created by mohamedzakaria on 8/12/16.
 */
public class HorizontalListAdapters extends RecyclerView.Adapter<HorizontalImageViewHolder> {
    ArrayList<String> images;
    Activity activity;
    int selectedItem = -1;
    OnImgClick imgClick;

    public HorizontalListAdapters(Activity activity, ArrayList<String> images, OnImgClick imgClick) {
        this.activity = activity;
        this.images = images;
        this.imgClick = imgClick;
    }

    @Override
    public HorizontalImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HorizontalImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.z_item_image_horizontal, null));
    }

    @Override
    public void onBindViewHolder(HorizontalImageViewHolder holder, final int position) {
        Glide.with(activity).load(images.get(position)).into(holder.image);
        ColorMatrix matrix = new ColorMatrix();
        if (selectedItem != position) {
            /*matrix.setSaturation(0);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.image.setColorFilter(filter);
            holder.image.setAlpha(0.5f);*/
            holder.llImageBG.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.rect_gray));
        } else {
            /*matrix.setSaturation(1);

            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
            holder.image.setColorFilter(filter);
            holder.image.setAlpha(1f);*/
            holder.llImageBG.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.rect_blue));
        }

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgClick.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setSelectedItem(int position) {
        selectedItem = position;
        notifyDataSetChanged();
    }
}
