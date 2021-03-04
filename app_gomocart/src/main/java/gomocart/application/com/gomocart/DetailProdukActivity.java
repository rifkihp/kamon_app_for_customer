package gomocart.application.com.gomocart;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.matrixxun.starry.badgetextview.MaterialBadgeTextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.widget.EditText;
import android.widget.TextView;
import gomocart.application.com.adapter.ListGrosirAdapter;
import gomocart.application.com.adapter.ListStokAdapter;
import gomocart.application.com.adapter.RecyclerViewProdukAdapter;
import gomocart.application.com.data.RestApi;
import gomocart.application.com.data.RetroFit;
import gomocart.application.com.libs.*;
import gomocart.application.com.libs.SmallBang;
import gomocart.application.com.model.ResponseAddToCart;
import gomocart.application.com.model.ResponseBeliBarang;
import gomocart.application.com.model.ResponseProdukTerkait;
import gomocart.application.com.model.ResponseStokStore;
import gomocart.application.com.model.barang;
import gomocart.application.com.model.cartstok;
import gomocart.application.com.model.gallery;
import gomocart.application.com.model.gallery_list;
import gomocart.application.com.model.grosir;
import gomocart.application.com.model.kategori;
import gomocart.application.com.model.produk;
import gomocart.application.com.model.produk_kategori;
import gomocart.application.com.model.stok;
import gomocart.application.com.model.ukuran;
import gomocart.application.com.model.user;
import gomocart.application.com.model.warna;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static gomocart.application.com.libs.CommonUtilities.getOptionsImage;
import static gomocart.application.com.libs.CommonUtilities.initImageLoader;

public class DetailProdukActivity extends AppCompatActivity /*implements BaseSliderView.OnSliderClickListener*/ {

    public static ImageLoader imageLoader;
    public static DisplayImageOptions imageOptionProduk;

    Context context;
    user data;

    int kode_grup;
    String kode_trx;
    produk produkSelected;

    SliderLayout mProdukSlider;

    TextView nama_produk;
    TextView nama_mitra;
    TextView harga;
    TextView cutprice_harga_jual;
    TextView cutprice_harga_diskon;
    TextView discount;
    TextView periode_promo;

    ImageView fav1;
    ImageView fav2;
    SmallBang mSmallBang;

    ImageView btn_back;
    DatabaseHandler dh;

    int total_cart;
    int total_wishlist;

    TextView spesifikasi_minimum_pesan;
    TextView spesifikasi_stok;
    TextView spesifikasi_kategori;
    TextView spesifikasi_penjelasan;

    Dialog dialog_loading;

    ArrayList<gallery> listGambar = new ArrayList<>();
    ArrayList<ukuran> listUkuran  = new ArrayList<>();
    ArrayList<warna> listWarna    = new ArrayList<>();

    //LIST PRODUK TERKAIT
    RecyclerView rv_produk_terkait;
    ArrayList<produk> listProdukTerkait = new ArrayList<>();
    RecyclerViewProdukAdapter produkTerkaitAdapter;

    LinearLayout linear_share;

    //STOK
    LinearLayout linearStok;
    ExpandableHeightListView listviewStok;
    ArrayList<stok> liststok = new ArrayList<>();
    ListStokAdapter stokAdapter;

    //GROSIR
    LinearLayout linearDetailGrosir;
    ExpandableHeightListView listviewGrosir;
    ArrayList<grosir> listgrosir = new ArrayList<>();
    ListGrosirAdapter grosirAdapter;
    
    LinearLayout linearSoldout;
    LinearLayout linearNew;
    LinearLayout linearGrosir;
    LinearLayout linearPreorder;
    LinearLayout linearDiscount;
    LinearLayout linearFreeongkir;
    LinearLayout linearCod;

    TextView kirim_pesan;
    TextView add_to_cart;

    RelativeLayout main_screen;
    LinearLayout linear_1, linear_button;

