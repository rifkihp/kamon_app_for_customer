package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRadioButton;

import java.util.ArrayList;

import gomocart.application.com.gomocart.KurirActivity;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.ongkir;

public class SelectKurirAdapter extends BaseAdapter {

    ArrayList<ongkir> listDataKurir;
    Context context;

    public SelectKurirAdapter(Context context, ArrayList<ongkir> listDataKurir) {
        this.context = context;
        this.listDataKurir = listDataKurir;
    }

    public void UpdateSelectKurirAdapter(ArrayList<ongkir> listDataKurir) {
        this.listDataKurir = listDataKurir;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listDataKurir.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataKurir.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public ImageView image;
        public TextView title;
        public AppCompatRadioButton radioSelect;
        public int position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder view;
        LayoutInflater inflator = LayoutInflater.from(parent.getContext());
        if (convertView == null) {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.select_kurir_item_list_gomocart, null);

            view.image       = convertView.findViewById(R.id.image);
            view.title       = convertView.findViewById(R.id.title);
            view.radioSelect = convertView.findViewById(R.id.radioSelect);

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        final ongkir prod = listDataKurir.get(position);
        view.position = listDataKurir.indexOf(prod);

        String server = CommonUtilities.SERVER_URL;
        String url = server + "/uploads/ekspedisi/" + listDataKurir.get(position).getGambar();
        ProsesCheckoutActivity.imageLoader.displayImage(url, view.image, ProsesCheckoutActivity.imageOptionDefault);

        view.title.setText(listDataKurir.get(position).getNama_kurir() + "\n" + listDataKurir.get(position).getNama_service() + (listDataKurir.get(position).getEtd().trim().length() > 0 ? " (" + listDataKurir.get(position).getEtd().trim() + " hari)" : "") + "\n" + listDataKurir.get(position).getTarif());
        view.radioSelect.setVisibility(View.GONE);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                ((KurirActivity) parent.getContext()).selectedOngkir(listDataKurir.get(view.position));
            }
        });

        return convertView;
    }
}
