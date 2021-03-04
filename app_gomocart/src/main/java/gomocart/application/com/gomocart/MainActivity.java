package gomocart.application.com.gomocart;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alexzh.circleimageview.CircleImageView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView.OnSliderClickListener;
import com.matrixxun.starry.badgetextview.MaterialBadgeTextView;
import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import gomocart.application.com.adapter.CartlistAdapter;
import gomocart.application.com.adapter.ListKategoriAdapter;
import gomocart.application.com.adapter.ListOngkirAdapter;
import gomocart.application.com.adapter.ListOrderAdapter;
import gomocart.application.com.adapter.MoreMenuAdapter;
import gomocart.application.com.data.RestApi;
import gomocart.application.com.data.RetroFit;
import gomocart.application.com.fragment.DaftarPesanan5BatalFragment;
import gomocart.application.com.fragment.DaftarPesanan1BelumBayarFragment;
import gomocart.application.com.fragment.DaftarPesanan0Fragment;
import gomocart.application.com.fragment.DaftarPesanan3SedangKirimFragment;
import gomocart.application.com.fragment.DaftarPesanan2SedangProsesFragment;
import gomocart.application.com.fragment.DaftarPesanan4SelesaiFragment;
import gomocart.application.com.fragment.InitialFragment;
import gomocart.application.com.fragment.KeranjangFragment;
import gomocart.application.com.fragment.Produk0Fragment;
import gomocart.application.com.fragment.Produk2PrimkopFragment;
import gomocart.application.com.fragment.Produk1PuskopFragment;
import gomocart.application.com.fragment.Produk3UmkmFragment;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.DatabaseHandler;
import gomocart.application.com.libs.MyImageDownloader;
import gomocart.application.com.model.ResponseDanaCreateOrder;
import gomocart.application.com.model.ResponseDashboard;
import gomocart.application.com.model.ResponseHapusBarang;
import gomocart.application.com.model.ResponseInformasiStore;
import gomocart.application.com.model.ResponseKeranjangStore;
import gomocart.application.com.model.ResponseOrder;
import gomocart.application.com.model.ResponseUpdateTransaksi;
import gomocart.application.com.model.alamat;
import gomocart.application.com.model.banner;
import gomocart.application.com.model.cart;
import gomocart.application.com.model.informasi;
import gomocart.application.com.model.informasi_list;
import gomocart.application.com.model.kategori;
import gomocart.application.com.model.moremenu;
import gomocart.application.com.model.notifikasi;
import gomocart.application.com.model.ongkir;
import gomocart.application.com.model.order;
import gomocart.application.com.model.market;
import gomocart.application.com.model.order_list;
import gomocart.application.com.model.perpesanan;
import gomocart.application.com.model.produk;
import gomocart.application.com.model.produk_kategori;
import gomocart.application.com.model.produk_list;
import gomocart.application.com.model.setting;
import gomocart.application.com.model.shortcut;
import gomocart.application.com.model.user;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnSliderClickListener, OnNavigationItemSelectedListener, OnClickListener {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    public static ArrayList<banner> dashboard_list_banner = new ArrayList<>();
    public static ArrayList<shortcut> dashboard_list_shortcut = new ArrayList<>();
    public static ArrayList<kategori> dashboard_list_kategori = new ArrayList<>();

    public static ArrayList<produk_kategori> dashboard_list_tab_kategori = new ArrayList<>();
    public static ArrayList<produk_kategori> dashboard_list_tab_induk_kategori = new ArrayList<>();

    public static int tampilkan_induk_kategori = 0;
    public static int tampilkan_shortcut = 0;
    public static int tampilkan_kategori = 0;
    public static int tampilkan_shortcut_bawah = 0;

    public static FrameLayout frame_container;
    private LinearLayout llShortCutMenu;
    private LinearLayout llMenuHome;
    private LinearLayout llMenuWishList;
    private LinearLayout llMenuCart;
    private LinearLayout llMenuMessage;
    private LinearLayout llMenuOrderHistory;
    private LinearLayout llMenuSetting;

    final int RESULT_FROM_PRODUK_DETAIL = 3;
    final int REQUEST_FROM_FILTER = 7;
    final int RESULT_FROM_PROSES_CHECKOUT = 8;
    final int RESULT_FROM_DETAIL_ORDER = 12;
    final int RESULT_FROM_DANA_PAYMENT = 13;

    public static Context context;
    public static TextView main_title;

    FrameLayout toolbar_layout_cart;
    MaterialBadgeTextView number_cart;

    LinearLayout toolbar_layout_more;

    public static DatabaseHandler dh;
    int total_daftar_pesanan;
    int total_message;

    int total_cart;

    //CART LIST
    public static ArrayList<produk> cartlist = new ArrayList<>();
    public static CartlistAdapter cartlistAdapter;


    // ORDER LIST
    int[] orderlist_page = new int[5];
    ArrayList<order>[] orderlist = new ArrayList[5];
    ListOrderAdapter[] orderlist_adapter = new ListOrderAdapter[5];

    //SLIDE MENU
    ArrayList<moremenu> moremenulist = new ArrayList<>();
    Map<moremenu, ArrayList<moremenu>> submoremenulist = new LinkedHashMap<>();
    MoreMenuAdapter moremenuAdapter;
    public static ExpandableListView moremenuListView;

    static int menu_selected = 1;

    public static ImageLoader imageLoader;
    public static DisplayImageOptions imageOptionsUser;
    public static DisplayImageOptions imageOptionProduk;
    public static DisplayImageOptions imageOptionKategori;
    public static DisplayImageOptions imageOptionBank;
    public static DisplayImageOptions imageOptionOngkir;
    public static DisplayImageOptions imageOptionInformasi;

    ImageView menu;

    public static DrawerLayout drawer;
    LinearLayout mDrawerPane;

    CircleImageView avatar;
    TextView name_avatar;
    TextView bagian_avatar;

    Dialog dialog_loading;

    public static market marketSelected;
    public static alamat alamatSelected;

    //FOR BOTTOM NAVIGATION...
    private LinearLayout llBottomNavigationHome;
    private LinearLayout llBottomNavigationMyOrder;
    private LinearLayout llBottomNavigationSetting;
    private LinearLayout llBottomNavigationProfile;
    private LinearLayout llBottomNavigationProduct;

    private ImageView ivBottomNavigationHome;
    private ImageView ivBottomNavigationMyOrder;
    private ImageView ivBottomNavigationSetting;
    private ImageView ivBottomNavigationProfile;
    private ImageView ivBottomNavigationProduct;

    private TextView tvBottomNavigationHome;
    private TextView tvBottomNavigationMyOrder;
    private TextView tvBottomNavigationSetting;
    private TextView tvBottomNavigationProfile;
    private TextView tvBottomNavigationProduct;

    public static user data;

    order orderSelected;
    String action;

    Dialog konfirmasi_dialog;
    TextView konfirmasi_dialog_title;
    TextView konfirmasi_dialog_text;
    TextView konfirmasi_dialog_yes;
    TextView konfirmasi_dialog_no;

    Handler mHandlerCekStok = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main_gomocart);

        if (Build.VERSION.SDK_INT >= 23) {
            insertDummyContactWrapper();
        }

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#308c8e"));
        }

        context = MainActivity.this;
        dh = new DatabaseHandler(context);
        data = CommonUtilities.getSettingUser(context);
        dh.createTable();

        //FOR BOTTOM NAVIGATION...
        llBottomNavigationHome    = findViewById(R.id.llBottomNavigationHome);
        llBottomNavigationMyOrder = findViewById(R.id.llBottomNavigationMyOrder);
        llBottomNavigationSetting = findViewById(R.id.llBottomNavigationSetting);
        llBottomNavigationProfile = findViewById(R.id.llBottomNavigationProfile);
        llBottomNavigationProduct = findViewById(R.id.llBottomNavigationProduct);

        ivBottomNavigationHome = findViewById(R.id.ivBottomNavigationHome);
        ivBottomNavigationMyOrder = findViewById(R.id.ivBottomNavigationMyOrder);
        ivBottomNavigationSetting = findViewById(R.id.ivBottomNavigationSetting);
        ivBottomNavigationProfile = findViewById(R.id.ivBottomNavigationProfile);
        ivBottomNavigationProduct = findViewById(R.id.ivBottomNavigationProduct);

        tvBottomNavigationHome    = findViewById(R.id.tvBottomNavigationHome);
        tvBottomNavigationMyOrder = findViewById(R.id.tvBottomNavigationMyOrder);
        tvBottomNavigationSetting = findViewById(R.id.tvBottomNavigationSetting);
        tvBottomNavigationProfile = findViewById(R.id.tvBottomNavigationProfile);
        tvBottomNavigationProduct = findViewById(R.id.tvBottomNavigationProduct);

        llBottomNavigationProduct.setOnClickListener(this);
        llBottomNavigationMyOrder.setOnClickListener(this);
        llBottomNavigationHome.setOnClickListener(this);
        llBottomNavigationProfile.setOnClickListener(this);
        llBottomNavigationSetting.setOnClickListener(this);

        //FOR SHORTCUT MENU...
        llShortCutMenu = findViewById(R.id.llShortCutMenu);
        llMenuHome = findViewById(R.id.llMenuHome);
        llMenuWishList = findViewById(R.id.llMenuWishList);
        llMenuCart = findViewById(R.id.llMenuCart);
        llMenuMessage = findViewById(R.id.llMenuMessage);
        llMenuOrderHistory = findViewById(R.id.llMenuOrderHistory);
        llMenuSetting = findViewById(R.id.llMenuSetting);
        
        llShortCutMenu.setOnClickListener(this);
        llMenuHome.setOnClickListener(this);
        llMenuWishList.setOnClickListener(this);
        llMenuCart.setOnClickListener(this);
        llMenuMessage.setOnClickListener(this);
        llMenuOrderHistory.setOnClickListener(this);
        llMenuSetting.setOnClickListener(this);

        llMenuWishList.setVisibility(View.GONE);
        llMenuMessage.setVisibility(View.GONE);


        menu = findViewById(R.id.menu);
        drawer = findViewById(R.id.drawer_layout);
        mDrawerPane = findViewById(R.id.drawerPane);

        int width = getResources().getDisplayMetrics().widthPixels;
        width = width - (width / 3);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mDrawerPane.getLayoutParams();
        params.width = width;
        mDrawerPane.setLayoutParams(params);

        frame_container = findViewById(R.id.frame_container);
        moremenuListView = findViewById(R.id.moremenulistview);
        main_title = findViewById(R.id.eshop);


        toolbar_layout_more = findViewById(R.id.toolbar_layout_more);
        toolbar_layout_more.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llShortCutMenu.getVisibility() == View.GONE) {
                    llShortCutMenu.setVisibility(View.VISIBLE);
                }
            }
        });

        main_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView(0);
            }
        });

        konfirmasi_dialog = new Dialog(context);
        konfirmasi_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        konfirmasi_dialog.setCancelable(true);
        konfirmasi_dialog.setContentView(R.layout.konfirmasi_dialog_gomocart);

        konfirmasi_dialog_yes = konfirmasi_dialog.findViewById(R.id.btn_yes);
        konfirmasi_dialog_yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                konfirmasi_dialog.dismiss();

                if(action.equalsIgnoreCase("delete_cartlist_satuan")) {
                    prosesHapusCartlistTerpilih();
                }else
                if(action.equalsIgnoreCase("delete_cartlist")) {
                    prosesHapusCartlistTerpilih();
                }else
                if(action.equalsIgnoreCase("batalkan_pesanan")) {
                    new prosesBatalkanPesanan().execute();
                }else
                if(action.equalsIgnoreCase("terima_pesanan")) {
                    new prosesTerimaPesanan().execute();
                }
            }
        });

        konfirmasi_dialog_no = konfirmasi_dialog.findViewById(R.id.btn_no);
        konfirmasi_dialog_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                konfirmasi_dialog.dismiss();

            }
        });

        konfirmasi_dialog_title = konfirmasi_dialog.findViewById(R.id.text_title);
        konfirmasi_dialog_text = konfirmasi_dialog.findViewById(R.id.text_dialog);

        toolbar_layout_cart =  findViewById(R.id.frameLayoutCart);
        number_cart =  findViewById(R.id.number_cart);

        toolbar_layout_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView(2);
            }
        });
        number_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView(2);
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        avatar = findViewById(R.id.avatar);
        name_avatar = findViewById(R.id.name);
        bagian_avatar = findViewById(R.id.bagian);

        moremenulist.add(new moremenu(false, 1, "", getResources().getString(R.string.menu_produk), "", R.drawable.menu_produk, 0));
        moremenulist.add(new moremenu(false, 2, "", getResources().getString(R.string.menu_checkout), "", R.drawable.menu_keranjang, 0));
        moremenulist.add(new moremenu(false, 3, "", getResources().getString(R.string.menu_order), "", R.drawable.menu_daftar_pesanan, 0));

        moremenuAdapter = new MoreMenuAdapter(context, moremenulist, submoremenulist);
        moremenuListView.setAdapter(moremenuAdapter);

        updateTotalCartlist();

        moremenuListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                moremenuAdapter.updateView(moremenulist, groupPosition);
                int id_menu = moremenulist.get(groupPosition).getId();
                openMoreMenu(id_menu);
                return false;
            }
        });

        avatar.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);

        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager)
                    context.getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize)
                .memoryCache(new FIFOLimitedMemoryCache(memoryCacheSize - 1000000))
                .denyCacheImageMultipleSizesInMemory()
                //.discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new MyImageDownloader(context))
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        imageOptionsUser     = CommonUtilities.getOptionsImage(R.drawable.userdefault, R.drawable.userdefault);
        imageOptionProduk    = CommonUtilities.getOptionsImage(R.drawable.attachment, R.drawable.attachment);
        imageOptionKategori  = CommonUtilities.getOptionsImage(R.drawable.attachment, R.drawable.attachment);
        imageOptionBank      = CommonUtilities.getOptionsImage(R.drawable.attachment, R.drawable.attachment);
        imageOptionOngkir    = CommonUtilities.getOptionsImage(R.drawable.attachment, R.drawable.attachment);
        imageOptionInformasi = CommonUtilities.getOptionsImage(R.drawable.attachment, R.drawable.attachment);
        menu_selected        = 0;

        if (savedInstanceState == null) {
            data = (user) getIntent().getSerializableExtra("user");
            menu_selected = getIntent().getIntExtra("menu_select", 0);
        }

        CommonUtilities.setSettingUser(context, data);

        //Toast.makeText(context, data.getId()+  "   " + data.getFirst_name(), Toast.LENGTH_LONG).show();
        displayView(menu_selected);
    }

    public void updateStock(produk item, int qty) {
        mHandlerCekStok.removeCallbacks(mCekStokTimeTask);
        mHandlerCekStok.postDelayed(mCekStokTimeTask, 1000);
    }

    public Runnable mCekStokTimeTask = new Runnable() {
        public void run() {
            mHandlerCekStok.removeCallbacks(this);
            //Toast.makeText(context, "TESSSS ", Toast.LENGTH_SHORT).show();



        }
    };

    public void openMoreMenu(int id) {
        switch (id) {
            case 1:
                displayView(1);
                break;

            case 2:
                displayView(2);
                break;

            case 3:

                displayView(3);
                break;

            case 4:

                displayView(4);
                break;

            case 5:

                displayView(5);
                break;

            default:
                break;
        }
    }

    private void sortProdukBy(int sortby, int index) {
        switch (index) {
            case 0: {
                Produk1PuskopFragment.loadDataProduk();
                break;
            }
            case 1: {
                Produk2PrimkopFragment.loadDataProduk();
                break;
            }
            case 2: {
                Produk3UmkmFragment.loadDataProduk(sortby);
                break;
            }
            case 3: {

                break;
            }
        }
    }

    public void openDetailOrder(order data) {
        Intent intent = new Intent(context, DetailPesananActivity.class);
        intent.putExtra("order", data);
        startActivityForResult(intent, RESULT_FROM_DETAIL_ORDER);
    }

    public void prosesPembayaran(String trxId) {
        new danaCreateOrder(trxId).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            if (menu_selected > 1) {
                displayView(menu_selected==11?2:0);
                return false;
            } else {
                finish();
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public void displayView(int position) {
        drawer.closeDrawer(GravityCompat.START);

        menu_selected = position;
        Fragment fragment = null;

        switch (position) {
            //inisial
            case 0: {
                fragment = new InitialFragment();
                break;
            }
            //produk
            case 1: {
                fragment = new Produk0Fragment();
                break;
            }
            //kerangjang
            case 2: {
                fragment = new KeranjangFragment();

                break;
            }
            //histori pesanan
            case 3: {
                fragment = new DaftarPesanan0Fragment();
                break;
            }
            default: {
                break;
            }
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().addToBackStack(null).replace(R.id.frame_container, fragment).commit();
        }
    }

    private void closeHandler() {
        try {

            try {
                unregisterReceiver(mHandleLoadMoreMenuReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                unregisterReceiver(mHandleLoadDashbooardReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                unregisterReceiver(mHandleLoadListKeranjangReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                unregisterReceiver(mHandleUpdateListKeranjangReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                unregisterReceiver(mHandleLoadListOrderReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                unregisterReceiver(mHandleRemoveFromCartReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                unregisterReceiver(mHandleUpdateTransaksiReceiver);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        closeHandler();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        closeHandler();
        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadMoreMenuReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_MORE_MENU"));
        registerReceiver(mHandleLoadDashbooardReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_DASHBOARD"));
        registerReceiver(mHandleLoadListKeranjangReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_CART"));
        registerReceiver(mHandleUpdateListKeranjangReceiver, new IntentFilter("gomocart.application.com.gomocart.UPDATE_DATA_CART"));
        registerReceiver(mHandleLoadListOrderReceiver, new IntentFilter("gomocart.application.com.gomocart.LOAD_DATA_ORDER"));
        registerReceiver(mHandleRemoveFromCartReceiver, new IntentFilter("gomocart.application.com.gomocart.REMOVE_FROM_CART"));
        registerReceiver(mHandleUpdateTransaksiReceiver, new IntentFilter("gomocart.application.com.gomocart.UPDATE_STATUS_TRANSAKSI"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadMoreMenuReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            moremenuAdapter.UpdateMoreMenuAdapter(moremenulist, submoremenulist);
        }
    };

    private final BroadcastReceiver mHandleLoadDashbooardReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context cntx, Intent intent) {
            if (menu_selected == 0) {
                Boolean success = intent.getBooleanExtra("success", false);
                dialog_loading.dismiss();

                if(success) {
                    menu_selected = 1;
                    setSignIn();
                } else {
                    InitialFragment.retry.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    private final BroadcastReceiver mHandleLoadListKeranjangReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (menu_selected == 2) {
                Boolean success = intent.getBooleanExtra("success", false);
                dialog_loading.dismiss();
                if (!success) {
                    KeranjangFragment.retry.setVisibility(View.VISIBLE);
                } else {
                    updateTotalCartlist();
                    cartlistAdapter = new CartlistAdapter(context, cartlist, dh, marketSelected.getKode_trx(), marketSelected.getId());
                    KeranjangFragment.listview.setAdapter(cartlistAdapter);
                    int jumlah =0; double total =0;
                    for(produk data: cartlist) {
                        jumlah+=data.getQty();
                        total+=data.getGrandtotal();
                    }

                    KeranjangFragment.edit_qty.setText(CommonUtilities.getNumberFormat(jumlah));
                    KeranjangFragment.edit_jumlah.setText(CommonUtilities.getCurrencyFormat(total, " Rp. "));
                }

            }
        }
    };

    private final BroadcastReceiver mHandleUpdateListKeranjangReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (menu_selected == 2) {
                Boolean success = intent.getBooleanExtra("success", false);
                String message = intent.getStringExtra("message");
                dialog_loading.dismiss();

                if (!success) {
                    openDialogMessage(message, false);
                } else {

                    updateTotalCartlist();
                    cartlistAdapter = new CartlistAdapter(context, cartlist, dh, marketSelected.getKode_trx(), marketSelected.getId());
                    KeranjangFragment.listview.setAdapter(cartlistAdapter);
                    int jumlah =0; double total =0;
                    for(produk data: cartlist) {
                        jumlah+=data.getQty();
                        total+=data.getGrandtotal();
                    }

                    KeranjangFragment.edit_qty.setText(CommonUtilities.getNumberFormat(jumlah));
                    KeranjangFragment.edit_jumlah.setText(CommonUtilities.getCurrencyFormat(total, " Rp. "));

                    Intent inten = new Intent(context, ProsesCheckoutActivity.class);

                    ArrayList<produk_list> temp_cartlist = new ArrayList<>();
                    temp_cartlist.add(new produk_list(cartlist));

                    market marketSelect = dh.getGroupOrderList("2");
                    if(marketSelect.getId()==marketSelected.getId()) {
                        inten.putExtra("market", marketSelect);
                        inten.putExtra("data_alamat", alamatSelected);
                        inten.putExtra("cartlist", temp_cartlist);

                        startActivityForResult(inten, RESULT_FROM_PROSES_CHECKOUT);
                    }

                }

            }
        }
    };

    private final BroadcastReceiver mHandleLoadListOrderReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("MENU",String.valueOf(menu_selected));
            if(menu_selected==3) {
                Boolean success = intent.getBooleanExtra("success", false);
                int index = intent.getIntExtra("index", 0);

                if (success) {
                    ArrayList<order_list> temp = intent.getParcelableArrayListExtra("order_list");
                    ArrayList<order> result = temp.get(0).getListData();

                    for (order flist : result) {
                        orderlist[index].add(flist);
                    }
                    orderlist_adapter[index].UpdateListOrderAdapter(orderlist[index]);
                }

                switch (index) {
                    case 0: {
                        DaftarPesanan1BelumBayarFragment.loading.setVisibility(View.GONE);
                        DaftarPesanan1BelumBayarFragment.retry.setVisibility(!success?View.VISIBLE:View.GONE);
                        break;
                    }
                    case 1: {
                        DaftarPesanan2SedangProsesFragment.loading.setVisibility(View.GONE);
                        DaftarPesanan2SedangProsesFragment.retry.setVisibility(!success?View.VISIBLE:View.GONE);
                        break;
                    }
                    case 2: {
                        DaftarPesanan3SedangKirimFragment.loading.setVisibility(View.GONE);
                        DaftarPesanan3SedangKirimFragment.retry.setVisibility(!success?View.VISIBLE:View.GONE);
                        break;
                    }
                    case 3: {
                        DaftarPesanan4SelesaiFragment.loading.setVisibility(View.GONE);
                        DaftarPesanan4SelesaiFragment.retry.setVisibility(!success?View.VISIBLE:View.GONE);
                        break;
                    }
                    case 4: {
                        DaftarPesanan5BatalFragment.loading.setVisibility(View.GONE);
                        DaftarPesanan5BatalFragment.retry.setVisibility(!success?View.VISIBLE:View.GONE);
                        break;
                    }
                }
            }
        }
    };

    private final BroadcastReceiver mHandleRemoveFromCartReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Boolean success = intent.getBooleanExtra("success", false);
            String message = intent.getStringExtra("message");

            dialog_loading.dismiss();
            if (success) {

                for(int i=cartlist.size()-1; i>=0; i--) {
                    if(cartlist.get(i).getChecked()) {
                        dh.deleteCartlist(marketSelected.getKode_trx(), marketSelected.getId(), cartlist.get(i));
                        cartlist.remove(cartlist.get(i));
                    }
                }

                cartlistAdapter.UpdateCartlistAdapter();

                updateTotalCartlist();
                updateSummaryCart();
            } else {
                openDialogMessage(message, false);
            }

        }
    };

    private final BroadcastReceiver mHandleUpdateTransaksiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context contx, Intent intent) {

            if(menu_selected==3) {
                dialog_loading.dismiss();

                Boolean success = intent.getBooleanExtra("success", false);
                String message = intent.getStringExtra("message");

                if (success) {
                    int index = DaftarPesanan0Fragment.viewPager.getCurrentItem();
                    if(index>0) {
                        loadOrderlist(index-1, true);
                    }
                    loadOrderlist(index, true);
                    if(index<3) {
                        loadOrderlist(index+1, true);
                    }
                } else {
                    openDialogMessage(message, success);
                }
            }

        }
    };

    public void openDialogMessage(String message, boolean status) {

        CommonUtilities.showSnackbar(message, status, this);
    }

    public void openProdukKategori(kategori kat) {
        displayView(1);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putInt("menu_selected", menu_selected);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        menu_selected = savedInstanceState.getInt("menu_selected");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data_intent);

        String fileName = new SimpleDateFormat("yyyyMMddhhmmss'.jpg'").format(new Date());
        String dest = CommonUtilities.getOutputPath(context, "images") + File.separator + fileName;

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_FROM_PRODUK_DETAIL: {
                    updateTotalCartlist();

                    String detail_go_to = data_intent.getStringExtra("goto");
                    if (detail_go_to != null) {
                        if (detail_go_to.equalsIgnoreCase("cart_list")) {
                            displayView(2);
                            return;
                        }
                    }

                    boolean cekongkir = data_intent.getBooleanExtra("cekongkir", false);
                    if (cekongkir) {
                        displayView(6);
                    }

                    boolean opendetail = data_intent.getBooleanExtra("opendetail", false);
                    if (opendetail) {
                        produk data = (produk) data_intent.getSerializableExtra("produk");
                        int kode_grup = data_intent.getIntExtra("kode_grup", 0);
                        openDetailProduk(data, kode_grup);
                    }
                    break;
                }
                case REQUEST_FROM_FILTER: {
                    break;
                }
                case RESULT_FROM_PROSES_CHECKOUT: {
                    updateTotalCartlist();
                    displayView(3);

                    break;
                }
                case RESULT_FROM_DETAIL_ORDER: {
                    int index = DaftarPesanan0Fragment.viewPager.getCurrentItem();
                    if(index>0) {
                        loadOrderlist(index-1, true);
                    }
                    loadOrderlist(index, true);
                    if(index<3) {
                        loadOrderlist(index+1, true);
                    }

                    break;
                }
            }
        }
    }

    public void updateTotalDaftarPesanan() {
        moremenulist.get(6).setTotalcount(total_daftar_pesanan);
        moremenuAdapter.UpdateMoreMenuAdapter(moremenulist, submoremenulist);
    }

    public void updateTotalMessage() {

        total_message = 0;
        moremenulist.get(4).setTotalcount(total_message);
        moremenuAdapter.UpdateMoreMenuAdapter(moremenulist, submoremenulist);
    }

    public void updateTotalCartlist() {
        total_cart = dh.getTotalCart();
        number_cart.setText(total_cart + "");
        number_cart.setVisibility(total_cart > 0 ? View.VISIBLE : View.INVISIBLE);

        moremenulist.get(1).setTotalcount(total_cart);
        moremenuAdapter.UpdateMoreMenuAdapter(moremenulist, submoremenulist);
    }

    public void updateSummaryCart() {
        double total_qty = 0;
        double jumlah = 0;
        for(produk item: cartlist) {
            total_qty+=item.getQty();
            jumlah+=item.getGrandtotal();
        }
        KeranjangFragment.edit_qty.setText(CommonUtilities.getNumberFormat(total_qty));
        KeranjangFragment.edit_jumlah.setText(CommonUtilities.getCurrencyFormat(jumlah, "Rp. "));
    }

    public void loadDataDashboard() {
        new loadDataDashboard().execute();
    }

    public class loadDataDashboard extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
            InitialFragment.retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {
            //Log.e("DADAN", data.getId()+"");

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseDashboard> postDashboardCall = api.postDashboard(data.getId()+"");
            postDashboardCall.enqueue(new Callback<ResponseDashboard>() {
                @Override
                public void onResponse(@NonNull Call<ResponseDashboard> call, @NonNull Response<ResponseDashboard> response) {

                    //PENGATURAN
                    tampilkan_shortcut       = Objects.requireNonNull(response.body()).getTampilkan_shortcut();
                    tampilkan_kategori       = Objects.requireNonNull(response.body()).getTampilkan_kategori();
                    tampilkan_induk_kategori = Objects.requireNonNull(response.body()).getTampilkan_induk_kategori();
                    tampilkan_shortcut_bawah = Objects.requireNonNull(response.body()).getTampilkan_shortcut_bawah();

                    dashboard_list_banner   = Objects.requireNonNull(response.body()).getBannerlist();
                    dashboard_list_shortcut = Objects.requireNonNull(response.body()).getShortcutlist();
                    dashboard_list_kategori = Objects.requireNonNull(response.body()).getKategorilist();

                    //TAB KATEGORI ATAS
                    dashboard_list_tab_induk_kategori = new ArrayList<>();
                    dashboard_list_tab_induk_kategori.add(new produk_kategori(0, "","HOME", new ArrayList<>(), 0));
                    if (tampilkan_induk_kategori == 1) {
                        for(kategori item: dashboard_list_kategori)
                            dashboard_list_tab_induk_kategori.add(new produk_kategori(item.getId(), item.getHeader(), item.getNama(), new ArrayList<>(), 0));
                    }

                    //CEK ENTITY USER SEBAGAI PUSKOP -> JIKA USER MERUPAKAN ENTITY PUSKOP MAKA PRIMKOP TIDAK ADA
                    dashboard_list_tab_kategori =  Objects.requireNonNull(response.body()).getProduk_kategorilist();

                    //data = Objects.requireNonNull(response.body()).getData_user();
                    //Log.i("USER", data.getId()+ " " + data.getFirst_name());
                    //CommonUtilities.setSettingUser(context, data);

                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_DASHBOARD");
                    i.putExtra("success", true);
                    sendBroadcast(i);

                }

                @Override
                public void onFailure(@NonNull Call<ResponseDashboard> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_DASHBOARD");
                    i.putExtra("success", false);
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    public void hapusCartlist(int index) {
        cartlist.get(index).setChecked(true);

        action = "delete_cartlist_satuan";
        konfirmasi_dialog_title.setText("Hapus Barang?");
        konfirmasi_dialog_text.setText("Barang yang dipilih akan hilang dari keranjang.");
        konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        konfirmasi_dialog.show();
    }
    
    public void hapusCartlistTerpilih() {
        action = "delete_cartlist";
        konfirmasi_dialog_title.setText("Hapus Barang?");
        konfirmasi_dialog_text.setText("Barang yang dipilih akan hilang dari keranjang.");
        konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        konfirmasi_dialog.show();
    }

    public void prosesHapusCartlistTerpilih() {

        String items = "";
        for(int i=cartlist.size()-1; i>=0; i--) {
            if(cartlist.get(i).getChecked()) {
                if(marketSelected.getId()==2) {
                    items+=(items.length()>0?"\n":"")+cartlist.get(i).getId()+"\t"+cartlist.get(i).getUkuran()+"\t"+cartlist.get(i).getWarna()+"\t"+cartlist.get(i).getQty();
                } else {
                    items += (items.length() > 0 ? "," : "") +cartlist.get(i).getSeqId();
                }
            }
        }

        if(items.length()>0) {
            new deleteCartList(items).execute();
        } else {
            openDialogMessage("Tidak ada barang yang terpilih untuk dihapus.", false);
        }

    }

    public class deleteCartList extends AsyncTask<String, Void, Void> {

        String items;

        deleteCartList(String items) {
            this.items = items;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api;
            Call<ResponseHapusBarang> hapusBarangCall;
            if(marketSelected.getId()==2) {
                api = RetroFit.getInstanceRetrofit();
                hapusBarangCall = api.postDeleteCart(marketSelected.getKode_trx(), items);
            } else {
                api = RetroFit.getInstanceRetrofit();
                hapusBarangCall = api.postHapusBarang(data.getKeyUserId(), marketSelected.getId()+"", items);
            }

            hapusBarangCall.enqueue(new Callback<ResponseHapusBarang>() {
                @Override
                public void onResponse(@NonNull Call<ResponseHapusBarang> call, @NonNull Response<ResponseHapusBarang> response) {

                    boolean suceess = Objects.requireNonNull(response.body()).getSuccess();
                    String message  = Objects.requireNonNull(response.body()).getMessage();
                    
                    Intent i = new Intent("gomocart.application.com.gomocart.REMOVE_FROM_CART");
                    i.putExtra("success", suceess);
                    i.putExtra("message", message);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseHapusBarang> call, @NonNull Throwable t) {
                    Intent i = new Intent("gomocart.application.com.gomocart.REMOVE_FROM_CART");
                    i.putExtra("success", false);
                    i.putExtra("message", "Gagal hapus barang. Coba lagi.");
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }
    
    public void pilihSemuaChartlist(boolean isChecked) {
        for (int i = 0; i < cartlist.size(); i++) {
            cartlist.get(i).setChecked(isChecked);
        }
        cartlistAdapter.UpdateCartlistAdapter();
    }

    public void updateCheckAll() {
        boolean unCheckAll = true;
        boolean checkAll = true;
        for (int i = 0; i < cartlist.size(); i++) {
            if (cartlist.get(i).getChecked()) {
                unCheckAll = false;
            } else {
                checkAll = false;
            }
        }

        if(checkAll) {
            KeranjangFragment.chk_pilihsemua.setChecked(true);
        }

        if(unCheckAll) {
            KeranjangFragment.chk_pilihsemua.setChecked(false);
        }

    }

    String getStringCartList() {
        String result = "";

        for(produk item: dh.getCartlist(marketSelected.getKode_trx(), marketSelected.getId())) {
            result+=(result.length()>0?"\n":"")+
                    item.getId() + "\t" +
                    item.getKode() + "\t" +
                    item.getNama() + "\t" +
                    item.getFoto1_produk() + "\t" +
                    item.getUkuran() + "\t" +
                    item.getWarna() + "\t" +
                    item.getQty() + "\t" +
                    item.getBerat() + "\t" +
                    item.getHarga_beli() + "\t" +
                    item.getHarga_jual() + "\t" +
                    item.getHarga_grosir() + "\t" +
                    item.getHarga_diskon() + "\t" +
                    item.getPersen_diskon() + "\t" +
                    item.getSubtotal() + "\t" +
                    item.getGrandtotal() + "\t" +
                    item.getMitra().getId();
        }


        return result;
    }

    public void loadCartlist() {
        marketSelected = dh.getGroupOrderList("2");  //PRODUK UMKM
        new loadCartlist().execute();
    }

    public class loadCartlist extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
            KeranjangFragment.retry.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {

            Call<ResponseKeranjangStore> keranjangListCall = RetroFit.getInstanceRetrofit()
                    .postCartList(data.getId()+"", marketSelected==null?"":marketSelected.getKode_trx());

            keranjangListCall.enqueue(new Callback<ResponseKeranjangStore>() {
                @Override
                public void onResponse(@NonNull Call<ResponseKeranjangStore> call, @NonNull Response<ResponseKeranjangStore> response) {

                    boolean success = Objects.requireNonNull(response.body()).getSuccess();
                    if(success) {
                        alamatSelected = Objects.requireNonNull(response.body()).getAlamatSelected();

                        cartlist = new ArrayList<>();
                        ArrayList<cart> temp_cartlist = Objects.requireNonNull(response.body()).getDataCart();
                        for(produk item: dh.getCartlist(marketSelected.getKode_trx(), marketSelected.getId())) {
                            boolean hapus = true;
                            for(cart temp: temp_cartlist) {
                                if(
                                    item.getId()==temp.getId_produk() &&
                                    item.getUkuran().equals(temp.getUkuran()) &&
                                    item.getWarna().equals(temp.getWarna())
                                )  {
                                    hapus = false;
                                    if(marketSelected.getSimitra().getId()==item.getMitra().getId()) {
                                        item.setSeqId(temp.getId());
                                        cartlist.add(item);
                                    }
                                }
                            }
                            if(hapus) {
                                dh.deleteCartlist(marketSelected.getKode_trx(), marketSelected.getId(), item);
                            }
                        }
                    }

                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_CART");
                    i.putExtra("success", true);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseKeranjangStore> call, @NonNull Throwable t) {
                    t.printStackTrace();

                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_CART");
                    i.putExtra("success", false);
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }


    public class updateCartlist extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
        }

        @Override
        protected Void doInBackground(String... urls) {


            Call<ResponseKeranjangStore> keranjangListCall;
            if(marketSelected.getId()==2) {
                Log.e("KELEBU", data.getId()+"|"+marketSelected.getKode_trx()+"|"+getStringCartList());

                keranjangListCall = RetroFit.getInstanceRetrofit()
                        .postCartUpdate(data.getId()+"", marketSelected.getKode_trx(), getStringCartList());
            } else {

                Log.e("KELEBU",  data.getKeyUserId()+"|"+data.getKeyEntityCd()+"|"+getStringCartList());


                keranjangListCall = RetroFit.getInstanceRetrofit()
                        .postKeranjangUpdate(
                                data.getKeyUserId(),
                                data.getKeyEntityCd(),
                                marketSelected.getKode_trx(),
                                marketSelected.getId()+"",
                                getStringCartList());
            }

            keranjangListCall.enqueue(new Callback<ResponseKeranjangStore>() {
                @Override
                public void onResponse(@NonNull Call<ResponseKeranjangStore> call, @NonNull Response<ResponseKeranjangStore> response) {

                    boolean success = Objects.requireNonNull(response.body()).getSuccess();
                    String message = "";
                    if(success) {
                        alamatSelected = Objects.requireNonNull(response.body()).getAlamatSelected();

                        cartlist = new ArrayList<>();
                        ArrayList<cart> temp_cartlist = Objects.requireNonNull(response.body()).getDataCart();
                        for(produk item: dh.getCartlist(marketSelected.getKode_trx(), marketSelected.getId())) {
                            boolean hapus = true;
                            for(cart temp: temp_cartlist) {
                                if(
                                        item.getId()==temp.getId_produk() &&
                                                item.getUkuran().equals(temp.getUkuran()) &&
                                                item.getWarna().equals(temp.getWarna())
                                )  {
                                    hapus = false;
                                    if(marketSelected.getSimitra().getId()==item.getMitra().getId()) {
                                        item.setSeqId(temp.getId());
                                        cartlist.add(item);
                                    }
                                }
                            }
                            if(hapus) {
                                dh.deleteCartlist(marketSelected.getKode_trx(), marketSelected.getId(), item);
                            }
                        }
                    } else {
                        message =  Objects.requireNonNull(response.body()).getMessage();
                    }

                    Intent i = new Intent("gomocart.application.com.gomocart.UPDATE_DATA_CART");
                    i.putExtra("success", success);
                    i.putExtra("message", message);

                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseKeranjangStore> call, @NonNull Throwable t) {
                    t.printStackTrace();

                    Intent i = new Intent("gomocart.application.com.gomocart.UPDATE_DATA_CART");
                    i.putExtra("success", false);
                    i.putExtra("message", "Tidak ada akses internet.");
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }


    Handler mHandler = new Handler();
    Runnable updateRemainingTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if(menu_selected==12) {
                boolean stop = false;
                for(int i=0; i<KeranjangFragment.listview.getChildCount(); i++) {
                    View layout = KeranjangFragment.listview.getChildAt(i);
                    TextView jam   = layout.findViewById(R.id.jam);
                    TextView menit = layout.findViewById(R.id.menit);
                    TextView detik = layout.findViewById(R.id.detik);
                    TextView expirationTime = layout.findViewById(R.id.expirationTime);

                    long currentTime = System.currentTimeMillis();
                    long expireTime  = Long.parseLong(expirationTime.getText().toString());
                    long timeDiff = expireTime - currentTime;

                    if(timeDiff>0) {
                        int seconds = (int) (timeDiff / 1000) % 60;
                        int minutes = (int) ((timeDiff / (1000 * 60)) % 60);
                        int hours = (int) ((timeDiff / (1000 * 60 * 60)) % 24);
                        jam.setText((hours < 10 ? "0" : "") + hours);
                        menit.setText((minutes < 10 ? "0" : "") + minutes);
                        detik.setText((seconds < 10 ? "0" : "") + seconds);
                    } else {
                        jam.setText("00");
                        menit.setText("00");
                        detik.setText("00");
                        stop = true;
                        loadCartlist();
                        break;
                    }
                }
                if(!stop) {
                    mHandler.postDelayed(this, 1000);
                } else {
                    mHandler.removeCallbacks(this);
                }
            }
        }
    };

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openKeranjang(market data) {
        marketSelected = data;
        displayView(11);
    }
    
    public void openDetailProduk(produk data_produk, int kode_grup) {

        Intent intent = new Intent(context, DetailProdukActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("kode_grup", kode_grup);
        intent.putExtra("produk", data_produk);
        intent.putExtra("damayUserLogin", data);

        startActivityForResult(intent, RESULT_FROM_PRODUK_DETAIL);
    }


    public void openDialogLoading() {
        dialog_loading.setCancelable(false);
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }

    public void openFilterProduk(int index) {
        CommonUtilities.showSnackbar("Maaf, Untuk sementara fitur Filter Belum bisa.", true, this);
        //Intent intent = new Intent(context, FilterProdukActivity.class);
        //startActivityForResult(intent, REQUEST_FROM_FILTER);
    }

    private void setSignIn() {
        bagian_avatar.setVisibility(View.VISIBLE);
        Log.e("TOPHOTO", "https://"+data.getPhoto());
        imageLoader.displayImage("https://"+data.getPhoto(), avatar, imageOptionsUser);

        name_avatar.setText(data.getFirst_name() + " " +data.getLast_name());
        bagian_avatar.setText(data.getPhone());

        displayView(menu_selected);
    }

    private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<>();
        List<String> permissionsList = new ArrayList<>();

        if (!addPermission(permissionsList, Manifest.permission.INTERNET)) {
            permissionsNeeded.add("INTERNET");
        }
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_NETWORK_STATE)) {
            permissionsNeeded.add("ACCESS_NETWORK_STATE");
        }
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
        }
        if (!addPermission(permissionsList, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissionsNeeded.add("READ_EXTERNAL_STORAGE");
        }
        if (!addPermission(permissionsList, Manifest.permission.CAMERA)) {
            permissionsNeeded.add("CAMERA");
        }

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {

                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++) {
                    message = message + ", " + permissionsNeeded.get(i);
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }

                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }

            return;
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permission);
                // Check for Rationale Option
                if (!shouldShowRequestPermissionRationale(permission))
                    return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();

                // Initial
                perms.put(Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }

                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                    displayView(menu_selected);

                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
                }

                break;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void prosesBatalkanPesanan(order data_order) {
        orderSelected = data_order;
        action = "batalkan_pesanan";
        konfirmasi_dialog_title.setText("Batalkan Pesanan?");
        konfirmasi_dialog_text.setText("Pesanan yang dipilih akan dibatalkan.");
        konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        konfirmasi_dialog.show();
    }

    public void prosesTerimaPesanan(order data_order) {
        orderSelected = data_order;
        action = "terima_pesanan";
        konfirmasi_dialog_title.setText("Pesanan diterima?");
        konfirmasi_dialog_text.setText("Barang pesanan sudah diterima.");
        konfirmasi_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        konfirmasi_dialog.show();
    }


    class prosesTerimaPesanan extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
        }

        @Override
        protected Void doInBackground(String... urls) {
            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseUpdateTransaksi> updateTransaksiCall = api.postUpdateTransaksi(data.getId()+"", orderSelected.getId()+"", "5");
            updateTransaksiCall.enqueue(new Callback<ResponseUpdateTransaksi>() {
                @Override
                public void onResponse(@NonNull Call<ResponseUpdateTransaksi> call, @NonNull Response<ResponseUpdateTransaksi> response) {

                    Boolean success = Objects.requireNonNull(response.body()).getSuccess();
                    String message = Objects.requireNonNull(response.body()).getMessage();

                    Intent i = new Intent("gomocart.application.com.gomocart.UPDATE_STATUS_TRANSAKSI");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseUpdateTransaksi> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.UPDATE_STATUS_TRANSAKSI");
                    i.putExtra("success", false);
                    i.putExtra("message", "Proses update transaksi gagal.");
                    sendBroadcast(i);
                }
            });

            return null;
        }


    }

    class prosesBatalkanPesanan extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
        }

        @Override
        protected Void doInBackground(String... urls) {
            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseUpdateTransaksi> updateTransaksiCall = api.postUpdateTransaksi(data.getId()+"", orderSelected.getId()+"", "4");
            updateTransaksiCall.enqueue(new Callback<ResponseUpdateTransaksi>() {
                @Override
                public void onResponse(@NonNull Call<ResponseUpdateTransaksi> call, @NonNull Response<ResponseUpdateTransaksi> response) {

                    Boolean success = Objects.requireNonNull(response.body()).getSuccess();
                    String message = Objects.requireNonNull(response.body()).getMessage();

                    Intent i = new Intent("gomocart.application.com.gomocart.UPDATE_STATUS_TRANSAKSI");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseUpdateTransaksi> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    Intent i = new Intent("gomocart.application.com.gomocart.UPDATE_STATUS_TRANSAKSI");
                    i.putExtra("success", false);
                    i.putExtra("message", "Proses update transaksi gagal.");
                    sendBroadcast(i);
                }
            });

            return null;
        }


    }




    //region FOR UPDATE BOTTOM NAVIGATION VIEW...
    public void updateBottomNavigationView(String viewType) {

        if (viewType.equals("Cart")) {
            ivBottomNavigationProduct.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationProduct.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            ivBottomNavigationProduct.setColorFilter(ContextCompat.getColor(context, R.color.colorLightGray), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationProduct.setTextColor(getResources().getColor(R.color.colorLightGray));
        }

        if (viewType.equals("Category")) {
            ivBottomNavigationProfile.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationProfile.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            ivBottomNavigationProfile.setColorFilter(ContextCompat.getColor(context, R.color.colorLightGray), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationProfile.setTextColor(getResources().getColor(R.color.colorLightGray));
        }

        if (viewType.equals("Setting")) {
            ivBottomNavigationSetting.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationSetting.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            ivBottomNavigationSetting.setColorFilter(ContextCompat.getColor(context, R.color.colorLightGray), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationSetting.setTextColor(getResources().getColor(R.color.colorLightGray));
        }

        if (viewType.equals("MyOrder")) {
            ivBottomNavigationMyOrder.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationMyOrder.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            ivBottomNavigationMyOrder.setColorFilter(ContextCompat.getColor(context, R.color.colorLightGray), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationMyOrder.setTextColor(getResources().getColor(R.color.colorLightGray));
        }

        if (viewType.equals("Home")) {
            ivBottomNavigationHome.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationHome.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            ivBottomNavigationHome.setColorFilter(ContextCompat.getColor(context, R.color.colorLightGray), android.graphics.PorterDuff.Mode.SRC_IN);
            tvBottomNavigationHome.setTextColor(getResources().getColor(R.color.colorLightGray));
        }
    }
    //endregion

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.llShortCutMenu) {
            if (llShortCutMenu.getVisibility() == View.VISIBLE) {
                llShortCutMenu.setVisibility(View.GONE);
            }
        } else if (id == R.id.llMenuHome) {
            if (llShortCutMenu.getVisibility() == View.VISIBLE) {
                llShortCutMenu.setVisibility(View.GONE);
            }

            if(menu_selected==1) return;
            displayView(1);
        } else if (id == R.id.llMenuWishList) {
            if (llShortCutMenu.getVisibility() == View.VISIBLE) {
                llShortCutMenu.setVisibility(View.GONE);
            }

        } else if (id == R.id.llMenuCart) {

            if (llShortCutMenu.getVisibility() == View.VISIBLE) {
                llShortCutMenu.setVisibility(View.GONE);
            }
            if(menu_selected==2) return;
            displayView(2);
        } else if (id == R.id.llMenuMessage) {
            if (llShortCutMenu.getVisibility() == View.VISIBLE) {
                llShortCutMenu.setVisibility(View.GONE);
            }

        } else if (id == R.id.llMenuOrderHistory) {
            if (llShortCutMenu.getVisibility() == View.VISIBLE) {
                llShortCutMenu.setVisibility(View.GONE);
            }
            if(menu_selected==3) return;
            displayView(3);
        } else if (id == R.id.llMenuSetting) {
            if (llShortCutMenu.getVisibility() == View.VISIBLE) {
                llShortCutMenu.setVisibility(View.GONE);
            }
            if(menu_selected==12) return;
            displayView(12);
        } else if (id == R.id.llBottomNavigationHome) {
            updateBottomNavigationView("Home");
            if(menu_selected==1) return;
            displayView(1);
        } else if (id == R.id.llBottomNavigationMyOrder) {
            updateBottomNavigationView("MyOrder");
            if(menu_selected==3) return;
            displayView(3);
        } else if (id == R.id.llBottomNavigationSetting) {
            updateBottomNavigationView("Setting");
            if(menu_selected==12) return;
            displayView(12);
        } else if (id == R.id.llBottomNavigationProfile) {
            updateBottomNavigationView("Category");
            CommonUtilities.showSnackbar("Maaf, Fitur ini belum bisa.", true, this);

        } else if (id == R.id.llBottomNavigationProduct) {
            updateBottomNavigationView("Cart");
            if(menu_selected==2) return;
            displayView(2);
        }
    }

    public void prosesCheckout() {
        if(cartlist.size()==0) {
            CommonUtilities.showSnackbar("Keranjang masih kosong.", false, this);
        } else {
            new updateCartlist().execute();
        }
    }

    AlertDialog alert12;
    public void openDialogSortBy(int index) {

        RadioButton radioTerbaru, radioRating, radioTermurah, radioTermahal;
        LinearLayout linearTerbaru, linearRating, linearTermurah, linearTermahal;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.sortby_produk_dialog, null);

        builder.setView(v);

        //radioTerbaru = v.findViewById(R.id.radioTerbaru);
        //linearTerbaru = v.findViewById(R.id.linearTerbaru);
        //radioRating = v.findViewById(R.id.radioRatting);
        //linearRating = v.findViewById(R.id.linearRating);

        radioTermurah = v.findViewById(R.id.radioTermurah);
        linearTermurah = v.findViewById(R.id.linearTermurah);
        radioTermahal = v.findViewById(R.id.radioTermahal);
        linearTermahal = v.findViewById(R.id.linearTermahal);

        /*linearTerbaru.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                //dialog_sort_by.dismiss();
                if (alert12 != null && alert12.isShowing()) {
                    alert12.dismiss();
                }
                sortProdukBy(1, index);
                radioTerbaru.setChecked(true);
                radioRating.setChecked(false);
                radioTermurah.setChecked(false);
                radioTermahal.setChecked(false);
            }
        });

        linearRating.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                //dialog_sort_by.dismiss();
                if (alert12 != null && alert12.isShowing()) {
                    alert12.dismiss();
                }
                sortProdukBy(2, index);
                radioTerbaru.setChecked(false);
                radioRating.setChecked(true);
                radioTermurah.setChecked(false);
                radioTermahal.setChecked(false);
            }
        });*/

        linearTermurah.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                //dialog_sort_by.dismiss();
                if (alert12 != null && alert12.isShowing()) {
                    alert12.dismiss();
                }
                sortProdukBy(3, index);
                /*radioTerbaru.setChecked(false);
                radioRating.setChecked(false);*/

                radioTermurah.setChecked(true);
                radioTermahal.setChecked(false);
            }
        });


        linearTermahal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                //dialog_sort_by.dismiss();
                if (alert12 != null && alert12.isShowing()) {
                    alert12.dismiss();
                }
                sortProdukBy(4, index);
                /*radioTerbaru.setChecked(false);
                radioRating.setChecked(false);
                radioTermurah.setChecked(false);
                radioTermahal.setChecked(true);*/
            }
        });

        alert12 = builder.create();
        alert12.show();
    }

    public void loadOrderlist(int index, boolean is_first) {
        if(is_first) {
            orderlist_page[index] = 1;
            orderlist[index] = new ArrayList<>();
            orderlist_adapter[index] = new ListOrderAdapter(context, orderlist[index], dh);
            switch (index) {
                case 0:
                    DaftarPesanan1BelumBayarFragment.listViewOrder.setAdapter(orderlist_adapter[index]);
                    break;
                case 1:
                    DaftarPesanan2SedangProsesFragment.listViewOrder.setAdapter(orderlist_adapter[index]);
                    break;
                case 2:
                    DaftarPesanan3SedangKirimFragment.listViewOrder.setAdapter(orderlist_adapter[index]);
                    break;
                case 3:
                    DaftarPesanan4SelesaiFragment.listViewOrder.setAdapter(orderlist_adapter[index]);
                    break;
                case 4:
                    DaftarPesanan5BatalFragment.listViewOrder.setAdapter(orderlist_adapter[index]);
                    break;
            }
        }
        new loadOrderlist(index).execute();
    }

    public class loadOrderlist extends AsyncTask<String, Void, Void> {

        int index = 0;
        loadOrderlist(int index) {
            this.index = index;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            switch (index) {
                case 0: {
                    DaftarPesanan1BelumBayarFragment.loading.setVisibility(View.VISIBLE);
                    DaftarPesanan1BelumBayarFragment.retry.setVisibility(View.GONE);
                    break;
                }
                case 1: {
                    DaftarPesanan2SedangProsesFragment.loading.setVisibility(View.VISIBLE);
                    DaftarPesanan2SedangProsesFragment.retry.setVisibility(View.GONE);
                    break;
                }
                case 2: {
                    DaftarPesanan3SedangKirimFragment.loading.setVisibility(View.VISIBLE);
                    DaftarPesanan3SedangKirimFragment.retry.setVisibility(View.GONE);
                    break;
                }
                case 3: {
                    DaftarPesanan4SelesaiFragment.loading.setVisibility(View.VISIBLE);
                    DaftarPesanan4SelesaiFragment.retry.setVisibility(View.GONE);
                    break;
                }
                case 4: {
                    DaftarPesanan5BatalFragment.loading.setVisibility(View.VISIBLE);
                    DaftarPesanan5BatalFragment.retry.setVisibility(View.GONE);
                    break;
                }
            }
        }

        @Override
        protected Void doInBackground(String... urls) {

            Log.e("IDORDER",
                    data.getId()+"\n"+
                    orderlist_page[index]+"\n"+
                    index+"\n"+
                    data.getTipe()+"\n"

                    );
            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseOrder> orderListCall = api.postOrderList(
                    data.getId()+"",
                    orderlist_page[index]+"",
                    index+"",
                    data.getTipe()+""
            );
            orderListCall.enqueue(new Callback<ResponseOrder>() {
                @Override
                public void onResponse(@NonNull Call<ResponseOrder> call, @NonNull Response<ResponseOrder> response) {

                    orderlist_page[index] = Objects.requireNonNull(response.body()).getNextpage();
                    ArrayList<order> result = Objects.requireNonNull(response.body()).getOrder();

                    boolean success = result!=null;
                    if(result==null) result = new ArrayList<>();
                    ArrayList<order_list> temp = new ArrayList<>();
                    temp.add(new order_list(result));

                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ORDER");
                    i.putExtra("index", index);
                    i.putExtra("order_list", temp);
                    i.putExtra("success", success);
                    i.putExtra("status", 0);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseOrder> call, @NonNull Throwable t) {

                    ArrayList<order>  result = new ArrayList<>();
                    ArrayList<order_list> temp = new ArrayList<>();
                    temp.add(new order_list(result));

                    Intent i = new Intent("gomocart.application.com.gomocart.LOAD_DATA_ORDER");
                    i.putExtra("index", index);
                    i.putExtra("order_list", temp);
                    i.putExtra("success", false);
                    i.putExtra("status", 0);
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    public class danaCreateOrder extends AsyncTask<String, Void, Void> {

        String trxId;
        danaCreateOrder(String id) {
            this.trxId=id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseDanaCreateOrder> danaCreateOrderCall = api.postDanaCreateOrder(
                    trxId
            );
            danaCreateOrderCall.enqueue(new Callback<ResponseDanaCreateOrder>() {
                @Override
                public void onResponse(@NonNull Call<ResponseDanaCreateOrder> call, @NonNull Response<ResponseDanaCreateOrder> response) {

                    boolean success      = Objects.requireNonNull(response.body()).getSuccess();
                    String message       = Objects.requireNonNull(response.body()).getMessage();
                    String acquirementId = Objects.requireNonNull(response.body()).getAcquirementId();
                    String checkoutUrl   = Objects.requireNonNull(response.body()).getCheckoutUrl();

                    Intent i = new Intent("gomocart.application.com.gomocart.DANA_CREATE_ORDER");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    i.putExtra("acquirementId", acquirementId);
                    i.putExtra("checkoutUrl", checkoutUrl);
                    sendBroadcast(i);
                }

                @Override
                public void onFailure(@NonNull Call<ResponseDanaCreateOrder> call, @NonNull Throwable t) {

                    Intent i = new Intent("gomocart.application.com.gomocart.DANA_CREATE_ORDER");
                    i.putExtra("success", false);
                    i.putExtra("message", "Tidak bisa terhubung dengan server.");
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }
}