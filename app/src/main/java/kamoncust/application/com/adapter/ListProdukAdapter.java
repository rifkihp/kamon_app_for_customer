package kamoncust.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.widget.TextView;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.DatabaseHandler;
import kamoncust.application.com.libs.ResizableImageView;
import kamoncust.application.com.model.produk;


public class ListProdukAdapter extends ArrayAdapter<produk> {

    //int number = 01;
    Context context;
    LayoutInflater lf;

    //List<ViewHolder> lstHolders;
    //Handler mHandler = new Handler();

    /*Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if(MainActivity.menu_selected==3) {
                synchronized (lstHolders) {
                    long currentTime = System.currentTimeMillis();
                    for (ViewHolder holder : lstHolders) {
                        holder.updateTimeRemaining(currentTime);
                    }
                }
                mHandler.postDelayed(this, 1000);
            } else {
                mHandler.removeCallbacks(this);
            }
        }
    };*/

    public ListProdukAdapter(Context context, List<produk> produklist) {
        super(context, 0, produklist);
        lf = LayoutInflater.from(context);
        this.context = context;

        //lstHolders = new ArrayList<>();
        //startUpdateTimer();
    }

    public void UpdateProduklistAdapter() {
        notifyDataSetChanged();
    }

    /*public void startUpdateTimer() {
        mHandler.postDelayed(updateRemainingTimeRunnable, 1000);
    }*/

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        final ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = lf.inflate(R.layout.list_vertical_productlist, parent, false);

            //LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            //convertView = layoutInflater.inflate(R.layout.cart_list,null);

            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.title = convertView.findViewById(R.id.txtNamaProduk);
            viewHolder.price = convertView.findViewById(R.id.txtHargaProduk);
            viewHolder.cutprice_harga_jual = convertView.findViewById(R.id.txtCutpriceHargaJual);
            viewHolder.cutprice_harga_diskon = convertView.findViewById(R.id.txtCutpriceHargaDiskon);
            viewHolder.periode_promo = convertView.findViewById(R.id.txtPeriodePromo);
            viewHolder.text = convertView.findViewById(R.id.text);
            viewHolder.plus = convertView.findViewById(R.id.plus);
            viewHolder.min  = convertView.findViewById(R.id.min);

            convertView.setTag(viewHolder);
            viewHolder.cutprice_harga_jual.setPaintFlags(viewHolder.cutprice_harga_jual.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            //viewHolder.cutprice_harga_diskon.setPaintFlags(viewHolder.cutprice_harga_diskon.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            /*synchronized (lstHolders) {
                lstHolders.add(viewHolder);
            }*/
        } else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final produk prod = getItem(position);

        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/produk/"+prod.getFoto1_produk();
        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);

        viewHolder.title.setText(prod.getNama());
        viewHolder.price.setText(CommonUtilities.getCurrencyFormat(prod.getSubtotal(), "Rp. ")+" / "+ prod.getSatuan());
        viewHolder.periode_promo.setVisibility(prod.getPeriode_promo().length()>0?View.VISIBLE:View.GONE);
        if(prod.getPeriode_promo().length()>0) {
            viewHolder.periode_promo.setText("Periode Promo: "+prod.getPeriode_promo());
        }

        if(prod.getHarga_diskon()>prod.getSubtotal()) {
            viewHolder.cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_diskon(), "Rp. "));
        } else if(prod.getHarga_jual()>prod.getSubtotal()) {
            viewHolder.cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_jual(), "Rp. "));
        } else {
            viewHolder.cutprice_harga_jual.setText("");
        }
        viewHolder.cutprice_harga_diskon.setText(prod.getPersen_diskon()>0?"("+prod.getPersen_diskon() + "%)":"");

        viewHolder.text.setText(""+prod.getQty());
        viewHolder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produk item = MainActivity.produklist.get(position);
                int qty = item.getQty();
                if (qty > 0) {
                    qty = qty - 1;
                    double grandtotal = qty*item.getSubtotal();
                    MainActivity.produklist.get(position).setQty(qty);
                    MainActivity.produklist.get(position).setGrandtotal(grandtotal);
                    MainActivity.produkadapter.UpdateProduklistAdapter();
                }
            }
        });

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produk item = MainActivity.produklist.get(position);
                int qty = item.getQty()+1;

                double grandtotal = qty*item.getSubtotal();
                MainActivity.produklist.get(position).setQty(qty);
                MainActivity.produklist.get(position).setGrandtotal(grandtotal);
                MainActivity.produkadapter.UpdateProduklistAdapter();

                //viewHolder.text.setText(""+qty);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {

            }
        });

        return convertView;
    }

    private class ViewHolder {

        ImageView image;
        TextView title;
        TextView price;
        TextView cutprice_harga_jual;
        TextView cutprice_harga_diskon;
        TextView periode_promo;
        TextView text;
        ImageView plus;
        ImageView min;
        
    }

}




