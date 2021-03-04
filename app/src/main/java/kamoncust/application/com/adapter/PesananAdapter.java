package kamoncust.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.model.produk;

public class PesananAdapter extends BaseAdapter {
    Context context;
    ArrayList<produk> pesanan_list;

    public PesananAdapter(Context context, ArrayList<produk> pesanan_list) {
        this.context = context;
        this.pesanan_list = pesanan_list;
    }

    public void UpdatePesananAdapter(ArrayList<produk> pesanan_list) {
        this.pesanan_list = pesanan_list;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return pesanan_list.size();
    }

    @Override
    public Object getItem(int position) {
        return pesanan_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {


        final ViewHolder viewHolder;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.pesanan_list,null);

            viewHolder = new ViewHolder();

            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.qty = convertView.findViewById(R.id.qty);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.total = convertView.findViewById(R.id.total);

            convertView.setTag(viewHolder);
        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final produk produk_item = (produk) getItem(position);

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/produk/"+produk_item.getFoto1_produk();
        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);

        viewHolder.title.setText(produk_item.getNama());
        viewHolder.qty.setText(produk_item.getQty()+" "+produk_item.getSatuan());
        viewHolder.price.setText(" @ "+CommonUtilities.getCurrencyFormat(produk_item.getHarga_jual(), "Rp. ")+" "+(produk_item.getPersen_diskon()>0?" (-"+produk_item.getPersen_diskon()+"%)"+CommonUtilities.getCurrencyFormat((produk_item.getPersen_diskon()*0.01)*produk_item.getHarga_jual(), "Rp. "):""));
        viewHolder.total.setText(CommonUtilities.getCurrencyFormat(produk_item.getGrandtotal(), "Rp. "));

        return convertView;
    }

    private class ViewHolder{
        ImageView image;
        TextView title;
        TextView qty;

        //View separator_ukuran;
        //TextView ukuran;
        //View separator_warna;
        //TextView warna;

        TextView price;
        TextView total;
    }
}




