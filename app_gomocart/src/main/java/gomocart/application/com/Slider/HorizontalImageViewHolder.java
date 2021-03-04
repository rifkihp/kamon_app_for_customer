package gomocart.application.com.Slider;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import gomocart.application.com.gomocart.R;

/**
 * Created by mohamedzakaria on 8/7/16.
 */
public class HorizontalImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView image;
    public LinearLayout llImageBG;

    public HorizontalImageViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.iv);
        llImageBG = (LinearLayout) itemView.findViewById(R.id.llImageBG);
    }
}
