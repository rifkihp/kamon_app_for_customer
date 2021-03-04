package kamoncust.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import android.widget.TextView;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.ResizableImageView;
import kamoncust.application.com.model.informasi;

public class InformasiAdapter extends BaseAdapter {

	ArrayList<informasi> listDataInformasi = new ArrayList<>();
	Context context;

	public InformasiAdapter(Context context, ArrayList<informasi> listDataInformasi) {
		this.context = context;
		this.listDataInformasi = listDataInformasi;
	}

	public void UpdateListInformasiAdapter(ArrayList<informasi> listDataInformasi) {
		this.listDataInformasi = listDataInformasi;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return listDataInformasi.size();
	}

	@Override
	public Object getItem(int position) {
		return listDataInformasi.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public ResizableImageView gambar;
		public TextView judul;
		public TextView tanggal;
		public TextView konten;
		public int position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {

		final ViewHolder view;
		LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
		if(convertView==null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.informasi_item, null);

			view.gambar = (ResizableImageView) convertView.findViewById(R.id.imgInformasi);
			view.judul = (TextView) convertView.findViewById(R.id.txtJudul);
			view.tanggal = (TextView) convertView.findViewById(R.id.txtTanggal);
			view.konten = (TextView) convertView.findViewById(R.id.txtKeterangan);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		final informasi info = listDataInformasi.get(position);
		view.position = listDataInformasi.indexOf(info);

		String server = CommonUtilities.SERVER_URL;
		String url = server+"/store/centercrop_3.php?url="+ server+"/uploads/informasi/"+info.getGambar()+"&width=300&height=300";
		MainActivity.imageLoader.displayImage(url, view.gambar, MainActivity.imageOptionInformasi);

		view.judul.setText(info.getJudul());
		view.tanggal.setText(info.getTanggal());
		view.konten.setText(info.getHeader());

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View convertView) {
				((MainActivity) parent.getContext()).openDetailInformasi(info);
			}
		});

		return convertView;
	}
}
