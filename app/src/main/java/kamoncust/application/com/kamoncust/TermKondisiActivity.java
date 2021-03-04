package kamoncust.application.com.kamoncust;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import kamoncust.application.com.libs.CommonUtilities;

public class TermKondisiActivity extends AppCompatActivity {

    Context context;

    ImageView back;
    TextView title;

    WebView webView;
    ProgressBar loading;
    LinearLayout retry;
    Button btnReload;

    int page_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.termkondisi);

        context = TermKondisiActivity.this;

        webView = (WebView) findViewById(R.id.webview_termkondisi);
        loading = (ProgressBar) findViewById(R.id.pgbarLoading);
        retry = (LinearLayout) findViewById(R.id.loadMask);
        btnReload = (Button) findViewById(R.id.btnReload);
        back = (ImageView) findViewById(R.id.back);
        title = findViewById(R.id.title);

        btnReload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                loadDetail();
            }
        });

        webView.setVerticalScrollBarEnabled(false);
        webView.setWebChromeClient(new MyWebViewClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });

        if(savedInstanceState==null) {
            page_id = getIntent().getIntExtra("page_id", 0);
        }

        switch (page_id) {
            case 1: {
                title.setText("TENTANG KAMI");
                break;
            }
            case 2: {
                title.setText("CARA PEMESANAN");
                break;
            }
            case 3: {
                title.setText("SYARAT & KETENTUAN");
                break;
            }
            case 5: {
                title.setText("HUBUNGI KAMI");
                break;
            }
        }

        loadDetail();
    }

    private class MyWebViewClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(newProgress==100) {
                loading.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            setResult(RESULT_OK, new Intent());
            finish();

            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void loadDetail() {
        loading.setVisibility(View.VISIBLE);
        retry.setVisibility(View.GONE);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie(); //remove

        String url = CommonUtilities.SERVER_URL+"/page/?id="+page_id;
        webView.loadUrl(url);
    }
}