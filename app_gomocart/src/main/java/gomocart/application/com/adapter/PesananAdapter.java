package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.model.produk;

public class PesananAdapter extends BaseAdapter {

    Context context;
    List<produk> cartlist;
    DatabaseHandler dh;
    int kode_grup;
    ImageLoader imageLoader;
    DisplayImageOptions imageOptions;

    public PesananAdapter(Context context, DatabaseHandler dh, List<produk> cartlist, ImageLoader imageLoader, DisplayImageOptions imageOptions, int kode_grup) {
        this.context = context;
        this.cartlist = cartlist;
        this.dh = dh;
        this.kode_grup = kode_grup;
        this.imageLoader = imageLoader;
        this.imageOptions = imageOptions;
    }

    public void UpdatePesananAdapter(List<produk> cartlist) {
        this.cartlist = cartlist;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return cartlist.size();
    }

    @Override
    public Object getItem(int position) {
        return cartlist.get(position);
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
            convertView = layoutInflater.inflate(R.layout.pesanan_list_gomocart,null);

            viewHolder = new ViewHolder();

            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.ukuran_warna = convertView.findViewById(R.id.ukuran_warna);

            viewHolder.qty                   = convertView.findViewById(R.id.qty);
            viewHolder.harga                 = convertView.findViewById(R.id.txtHargaProduk);
            viewHolder.ket_grosir            = convertView.findViewById(R.id.ket_grosir);
            viewHolder.cutprice_harga_jual   = convertView.findViewById(R.id.txtCutpriceHargaJual);
            viewHolder.cutprice_harga_diskon = convertView.findViewById(R.id.txtCutpriceHargaDiskon);
            viewHolder.total                 = convertView.findViewById(R.id.total);

            convertView.setTag(viewHolder);
            viewHolder.cutprice_harga_jual.setPaintFlags(viewHolder.cutprice_harga_jual.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final produk item = (produk) getItem(position);
        String server = kode_grup==2?CommonUtilities.SERVER_URL+"/uploads/produk/":CommonUtilities.SERVER_URL+"/images/barang/";
        String url = server+item.getFoto1_produk();

        imageLoader.displayImage(url, viewHolder.image, imageOptions);

        viewHolder.title.setText(item.getNama());
        viewHolder.ukuran_warna.setText(item.getUkuran()+ " / "+item.getWarna());

        viewHolder.qty.setText(CommonUtilities.getNumberFormat(item.getQty())+" "+item.getSatuan());
        viewHolder.harga.setText(" @ "+ CommonUtilities.getCurrencyFormat(item.getSubtotal(), "Rp. "));
        if(item.getHarga_grosir()>0) {
            viewHolder.ket_grosir.setVisibility(View.VISIBLE);
            viewHolder.cutprice_harga_jual.setVisibility(View.GONE);
            viewHolder.cutprice_harga_diskon.setVisibility(View.GONE);
        } else {
            viewHolder.ket_grosir.setVisibility(View.GONE);
            viewHolder.cutprice_harga_jual.setVisibility(View.VISIBLE);
            viewHolder.cutprice_harga_diskon.setVisibility(View.VISIBLE);
        }

        if(item.getHarga_diskon()>item.getSubtotal()) {
            viewHolder.cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(item.getHarga_diskon(), "Rp. "));
        } else if(item.getHarga_jual()>item.getSubtotal()) {
            viewHolder.cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(item.getHarga_jual(), "Rp. "));
        } else {
            viewHolder.cutprice_harga_jual.setText("");
        }
        viewHolder.cutprice_harga_diskon.setText(item.getPersen_diskon()>0?"("+item.getPersen_diskon() + "%)":"");
        viewHolder.total.setText(CommonUtilities.getCurrencyFormat(item.getGrandtotal(), "Rp. "));

        return convertView;
    }

    private class ViewHolder{
        ImageView image;
        TextView title;
        TextView ukuran_warna;
        TextView qty;

        TextView harga;
        TextView ket_grosir;
        TextView cutprice_harga_jual;
        TextView cutprice_harga_diskon;
        TextView total;
    }
}




