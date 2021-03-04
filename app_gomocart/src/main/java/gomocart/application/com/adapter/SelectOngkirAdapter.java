package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import android.widget.TextView;
import gomocart.application.com.fragment.ProsesCheckoutKurirPengirimanFragment;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.ongkir;

public class SelectOngkirAdapter extends BaseAdapter {

    ArrayList<ongkir> listDataOngkir;
    Context context;

    public SelectOngkirAdapter(Context context, ArrayList<ongkir> listDataOngkir) {
        this.context = context;
        this.listDataOngkir = listDataOngkir;
    }

    public void UpdateSelectOngkirAdapter(ArrayList<ongkir> listDataOngkir) {
        this.listDataOngkir = listDataOngkir;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listDataOngkir.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataOngkir.get(position);
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
            convertView = inflator.inflate(R.layout.select_ongkir_item_list_gomocart, null);

            view.image       = convertView.findViewById(R.id.image);
            view.title       = convertView.findViewById(R.id.title);
            view.radioSelect = convertView.findViewById(R.id.radioSelect);

            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }

        final ongkir prod = listDataOngkir.get(position);
        view.position = listDataOngkir.indexOf(prod);

        String server = CommonUtilities.SERVER_URL;
        String url = server + "/uploads/ekspedisi/" + listDataOngkir.get(position).getGambar();
        ProsesCheckoutActivity.imageLoader.displayImage(url, view.image, ProsesCheckoutActivity.imageOptionDefault);

        view.title.setText(listDataOngkir.get(position).getNama_kurir() + "\n" + listDataOngkir.get(position).getNama_service() + (listDataOngkir.get(position).getEtd().trim().length() > 0 ? " (" + listDataOngkir.get(position).getEtd().trim() + " hari)" : "") + "\n" + listDataOngkir.get(position).getTarif());
        view.radioSelect.setChecked(prod.getSelected());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                int ongkir_id = -1;
                ongkir_id = ((ProsesCheckoutActivity) parent.getContext()).getOngkir_index();

                if (ongkir_id > -1 && ongkir_id < listDataOngkir.size()) {
                    listDataOngkir.get(ongkir_id).setSelected(false);
                    View v = ProsesCheckoutKurirPengirimanFragment.listview.getChildAt(ongkir_id);
                    if (v == null) return;
                    AppCompatRadioButton radioS = v.findViewById(R.id.radioSelect);
                    radioS.setChecked(false);
                }

                ((ProsesCheckoutActivity) parent.getContext()).setOngkir(view.position, listDataOngkir.get(view.position));

                listDataOngkir.get(view.position).setSelected(true);
                View v;
                v = ProsesCheckoutKurirPengirimanFragment.listview.getChildAt(view.position);

                if (v == null) return;
                AppCompatRadioButton radioS = v.findViewById(R.id.radioSelect);
//                radioS.setImageResource(R.drawable.radioblack);
                radioS.setChecked(true);
            }
        });

        return convertView;
    }
}
