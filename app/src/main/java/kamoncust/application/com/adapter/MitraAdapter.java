package kamoncust.application.com.adapter;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;

import android.widget.TextView;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.libs.MyImageDownloader;
import kamoncust.application.com.model.mitra;

public class MitraAdapter extends BaseAdapter {

    Context context;
    ArrayList<mitra> mitralist;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public MitraAdapter(Context context, ArrayList<mitra> listShortcut) {
        this.context = context;
        this.mitralist = listShortcut;

        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager)
                    context.getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize)
                .memoryCache(new FIFOLimitedMemoryCache(memoryCacheSize - 1000000))
                .denyCacheImageMultipleSizesInMemory()
                //.discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new MyImageDownloader(context))
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        options = CommonUtilities.getOptionsImage(R.drawable.userdefault, R.drawable.userdefault);
    }

    public void UpdateMitraAdapter(ArrayList<mitra> listShortcut) {
        this.mitralist = listShortcut;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mitralist.size();
    }

    @Override
    public Object getItem(int position) {
        return mitralist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listmitra, null);

            viewHolder = new ViewHolder();

            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.image.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final mitra data = (mitra) getItem(position);
        viewHolder.position = position;

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/member/"+mitralist.get(position).getPhoto();
        imageLoader.displayImage(url, viewHolder.image, options);
        viewHolder.title.setText(mitralist.get(position).getNama());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                ((MainActivity) context).openProdukMitra(mitralist.get(viewHolder.position));
            }
        });

        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        TextView title;
        int position;
    }
}
