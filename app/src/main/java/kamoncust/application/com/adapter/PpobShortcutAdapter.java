package kamoncust.application.com.adapter;

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
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.model.kategori;

public class PpobShortcutAdapter extends BaseAdapter {

    Context context;
    ArrayList<kategori> kategorilist;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public PpobShortcutAdapter(Context context, ArrayList<kategori> listShortcut) {
        this.context = context;
        this.kategorilist = listShortcut;

        imageLoader = ImageLoader.getInstance();
        options = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
    }

    public void UpdatePpobShortcutAdapter(ArrayList<kategori> listShortcut) {
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
            convertView = layoutInflater.inflate(R.layout.listppobshortcut, null);

            viewHolder = new ViewHolder();

            viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final kategori data = (kategori) getItem(position);
        viewHolder.position = position;

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/ppob/"+data.getHeader();
        imageLoader.displayImage(url, viewHolder.image, options);
        viewHolder.title.setText(data.getNama());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                //((MainActivity) context).openPpob(data);
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
