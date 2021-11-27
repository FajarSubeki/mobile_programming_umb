package demoapp.co.id;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ToastAlertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast_alert);
    }

    public void toastClick2(View view) {
        Toast t2 = Toast.makeText(this, "Toast Long", Toast.LENGTH_LONG);
        t2.show();
    }

    public void toastClick1(View view) {
        Toast t = Toast.makeText(this, "Toast Short", Toast.LENGTH_SHORT);
        t.show();
    }

    public void toastClick3(View view) {
        Toast t3 = Toast.makeText(this, "Toast ada di tengah", Toast.LENGTH_SHORT);
        t3.setGravity(Gravity.CENTER, 0,0);
        t3.show();
    }

    public void aletDialogClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Ini alertdialog, mau dilanjutkan?")
                .setTitle("PERINGATAN")
                .setPositiveButton("YA", (dialog, id) -> {
                    Toast t = Toast.makeText(getApplicationContext(), "YA",
                            Toast.LENGTH_SHORT);
                    t.show();
                })
                .setNegativeButton("Tidak", (dialog, id) -> {
                    Toast t = Toast.makeText(getApplicationContext(),
                            "Tidak", Toast.LENGTH_SHORT);
                    t.show();
                })
                .setNeutralButton("BATAL", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}