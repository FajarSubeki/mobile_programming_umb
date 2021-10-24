package demoapp.co.id;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SegitigaActivity extends AppCompatActivity {

    private AppCompatEditText etValueAlas;
    private AppCompatEditText etValueTinggi;
    private TextView tvResult;
    private Button btnHitung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segitiga);

        init();
    }

    private void init(){
        etValueAlas = findViewById(R.id.etValueAlas);
        etValueTinggi = findViewById(R.id.etValueTinggi);
        tvResult = findViewById(R.id.etValueHasil);
        btnHitung = findViewById(R.id.btnSubmit);

        btnHitung.setOnClickListener(view -> {
            String alas = etValueAlas.getText().toString();
            String tinggi = etValueTinggi.getText().toString();
            String result;
            if (!TextUtils.isEmpty(alas) && !TextUtils.isEmpty(tinggi)){
                double d_alas = Double.parseDouble(alas);
                double d_tinggi = Double.parseDouble(tinggi);
                result = String.valueOf(luasSegitiga(d_alas, d_tinggi));
                tvResult.setText(result);
            }else{
                Toast.makeText(SegitigaActivity.this, "Please input alas or tinggi...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private double luasSegitiga(double a, double t){
        return a*t/2;
    }


}