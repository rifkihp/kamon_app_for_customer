package kamoncust.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alexzh.circleimageview.CircleImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.ArrayList;

import android.widget.TextView;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.model.kategori;

public class ListKategoriAdapter extends BaseAdapter {

    Context context;
    ArrayList<kategori> listDataKategori = new ArrayList<>();
    int level;

    public ListKategoriAdapter(Context context, ArrayList<kategori> listDataKategori, int level) {
        this.context = context;
        this.listDataKategori = listDataKategori;
        this.level = level;
    }

    public void UpdateListKategoriAdapter(ArrayList<kategori> listDataKategori) {
        this.listDataKategori = listDataKategori;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listDataKategori.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataKategori.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public CircleImageView image;
        public TextView title;
        public int position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder view;
        LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
        if(convertView==null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.kategori_item_list, null);

            view.image = (CircleImageView) convertView.findViewById(R.id.image);
            view.title = (TextView) convertView.findViewById(R.id.title);
            view.image.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        final kategori prod = listDataKategori.get(position);
        view.position = listDataKategori.indexOf(prod);

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/category/"+listDataKategori.get(position).getHeader();
        MainActivity.imageLoader.displayImage(url, view.image, MainActivity.imageOptionKategori);

        view.title.setText(listDataKategori.get(position).getNama());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                //((MainActivity) parent.getContext()).openKategori(prod, level);
            }
        });

        return convertView;
    }
}
