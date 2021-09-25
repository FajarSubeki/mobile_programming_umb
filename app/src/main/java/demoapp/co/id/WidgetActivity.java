package demoapp.co.id;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

public class WidgetActivity extends AppCompatActivity implements View.OnClickListener{

    private CheckBox mCbBandung, mCbBogor, mCbBanjarmasin, mCbBontang;
    private Button mBtnQuest1, mBtnQuest2;
    private TextView mTvResult1, mTvResult2;
    private RadioButton mRbSamarinda, mRbKendari, mRbPalu, mRbMakasar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget);
        init();
    }

    private void init(){
        mCbBandung = findViewById(R.id.cbBandung);
        mCbBogor = findViewById(R.id.cbBogor);
        mCbBanjarmasin = findViewById(R.id.cbBanjarmmasin);
        mCbBontang = findViewById(R.id.cbBontang);
        mRbSamarinda = findViewById(R.id.rbSamarinda);
        mRbKendari = findViewById(R.id.rbKendari);
        mRbPalu = findViewById(R.id.rbPalu);
        mRbMakasar = findViewById(R.id.rbMakasar);
        mBtnQuest1 = findViewById(R.id.btnQuestion1);
        mBtnQuest2 = findViewById(R.id.btnQuestion2);
        mTvResult1 = findViewById(R.id.tvValueQ1);
        mTvResult2 = findViewById(R.id.tvValueQ2);

        mBtnQuest1.setOnClickListener(this);
        mBtnQuest2.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (view == mBtnQuest1){
            int nilai = 0;
            if (mCbBandung.isChecked()){
                nilai += 10;
            }
            if (mCbBogor.isChecked()){
                nilai += -5;
            }
            if (mCbBanjarmasin.isChecked()){
                nilai += 10;
            }
            if (mCbBontang.isChecked()){
                nilai += -5;
            }
            mTvResult1.setText(getString(R.string.str_nilai_anda_0) + " " + nilai);
        }else if (view == mBtnQuest2){
            int nilai = 0;
            if (mRbSamarinda.isChecked()){
                nilai += -2;
            }else if (mRbKendari.isChecked()){
                nilai += 10;
            }else if (mRbPalu.isChecked()){
                nilai += -2;
            }else if (mRbMakasar.isChecked()){
                nilai += -2;
            }
            mTvResult2.setText(getString(R.string.str_nilai_anda_0) + " " + nilai);
        }
    }

}