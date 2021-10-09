package demoapp.co.id;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import demoapp.co.id.util.ListviewActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    Button mBtnLingkaran;
    Button mBtnWidget;
    Button mBtnListProvince;
    Button mBtnToastAlert;
    Button mBtnDataIntent;
    Button mBtnCamera;
    private int CODE_LAUNCH_DATA = 101;

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
        mBtnDataIntent = findViewById(R.id.btnDataIntent);
        mBtnCamera = findViewById(R.id.btnCamera);

        mBtnLingkaran.setOnClickListener(this);
        mBtnWidget.setOnClickListener(this);
        mBtnListProvince.setOnClickListener(this);
        mBtnToastAlert.setOnClickListener(this);
        mBtnDataIntent.setOnClickListener(this);
        mBtnCamera.setOnClickListener(this);
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
        }else if (view == mBtnDataIntent){
            Intent intent = new Intent(MenuActivity.this, IntentDataActivity.class);
            intent.putExtra("datakirim", "ini kiriman dari activity utama!");
            startActivityForResult(intent, CODE_LAUNCH_DATA);
        }else if (view == mBtnCamera){
            startActivity(new Intent(MenuActivity.this, CameraActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_LAUNCH_DATA && resultCode == RESULT_OK){
            String pesan = data.getStringExtra("dataterima");
            if (!TextUtils.isEmpty(pesan)){
                Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show();
            }
        }
    }
}