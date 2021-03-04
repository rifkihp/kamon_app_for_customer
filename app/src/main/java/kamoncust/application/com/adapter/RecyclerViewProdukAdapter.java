package kamoncust.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kamoncust.application.com.kamoncust.DetailProdukTokoActivity;
import kamoncust.application.com.kamoncust.DetailProdukTokoActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.DatabaseHandlerToko;
import kamoncust.application.com.libs.SmallBang;
import kamoncust.application.com.libs.SmallBangListener;
import kamoncust.application.com.model.produk;

public class RecyclerViewProdukAdapter extends RecyclerView.Adapter<RecyclerViewProdukAdapter.DataObjectHolder> {

	Context context;
	DatabaseHandlerToko dh;
	ArrayList<produk> produklist;
	int kode_grup;
	int size_image_square = 0;

	public RecyclerViewProdukAdapter(Context context, DatabaseHandlerToko dh, ArrayList<produk> produklist, int kode_grup) {
		this.context = context;
		this.dh = dh;
		this.produklist = produklist;
		this.kode_grup = kode_grup;
	}

	public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		LinearLayout linear_utama;
		ImageView image;
		TextView title;

		TextView harga;
		TextView cutprice_harga_jual;
		//TextView cutprice_harga_diskon;
		TextView discount;
		TextView nama_mitra;

		CardView cardSoldout;
		CardView cardNew;
		CardView cardGrosir;
		CardView cardPreorder;
		CardView cardDiscount;
		CardView cardFreeongkir;
		CardView cardCod;

		ImageView fav1;
		ImageView fav2;
		SmallBang mSmallBang;

		int position;

		public DataObjectHolder(View convertView, ViewGroup parent) {
			super(convertView);

			linear_utama = convertView.findViewById(R.id.linear_utama);

			image = convertView.findViewById(R.id.image);
			title = convertView.findViewById(R.id.title);
			nama_mitra = convertView.findViewById(R.id.nama_mitra);

			harga = convertView.findViewById(R.id.txtHargaProduk);
			cutprice_harga_jual = convertView.findViewById(R.id.txtCutpriceHargaJual);
			//cutprice_harga_diskon = convertView.findViewById(R.id.txtCutpriceHargaDiskon);
			discount = convertView.findViewById(R.id.discount);

			cardSoldout = convertView.findViewById(R.id.card_soldout);
			cardNew = convertView.findViewById(R.id.card_new);
			cardGrosir = convertView.findViewById(R.id.card_grosir);
			cardPreorder = convertView.findViewById(R.id.card_preorder);
			cardDiscount =  convertView.findViewById(R.id.card_discount);
			cardFreeongkir = convertView.findViewById(R.id.card_freeongkir);
			cardCod = convertView.findViewById(R.id.card_cod);

			fav1 = convertView.findViewById(R.id.fav1);
			fav2 = convertView.findViewById(R.id.fav2);
			
			cutprice_harga_jual.setPaintFlags(cutprice_harga_jual.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

			if(size_image_square>0) {
				ViewGroup.LayoutParams params = image.getLayoutParams();
				params.height = size_image_square;
				params.width = size_image_square;
				image.setLayoutParams(params);
			}

			itemView.setOnClickListener(this);

		}

		@Override
		public void onClick(View v) {
			Intent i = new Intent("kamoncust.application.com.kamoncust.TERKAIT_OPEN_DETAIL_PRODUK");
			i.putExtra("produk", produklist.get(getAdapterPosition()));
			context.sendBroadcast(i);
		}
	}

