package kamoncust.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import android.widget.EditText;
import android.widget.TextView;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.model.stok;

public class ListStokAdapter extends BaseAdapter {

	ArrayList<stok> liststok;
	Context context;
	int status_stok;

	public ListStokAdapter(Context context, ArrayList<stok> liststok, int status_stok) {
		this.context = context;
		this.liststok = liststok;
		this.status_stok = status_stok;
	}

	public void UpdateListStokUmkmAdapter(ArrayList<stok> liststok) {
		this.liststok = liststok;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return liststok.size();
	}

	@Override
	public Object getItem(int position) {
		return liststok.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public static class ViewHolder {
		public TextView ukuran;
		public TextView stok;
		public EditText qty;
		public int position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {

		final ViewHolder view;
		LayoutInflater inflator =  LayoutInflater.from(parent.getContext());
		if(convertView==null) {
			view = new ViewHolder();
			convertView = inflator.inflate(R.layout.stok_item_list, null);

			view.ukuran = convertView.findViewById(R.id.ukuran);
			view.stok   = convertView.findViewById(R.id.stok);
			view.qty    = convertView.findViewById(R.id.qty);

			convertView.setTag(view);
		} else {
			view = (ViewHolder) convertView.getTag();
		}

		final stok data = liststok.get(position);
		view.ukuran.setText(data.getUkuran() + " " + data.getWarna());
		if(status_stok==0) {
			view.stok.setText(data.getQty()>0?CommonUtilities.getNumberFormat(data.getQty()):"Tidak Tersedia");
		} else {
			view.stok.setText(data.getQty()>0?"Tersedia":"Tidak Tersedia");
		}
		view.qty.setText("");

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View convertView) {

			}
		});

		return convertView;
	}
}
