package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import androidx.appcompat.widget.AppCompatRadioButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import android.widget.TextView;
import gomocart.application.com.fragment.ProsesCheckoutMetodePembayaranFragment;
import gomocart.application.com.gomocart.ProsesCheckoutActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.model.bank;

public class SelectBankAdapter extends BaseAdapter {

    Context context;
    ArrayList<bank> banklist;

    public SelectBankAdapter(Context context, ArrayList<bank> banklist) {
        this.context = context;
        this.banklist = banklist;
    }

    public void UpdateBankAdapter(ArrayList<bank> banklist) {
        this.banklist = banklist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return banklist.size();
    }

    @Override
    public Object getItem(int position) {
        return banklist.get(position);
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
            convertView = layoutInflater.inflate(R.layout.select_bank_item_list_gomocart, null);

            viewHolder = new ViewHolder();
            viewHolder.logo_bank = convertView.findViewById(R.id.image);
            viewHolder.nama_bank = convertView.findViewById(R.id.title);
            viewHolder.radioSelect = convertView.findViewById(R.id.radioSelect);

            convertView.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final bank bank_item = (bank) getItem(position);
        viewHolder.position = banklist.indexOf(bank_item);

        viewHolder.nama_bank.setText(bank_item.getNama_bank() + "\n" + bank_item.getNo_rekening() + " " + bank_item.getNama_pemilik_rekening());
        viewHolder.radioSelect.setChecked(bank_item.getSelected());

        String server = CommonUtilities.SERVER_URL;
        String url = server + "/uploads/bank/" + bank_item.getGambar();//+"?"+ token;
        ProsesCheckoutActivity.imageLoader.displayImage(url, viewHolder.logo_bank, ProsesCheckoutActivity.imageOptionDefault);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                int bank_id = -1;
                bank_id = ((ProsesCheckoutActivity) parent.getContext()).getBank_index();

                if (bank_id > -1 && bank_id < banklist.size()) {
                    banklist.get(bank_id).setSelected(false);
                    View v = ProsesCheckoutMetodePembayaranFragment.listview.getChildAt(bank_id);
                    if (v == null) return;
                    AppCompatRadioButton radioS = v.findViewById(R.id.radioSelect);
                    radioS.setChecked(false);
                }
                ((ProsesCheckoutActivity) parent.getContext()).setBank(viewHolder.position, banklist.get(viewHolder.position));

                banklist.get(viewHolder.position).setSelected(true);
                View v;
                v = ProsesCheckoutMetodePembayaranFragment.listview.getChildAt(viewHolder.position);

                if (v == null) return;
                AppCompatRadioButton radioS = v.findViewById(R.id.radioSelect);
//                radioS.setImageResource(R.drawable.radioblack);
                radioS.setChecked(true);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView nama_bank;
        ImageView logo_bank;
        public AppCompatRadioButton radioSelect;

        public int position;
    }
}




