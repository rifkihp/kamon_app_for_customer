package gomocart.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

import android.widget.LinearLayout;
import android.widget.TextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.model.order;

/**
 * Created by apple on 01/04/16.
 */
public class ListOrderAdapter extends BaseAdapter {

    Context context;
    ArrayList<order> orderlist;
    DatabaseHandler dh;

    public ListOrderAdapter(Context context, ArrayList<order> orderlist, DatabaseHandler dh) {
        this.context = context;
        this.orderlist = orderlist;
        this.dh = dh;
    }

    public void UpdateListOrderAdapter(ArrayList<order> orderlist) {
        this.orderlist = orderlist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return orderlist.size();
    }

    @Override
    public Object getItem(int position) {
        return orderlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_order_gomocart,null);

            viewHolder = new ViewHolder();

            viewHolder.no_pesanan = convertView.findViewById(R.id.no_pesanan);
            viewHolder.nama_toko = convertView.findViewById(R.id.nama_toko);
            viewHolder.tanggal = convertView.findViewById(R.id.tanggal);
            viewHolder.ekspedisi = convertView.findViewById(R.id.ekspedisi);
            viewHolder.no_resi = convertView.findViewById(R.id.no_resi);
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.linear_no_resi = convertView.findViewById(R.id.linear_no_resi);

            viewHolder.total_pembelian = convertView.findViewById(R.id.total_pembelian);
            viewHolder.total_pembayaran = convertView.findViewById(R.id.total_pembayaran);

            viewHolder.batal =  convertView.findViewById(R.id.batal);
            viewHolder.bayar =  convertView.findViewById(R.id.bayar);
            viewHolder.lacak =  convertView.findViewById(R.id.lacak);
            viewHolder.terima =  convertView.findViewById(R.id.terima);
            viewHolder.detail =  convertView.findViewById(R.id.detail);

            convertView.setTag(viewHolder);

        }else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final order data_order = (order) getItem(position);

        viewHolder.no_pesanan.setText(data_order.getNo_transaksi());
        viewHolder.nama_toko.setText(data_order.getMarket().getSimitra().getNama() + " " + data_order.getMarket().getSimitra().getCity_name());
        viewHolder.tanggal.setText(data_order.getTgl_transaksi());
        viewHolder.ekspedisi.setText(data_order.getKurir()+" ("+data_order.getEstimasi()+" Hari)");
        viewHolder.no_resi.setText(data_order.getNoresi());
        viewHolder.status.setText(
                data_order.getStatus()==0?"Belum Bayar":(
                data_order.getStatus()==2?"Sedang Dikemas":(
                data_order.getStatus()==6?"Sedang Dikirm":(
                data_order.getStatus()==5?"Selesai":(
                data_order.getStatus()==4?"Dibatalkan":""))))
        );

        viewHolder.status.setTextColor(context.getResources().getColor(
                data_order.getStatus()==0?R.color.blue_light_holo:(
                        data_order.getStatus()==2?R.color.orange:(
                                data_order.getStatus()==6?R.color.orange:(
                                        data_order.getStatus()==5?R.color.grrenonion:(
                                                data_order.getStatus()==4?R.color.red_sober:R.color.Tex))))
                )
        );
        viewHolder.linear_no_resi.setVisibility(data_order.getNoresi()!=null && data_order.getNoresi().length()>0?View.VISIBLE:View.GONE);

        viewHolder.total_pembelian.setText("Qty: "+data_order.getQty());
        viewHolder.total_pembayaran.setText("Total: "+CommonUtilities.getCurrencyFormat(data_order.getJumlah(), "Rp. "));

        viewHolder.batal.setVisibility(data_order.getStatus()==0?View.VISIBLE:View.GONE);
        viewHolder.bayar.setVisibility(data_order.getStatus()==0?View.VISIBLE:View.GONE);
        viewHolder.lacak.setVisibility(data_order.getNoresi()!=null && data_order.getNoresi().length()>0?View.VISIBLE:View.GONE);
        viewHolder.terima.setVisibility(data_order.getStatus()==6?View.VISIBLE:View.GONE);

        viewHolder.batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).prosesBatalkanPesanan(data_order);
            }
        });

        viewHolder.bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).prosesPembayaran(data_order.getId()+"");

            }
        });

        viewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).openDetailOrder(data_order);
            }
        });

        viewHolder.terima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).prosesTerimaPesanan(data_order);
            }
        });

        viewHolder.lacak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return convertView;
    }

    private class ViewHolder{

        TextView no_pesanan;
        TextView nama_toko;
        TextView tanggal;
        TextView ekspedisi;

        LinearLayout linear_no_resi;
        TextView no_resi;

        TextView status;

        TextView total_pembelian;
        TextView total_pembayaran;

        TextView batal;
        TextView bayar;
        TextView lacak;
        TextView terima;
        TextView detail;
    }
}

