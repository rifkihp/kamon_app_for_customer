package kamoncust.application.com.kamoncust;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.Objects;

import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import kamoncust.application.com.data.RestApi;
import kamoncust.application.com.data.RetroFit;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.DatabaseHandler;
import kamoncust.application.com.model.ResponseSplash;
import kamoncust.application.com.model.user;
import kamoncust.application.com.model.version;

import static kamoncust.application.com.libs.CommonUtilities.getOptionsImage;
import static kamoncust.application.com.libs.CommonUtilities.initImageLoader;


public class SplashActivity extends Activity {

    Dialog dialog_update;
    TextView text_dialog;
    TextView btn_ok;

    // Splash screen timer
    static int SPLASH_TIME_OUT = 3000;
    static int TIME_TO_CHECKED = 100;
    Context context;
    DatabaseHandler dh;

    FrameLayout imagebg;
    ImageView image_bg;
    ImageView imagelogo;

    ImageLoader imageLoader;
    DisplayImageOptions imageOptionLogo;
    DisplayImageOptions imageOptionBackground;

    Handler mHandler;
    boolean is_ready_logo, is_ready_bg;

    String logo = "default_logo.png";
    String bg = "default_bg.jpg";
    String app_ver_no = "";
    String app_ver_name = "";
    String app_desc = "";
    int landing_page = 0;
    version app_ver;

    user user_login;
    
    public Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            mHandler.removeCallbacks(this);
            if(is_ready_bg && is_ready_logo) {
                imagebg.setVisibility(View.VISIBLE);
                imagelogo.setVisibility(View.VISIBLE);

                setAnimation();

                //******change activity here*******
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        CommonUtilities.setLandingPage(context, landing_page);
                        Intent i = new Intent(SplashActivity.this, landing_page==1?(user_login.getId()>0?MainActivity.class:LoginActivity.class):MainActivity.class);
                        if(landing_page==1) {
                            i.putExtra("landing_page", landing_page);
                        }
                        startActivity(i);
                        finish();
                    }
                }, SPLASH_TIME_OUT);

            } else {
                mHandler.postDelayed(this, TIME_TO_CHECKED);
            }

        }
    };

    @Override
    protected void onDestroy() {
        try {
            unregisterReceiver(mHandleLoadSplashReceiver);
            mHandler.removeCallbacks(mUpdateTimeTask);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleLoadSplashReceiver);
            mHandler.removeCallbacks(mUpdateTimeTask);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleLoadSplashReceiver, new IntentFilter("kamoncust.application.com.kamoncust.LOAD_SPLASH_SCREEN"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleLoadSplashReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (app_ver_no.equalsIgnoreCase(app_ver.getNo()) && app_ver_name.equalsIgnoreCase(app_ver.getNama())) {

                //background
                imageLoader.loadImage(CommonUtilities.SERVER_URL + "/uploads/umum/" + bg, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        image_bg.setImageBitmap(loadedImage);
                        is_ready_bg = true;
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });

                //logo
                imageLoader.loadImage(CommonUtilities.SERVER_URL + "/uploads/umum/" + logo, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        imagelogo.setImageBitmap(loadedImage);
                        is_ready_logo = true;
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            } else {
                text_dialog.setText(app_desc);
                dialog_update.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_update.show();
            }
        }
    };

    public class loadSplashScreen extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imagebg.setVisibility(View.GONE);
            imagelogo.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(String... urls) {

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseSplash> splashCall = api.postSplash(user_login.getId()+"");
            splashCall.enqueue(new Callback<ResponseSplash>() {
                @Override
                public void onResponse(@NonNull Call<ResponseSplash> call, @NonNull Response<ResponseSplash> response) {

                    try {

                        bg           = Objects.requireNonNull(response.body()).getBg();
                        logo         = Objects.requireNonNull(response.body()).getLogo();
                        app_ver_no   = Objects.requireNonNull(response.body()).getApp_ver_no();
                        app_ver_name = Objects.requireNonNull(response.body()).getApp_ver_name();
                        app_desc     = Objects.requireNonNull(response.body()).getApp_desc();
                        landing_page = Objects.requireNonNull(response.body()).getLanding_page();

                        Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_SPLASH_SCREEN");
                        sendBroadcast(i);

                    } catch (Exception e) {
                        Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_SPLASH_SCREEN");
                        sendBroadcast(i);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseSplash> call, @NonNull Throwable t) {
                    Intent i = new Intent("kamoncust.application.com.kamoncust.LOAD_SPLASH_SCREEN");
                    sendBroadcast(i);
                }
            });

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        context = SplashActivity.this;
        user_login = CommonUtilities.getSettingUser(context);

        app_ver      = CommonUtilities.getAppVersion(context);
        app_ver_no   = app_ver.getNo();
        app_ver_name = app_ver.getNama();

        initImageLoader(context);
        imageLoader           = ImageLoader.getInstance();
        imageOptionLogo       = getOptionsImage(R.drawable.blankicon, R.drawable.blankicon);
        imageOptionBackground = getOptionsImage(R.drawable.blankicon, R.drawable.blankicon);

        imagebg      = findViewById(R.id.imagebg);
        image_bg     = findViewById(R.id.image_bg);
        imagelogo    = findViewById(R.id.imagelogo);

        dialog_update = new Dialog(context);
        dialog_update.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_update.setCancelable(false);
        dialog_update.setContentView(R.layout.update_dialog);

        btn_ok = dialog_update.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                //Toast.makeText(context, appPackageName, Toast.LENGTH_SHORT).show();
                dialog_update.dismiss();
                finish();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        text_dialog = dialog_update.findViewById(R.id.text_dialog);

        is_ready_logo=false;
        is_ready_bg=false;

        mHandler  = new Handler();
        mHandler.postDelayed(mUpdateTimeTask, TIME_TO_CHECKED);

        dh = new DatabaseHandler(context);
        dh.createTable();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        new loadSplashScreen().execute();
    }


    private void setAnimation() {

        //start logo animation
        findViewById(R.id.imagelogo).setAlpha(1.0F);
        Animation anim_logo = AnimationUtils.loadAnimation(this, R.anim.fade);
        findViewById(R.id.imagelogo).startAnimation(anim_logo);
    }
}