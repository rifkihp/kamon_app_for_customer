package kamoncust.application.com.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;

import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.SmallBang;
import kamoncust.application.com.libs.SmallBangListener;

import kamoncust.application.com.model.produk;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.DatabaseHandlerToko;

public class GridProdukTokoAdapter extends BaseAdapter {

    Context context;
    DatabaseHandlerToko dh;
    ArrayList<produk> produklist;
    int kode_grup;
    int size_image_square = 0;

    public GridProdukTokoAdapter(Context context, DatabaseHandlerToko dh, ArrayList<produk> produklist, int kode_grup) {
        this.context = context;
        this.dh = dh;
        this.produklist = produklist;
        this.kode_grup = kode_grup;
    }

    public void UpdateGridProdukTokoAdapter(ArrayList<produk> produklist) {
        this.produklist = produklist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return produklist.size();
    }

    @Override
    public Object getItem(int position) {
        return produklist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.productlist_toko, null);

            viewHolder = new ViewHolder();

            viewHolder.linear_utama = convertView.findViewById(R.id.linear_utama);

            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.nama_mitra = convertView.findViewById(R.id.nama_mitra);

            viewHolder.harga = convertView.findViewById(R.id.txtHargaProduk);
            viewHolder.cutprice_harga_jual = convertView.findViewById(R.id.txtCutpriceHargaJual);
            //viewHolder.cutprice_harga_diskon = convertView.findViewById(R.id.txtCutpriceHargaDiskon);
            viewHolder.discount = convertView.findViewById(R.id.discount);

            viewHolder.cardSoldout = convertView.findViewById(R.id.card_soldout);
            viewHolder.cardNew = convertView.findViewById(R.id.card_new);
            viewHolder.cardGrosir = convertView.findViewById(R.id.card_grosir);
            viewHolder.cardPreorder = convertView.findViewById(R.id.card_preorder);
            viewHolder.cardDiscount =  convertView.findViewById(R.id.card_discount);
            viewHolder.cardFreeongkir = convertView.findViewById(R.id.card_freeongkir);
            viewHolder.cardCod = convertView.findViewById(R.id.card_cod);

            viewHolder.fav1 = convertView.findViewById(R.id.fav1);
            viewHolder.fav2 = convertView.findViewById(R.id.fav2);

            convertView.setTag(viewHolder);

            viewHolder.cutprice_harga_jual.setPaintFlags(viewHolder.cutprice_harga_jual.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(size_image_square>0) {
                ViewGroup.LayoutParams params = viewHolder.image.getLayoutParams();
                params.height = size_image_square;
                params.width = size_image_square;
                viewHolder.image.setLayoutParams(params);
            }
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final produk prod = (produk) getItem(position);
        viewHolder.position = position;

        final String url = CommonUtilities.SERVER_TOKO_URL+"/uploads/produk/"+prod.getFoto1_produk();
        if(size_image_square==0) {
            ViewTreeObserver viewTreeObserver = viewHolder.image.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onGlobalLayout() {
                        viewHolder.image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        size_image_square = viewHolder.image.getMeasuredWidth();
                        ViewGroup.LayoutParams params = viewHolder.image.getLayoutParams();
                        params.height = size_image_square;
                        params.width = size_image_square;
                        viewHolder.image.setLayoutParams(params);
                        MainActivity.imageLoader.displayImage(url.replaceAll("\\s", "%20"), viewHolder.image, MainActivity.imageOptionProduk);
                    }
                });
            }
        } else {
            MainActivity.imageLoader.displayImage(url.replaceAll("\\s", "%20"), viewHolder.image, MainActivity.imageOptionProduk);
        }

        //viewHolder.nama_mitra.setText(prod.getMitra().getCity_name()+(kode_grup==2?" - "+prod.getMitra().getNama():""));

        Log.e("MITRATRA", prod.getMitra().getCity_name()+" - "+prod.getMitra().getNama());
        viewHolder.nama_mitra.setText(prod.getMitra().getCity_name());

        viewHolder.title.setText(prod.getNama());
        viewHolder.harga.setText(CommonUtilities.getCurrencyFormat(prod.getSubtotal(), "Rp. "));
        if(prod.getHarga_diskon()>prod.getSubtotal()) {
            viewHolder.cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_diskon(), "Rp. "));
        } else if(prod.getHarga_jual()>prod.getSubtotal()) {
            viewHolder.cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_jual(), "Rp. "));
        } else {
            viewHolder.cutprice_harga_jual.setText("");
        }

        //viewHolder.cutprice_harga_diskon.setText(prod.getPersen_diskon()>0?"("+prod.getPersen_diskon() + "%)":"");
        viewHolder.discount.setText(prod.getPersen_diskon()>0?prod.getPersen_diskon() + "%":"");

        viewHolder.cardSoldout.setVisibility(prod.getProduk_soldout()==1?View.VISIBLE:View.GONE);
        viewHolder.cardNew.setVisibility(prod.getProduk_terbaru()==1?View.VISIBLE:View.GONE);
        viewHolder.cardGrosir.setVisibility(prod.getProduk_grosir()==1?View.VISIBLE:View.GONE);
        viewHolder.cardPreorder.setVisibility(prod.getProduk_preorder()==1?View.VISIBLE:View.GONE);
        viewHolder.cardDiscount.setVisibility(prod.getPersen_diskon()>0?View.VISIBLE:View.GONE);
        viewHolder.cardFreeongkir.setVisibility(prod.getProduk_freeongkir()==1?View.GONE:View.GONE);
        viewHolder.cardCod.setVisibility(prod.getProduk_cod()==1?View.GONE:View.GONE);

        viewHolder.mSmallBang = SmallBang.attach2Window((MainActivity) parent.getContext());

        viewHolder.fav1.setVisibility(prod.getWishlist()?View.GONE:View.VISIBLE);
        viewHolder.fav2.setVisibility(prod.getWishlist()?View.VISIBLE:View.GONE);

        viewHolder.fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dh.insertBadge(prod.getId(), 1, "WISH", kode_grup);
                prod.setWishlist(true);
                notifyDataSetChanged();

                //((MainActivity) parent.getContext()).updateWishlistGrid(viewHolder.position, true, prod);
                viewHolder.fav2.setVisibility(View.VISIBLE);
                viewHolder.fav1.setVisibility(View.GONE);
                like(v);

            }

            public void like(View view){
                viewHolder.fav2.setImageResource(R.drawable.f4);
                viewHolder.mSmallBang.bang(view);
                viewHolder.mSmallBang.setmListener(new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {

                    }

                });
            }

        });

        viewHolder.fav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dh.deleteBadge(prod.getId(), "WISH", kode_grup);
                prod.setWishlist(false);
                notifyDataSetChanged();

                //((MainActivity) parent.getContext()).updateWishlistGrid(viewHolder.position, false, prod);
                viewHolder.fav2.setVisibility(View.GONE);
                viewHolder.fav1.setVisibility(View.VISIBLE);

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                //Log.e("AAAA2", kode_grup+" A");
                ((MainActivity) parent.getContext()).openDetailProdukToko(prod, kode_grup);
            }
        });

        return convertView;

    }

    private class ViewHolder {

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
    }
}