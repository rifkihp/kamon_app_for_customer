package gomocart.application.com.libs;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;

import com.nostra13.universalimageloader.cache.memory.impl.FIFOLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import gomocart.application.com.gomocart.BuildConfig;
import gomocart.application.com.gomocart.R;
import gomocart.application.com.model.perpesanan;
import gomocart.application.com.model.remember;
import gomocart.application.com.model.setting;
import gomocart.application.com.model.user;
import gomocart.application.com.model.version;

import com.andrognito.flashbar.Flashbar;
import org.jetbrains.annotations.NotNull;

public final class CommonUtilities {
    public static long size;

    public static final String TAG = "TOKO KAMON";
    public static final String SERVER_URL = "https://kamon.id/tokokamon";

    public static void showSnackbar(String msg, boolean isSuccess, Activity activity){

        Flashbar.Builder flashbar = new Flashbar.Builder(activity);
        flashbar.gravity(Flashbar.Gravity.BOTTOM)
                .message(msg)
                .primaryActionText("OK")
                .primaryActionTextColor(R.color.black)
                .dismissOnTapOutside()
                .duration(5000)
                .primaryActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar flashbar) {
                        flashbar.dismiss();
                    }
                });

        if (isSuccess){
            flashbar.backgroundColorRes(R.color.light_green);
        }else {
            flashbar.backgroundColorRes(R.color.red);
        }

        flashbar.build();
        flashbar.show();

    }

    public static String toTitleCase(String str) {

        if (str == null) {
            return null;
        }

        boolean space = true;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (space) {
                if (!Character.isWhitespace(c)) {
                    // Convert to title case and switch out of whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    space = false;
                }
            } else if (Character.isWhitespace(c)) {
                space = true;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }

        return builder.toString();
    }

    public static void setLanguageType(Context context, String language_type) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("language_type", language_type);
        editor.commit();

        String data1 = language_type.toString();
        byte[] byteArray = new byte[0];
        try {
            byteArray = data1.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        size += byteArray.length;
    }

    public static String getLanguageType(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("language_type", "en");
    }

    public static void setTokenApp(Context context, String token_app) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("token_app", token_app);
        editor.commit();

        String data1 = String.valueOf(token_app);
        byte[] byteArray = new byte[0];
        try {
            byteArray = data1.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        size += byteArray.length;
    }

    public static String getTokenApp(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("token_app", "");
    }

    public static String getOutputPath(Context context, String dest) {
        File path = new File(Environment.getExternalStorageDirectory() + File.separator + context.getResources().getString(R.string.app_name), dest);
        if (!path.exists()) path.mkdirs();

        return path.getPath();
    }

    /*public static void setKodeTransaksi(Context context, String kode_trx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();

        editor.putString("checkout_kode_trx", kode_trx);
        editor.commit();
    }

    public static String getKodeTransaksi(Context context) {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
        return data.getString("checkout_kode_trx", "");
    }*/

    public static void setRememberPassword(Context context, remember data) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();

        editor.putString("email_remember", data.getEmail());
        editor.putString("password_remember", data.getPassword());
        editor.putString("checklist_remember", data.getChecklist());
        editor.commit();


        String data1 = data.toString();
        byte[] byteArray = new byte[0];
        try {
            byteArray = data1.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        size += byteArray.length;
        Log.e("size", String.valueOf(size));
    }

    public static remember getRememberPassword(Context context) {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
        return new remember(
                data.getString("email_remember", ""),
                data.getString("password_remember", ""),
                data.getString("checklist_remember", "")
        );
    }

    public static void setSettingAplikasi(Context context, setting data) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();

        editor.putString(context.getResources().getString(R.string.setting_notification), data.getSet_notifikasi());
        editor.putBoolean("setting_update_pesanan", data.getUpdate_pesanan());
        editor.putBoolean("setting_notifikasi", data.getNotifikasi());
        editor.putBoolean("setting_informasi", data.getInformasi());
        editor.putBoolean("setting_chat", data.getChat());

        editor.commit();

        String data1 = data.toString();
        byte[] byteArray = new byte[0];
        try {
            byteArray = data1.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        size += byteArray.length;
    }

    public static setting getSettingApplikasi(Context context) {
        SharedPreferences data = PreferenceManager.getDefaultSharedPreferences(context);
        return new setting(
                data.getString(context.getResources().getString(R.string.setting_notification), context.getResources().getString(R.string.ring_vibrate)),
                data.getBoolean("setting_update_pesanan", true),
                data.getBoolean("setting_informasi", true),
                data.getBoolean("setting_notifikasi", true),
                data.getBoolean("setting_chat", true)
        );
    }

    public static void setCurrentLaporan(Context context, perpesanan data) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putInt("laporan_id", data.getId());
        editor.putInt("laporan_id_produk", data.getId_produk());
        editor.putString("laporan_kode", data.getKode());
        editor.putString("laporan_nama", data.getNama());
        editor.putString("laporan_gambar", data.getGambar());
        editor.putString("laporan_tanggal", data.getTanggal());
        editor.putString("laporan_pesan", data.getPesan());
        editor.putInt("laporan_from_id", data.getFrom_id());
        editor.putString("laporan_from_nama", data.getFrom_nama());
        editor.putString("laporan_from_photo", data.getFrom_photo());
        editor.putInt("laporan_total_unread", data.getTotal_unread());

        editor.commit();


        String data1 = data.toString();
        byte[] byteArray = new byte[0];
        try {
            byteArray = data1.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        size += byteArray.length;
    }

    public static perpesanan getCurrentLaporan(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return new perpesanan(
                prefs.getInt("laporan_id", 0),
                prefs.getInt("laporan_id_produk", 0),
                prefs.getString("laporan_kode", ""),
                prefs.getString("laporan_nama", ""),
                prefs.getString("laporan_gambar", ""),
                prefs.getString("laporan_tanggal", ""),
                prefs.getString("laporan_pesan", ""),
                prefs.getInt("laporan_from_id", 0),
                prefs.getString("laporan_from_nama", ""),
                prefs.getString("laporan_from_photo", ""),
                prefs.getInt("laporan_total_unread", 0)
        );
    }

    public static void setSettingUser(Context context, user data) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();

        editor.putInt("gomo_id", data.getId());
        editor.putString("gomo_username", data.getUsername());
        editor.putString("gomo_first_nama", data.getFirst_name());
        editor.putString("gomo_last_nama", data.getLast_name());
        editor.putString("gomo_email", data.getEmail());
        editor.putString("gomo_phone", data.getPhone());
        editor.putString("gomo_dropship_name", data.getDropship_name());
        editor.putString("gomo_dropship_phone", data.getDropship_phone());
        editor.putString("gomo_jenis_user", data.getJenis_user());
        editor.putString("gomo_photo", data.getPhoto());
        editor.putString("gomo_saldo", data.getSaldo()+"");
        editor.putInt("gomo_tipe", data.getTipe());
        editor.putString("gomo_keyUserId", data.getKeyUserId());
        editor.putString("gomo_keyEntityCd", data.getKeyEntityCd());
        editor.commit();
    }

    public static user getSettingUser(Context context) {
        SharedPreferences data_user = PreferenceManager.getDefaultSharedPreferences(context);
        return new user(
                data_user.getInt("gomo_id", 0),
                data_user.getString("gomo_username", ""),
                data_user.getString("gomo_first_nama", "Welcome"),
                data_user.getString("gomo_last_nama", "Guest"),
                data_user.getString("gomo_email", ""),
                data_user.getString("gomo_phone", ""),
                data_user.getString("gomo_dropship_name", ""),
                data_user.getString("gomo_dropship_phone", ""),
                data_user.getString("gomo_jenis_user", ""),
                data_user.getString("gomo_photo", ""),
                Double.parseDouble(data_user.getString("gomo_saldo", "0")),
                data_user.getInt("gomo_tipe", 0),
                data_user.getString("gomo_keyUserId", ""),
                data_user.getString("gomo_keyEntityCd", "")
        );
    }

    public static String getCache(Context context) {
        final DecimalFormat format = new DecimalFormat("#.##");
        final long MiB = 1024 * 1024;
        final long KiB = 1024;
        long size = 0;
        File[] files = context.getCacheDir().listFiles();
        for (File f : files) {
            size = size + f.length();
        }
        if (size > MiB) {
            return format.format(size / MiB) + " MB";
        }
        if (size > KiB) {
            return format.format(size / KiB) + " KB";
        }
        return format.format(size) + " B";
    }

    public static void deleteCache(Context context) {
        FileUtils.deleteQuietly(context.getCacheDir());

        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        /*File f = new File(context.getCacheDir());*/
        Uri contentUri = Uri.fromFile(context.getCacheDir());
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
        /*try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public static void setGcm_regid(Context context, String regid) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("gcm_regid", regid);
        editor.commit();

        String data1 = regid.toString();
        byte[] byteArray = new byte[0];
        try {
            byteArray = data1.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        size += byteArray.length;
    }

    public static String getGcm_regid(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("gcm_regid", "");
    }

    public static void setNoHpAktivasi(Context context, String no_hp) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = prefs.edit();
        editor.putString("no_hp_aktivasi", no_hp);
        editor.commit();

        String data1 = no_hp.toString();
        byte[] byteArray = new byte[0];
        try {
            byteArray = data1.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        size += byteArray.length;
    }

    public static String getNoHpAktivasi(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString("no_hp_aktivasi", "");
    }
    public static long getSize() {

        Log.e("size", String.valueOf(size));
        return size;
    }


    public static void ClearPreferences(Context context) {
        size = 0;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().apply();

    }




    public static DisplayImageOptions getOptionsRoundedImage(int stubImg, int imgRes) {
        return new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(5)) //rounded corner bitmap
                .showStubImage(stubImg)            //	Display Stub Image
                .showImageForEmptyUri(imgRes)    //	If Empty image found
                .cacheInMemory()
                .cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();

    }


    public static DisplayImageOptions getOptionsImage(int stubImg, int imgRes) {
        return new DisplayImageOptions.Builder()
                .showStubImage(stubImg)            //	Display Stub Image
                .showImageForEmptyUri(imgRes)    //	If Empty image found
                .cacheInMemory()
                .cacheOnDisc().bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    public static void initImageLoader(Context context) {
        int memoryCacheSize;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            int memClass = ((ActivityManager)
                    context.getSystemService(Context.ACTIVITY_SERVICE))
                    .getMemoryClass();
            memoryCacheSize = (memClass / 8) * 1024 * 1024;
        } else {
            memoryCacheSize = 2 * 1024 * 1024;
        }

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCacheSize(memoryCacheSize)
                .memoryCache(new FIFOLimitedMemoryCache(memoryCacheSize - 1000000))
                .denyCacheImageMultipleSizesInMemory()
                //.discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .imageDownloader(new MyImageDownloader(context))
                .build();

        ImageLoader.getInstance().init(config);
    }

    public static String getRealPathFromURI(Context context, String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public static String getDateFrom(String datetime, Integer tipe) {

        if (datetime.length() == 0) return "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd MM yyyy");
        SimpleDateFormat newTimeFormatter = new SimpleDateFormat("HH:mm");
        SimpleDateFormat newDateTimeFormatter = new SimpleDateFormat("dd MM yyyy HH:mm");


        try {
            Date date = formatter.parse(datetime);
            return tipe == 0 ? newDateFormatter.format(date) : (tipe == 1 ? newTimeFormatter.format(date) : (tipe == 2 ? newDateTimeFormatter.format(date) : ""));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static String getDateMassage(String datetime) {

        if (datetime.length() == 0) return "";
        if (datetime.equalsIgnoreCase("0000-00-00 00:00:00")) return "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat newDateFormatter = new SimpleDateFormat("dd MM yyyy");
        SimpleDateFormat newTimeFormatter = new SimpleDateFormat("HH:mm");

        try {
            Date date = formatter.parse(datetime);

            String tanggal = newDateFormatter.format(date);
            String waktu = newTimeFormatter.format(date);

            return tanggal + " " + waktu;

            //date = new Date();
            //String current_tanggal = newDateFormatter.format(date);

            //if(current_tanggal.equalsIgnoreCase(tanggal)) return waktu;
            //else return tanggal;

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static String getCurrencyFormat(double number, String currency) {
        DecimalFormat format = new DecimalFormat("#,###,###");
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        format.setDecimalFormatSymbols(symbols);

        NumberFormat formatter = format;
        return currency + formatter.format(number);
    }

    public static String getNumberFormat(double number) {
        DecimalFormat format = new DecimalFormat("#,###,###");
        DecimalFormatSymbols symbols = format.getDecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        symbols.setGroupingSeparator(',');
        format.setDecimalFormatSymbols(symbols);

        NumberFormat formatter = format;
        return formatter.format(number);
    }

    public static Boolean compressImage(Context context, String imageUri, String imageDes) {

        ImageLoadingUtils utils = new ImageLoadingUtils(context);
        String filePath = getRealPathFromURI(context, imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;
        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        options.inSampleSize = utils.calculateInSampleSize(options, actualWidth, actualHeight);
        options.inJustDecodeBounds = false;
        options.inDither = false;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));


        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(imageDes);
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }


    public static Bitmap getBitmap(String urls) {
        try {
            URL url = new URL(urls);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}