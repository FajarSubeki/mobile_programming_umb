package demoapp.co.id;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import demoapp.co.id.util.ListviewActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button mBtnLingkaran;
    Button mBtnWidget;
    Button mBtnListProvince;
    Button mBtnToastAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        init();
    }

    private void init(){
        mBtnLingkaran = findViewById(R.id.btnLingkaran);
        mBtnWidget = findViewById(R.id.btnWidget);
        mBtnListProvince = findViewById(R.id.btnProvince);
        mBtnToastAlert = findViewById(R.id.btnToastAlert);

        mBtnLingkaran.setOnClickListener(this);
        mBtnWidget.setOnClickListener(this);
        mBtnListProvince.setOnClickListener(this);
        mBtnToastAlert.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnLingkaran){
            startActivity(new Intent(MenuActivity.this, MainActivity.class));
        }else if (view == mBtnWidget){
            startActivity(new Intent(MenuActivity.this, WidgetActivity.class));
        }else if (view == mBtnListProvince){
            startActivity(new Intent(MenuActivity.this, ListviewActivity.class));
        }else if (view == mBtnToastAlert){
            startActivity(new Intent(MenuActivity.this, ToastAlertActivity.class));
        }
    }
}