package kamoncust.application.com.adapter;

import android.app.Activity;
import android.content.Context;
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


public class CartlistAdapter extends ArrayAdapter<produk> {

    int number = 01;

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

    public CartlistAdapter(Context context, List<produk> cartlist) {
        super(context, 0, cartlist);
        lf = LayoutInflater.from(context);
        this.context = context;

        //lstHolders = new ArrayList<>();
        //startUpdateTimer();
    }

    public void UpdateCartlistAdapter() {
        notifyDataSetChanged();
    }

    /*public void startUpdateTimer() {
        mHandler.postDelayed(updateRemainingTimeRunnable, 1000);
    }*/

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {


        final ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = lf.inflate(R.layout.cart_list, parent, false);

            //LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            //convertView = layoutInflater.inflate(R.layout.cart_list,null);

            viewHolder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.image = (ResizableImageView)convertView.findViewById(R.id.image);
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.price = (TextView)convertView.findViewById(R.id.price);
            viewHolder.diskon = (TextView)convertView.findViewById(R.id.diskon);
            viewHolder.total = (TextView)convertView.findViewById(R.id.total);
            viewHolder.text = (TextView)convertView.findViewById(R.id.text);
            viewHolder.separator_ukuran = (View)convertView.findViewById(R.id.separator_ukuran);
            viewHolder.ukuran = (TextView)convertView.findViewById(R.id.ukuran);
            viewHolder.separator_warna = (View)convertView.findViewById(R.id.separator_warna);
            viewHolder.warna = (TextView)convertView.findViewById(R.id.warna);
            viewHolder.plus = (ImageView)convertView.findViewById(R.id.plus);
            viewHolder.min  = (ImageView)convertView.findViewById(R.id.min);
            viewHolder.hapus = (ImageView)convertView.findViewById(R.id.hapus);
            viewHolder.jam   = (TextView)convertView.findViewById(R.id.jam);
            viewHolder.menit = (TextView)convertView.findViewById(R.id.menit);
            viewHolder.detik = (TextView)convertView.findViewById(R.id.detik);
            viewHolder.expirationTime   = (TextView)convertView.findViewById(R.id.expirationTime);


            convertView.setTag(viewHolder);
            /*synchronized (lstHolders) {
                lstHolders.add(viewHolder);
            }*/
        } else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final produk cart_item = (produk) getItem(position);

