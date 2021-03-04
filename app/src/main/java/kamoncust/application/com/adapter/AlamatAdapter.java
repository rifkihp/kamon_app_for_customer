package kamoncust.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import android.widget.TextView;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.model.alamat;

import static kamoncust.application.com.libs.CommonUtilities.getOptionsImage;
import static kamoncust.application.com.libs.CommonUtilities.initImageLoader;


public class AlamatAdapter extends BaseAdapter {

    Context context;
    ArrayList<alamat> alamatlist;

    public AlamatAdapter(Context context, ArrayList<alamat> alamatlist) {
        this.context = context;
        this.alamatlist = alamatlist;
    }

    public void UpdateAlamatAdapter(ArrayList<alamat> alamatlist) {
        this.alamatlist = alamatlist;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return alamatlist.size();
    }

    @Override
    public Object getItem(int position) {
        return alamatlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.alamat_item_list, null);

            viewHolder = new ViewHolder();
            viewHolder.nama       = convertView.findViewById(R.id.txtNamaAlamat);
            viewHolder.alamat     = convertView.findViewById(R.id.txtDetailAlamat);
            viewHolder.utamakan   = convertView.findViewById(R.id.utamakan);
            viewHolder.edit       = convertView.findViewById(R.id.edit);
            viewHolder.hapus      = convertView.findViewById(R.id.hapus);
            viewHolder.as_default = convertView.findViewById(R.id.checkbox_as_default);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final alamat alamat_item = (alamat) getItem(position);

        viewHolder.nama.setText(alamat_item.getNama());
        viewHolder.alamat.setText(alamat_item.getAlamat());
        viewHolder.as_default.setChecked(alamat_item.getAsDefaultAlamat());

        viewHolder.as_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) parent.getContext()).utamakanSelectedAlamat(alamat_item);
            }
        });

        viewHolder.utamakan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) parent.getContext()).utamakanSelectedAlamat(alamat_item);
            }
        });

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) parent.getContext()).editSelectedAlamat(alamat_item);
            }
        });

        viewHolder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) parent.getContext()).deleteSelectedAlamat(alamat_item);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                //.loadDetailMenu(alamat_item);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView nama, alamat;
        TextView utamakan, edit, hapus;
        CheckBox as_default;

    }
}




