package demoapp.co.id;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import demoapp.co.id.adapter.MyCustomAdapter;
import demoapp.co.id.util.ContactsInfo;

public class ContactActivity extends AppCompatActivity {

    public static final int PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    MyCustomAdapter dataAdapter = null;
    ListView listView;
    Button btnGetContacts;
    List<ContactsInfo> contactsInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        btnGetContacts = (Button) findViewById(R.id.btnGetContacts);
        listView = (ListView) findViewById(R.id.lstContacts);
        listView.setAdapter(dataAdapter);
        btnGetContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestContactPermission();
            }
        });

    }

    @SuppressLint("Range")
    private void getContacts() {
        ContentResolver contentResolver = getContentResolver();
        String contactId = null;
        String displayName = null;
        contactsInfoList = new ArrayList<ContactsInfo>();
        Cursor cursor =
                getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null,
                        null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") int hasPhoneNumber =
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    ContactsInfo contactsInfo = new ContactsInfo();
                    contactId =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    displayName =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    ;
                    contactsInfo.setContactId(contactId);
                    contactsInfo.setDisplayName(displayName);
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +
                                    " = ?",
                            new String[]{contactId}, null);

                    if (phoneCursor.moveToNext()) {
                        String phoneNumber =
                                phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contactsInfo.setPhoneNumber(phoneNumber);
                    }
                    phoneCursor.close();
                    contactsInfoList.add(contactsInfo);

                }
            }
        }
        cursor.close();
        dataAdapter = new MyCustomAdapter(ContactActivity.this, R.layout.contact_info, contactsInfoList);
        listView.setAdapter(dataAdapter);
    }

    public void requestContactPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.READ_CONTACTS) !=
                    PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.READ_CONTACTS)) {
                    AlertDialog.Builder builder = new
                            AlertDialog.Builder(this);
                    builder.setTitle("Read contacts access needed");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Please enable access to contacts.");
                    builder.setOnDismissListener(dialog -> requestPermissions(
                            new String[]

                                    {android.Manifest.permission.READ_CONTACTS}
                            , PERMISSIONS_REQUEST_READ_CONTACTS));
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new
                                    String[]{android.Manifest.permission.READ_CONTACTS},
                            PERMISSIONS_REQUEST_READ_CONTACTS);
                }
            } else {
                getContacts();
            }
        } else {
            getContacts();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String
            permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0
                    && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                getContacts();
            } else {
                Toast.makeText(this, "You have disabled a contacts permission", Toast.LENGTH_LONG).show();
            }
        }
    }


}