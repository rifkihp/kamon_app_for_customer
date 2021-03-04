package kamoncust.application.com.kamoncust;

import android.Manifest;
import android.app.Dialog;
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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.widget.EditText;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import kamoncust.application.com.data.RestApi;
import kamoncust.application.com.data.RetroFit;
import kamoncust.application.com.libs.CommonUtilities;
import kamoncust.application.com.libs.DatabaseHandler;
import kamoncust.application.com.model.ResponseSignIn;
import kamoncust.application.com.model.order;
import kamoncust.application.com.model.remember;

public class LoginActivity extends AppCompatActivity {
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    Context context;

    TextView signin;
    TextView signup;
    TextView forgotpass;
    CheckBox checkboremember;

    Dialog dialog_loading;

    Dialog dialog_informasi;
    TextView btn_ok;
    TextView text_title;
    TextView text_informasi;

    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = LoginActivity.this;

        if (Build.VERSION.SDK_INT >= 23) {
            insertDummyContactWrapper();
        }

        setContentView(R.layout.activity_login);
        remember data_remember = CommonUtilities.getRememberPassword(context);

        signin          = findViewById(R.id.signin1);
        signup          = findViewById(R.id.signup);
        forgotpass      = findViewById(R.id.forgotpass);
        checkboremember = findViewById(R.id.checkbocremember);
        email           = findViewById(R.id.email);
        password        = findViewById(R.id.edit_password);

        email.setText(data_remember.getEmail());
        password.setText(data_remember.getPassword());
        
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DaftarActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkboremember.isChecked()) {
                        remember data = new remember(email.getText().toString(), password.getText().toString(), (checkboremember.isChecked()?"Y":"N"));
                        CommonUtilities.setRememberPassword(context, data);
                    }
                    new prosesSingIn().execute();
                }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LupaPasswordActivity.class);
                startActivity(intent);
            }
        });

        dialog_loading = new Dialog(context);
        dialog_loading.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_loading.setCancelable(false);
        dialog_loading.setContentView(R.layout.loading_dialog);

        dialog_informasi = new Dialog(context);
        dialog_informasi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_informasi.setCancelable(true);
        dialog_informasi.setContentView(R.layout.informasi_dialog);

        btn_ok =  dialog_informasi.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog_informasi.dismiss();
            }
        });

        text_title =  dialog_informasi.findViewById(R.id.text_title);
        text_informasi =  dialog_informasi.findViewById(R.id.text_dialog);
    }

    public void openDialogLoading() {
        dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog_loading.show();
    }

    class prosesSingIn extends AsyncTask<String, Void, Void> {

        boolean success;
        String message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            openDialogLoading();
        }

        @Override
        protected Void doInBackground(String... urls) {
            success = false;
            message = "Proses masuk gagal. Cobalah lagi.";

            RestApi api = RetroFit.getInstanceRetrofit();
            Call<ResponseSignIn> signInCall = api.postSignIn(
                    email.getText().toString(),
                    password.getText().toString()
            );
            signInCall.enqueue(new Callback<ResponseSignIn>() {
                @Override
                public void onResponse(@NonNull Call<ResponseSignIn> call, @NonNull Response<ResponseSignIn> response) {

                    try {

                        success = Objects.requireNonNull(response.body()).getSuccess();
                        message = Objects.requireNonNull(response.body()).getMessage();
                        if (success) {
                            CommonUtilities.setSettingUser(context, Objects.requireNonNull(response.body()).getUser());
                            DatabaseHandler dh = new DatabaseHandler(context);
                            dh.clearOrderlist();
                            for (order data_order: Objects.requireNonNull(response.body()).getData_order()) {
                                dh.insertOrderlist(data_order);
                            }
                        }

                        Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_SIGN_IN");
                        i.putExtra("success", success);
                        i.putExtra("message", message);
                        sendBroadcast(i);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_SIGN_IN");
                        i.putExtra("success", success);
                        i.putExtra("message", message);
                        sendBroadcast(i);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseSignIn> call, @NonNull Throwable t) {
                    Intent i = new Intent("kamoncust.application.com.kamoncust.PROSES_SIGN_IN");
                    i.putExtra("success", success);
                    i.putExtra("message", message);
                    sendBroadcast(i);
                }
            });

            return null;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(mHandleSignInReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        try {
            unregisterReceiver(mHandleSignInReceiver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onPause();
    }

    @Override
    protected void onResume() {
        registerReceiver(mHandleSignInReceiver, new IntentFilter("kamoncust.application.com.kamoncust.PROSES_SIGN_IN"));

        super.onResume();
    }

    private final BroadcastReceiver mHandleSignInReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctx, Intent intent) {
            Boolean success = intent.getBooleanExtra("success", false);
            String message = intent.getStringExtra("message");

            dialog_loading.dismiss();
            if (!success) {
                text_informasi.setText(message);
                text_title.setText("KESALAHAN");
                dialog_informasi.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog_informasi.show();
            } else {
                Intent i = new Intent(context, SplashActivity.class);
                startActivity(i);
                finish();
            }

        }
    };

    private void insertDummyContactWrapper() {
        List<String> permissionsNeeded = new ArrayList<>();
        final List<String> permissionsList = new ArrayList<>();

        if (!addPermission(permissionsList, android.Manifest.permission.INTERNET)) {
            permissionsNeeded.add("INTERNET");
        }
        if (!addPermission(permissionsList, android.Manifest.permission.ACCESS_NETWORK_STATE)) {
            permissionsNeeded.add("ACCESS_NETWORK_STATE");
        }
        if (!addPermission(permissionsList, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissionsNeeded.add("WRITE_EXTERNAL_STORAGE");
        }
        if (!addPermission(permissionsList, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissionsNeeded.add("READ_EXTERNAL_STORAGE");
        }
        if (!addPermission(permissionsList, android.Manifest.permission.CAMERA)) {
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
                return shouldShowRequestPermissionRationale(permission);
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
                perms.put(android.Manifest.permission.INTERNET, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.ACCESS_NETWORK_STATE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);

                // Fill with results
                for (int i = 0; i < permissions.length; i++) {
                    perms.put(permissions[i], grantResults[i]);
                }

                // Check for ACCESS_FINE_LOCATION
                if (perms.get(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

                    // All Permissions Granted
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    // Permission Denied
                    Toast.makeText(context, "Some Permission is Denied", Toast.LENGTH_SHORT).show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