    FrameLayout toolbar_cart;
    MaterialBadgeTextView number_cart;


    final int RESULT_FROM_KIRIM_PESAN = 1;

    ArrayList<gallery> gallerylist = new ArrayList<>();

    int status_stok = 0;
    int jumlah_stok = 0;

    boolean is_drawn = false;

    ProgressBar loading;
    LinearLayout retry;
    TextView btnReload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_produk_gomocart);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#308c8e"));
        }

        context = DetailProdukActivity.this;
        data = CommonUtilities.getSettingUser(context);
        dh = new DatabaseHandler(context);

        initImageLoader(context);
        imageLoader = ImageLoader.getInstance();
        imageOptionProduk = getOptionsImage(R.drawable.attachment, R.drawable.attachment);

        mProdukSlider = findViewById(R.id.slider);
        nama_produk = findViewById(R.id.nama_produk);
        nama_mitra = findViewById(R.id.nama_mitra);
        harga                  = findViewById(R.id.txtHargaProduk);
        cutprice_harga_jual    = findViewById(R.id.txtCutpriceHargaJual);
        cutprice_harga_diskon  = findViewById(R.id.txtCutpriceHargaDiskon);
        discount               = findViewById(R.id.discount);
        periode_promo          = findViewById(R.id.txtPeriodePromo);

        spesifikasi_minimum_pesan = findViewById(R.id.spesifikasi_minimum_pesan);
        spesifikasi_stok          = findViewById(R.id.spesifikasi_stok);
        spesifikasi_kategori      = findViewById(R.id.spesifikasi_kategori);
        spesifikasi_penjelasan    = findViewById(R.id.detail_produk);

        linear_share  = findViewById(R.id.toolbar_layout_share);
        linear_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shareBody = CommonUtilities.SERVER_URL + "/produk/detail.php?id="+produkSelected.getId();
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.app_name)));
            }
        });

        loading   = findViewById(R.id.pgbarLoading);
        retry     = findViewById(R.id.loadMask);
        btnReload = findViewById(R.id.btnReload);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new loadDataProdukTerkait().execute();
            }
        });

        btn_back       = findViewById(R.id.btn_back);
        add_to_cart    = findViewById(R.id.add_to_cart);
        kirim_pesan    = findViewById(R.id.kirim_pesan);

        fav1 = findViewById(R.id.fav1);
        fav2 = findViewById(R.id.fav2);

        linearSoldout    = findViewById(R.id.linear_soldout);
        linearNew        = findViewById(R.id.linear_new);
        linearGrosir     = findViewById(R.id.linear_grosir);
        linearPreorder   = findViewById(R.id.linear_preorder);
        linearDiscount   = findViewById(R.id.linear_discount);
        linearFreeongkir = findViewById(R.id.linear_freeongkir);
        linearCod        = findViewById(R.id.linear_cod);

        linearStok               = findViewById(R.id.linear7);
        listviewStok             = findViewById(R.id.listviewStok);

        linearDetailGrosir       = findViewById(R.id.linear8);
        listviewGrosir           = findViewById(R.id.listviewGrosir);

        mSmallBang = SmallBang.attach2Window(this);
        fav1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dh.insertBadge(produkSelected.getId(), 1, "WISH", kode_grup);
                produkSelected.setWishlist(true);

                fav2.setVisibility(View.VISIBLE);
                fav1.setVisibility(View.GONE);
                like(v);
            }

            public void like(View view){
                fav2.setImageResource(R.drawable.f4);
                mSmallBang.bang(view);
                mSmallBang.setmListener(new gomocart.application.com.libs.SmallBangListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {

                    }
                });
            }
        });

        fav2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dh.deleteBadge(produkSelected.getId(), "WISH", kode_grup);
                produkSelected.setWishlist(false);

                fav2.setVisibility(View.GONE);
                fav1.setVisibility(View.VISIBLE);

            }
        });

        toolbar_cart  = findViewById(R.id.frameLayoutCart);
        number_cart   = findViewById(R.id.number_cart);
        updateTotalCartlist();

        toolbar_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("produk", produkSelected);
                intent.putExtra("goto", "cart_list");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        number_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("produk", produkSelected);
                intent.putExtra("goto", "cart_list");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("produk", produkSelected);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(produkSelected.getProduk_soldout()==1 || jumlah_stok==0) {
                    openDialogMessage("Maaf Stok Produk Kosong.", false);
                    return;
                }

                String items = "";
                for (int i = 0; i < listviewStok.getChildCount(); i++) {
                    EditText qty = listviewStok.getChildAt(i).findViewById(R.id.qty);

                    if(qty.getText().toString().length() > 4) {
                        CommonUtilities.showSnackbar("Input Qty tidak benar.", false, DetailProdukActivity.this);
                    } else
                    if (qty.getText().toString().length() > 0 && Integer.parseInt(qty.getText().toString()) > 0) {
                        if(kode_grup==2) {
                            items += (items.length() > 0 ? "\n" : "") + produkSelected.getId() + "\t" + liststok.get(i).getUkuran() + "\t" + liststok.get(i).getWarna() + "\t" + qty.getText().toString();
                        } else {
                            String id_barang   = produkSelected.getKode();
                            String barcode     = produkSelected.getUkuran();
                            String nm_barang   = produkSelected.getNama();
                            String id_kategori = produkSelected.getWarna();
                            String nm_kategori = produkSelected.getCategory_name();
                            String id_satuan   = produkSelected.getPeriode_promo();
                            String nm_satuan   = produkSelected.getSatuan();
                            String harga_beli  = produkSelected.getHarga_beli()+"";
                            String harga_jual  = produkSelected.getHarga_jual()+"";
                            String stock       = produkSelected.getQty()+"";
                            String stock_palsu = produkSelected.getMax_qty()+"";
                            String keterangan  = produkSelected.getPenjelasan();
                            String type        = produkSelected.getList_warna();
                            String photo       = produkSelected.getFoto1_produk();
                            String entity_cd   = produkSelected.getList_ukuran();

                            barang dbarang = new barang(id_barang, barcode, nm_barang, id_kategori, nm_kategori, id_satuan, nm_satuan, harga_beli, harga_jual, stock, stock_palsu, keterangan, type, photo, entity_cd);
                            new addItemToKeranjang(dbarang, qty.getText().toString()).execute();
                        }

                    }
                }

                if (items.length() > 0) {
                    new addProductToCart(items).execute();
                }

                for (int i = 0; i < listviewStok.getChildCount(); i++) {
                    EditText qty = listviewStok.getChildAt(i).findViewById(R.id.qty);
                    if(qty.getText().toString().length() > 4) {
                        CommonUtilities.showSnackbar("Input Qty tidak benar.", false, DetailProdukActivity.this);
                    } else
                    if (qty.getText().toString().length() > 0 && Integer.parseInt(qty.getText().toString()) > 0) {
                        items += (items.length() > 0 ? "\n" : "") + produkSelected.getId() + "\t" + liststok.get(i).getUkuran() + "\t" + liststok.get(i).getWarna() + "\t" + qty.getText().toString();
                    }
                }
            }
        });

        kirim_pesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("produk", produkSelected);
                startActivityForResult(intent, RESULT_FROM_KIRIM_PESAN);
            }
        });

        if(savedInstanceState==null) {
            produkSelected = (produk) getIntent().getSerializableExtra("produk");
            kode_grup = getIntent().getIntExtra("kode_grup", 0);
            kode_trx = dh.getKodeTrx(kode_grup, produkSelected.getMitra().getId());

            nama_produk.setText(produkSelected.getNama());
            nama_mitra.setText("Dijual Oleh "+produkSelected.getMitra().getNama()+" - "+produkSelected.getMitra().getCity_name());
            harga.setText(CommonUtilities.getCurrencyFormat(produkSelected.getSubtotal(), "Rp. "));

            periode_promo.setVisibility(produkSelected.getPeriode_promo()!=null&&produkSelected.getPeriode_promo().length()>0?View.VISIBLE:View.GONE);
            if(produkSelected.getPeriode_promo()!=null&&produkSelected.getPeriode_promo().length()>0) {
                periode_promo.setText("Periode Promo: "+produkSelected.getPeriode_promo());
            }

            cutprice_harga_jual.setPaintFlags(cutprice_harga_jual.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            if(produkSelected.getHarga_diskon()>produkSelected.getSubtotal()) {
                cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(produkSelected.getHarga_diskon(), "Rp. "));
            } else if(produkSelected.getHarga_jual()>produkSelected.getSubtotal()) {
                cutprice_harga_jual.setText(CommonUtilities.getCurrencyFormat(produkSelected.getHarga_jual(), "Rp. "));
            } else {
                cutprice_harga_jual.setText("");
            }

            cutprice_harga_diskon.setText(produkSelected.getPersen_diskon()>0?"("+produkSelected.getPersen_diskon() + "%)":"");
            discount.setText(produkSelected.getPersen_diskon() + "%");

            spesifikasi_minimum_pesan.setText(CommonUtilities.getNumberFormat(produkSelected.getMinimum_pesan())+" "+produkSelected.getSatuan());

            spesifikasi_kategori.setText(produkSelected.getCategory_name());
            spesifikasi_penjelasan.setText(Html.fromHtml(produkSelected.getPenjelasan()));

            fav1.setVisibility(dh.getTotalBadge(produkSelected.getId(), "WISH", kode_grup)>0?View.GONE:View.VISIBLE);
            fav2.setVisibility(dh.getTotalBadge(produkSelected.getId(), "WISH", kode_grup)>0?View.VISIBLE:View.GONE);

            linearSoldout.setVisibility(produkSelected.getProduk_soldout()==1?View.VISIBLE:View.GONE);
            linearNew.setVisibility(produkSelected.getProduk_terbaru()==1?View.VISIBLE:View.GONE);
            linearGrosir.setVisibility(produkSelected.getProduk_grosir()==1?View.VISIBLE:View.GONE);
            linearPreorder.setVisibility(produkSelected.getProduk_preorder()==1?View.VISIBLE:View.GONE);
            linearDiscount.setVisibility(produkSelected.getPersen_diskon()>0?View.VISIBLE:View.GONE);
            linearFreeongkir.setVisibility(produkSelected.getProduk_freeongkir()==1?View.VISIBLE:View.GONE);
            linearCod.setVisibility(produkSelected.getProduk_cod()==1?View.VISIBLE:View.GONE);
        }

        //*********RECYCLERVIEWS*********
        rv_produk_terkait =findViewById(R.id.rv2);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rv_produk_terkait.setLayoutManager(mLayoutManager);

        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);

        mProdukSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mProdukSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mProdukSlider.setCustomAnimation(new ChildAnimationExample());
        mProdukSlider.setDuration(4000);

        main_screen = findViewById(R.id.main_screen);
        main_screen.post(new Runnable() {
            @Override
            public void run() {
                if(!is_drawn) {
                    is_drawn = true;
                    linear_1 = findViewById(R.id.linear1);
                    linear_button = findViewById(R.id.linear_button);
                    ViewGroup.LayoutParams params = mProdukSlider.getLayoutParams();
                    params.width = mProdukSlider.getMeasuredWidth();
                    params.height = mProdukSlider.getMeasuredWidth();
                    mProdukSlider.setLayoutParams(params);

                    new loadDataProdukTerkait().execute();
                }
            }
        });
    }

    public class addProductToCart extends AsyncTask<String, Void, Void> {

        String items;

        addProductToCart(String items) {
            this.items = items;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
        }

        @Override
        protected Void doInBackground(String... urls) {
            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseAddToCart> postAddToCartCall = api.postAddToCart(
                    data.getId()+"",
                    produkSelected.getId()+"",
                    kode_trx,
                    items
            );
            postAddToCartCall.enqueue(new Callback<ResponseAddToCart>() {
                @Override
                public void onResponse(@NonNull Call<ResponseAddToCart> call, @NonNull Response<ResponseAddToCart> response) {

                    boolean success        = Objects.requireNonNull(response.body()).getSuccess();
                    String message         = Objects.requireNonNull(response.body()).getMessage();

                    if(success) {
                        kode_trx           = Objects.requireNonNull(response.body()).getKode_trx();
                        jumlah_stok        = Objects.requireNonNull(response.body()).getJumlah_stok();
                        listUkuran         = Objects.requireNonNull(response.body()).getUkuranlist();
                        listWarna          = Objects.requireNonNull(response.body()).getWarnalist();
                        liststok           = Objects.requireNonNull(response.body()).getStoklist();

                        ArrayList<cartstok> cart = Objects.requireNonNull(response.body()).getCartstok();
                        for(cartstok itemcart: cart) {
                            produk temp = produkSelected;
                            temp.setUkuran(itemcart.getUkuran());
                            temp.setWarna(itemcart.getWarna());
                            temp.setQty(itemcart.getQty());

                            double harga_grosir = itemcart.getHarga_grosir();
                            if(harga_grosir>0 && harga_grosir<=produkSelected.getSubtotal()) {
                                //JIKA DIAMBIL HARGA GROSIR
                                //Log.e("GROSIR", harga_grosir+" @@");
                                temp.setHarga_grosir(harga_grosir);
                                temp.setSubtotal(harga_grosir);
                                temp.setGrandtotal(itemcart.getQty()*harga_grosir);
                            } else {
                                temp.setGrandtotal(itemcart.getQty()*produkSelected.getSubtotal());
                            }
                            dh.insertCartlist(temp, kode_trx, kode_grup);
                        }

                    }

                    Intent i = new Intent("gomocart.application.com.gomocart.ADD_TO_CART");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);

                }

                @Override
                public void onFailure(@NonNull Call<ResponseAddToCart> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.ADD_TO_CART");
                    i.putExtra("success", false);
                    i.putExtra("message", "Gagal menambahkan produk.");
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    public void openDialogLoading() {
        dialog_loading.setCancelable(false);
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }

    public void openDialogMessage(String message, boolean status) {
        CommonUtilities.showSnackbar(message, status, this);
    }

    public void updateTotalCartlist() {
        total_cart = dh.getTotalCart();
        number_cart.setText(total_cart + "");
        number_cart.setVisibility(total_cart > 0 ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("produk", produkSelected);
                setResult(RESULT_OK, intent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.putExtra("produk", produkSelected);
            setResult(RESULT_OK, intent);
            finish();

            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        mProdukSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadProdukTerkaitReceiver);
            unregisterReceiver(mHandleAddToCartReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadProdukTerkaitReceiver);
            unregisterReceiver(mHandleAddToCartReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadProdukTerkaitReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_PRODUK_TERKAIT"));
        registerReceiver(mHandleAddToCartReceiver, new IntentFilter("gomocart.application.com.gomocart.ADD_TO_CART"));

        super.onResume();
    }



    private final BroadcastReceiver mHandleAddToCartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Boolean success = intent.getBooleanExtra("success", false);
            String message = intent.getStringExtra("message");

            dialog_loading.dismiss();

            if(success) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                //spesifikasi stok produk
                if(status_stok==0) {
                    spesifikasi_stok.setText(jumlah_stok>0?CommonUtilities.getNumberFormat(jumlah_stok)+" "+produkSelected.getSatuan():"Tidak Tersedia");
                } else {
                    spesifikasi_stok.setText(jumlah_stok>0?"Tersedia":"Tidak Tersedia");
                }

                //list stok
                linearStok.setVisibility(jumlah_stok>0?View.VISIBLE:View.GONE);
                stokAdapter.UpdateListStokUmkmAdapter(liststok);

                updateTotalCartlist();
            } else {
                openDialogMessage(message, false);
            }
        }
    };
    
    private final BroadcastReceiver mHandleLoadProdukTerkaitReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {

            loading.setVisibility(View.GONE);
            Boolean success = intent.getBooleanExtra("success", false);
            if(success) {
                //Sliding Image Produk
                if(listGambar.size()==0) {
                    listGambar.add(new gallery(0, "", produkSelected.getFoto1_produk()));;
                }
                for (gallery gambar : listGambar) {
                    String url = (kode_grup==2?CommonUtilities.SERVER_URL + "/uploads/produk/":CommonUtilities.SERVER_URL + "/images/barang/") + gambar.getGambar();
                    TextSliderView textSliderView = new TextSliderView(context);

                    textSliderView
                            .image(url.replaceAll("\\s", "%20"))
                            .setScaleType(BaseSliderView.ScaleType.Fit);
                            //.setOnSliderClickListener(DetailProdukActivity.this);

                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle().putString("extra", gambar.getGambar());

                    mProdukSlider.addSlider(textSliderView);
                }

                //spesifikasi stok produk
                if(status_stok==0) {
                    spesifikasi_stok.setText(jumlah_stok>0?CommonUtilities.getNumberFormat(jumlah_stok)+" "+produkSelected.getSatuan():"Tidak Tersedia");
                } else {
                    spesifikasi_stok.setText(jumlah_stok>0?"Tersedia":"Tidak Tersedia");
                }

                //list stok
                linearStok.setVisibility(jumlah_stok>0?View.VISIBLE:View.GONE);
                stokAdapter = new ListStokAdapter(context, liststok, status_stok);
                listviewStok.setAdapter(stokAdapter);

                //list grosir
                linearDetailGrosir.setVisibility(listgrosir.size()>0?View.VISIBLE:View.GONE);
                grosirAdapter = new ListGrosirAdapter(context, listgrosir);
                listviewGrosir.setAdapter(grosirAdapter);

                //produk terkait
                produkTerkaitAdapter = new RecyclerViewProdukAdapter(context, dh, listProdukTerkait, kode_grup);
                rv_produk_terkait.setAdapter(produkTerkaitAdapter);

            } else {
                retry.setVisibility(View.VISIBLE);
            }
        }
    };

    /*@Override
    public void onSliderClick(BaseSliderView slider) {

        ArrayList<gallery_list> gallery_lists = new ArrayList<>();
        gallery_lists.add(new gallery_list(gallerylist));
        Intent inten = new Intent(context, DetailSlideGalleryActivity.class);
        inten.putExtra("gallery", gallery_lists);
        startActivity(inten);
    }*/

    public class loadDataProdukTerkait extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);

        }

        @Override
        protected Void doInBackground(String... urls) {

            listGambar = new ArrayList<>();
            listUkuran = new ArrayList<>();
            listWarna  = new ArrayList<>();
            listProdukTerkait = new ArrayList<>();
            liststok = new ArrayList<>();
            listgrosir = new ArrayList<>();

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseProdukTerkait> postProdukTerkaitCall = api.postProdukTerkait(
                    data.getId()+"",
                    produkSelected.getId()+"",
                    kode_grup+""
            );
            postProdukTerkaitCall.enqueue(new Callback<ResponseProdukTerkait>() {
                @Override
                public void onResponse(@NonNull Call<ResponseProdukTerkait> call, @NonNull Response<ResponseProdukTerkait> response) {

                    boolean success        = Objects.requireNonNull(response.body()).getSuccess();
                    String message         = Objects.requireNonNull(response.body()).getMessage();

                    if(success) {
                        status_stok = Objects.requireNonNull(response.body()).getStatus_stok();
                        jumlah_stok = Objects.requireNonNull(response.body()).getJumlah_stok();

                        listUkuran = Objects.requireNonNull(response.body()).getUkuranlist();
                        listWarna = Objects.requireNonNull(response.body()).getWarnalist();

                        listGambar = Objects.requireNonNull(response.body()).getGambarlist();
                        listProdukTerkait = Objects.requireNonNull(response.body()).getProduklist();
                        liststok = Objects.requireNonNull(response.body()).getStoklist();
                        listgrosir = Objects.requireNonNull(response.body()).getGrosirlist();

                        if(kode_grup<2) {
                            status_stok = 0;
                            jumlah_stok = produkSelected.getMax_qty();
                            if(liststok.size()>0) {
                                liststok.set(0, new stok(produkSelected.getId(), produkSelected.getUkuran(), produkSelected.getWarna(), CommonUtilities.getCurrencyFormat(produkSelected.getHarga_jual(), "Rp."), produkSelected.getMax_qty()));
                            }
                        }
                    }
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_PRODUK_TERKAIT");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);

                }

                @Override
                public void onFailure(@NonNull Call<ResponseProdukTerkait> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_PRODUK_TERKAIT");
                    i.putExtra("success", false);
                    i.putExtra("message", "Load data gagal.");
                    sendBroadcast(i);
                }
            });
            
            return null;
        }
    }



    public class addItemToKeranjang extends AsyncTask<String, Void, Void> {

        barang dtbarang;
        int qty = 0;
        addItemToKeranjang(barang item, String qty) {
            this.dtbarang = item;
            this.qty = Integer.parseInt(qty);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseBeliBarang> beliBarangCall = api.postBeliBarang(
                    data.getKeyUserId(),
                    dtbarang.getEntity_cd(),
                    dtbarang.getId_barang(),
                    produkSelected.getUkuran(),
                    produkSelected.getWarna(),
                    dtbarang.getHarga_beli(),
                    dtbarang.getHarga_jual(),
                    qty+"",
                    dtbarang.getType(),
                    kode_grup+"",
                    jumlah_stok+""
                    );
            beliBarangCall.enqueue(new Callback<ResponseBeliBarang>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBeliBarang> call, @NonNull Response<ResponseBeliBarang> response) {

                    String result  = response.body().getResult();
                    String message = response.body().getMsg();

                    if(result.equals("1")) {
                        int seq_penjualan = response.body().getSeq_penjualan();
                        jumlah_stok = response.body().getSisa();
                        produkSelected.setSeqId(seq_penjualan);
                        produkSelected.setQty(qty);
                        produkSelected.setGrandtotal(qty*produkSelected.getSubtotal());
                        Log.e("UKURAN_WARNA", produkSelected.getUkuran()+ " --- "+produkSelected.getWarna());
                        dh.insertCartlist(produkSelected, "", kode_grup);

                        stok itemstok = liststok.get(0);
                        liststok.set(0, new stok(itemstok.getId(), itemstok.getUkuran(), itemstok.getWarna(), itemstok.getHarga(), jumlah_stok));

                        Intent i = new Intent("gomocart.application.com.gomocart.ADD_TO_CART");
                        i.putExtra("success", true);
                        i.putExtra("message", message);

                        sendBroadcast(i);
                    } else {
                        Intent i = new Intent("gomocart.application.com.gomocart.ADD_TO_CART");
                        i.putExtra("success", false);
                        i.putExtra("message", message);

                        sendBroadcast(i);
                    }

                }

                @Override
                public void onFailure(@NonNull Call<ResponseBeliBarang> call, @NonNull Throwable t) {
                    Intent i = new Intent("gomocart.application.com.gomocart.ADD_TO_CART");
                    i.putExtra("success", false);
                    i.putExtra("message", "Gagal tambah barang. Coba lagi.");
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data_intent);

    }

}
