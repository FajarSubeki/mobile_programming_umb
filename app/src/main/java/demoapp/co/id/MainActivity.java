package demoapp.co.id;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import demoapp.co.id.util.BaseUtil;

public class MainActivity extends AppCompatActivity {

    Button mBtnSubmit;
    AppCompatEditText mEtJari;
    TextView mTvResultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        luasLingkaran();
    }

    private void luasLingkaran(){
        mBtnSubmit = findViewById(R.id.btnSubmit);
        mEtJari = findViewById(R.id.etValueJari);
        mTvResultValue = findViewById(R.id.tvResultValue);

        mBtnSubmit.setOnClickListener(view -> {
            String getValueJari = mEtJari.getText().toString();
            if (!TextUtils.isEmpty(getValueJari))
                mTvResultValue.setText(String.valueOf(BaseUtil.luasLingkaran(Integer.parseInt(getValueJari))));
            else
                Toast.makeText(this, "Harap input nilai terlebih dahulu", Toast.LENGTH_SHORT).show();
        });
    }

}