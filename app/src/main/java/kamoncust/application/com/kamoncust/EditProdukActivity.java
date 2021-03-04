package kamoncust.application.com.kamoncust;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import android.widget.TextView;
import im.delight.android.webview.AdvancedWebView;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.model.user;

public class EditProdukActivity extends AppCompatActivity  implements AdvancedWebView.Listener{

    Context context;
    user userlogin;

    TextView title;
    ImageView back;

    AdvancedWebView webview;
    ProgressBar loading;
    LinearLayout retry;
    Button btnReload;

    Dialog dialog_loading;
    String produk_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_produk);

        context   = EditProdukActivity.this;
        userlogin = CommonUtilities.getSettingUser(context);

        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);

        back      = findViewById(R.id.back);
        title     = findViewById(R.id.title);
        webview   = findViewById(R.id.webview);
        loading   = findViewById(R.id.pgbarLoading);
        retry     = findViewById(R.id.loadMask);
        btnReload = findViewById(R.id.btnReload);

        back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                loadDetail();
            }
        });

        webview.setListener(this, this);
        if(savedInstanceState==null) {
            produk_id = getIntent().getStringExtra("id");
        }
        loadDetail();

    }

    private void loadDetail() {
        loading.setVisibility(View.VISIBLE);
        retry.setVisibility(View.GONE);

        webview.addJavascriptInterface(new IJavascriptHandler(), "cpjs");
        if(produk_id==null) {
            webview.loadUrl(CommonUtilities.SERVER_URL + "/adminweb/product/androidadd.php?id="+userlogin.getId());
        } else {
            title.setText("EDIT PRODUK SAYA");
            webview.loadUrl(CommonUtilities.SERVER_URL + "/adminweb/product/androidedit.php?id="+produk_id);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data_intent) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data_intent);
        webview.onActivityResult(requestCode, resultCode, data_intent);
    }

    @Override
    public void onBackPressed() {
        if (!webview.onBackPressed()) { return; }
        // ...
        super.onBackPressed();
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) { }

    @Override
    public void onPageFinished(String url) {
        loading.setVisibility(View.GONE);

    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {

        loading.setVisibility(View.GONE);
        retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType, long contentLength, String contentDisposition, String userAgent) { }

    @Override
    public void onExternalPageRequest(String url) { }

    final class IJavascriptHandler {
        IJavascriptHandler() {

        }

        @JavascriptInterface
        public void showProgressDialog(final boolean status) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if(status) {
                        openDialogLoading();
                    } else {
                        dialog_loading.dismiss();
                    }
                }
            });
        }

        @JavascriptInterface
        public void showMessageDialog(final String message, final boolean status) {
            runOnUiThread(new Runnable() {
                public void run() {
                    if(status) {
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        showInformationDialog(message, status);
                    }
                }
            });
        }
    }

    public void openDialogLoading() {
        dialog_loading.setCancelable(false);
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }

    public void showInformationDialog(String message, final boolean status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(status?"BERHASIL":"GAGAL");
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        if(status) {
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}