        viewHolder.checkbox.setChecked(cart_item.getChecked());
        String server = CommonUtilities.SERVER_URL;
        String url = server+"/uploads/produk/"+cart_item.getFoto1_produk();
        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);

        viewHolder.separator_ukuran.setVisibility(cart_item.getList_ukuran().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.ukuran.setVisibility(cart_item.getList_ukuran().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.ukuran.setText(cart_item.getUkuran());
        viewHolder.ukuran.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int index = MainActivity.cartlist.indexOf(cart_item);
                ((MainActivity) context).openDialogUkuran(cart_item.getList_stok(), index);
            }
        });

        viewHolder.separator_warna.setVisibility(cart_item.getList_warna().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.warna.setVisibility(cart_item.getList_warna().length()==0?View.INVISIBLE:View.VISIBLE);
        viewHolder.warna.setText(cart_item.getWarna());
        viewHolder.warna.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int index = MainActivity.cartlist.indexOf(cart_item);
                ((MainActivity) context).openDialogWarna(cart_item.getList_stok(), cart_item.getUkuran(), index);
            }
        });

        viewHolder.title.setText(cart_item.getNama());
        viewHolder.price.setText(CommonUtilities.getCurrencyFormat(cart_item.getHarga_jual(), "Rp. "));
        viewHolder.diskon.setText(cart_item.getPersen_diskon()>0?" (-"+cart_item.getPersen_diskon()+"%) "+CommonUtilities.getCurrencyFormat((cart_item.getPersen_diskon()*0.01)*cart_item.getHarga_jual(), "Rp. "):"");
        viewHolder.total.setText(CommonUtilities.getCurrencyFormat(cart_item.getGrandtotal(), "Rp. "));

        number = cart_item.getQty();
        viewHolder.text.setText(""+number);

        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int index = MainActivity.cartlist.indexOf(cart_item);
                if (index > -1) {
                    MainActivity.cartlist.get(index).setChecked(isChecked);
                    MainActivity.cartlistAdapter.UpdateCartlistAdapter();
                }
            }
        });

        viewHolder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number > 1) {
                    number = number - 1;
                    int index = MainActivity.cartlist.indexOf(cart_item);
                    MainActivity.cartlist.get(index).setQty(number);
                    MainActivity.cartlist.get(index).setGrandtotal(number*cart_item.getSubtotal());

                    MainActivity.cartlistAdapter.UpdateCartlistAdapter();
                    new DatabaseHandler(parent.getContext()).updateCartlists(MainActivity.cartlist.get(index), MainActivity.cartlist.get(index).getId(), MainActivity.cartlist.get(index).getUkuran(), MainActivity.cartlist.get(index).getWarna());

                    viewHolder.text.setText(""+number);
                    viewHolder.total.setText(CommonUtilities.getCurrencyFormat(cart_item.getGrandtotal(), "Rp. "));
                    ((MainActivity) context).updateTotalCartlist();
                    ((MainActivity) context).updateSummaryCart();
                }
            }
        });

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (number < cart_item.getMax_qty()) {
                    number = number + 1;

                    int index = MainActivity.cartlist.indexOf(cart_item);
                    MainActivity.cartlist.get(index).setQty(number);
                    MainActivity.cartlist.get(index).setGrandtotal(number*cart_item.getSubtotal());

                    MainActivity.cartlistAdapter.UpdateCartlistAdapter();
                    new DatabaseHandler(parent.getContext()).updateCartlists(MainActivity.cartlist.get(index), MainActivity.cartlist.get(index).getId(), MainActivity.cartlist.get(index).getUkuran(), MainActivity.cartlist.get(index).getWarna());

                    viewHolder.text.setText(""+number);
                    viewHolder.total.setText(CommonUtilities.getCurrencyFormat(cart_item.getGrandtotal(), "Rp. "));
                    ((MainActivity) context).updateTotalCartlist();
                    ((MainActivity) context).updateSummaryCart();
                }

            }
        });


        viewHolder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = MainActivity.cartlist.indexOf(cart_item);
                ((MainActivity) context).prosesHapusCartlist(index);

                /*new DatabaseHandler(parent.getContext()).deleteCartlist(cart_item);

                MainActivity.cartlist.remove(index);
                MainActivity.cartlistAdapter.UpdateCartlistAdapter(MainActivity.cartlist);

                ((MainActivity) context).updateTotalCartlist();
                ((MainActivity) context).updateSummaryCart();*/
            }
        });

        viewHolder.setData(getItem(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                //((MainActivity) parent.getContext()).openDetailProduk(cart_item);
            }
        });

        return convertView;
    }

    private class ViewHolder{
        CheckBox checkbox;
        ResizableImageView image;
        TextView title;
        TextView price;
        TextView diskon;
        TextView total;
        TextView text;
        View separator_ukuran;
        TextView ukuran;
        View separator_warna;
        TextView warna;
        ImageView plus;
        ImageView min;
        ImageView hapus;
        TextView detik;
        TextView jam;
        TextView menit;
        TextView expirationTime;

        public void setData(produk mProduct) {

            long currentTime = System.currentTimeMillis();
            long timeDiff = mProduct.getExpirationTime() - currentTime;
            int seconds = (int) (timeDiff / 1000) % 60;
            int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
            int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);

            expirationTime.setText(mProduct.getExpirationTime()+"");
            jam.setText((hours<10?"0":"")+hours);
            menit.setText((minutes<10?"0":"")+minutes);
            detik.setText((seconds<10?"0":"")+seconds);
        }
    }

}




