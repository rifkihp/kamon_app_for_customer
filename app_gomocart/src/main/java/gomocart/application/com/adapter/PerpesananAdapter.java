package gomocart.application.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import android.widget.TextView;
import gomocart.application.com.gomocart.MainActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.ResizableImageView;
import gomocart.application.com.model.perpesanan;

public class PerpesananAdapter extends BaseAdapter {

	Context context;
	ArrayList<perpesanan> listPerpesanan;

	public PerpesananAdapter(Context context, ArrayList<perpesanan> listPerpesanan) {
		this.context = context;
		this.listPerpesanan = listPerpesanan;
	}

	public void UpdateLaporanAdapter(ArrayList<perpesanan> listPerpesanan) {
		this.listPerpesanan = listPerpesanan;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listPerpesanan.size();
	}

	@Override
	public Object getItem(int position) {
		return listPerpesanan.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
		
	public static class ViewHolder {
		
		public ResizableImageView image;
		public TextView title;
		public TextView pesan_terakhir;
		public TextView tanggal_jam;
		public TextView total_unread;

		public int position;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
		
		final ViewHolder view;
		LayoutInflater inflator =  LayoutInflater.from(parent.getContext());	
		if(convertView==null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.perpesanan_item, null);
			
			view.image          = (ResizableImageView) convertView.findViewById(R.id.image);
			view.title          = (TextView) convertView.findViewById(R.id.title);
			view.pesan_terakhir = (TextView) convertView.findViewById(R.id.last_message);
			view.tanggal_jam    = (TextView) convertView.findViewById(R.id.tanggal_jam);
			view.total_unread   = (TextView) convertView.findViewById(R.id.total_unread);

			convertView.setLongClickable(true);
			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}


		final perpesanan data = listPerpesanan.get(position);
		view.position = listPerpesanan.indexOf(data);

		view.title.setText(data.getNama());
		view.pesan_terakhir.setText(data.getPesan());
		view.tanggal_jam.setText(CommonUtilities.getDateMassage(data.getTanggal()));
		view.total_unread.setText(data.getTotal_unread()+"");
		view.total_unread.setVisibility(data.getTotal_unread()>0?View.VISIBLE:View.INVISIBLE);

		String server = CommonUtilities.SERVER_URL;
		String url = server+"/uploads/produk/"+data.getGambar();
		MainActivity.imageLoader.displayImage(url, view.image, MainActivity.imageOptionProduk);

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View convertView) {

			}
		});
		
		return convertView;
	}
}
