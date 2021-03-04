package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import android.widget.TextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.kategori;

public class KategoriAdapter extends BaseAdapter {

    Context context;
    ArrayList<kategori> kategorilist;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public KategoriAdapter(Context context, ArrayList<kategori> listShortcut) {
        this.context = context;
        this.kategorilist = listShortcut;

        imageLoader = ImageLoader.getInstance();
        options = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
    }

    public void UpdateShortcutAdapter(ArrayList<kategori> listShortcut) {
        this.kategorilist = listShortcut;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return kategorilist.size();
    }

    @Override
    public Object getItem(int position) {
        return kategorilist.get(position);
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
            convertView = layoutInflater.inflate(R.layout.listkategori_gomocart, null);

            viewHolder = new ViewHolder();

            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.image.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final kategori data = (kategori) getItem(position);
        viewHolder.position = position;

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/category/"+kategorilist.get(position).getHeader();
        imageLoader.displayImage(url, viewHolder.image, options);
        viewHolder.title.setText(kategorilist.get(position).getNama());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                ((MainActivity) context).openProdukKategori(kategorilist.get(viewHolder.position));
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
