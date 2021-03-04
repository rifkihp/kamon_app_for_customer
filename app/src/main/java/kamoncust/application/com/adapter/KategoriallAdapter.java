package kamoncust.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.ResizableImageView;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.model.kategori;

public class KategoriallAdapter extends BaseAdapter {

    Context context;
    ArrayList<kategori> kategorialllist;

    public KategoriallAdapter(Context context, ArrayList<kategori> kategorialllist) {
        this.context = context;
        this.kategorialllist = kategorialllist;
    }

    public void UpdateKategoriallAdapter(ArrayList<kategori> listShortcut) {
        this.kategorialllist = listShortcut;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return kategorialllist.size();
    }

    @Override
    public Object getItem(int position) {
        return kategorialllist.get(position);
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
            convertView = layoutInflater.inflate(R.layout.listkategoriall_umkm, null);

            viewHolder = new ViewHolder();

            viewHolder.image = convertView.findViewById(R.id.gambar);
            //viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.image.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        //final kategori data = (kategori) getItem(position);
        viewHolder.position = position;

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/category/"+kategorialllist.get(position).getHeader();
        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionKategori);
        //viewHolder.title.setText(kategorialllist.get(position).getNama());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                //((MainActivity) context).openProdukMitra(kategorialllist.get(viewHolder.position));
            }
        });

        return convertView;

    }

    private class ViewHolder {

        ResizableImageView image;
        //TextView title;
        int position;
    }
}
