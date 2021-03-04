package gomocart.application.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import gomocart.application.com.gomocart.DetailProdukActivity;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.SmallBang;
import gomocart.application.com.libs.SmallBangListener;
import gomocart.application.com.model.produk;

public class RecyclerViewProdukAdapter extends RecyclerView.Adapter<RecyclerViewProdukAdapter.DataObjectHolder> {

	Context context;
	DatabaseHandler dh;
	ArrayList<produk> produklist;
	int kode_grup;
	int size_image_square = 0;

	public RecyclerViewProdukAdapter(Context context, DatabaseHandler dh, ArrayList<produk> produklist, int kode_grup) {
		this.context = context;
		this.dh = dh;
		this.produklist = produklist;
		this.kode_grup = kode_grup;
	}

	public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		ImageView image;
		TextView title;

		TextView harga;
		TextView cutprice_harga_jual;
		//TextView cutprice_harga_diskon;
		TextView discount;

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

		public DataObjectHolder(View convertView, ViewGroup parent) {
			super(convertView);

			image = convertView.findViewById(R.id.image);
			title = convertView.findViewById(R.id.title);

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

			mSmallBang = SmallBang.attach2Window((DetailProdukActivity) parent.getContext());

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
			Intent i = new Intent("gomocart.application.com.gomocart.TERKAIT_OPEN_DETAIL_PRODUK");
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
		
		String server = kode_grup==2?CommonUtilities.SERVER_URL+"/uploads/produk/":CommonUtilities.SERVER_URL+"/images/barang/";
		final String url = server+prod.getFoto1_produk();

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
						DetailProdukActivity.imageLoader.displayImage(url, holder.image, DetailProdukActivity.imageOptionProduk);
					}
				});
			}
		} else {
			DetailProdukActivity.imageLoader.displayImage(url, holder.image, DetailProdukActivity.imageOptionProduk);
		}

		holder.title.setText(prod.getNama());
		holder.harga.setText(CommonUtilities.getCurrencyFormat(prod.getSubtotal(), "Rp. ")+" / "+ prod.getSatuan());
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

		holder.fav1.setVisibility(prod.getWishlist()?View.GONE:View.VISIBLE);
		holder.fav2.setVisibility(prod.getWishlist()?View.VISIBLE:View.GONE);

		holder.fav1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dh.insertBadge(prod.getId(), 1, "WISH", kode_grup);
				prod.setWishlist(true);
				notifyDataSetChanged();

				//((MainActivity) parent.getContext()).updateWishlistGrid(holder.position, true, prod);
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

				//((MainActivity) parent.getContext()).updateWishlistGrid(holder.position, false, prod);
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
