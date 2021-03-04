package gomocart.application.com.gomocart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import gomocart.application.com.libs.CommonUtilities;
import gomocart.application.com.libs.LocaleHelper;

public class LanguageActivity extends AppCompatActivity {

    ImageView back;
    CheckBox cbindonesia, cbenglish;
    LinearLayout lay_english, lay_indonesia;
    TextView btnsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_language_gomocart);

        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cbindonesia = (CheckBox) findViewById(R.id.cbindonesia);
        cbenglish = (CheckBox) findViewById(R.id.cbenglish);
        lay_english = findViewById(R.id.lay_english);
        lay_indonesia = findViewById(R.id.lay_indonesia);
        btnsave = findViewById(R.id.btnsave);

        String language_type = CommonUtilities.getLanguageType(LanguageActivity.this);


        if(language_type.equals("en")){
            cbenglish.setChecked(true);
        }else if(language_type.equals("in")){
            cbindonesia.setChecked(true);
        }

        cbindonesia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_indonesia.performClick();
            }
        });

        cbenglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lay_english.performClick();
            }
        });

        lay_english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbindonesia.setChecked(false);
                cbenglish.setChecked(true);
            }
        });

        lay_indonesia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cbindonesia.setChecked(true);
                cbenglish.setChecked(false);
            }
        });


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbenglish.isChecked()){
                    LocaleHelper.setLocale(LanguageActivity.this, "en");
                    CommonUtilities.setLanguageType(LanguageActivity.this,"en");
                }else if(cbindonesia.isChecked()){
                    LocaleHelper.setLocale(LanguageActivity.this, "in");
                    CommonUtilities.setLanguageType(LanguageActivity.this,"in");
                }

                Intent detailAct = new Intent(LanguageActivity.this, MainActivity.class);
                detailAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(detailAct);
                finish();

            }
        });
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }

}
