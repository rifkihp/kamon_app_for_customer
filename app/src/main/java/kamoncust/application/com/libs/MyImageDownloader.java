package kamoncust.application.com.libs;

import android.content.Context;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import java.io.IOException;
import java.net.HttpURLConnection;

public class MyImageDownloader extends BaseImageDownloader {

    public MyImageDownloader(Context context) {
        super(context);
    }

    @Override
    protected HttpURLConnection createConnection(String url, Object extra) throws IOException {
        HttpURLConnection conn = super.createConnection(url, extra);
        //Log.i("MASUK_PAK_EKO", header.getKey()+"  == "+header.getValue());
        //conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        conn.setRequestProperty("Content-Length", "0");

        return conn;
    }
}