	@Override
	public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_horizontal_productlist, parent, false);

		DataObjectHolder dataObjectHolder = new DataObjectHolder(view, parent);

		return dataObjectHolder;
	}

	@Override
	public void onBindViewHolder(final DataObjectHolder holder, final int position) {

		final produk prod = produklist.get(position);
		holder.position = position;

		final String url = CommonUtilities.SERVER_TOKO_URL+"/uploads/produk/"+prod.getFoto1_produk();
		if(size_image_square==0) {
			ViewTreeObserver viewTreeObserver = holder.image.getViewTreeObserver();
			if (viewTreeObserver.isAlive()) {
				viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
					@SuppressLint("NewApi")
					@Override
					public void onGlobalLayout() {
						holder.image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
						size_image_square = holder.image.getMeasuredWidth();
						ViewGroup.LayoutParams params = holder.image.getLayoutParams();
						params.height = size_image_square;
						params.width = size_image_square;
						holder.image.setLayoutParams(params);
						DetailProdukTokoActivity.imageLoader.displayImage(url.replaceAll("\\s", "%20"), holder.image, DetailProdukTokoActivity.imageOptionProduk);
					}
				});
			}
		} else {
			DetailProdukTokoActivity.imageLoader.displayImage(url.replaceAll("\\s", "%20"), holder.image, DetailProdukTokoActivity.imageOptionProduk);
		}

		//holder.nama_mitra.setText(prod.getMitra().getCity_name()+(kode_grup==2?" - "+prod.getMitra().getNama():""));

		Log.e("MITRATRA", prod.getMitra().getCity_name()+" - "+prod.getMitra().getNama());
		holder.nama_mitra.setText(prod.getMitra().getCity_name());

		holder.title.setText(prod.getNama());
		holder.harga.setText(CommonUtilities.getCurrencyFormat(prod.getSubtotal(), "Rp. "));
		if(prod.getHarga_diskon()>prod.getSubtotal()) {
			holder.cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_diskon(), "Rp. "));
		} else if(prod.getHarga_jual()>prod.getSubtotal()) {
			holder.cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_jual(), "Rp. "));
		} else {
			holder.cutprice_harga_jual.setText("");
		}

		//holder.cutprice_harga_diskon.setText(prod.getPersen_diskon()>0?"("+prod.getPersen_diskon() + "%)":"");
		holder.discount.setText(prod.getPersen_diskon()>0?prod.getPersen_diskon() + "%":"");

		holder.cardSoldout.setVisibility(prod.getProduk_soldout()==1?View.VISIBLE:View.GONE);
		holder.cardNew.setVisibility(prod.getProduk_terbaru()==1?View.VISIBLE:View.GONE);
		holder.cardGrosir.setVisibility(prod.getProduk_grosir()==1?View.VISIBLE:View.GONE);
		holder.cardPreorder.setVisibility(prod.getProduk_preorder()==1?View.VISIBLE:View.GONE);
		holder.cardDiscount.setVisibility(prod.getPersen_diskon()>0?View.VISIBLE:View.GONE);
		holder.cardFreeongkir.setVisibility(prod.getProduk_freeongkir()==1?View.GONE:View.GONE);
		holder.cardCod.setVisibility(prod.getProduk_cod()==1?View.GONE:View.GONE);

		holder.mSmallBang = SmallBang.attach2Window((DetailProdukTokoActivity) context);

		holder.fav1.setVisibility(prod.getWishlist()?View.GONE:View.VISIBLE);
		holder.fav2.setVisibility(prod.getWishlist()?View.VISIBLE:View.GONE);

		holder.fav1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dh.insertBadge(prod.getId(), 1, "WISH", kode_grup);
				prod.setWishlist(true);
				notifyDataSetChanged();

				//((DetailProdukTokoActivity) parent.getContext()).updateWishlistGrid(holder.position, true, prod);
				holder.fav2.setVisibility(View.VISIBLE);
				holder.fav1.setVisibility(View.GONE);
				like(v);

			}

			public void like(View view){
				holder.fav2.setImageResource(R.drawable.f4);
				holder.mSmallBang.bang(view);
				holder.mSmallBang.setmListener(new SmallBangListener() {
					@Override
					public void onAnimationStart() {

					}

					@Override
					public void onAnimationEnd() {

					}

				});
			}

		});

		holder.fav2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dh.deleteBadge(prod.getId(), "WISH", kode_grup);
				prod.setWishlist(false);
				notifyDataSetChanged();

				//((DetailProdukTokoActivity) parent.getContext()).updateWishlistGrid(holder.position, false, prod);
				holder.fav2.setVisibility(View.GONE);
				holder.fav1.setVisibility(View.VISIBLE);

			}
		});
		
	}

	@Override
	public int getItemCount() {
		return produklist.size();
	}
}
