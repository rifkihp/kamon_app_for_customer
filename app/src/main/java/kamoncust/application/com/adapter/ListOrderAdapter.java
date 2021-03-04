package kamoncust.application.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import android.widget.TextView;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.model.order;

/**
 * Created by apple on 01/04/16.
 */
public class ListOrderAdapter extends BaseAdapter {

    Context context;
    ArrayList<order> orderlist;

    public ListOrderAdapter(Context context, ArrayList<order> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
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
            convertView = layoutInflater.inflate(R.layout.list_order,null);

            viewHolder = new ViewHolder();

            viewHolder.no_invoice = convertView.findViewById(R.id.no_invoice);
            viewHolder.nama_terapis = convertView.findViewById(R.id.nama_terapis);
            viewHolder.tanggal_terapi = convertView.findViewById(R.id.tanggal_terapi);
            viewHolder.detail_riwayat_kesehatan = convertView.findViewById(R.id.detail_riwayat_kesehatan);
            viewHolder.status = convertView.findViewById(R.id.status);
            viewHolder.total_pembayaran = convertView.findViewById(R.id.total_pembayaran);

            viewHolder.batalkan =  convertView.findViewById(R.id.batalkan);
            viewHolder.rincian = convertView.findViewById(R.id.rincian);

            convertView.setTag(viewHolder);

        }else {

            viewHolder = (ViewHolder)convertView.getTag();
        }

        final order data_order = (order) getItem(position);

        viewHolder.no_invoice.setText("No. Pesanan: #" + data_order.getNo_transaksi());
        viewHolder.nama_terapis.setText("Nama Terapis: " +data_order.getMitra_nama());
        viewHolder.tanggal_terapi.setText("Jadwal Terapis: "+data_order.getTanggal_terapi() + " Jam "+data_order.getJam_terapi());
        viewHolder.detail_riwayat_kesehatan.setText(data_order.getRiwayat_kesehatan());

        viewHolder.status.setText("Status: "+/*data_order.getStatus()+"  "+*/(
                data_order.getStatus()==0?"Menunggu":(
                data_order.getStatus()==1?"Proses":(
                        data_order.getStatus()==2?"Selesai":(
                                data_order.getStatus()==3?"Batal":"")))));

        viewHolder.total_pembayaran.setText("Pembayaran: "+CommonUtilities.getCurrencyFormat(data_order.getJumlah(), "Rp. "));

        viewHolder.batalkan.setVisibility(data_order.getStatus()==0?View.VISIBLE:View.INVISIBLE);

        viewHolder.batalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).prosesBatalkanPesanan(data_order);
            }
        });

        viewHolder.rincian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) context).openDetailOrder(data_order);
            }
        });

        return convertView;
    }

    private class ViewHolder{

        TextView no_invoice;
        TextView nama_terapis;
        TextView tanggal_terapi;
        TextView detail_riwayat_kesehatan;
        TextView status;
        TextView total_pembayaran;

        TextView batalkan;
        TextView rincian;
    }
}

