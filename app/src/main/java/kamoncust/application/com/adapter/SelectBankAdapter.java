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
import java.util.UUID;

import android.widget.TextView;
import kamoncust.application.com.fragment.ProsesCheckoutMetodePembayaranFragment;
import kamoncust.application.com.kamoncust.ProsesCheckoutActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.ResizableImageView;
import kamoncust.application.com.model.bank;


public class SelectBankAdapter extends BaseAdapter {

    Context context;
    ArrayList<bank> banklist;
    ImageLoader imageLoader;
    DisplayImageOptions options;

    public SelectBankAdapter(Context context, ArrayList<bank> banklist) {
        this.context = context;
        this.banklist = banklist;

        imageLoader = ImageLoader.getInstance();
        options = CommonUtilities.getOptionsImage(R.drawable.logo_grayscale, R.drawable.logo_grayscale);
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
            convertView = layoutInflater.inflate(R.layout.select_bank_item_list, null);

            viewHolder = new ViewHolder();
            viewHolder.logo_bang   = (ImageView) convertView.findViewById(R.id.image);
            viewHolder.nama_bank   = (TextView)convertView.findViewById(R.id.title);
            viewHolder.radioSelect = (ImageView) convertView.findViewById(R.id.radioSelect);
            
            convertView.setTag(viewHolder);

        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final bank bank_item = (bank) getItem(position);
        viewHolder.position = banklist.indexOf(bank_item);

        viewHolder.nama_bank.setText(bank_item.getNama_bank()+"\n"+bank_item.getNo_rekening() + " an. " +bank_item.getNama_pemilik_rekening());
        viewHolder.radioSelect.setImageResource(bank_item.getSelected()?R.drawable.radioblack:R.drawable.radiouncheked);

        String server = CommonUtilities.SERVER_URL;
        //UUID token = UUID.randomUUID();
        String url = server+"/uploads/bank/"+bank_item.getGambar();//+"?"+ token;
        imageLoader.displayImage(url, viewHolder.logo_bang, options);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                int bank_id = ((ProsesCheckoutActivity) parent.getContext()).getBank_index();
                if(bank_id>-1 && bank_id<banklist.size()) {
                    banklist.get(bank_id).setSelected(false);
                    View v = ProsesCheckoutMetodePembayaranFragment.listViewBank.getChildAt(bank_id);
                    if(v == null) return;
                    ImageView radioS = (ImageView) v.findViewById(R.id.radioSelect);
                    radioS.setImageResource(R.drawable.radiouncheked);
                }

                ((ProsesCheckoutActivity) parent.getContext()).setMetodePembayaranBank(viewHolder.position,  banklist.get(viewHolder.position));
                banklist.get(viewHolder.position).setSelected(true);
                View v = ProsesCheckoutMetodePembayaranFragment.listViewBank.getChildAt(viewHolder.position);
                if(v == null) return;
                ImageView radioS = (ImageView) v.findViewById(R.id.radioSelect);
                radioS.setImageResource(R.drawable.radioblack);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView nama_bank;
        ImageView logo_bang;
        public ImageView radioSelect;

        public int position;
    }
}




