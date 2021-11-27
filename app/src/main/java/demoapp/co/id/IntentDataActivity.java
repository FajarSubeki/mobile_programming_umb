package demoapp.co.id;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class IntentDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent in = getIntent();
        String msg = in.getStringExtra("datakirim");
        EditText et = findViewById(R.id.etData);
        et.setText(msg);
    }


    public void clickSubmit(View view) {
        Intent i = getIntent();
        EditText et = findViewById(R.id.etData);
        String nama = et.getText().toString();
        i.putExtra("dataterima", nama);
        setResult(RESULT_OK, i);
        finish();
    }
}