package kamoncust.application.com.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

import kamoncust.application.com.adapter.AlamatAdapter;
import kamoncust.application.com.kamoncust.MainActivity;
import kamoncust.application.com.kamoncust.R;
import kamoncust.application.com.libs.CommonUtilities;

public class PageFragment extends Fragment {

	ImageView back;
	TextView title;
	SwipeRefreshLayout swipeRefreshLayout;
	WebView webView;
	ProgressBar loading;
	LinearLayout retry;
	Button btnReload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View rootView = inflater.inflate(R.layout.fragment_page, container, false);

		back               = rootView.findViewById(R.id.back);
		title              = rootView.findViewById(R.id.title);
		swipeRefreshLayout = rootView.findViewById(R.id.swipe_container);
		webView            = rootView.findViewById(R.id.webview);
		loading            = rootView.findViewById(R.id.pgbarLoading);
		retry              = rootView.findViewById(R.id.loadMask);
		btnReload          = rootView.findViewById(R.id.btnReload);

		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				((MainActivity) getActivity()).displayView(0);
			}
		});

		webView.setVerticalScrollBarEnabled(false);
		webView.setWebChromeClient(new MyWebViewClient());
		webView.getSettings().setDomStorageEnabled(true);
		CookieManager cookieManager = CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);

		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		btnReload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				loadDataPage();
			}
		});

		swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				swipeRefreshLayout.setRefreshing(false);
				loadDataPage();
			}
		});



		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//retry.setVisibility(View.GONE);
		//loadDataPage();
	}

	private class MyWebViewClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				loading.setVisibility(View.GONE);
			}
			super.onProgressChanged(view, newProgress);
		}
	}

	public void loadDataPage() {
		loading.setVisibility(View.VISIBLE);
		String url = CommonUtilities.SERVER_URL+"/page/?id="+ MainActivity.page_id;
		webView.loadUrl(url);

	}

}
