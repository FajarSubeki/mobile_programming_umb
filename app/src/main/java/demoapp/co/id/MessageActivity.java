package demoapp.co.id;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {

    private EditText phoneNumber;
    private EditText smsBody;
    private Button smsManagerBtn;
    private Button smsSendToBtn;
    private Button smsViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        phoneNumber = findViewById(R.id.phoneNumber);
        smsBody = findViewById(R.id.smsBody);
        smsManagerBtn = findViewById(R.id.smsManager);
        smsSendToBtn = findViewById(R.id.smsSIntent);
        smsViewBtn = findViewById(R.id.smsVIntent);
        smsManagerBtn.setOnClickListener(view -> sendSmsByManager());
        smsSendToBtn.setOnClickListener(view -> sendSmsBySIntent());
        smsViewBtn.setOnClickListener(view -> sendSmsByVIntent());
    }

    public void sendSmsByManager() {
        try {
            // Get the default instance of the SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber.getText().toString(),
                    null,
                    smsBody.getText().toString(),
                    null,
                    null);
            Toast.makeText(getApplicationContext(), "SMS Berhasil Dikirim!",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(),"Pengiriman SMS Gagal...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSmsBySIntent() {
        // add the phone number in the data
        Uri uri = Uri.parse("smsto:" + phoneNumber.getText().toString());
        Intent smsSIntent = new Intent(Intent.ACTION_SENDTO, uri);
        // add the message at the sms_body extra field
        smsSIntent.putExtra("sms_body", smsBody.getText().toString());
        try{
            startActivity(smsSIntent);
        } catch (Exception ex) {
            Toast.makeText(MessageActivity.this, "Pengiriman SMS Gagal...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void sendSmsByVIntent() {
        Intent smsVIntent = new Intent(Intent.ACTION_VIEW);
        // hanya akan membuka aplikasi SMS/MMS default di Android
        smsVIntent.setType("vnd.android-dir/mms-sms");
        // menambahkan nomor telepon dan isi SMS otomatis
        smsVIntent.putExtra("address", phoneNumber.getText().toString());
        smsVIntent.putExtra("sms_body", smsBody.getText().toString());
        try{
            startActivity(smsVIntent);
        } catch (Exception ex) {
            Toast.makeText(MessageActivity.this, "Pengiriman SMS Gagal...",
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
}