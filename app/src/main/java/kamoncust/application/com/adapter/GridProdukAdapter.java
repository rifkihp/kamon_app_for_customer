package kamoncust.application.com.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import android.widget.TextView;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.SmallBang;
import kamoncust.application.com.libs.SmallBangListener;
import kamoncust.application.com.libs.DatabaseHandler;
import kamoncust.application.com.model.produk;

public class GridProdukAdapter extends BaseAdapter {

    Context context;
    DatabaseHandler dh;
    ArrayList<produk> produklist;

    public GridProdukAdapter(Context context, DatabaseHandler dh, ArrayList<produk> produklist) {
        this.context = context;
        this.dh = dh;
        this.produklist = produklist;
    }

    public void UpdateGridProdukAdapter(ArrayList<produk> produklist) {
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
            convertView = layoutInflater.inflate(R.layout.productlist_gomocart, null);

            viewHolder = new ViewHolder();

            viewHolder.linear_utama = convertView.findViewById(R.id.linear_utama);

            viewHolder.image = convertView.findViewById(R.id.image);
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.cutprice = convertView.findViewById(R.id.cutprice);
            viewHolder.discount = convertView.findViewById(R.id.discount);

            viewHolder.linearSoldout = convertView.findViewById(R.id.linear_soldout);
            viewHolder.linearNew = convertView.findViewById(R.id.linear_new);
            viewHolder.linearGrosir = convertView.findViewById(R.id.linear_grosir);
            viewHolder.linearPreorder = convertView.findViewById(R.id.linear_preorder);
            viewHolder.linearDiscount = convertView.findViewById(R.id.linear_discount);
            viewHolder.linearFreeongkir = convertView.findViewById(R.id.linear_freeongkir);
            viewHolder.linearCod = convertView.findViewById(R.id.linear_cod);

            viewHolder.cardSoldout = convertView.findViewById(R.id.card_soldout);
            viewHolder.cardNew = convertView.findViewById(R.id.card_new);
            viewHolder.cardGrosir = convertView.findViewById(R.id.card_grosir);
            viewHolder.cardPreorder = convertView.findViewById(R.id.card_preorder);
            viewHolder.cardDiscount =  convertView.findViewById(R.id.card_discount);
            viewHolder.cardFreeongkir = convertView.findViewById(R.id.card_freeongkir);

            viewHolder.fav1 = convertView.findViewById(R.id.fav1);
            viewHolder.fav2 = convertView.findViewById(R.id.fav2);

            convertView.setTag(viewHolder);

            viewHolder.cutprice.setPaintFlags(viewHolder.cutprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if(MainActivity.image_produk_size_vertical>0) {
                ViewGroup.LayoutParams params = viewHolder.image.getLayoutParams();
                params.height = MainActivity.image_produk_size_vertical;
                params.width = MainActivity.image_produk_size_vertical;
                viewHolder.image.setLayoutParams(params);
            }
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        final produk prod = (produk) getItem(position);
        viewHolder.position = position;

        String server = CommonUtilities.SERVER_URL;
        final String url = server+"/uploads/produk/"+prod.getFoto1_produk();
        //final String url = server+"/store/centercrop.php?url="+ server+"/uploads/produk/"+prod.getFoto1_produk()+"&width=300&height=300";
        //Log.i("url", url);

        if(MainActivity.image_produk_size_vertical==0) {
            ViewTreeObserver viewTreeObserver = viewHolder.image.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @SuppressLint("NewApi")
                    @Override
                    public void onGlobalLayout() {
                        viewHolder.image.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        MainActivity.image_produk_size_vertical = viewHolder.image.getMeasuredWidth();
                        ViewGroup.LayoutParams params = viewHolder.image.getLayoutParams();
                        params.height = MainActivity.image_produk_size_vertical;
                        params.width = MainActivity.image_produk_size_vertical;
                        viewHolder.image.setLayoutParams(params);
                        MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);
                    }
                });
            }
        } else {
            MainActivity.imageLoader.displayImage(url, viewHolder.image, MainActivity.imageOptionProduk);
        }

        viewHolder.title.setText(prod.getNama());
        viewHolder.price.setText(CommonUtilities.getCurrencyFormat(prod.getHarga_diskon()>0?prod.getHarga_diskon():prod.getHarga_jual(), "Rp. "));
        viewHolder.cutprice.setText(prod.getHarga_diskon()>0? CommonUtilities.getCurrencyFormat(prod.getHarga_jual(), "Rp. "):"");

        viewHolder.linearSoldout.setVisibility(prod.getProduk_soldout()==1?View.VISIBLE:View.GONE);
        viewHolder.linearNew.setVisibility(prod.getProduk_terbaru()==1?View.VISIBLE:View.GONE);
        viewHolder.linearGrosir.setVisibility(prod.getProduk_grosir()==1?View.VISIBLE:View.GONE);
        viewHolder.linearPreorder.setVisibility(prod.getProduk_preorder()==1?View.VISIBLE:View.GONE);
        viewHolder.linearDiscount.setVisibility(prod.getPersen_diskon()>0?View.VISIBLE:View.GONE);
        viewHolder.linearFreeongkir.setVisibility(prod.getProduk_freeongkir()>0?View.GONE:View.GONE);
        //viewHolder.linearCod.setVisibility(prod.getProduk_cod()>0?View.VISIBLE:View.GONE);

        viewHolder.cardSoldout.setVisibility(prod.getProduk_soldout()==1?View.VISIBLE:View.GONE);
        viewHolder.cardNew.setVisibility(prod.getProduk_terbaru()==1?View.VISIBLE:View.GONE);
        viewHolder.cardGrosir.setVisibility(prod.getProduk_grosir()==1?View.VISIBLE:View.GONE);
        viewHolder.cardPreorder.setVisibility(prod.getProduk_preorder()==1?View.VISIBLE:View.GONE);
        viewHolder.cardDiscount.setVisibility(prod.getPersen_diskon()>0?View.VISIBLE:View.GONE);
        viewHolder.cardFreeongkir.setVisibility(prod.getProduk_freeongkir()==1?View.GONE:View.GONE);

        viewHolder.discount.setText(prod.getPersen_diskon()+"%");
        //viewHolder.ratingtext.setText("("+CommonUtilities.getNumberFormat(Double.parseDouble(prod.getTotal_responden()+""))+")");
        //viewHolder.ratingbar.setRating(prod.getRating());

        viewHolder.mSmallBang = SmallBang.attach2Window((MainActivity) parent.getContext());

        viewHolder.fav1.setVisibility(prod.getWishlist()?View.GONE:View.VISIBLE);
        viewHolder.fav2.setVisibility(prod.getWishlist()?View.VISIBLE:View.GONE);

        viewHolder.fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dh.insertWishlists(prod);
                prod.setWishlist(true);
                notifyDataSetChanged();

                ((MainActivity) parent.getContext()).updateWishlistGrid(viewHolder.position, true, prod);
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

                dh.deleteWishlist(prod);
                prod.setWishlist(false);
                notifyDataSetChanged();

                ((MainActivity) parent.getContext()).updateWishlistGrid(viewHolder.position, false, prod);
                viewHolder.fav2.setVisibility(View.GONE);
                viewHolder.fav1.setVisibility(View.VISIBLE);

            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertView) {
                //((MainActivity) parent.getContext()).openDetailProduk(prod);
            }
        });

        return convertView;

    }

    private class ViewHolder {

        LinearLayout linear_utama;
        ImageView image;
        TextView title;
        TextView price;
        TextView cutprice;
        TextView discount;

        LinearLayout linearNew;
        LinearLayout linearGrosir;
        LinearLayout linearPreorder;
        LinearLayout linearDiscount;
        LinearLayout linearSoldout;
        LinearLayout linearFreeongkir;
        LinearLayout linearCod;

        CardView cardNew;
        CardView cardDiscount;
        //LinearLayout cardDiscount;

        CardView cardPreorder;
        CardView cardSoldout;
        CardView cardGrosir;
        CardView cardFreeongkir;

        //RatingBar ratingbar;
        //TextView ratingtext;
        ImageView fav1;
        ImageView fav2;
        SmallBang mSmallBang;

        int position;
    }
}







