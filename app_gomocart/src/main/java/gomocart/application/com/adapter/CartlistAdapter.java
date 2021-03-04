package gomocart.application.com.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.model.produk;

public class CartlistAdapter extends ArrayAdapter<produk> {

    int number = 01;
    DatabaseHandler dh;
    Context context;
    int kode_grup;
    String kode_trx;
    LayoutInflater lf;

    public CartlistAdapter(Context context, List<produk> cartlist, DatabaseHandler dh, String kode_trx, int kode_grup) {
        super(context, 0, cartlist);
        lf = LayoutInflater.from(context);
        this.context = context;
        this.dh = dh;
        this.kode_trx = kode_trx;
        this.kode_grup = kode_grup;
    }

    public void UpdateCartlistAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {


        final ViewHolder viewHolder;

        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = lf.inflate(R.layout.cart_list_gomocart, parent, false);

            viewHolder.checkbox =  convertView.findViewById(R.id.checkbox);
            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.title = convertView.findViewById(R.id.title);

            viewHolder.harga                 = convertView.findViewById(R.id.txtHargaProduk);
            viewHolder.ket_grosir            = convertView.findViewById(R.id.ket_grosir);
            viewHolder.cutprice_harga_jual   = convertView.findViewById(R.id.txtCutpriceHargaJual);
            viewHolder.cutprice_harga_diskon = convertView.findViewById(R.id.txtCutpriceHargaDiskon);
            viewHolder.total                 = convertView.findViewById(R.id.total);

            viewHolder.text = convertView.findViewById(R.id.text);
            viewHolder.separator_ukuran = convertView.findViewById(R.id.separator_ukuran);
            viewHolder.ukuran = convertView.findViewById(R.id.ukuran);
            viewHolder.separator_warna = convertView.findViewById(R.id.separator_warna);
            viewHolder.warna = convertView.findViewById(R.id.warna);

            viewHolder.plus = convertView.findViewById(R.id.plus);
            viewHolder.min  = convertView.findViewById(R.id.min);

            //viewHolder.edit = convertView.findViewById(R.id.edit);
            viewHolder.hapus = convertView.findViewById(R.id.hapus);
            viewHolder.jam   = convertView.findViewById(R.id.jam);
            viewHolder.menit = convertView.findViewById(R.id.menit);
            viewHolder.detik = convertView.findViewById(R.id.detik);
            viewHolder.expirationTime  = convertView.findViewById(R.id.expirationTime);

            convertView.setTag(viewHolder);
            viewHolder.cutprice_harga_jual.setPaintFlags(viewHolder.cutprice_harga_jual.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final produk item = getItem(position);
        viewHolder.position = position;

        viewHolder.hapus.setVisibility(View.GONE);
        viewHolder.checkbox.setChecked(item.getChecked());

        String server = kode_grup==2?CommonUtilities.SERVER_URL+"/uploads/produk/":CommonUtilities.SERVER_URL+"/images/barang/";
        String url = server+item.getFoto1_produk();
        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);

        viewHolder.title.setText(item.getNama());

        number = item.getQty();
        viewHolder.text.setText(""+number);

        viewHolder.ukuran.setText(item.getUkuran());
        viewHolder.warna.setText(item.getWarna());

        viewHolder.harga.setText(CommonUtilities.getCurrencyFormat(item.getSubtotal(), "Rp. "));
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

        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //int index = MainActivity.cartlist.indexOf(item);
                //if (index > -1) {
                    MainActivity.cartlist.get(viewHolder.position).setChecked(isChecked);
                    MainActivity.cartlistAdapter.UpdateCartlistAdapter();
                    ((MainActivity) context).updateCheckAll();
                //}
            }
        });

        viewHolder.min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = MainActivity.cartlist.indexOf(item);
                if(index>=0) {
                    //number = item.getQty();
                    if (number > 1) {
                        number--;

                        //MainActivity.cartlist.get(index).setQty(number);
                        //MainActivity.cartlist.get(index).setGrandtotal(number * item.getSubtotal());

                        //MainActivity.cartlistAdapter.UpdateCartlistAdapter();
                        //dh.updateCartlist(MainActivity.cartlist.get(index), MainActivity.cartlist.get(index).getId(), MainActivity.cartlist.get(index).getUkuran(), MainActivity.cartlist.get(index).getWarna(), kode_trx, kode_grup);

                        viewHolder.text.setText("" + number);
                        //viewHolder.total.setText(CommonUtilities.getCurrencyFormat(item.getGrandtotal(), "Rp. "));
                        //((MainActivity) context).updateTotalCartlist();
                        //((MainActivity) context).updateSummaryCart();
                        ((MainActivity) context).updateStock(item, number);
                    }
                }
            }
        });

        viewHolder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = MainActivity.cartlist.indexOf(item);
                if(index>=0) {
                    //number = item.getQty();
                    number++;

                    //MainActivity.cartlist.get(index).setQty(number);
                    //MainActivity.cartlist.get(index).setGrandtotal(number*item.getSubtotal());

                    //MainActivity.cartlistAdapter.UpdateCartlistAdapter();
                    //dh.updateCartlist(MainActivity.cartlist.get(index), MainActivity.cartlist.get(index).getId(), MainActivity.cartlist.get(index).getUkuran(), MainActivity.cartlist.get(index).getWarna(), kode_trx, kode_grup);

                    viewHolder.text.setText(""+number);
                    //viewHolder.total.setText(CommonUtilities.getCurrencyFormat(item.getGrandtotal(), "Rp. "));
                    //((MainActivity) context).updateTotalCartlist();
                    //((MainActivity) context).updateSummaryCart();
                    ((MainActivity) context).updateStock(item, number);
                }

            }
        });

        viewHolder.hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = MainActivity.cartlist.indexOf(item);
                ((MainActivity) context).hapusCartlist(index);
            }
        });

        /*viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int index = MainActivity.cartlist.indexOf(item);
                ((MainActivity) context).openDetailProduk(item, kode_grup);
            }
        });*/

        viewHolder.setData(getItem(position));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                //((MainActivity) parent.getContext()).openDetailProduk(item);
            }
        });

        return convertView;
    }

    private class ViewHolder{
        CheckBox checkbox;
        ImageView image;
        TextView title;

        TextView harga;
        TextView ket_grosir;
        TextView cutprice_harga_jual;
        TextView cutprice_harga_diskon;
        TextView total;

        TextView text;
        View separator_ukuran;
        TextView ukuran;
        View separator_warna;
        TextView warna;
        ImageView plus;
        ImageView min;
        ImageView hapus;
        //ImageView edit;

        TextView detik;
        TextView jam;
        TextView menit;
        TextView expirationTime;

        int position;

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




