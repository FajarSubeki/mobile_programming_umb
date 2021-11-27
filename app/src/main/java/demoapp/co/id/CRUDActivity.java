package demoapp.co.id;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import demoapp.co.id.adapter.CRUDAdapter;
import demoapp.co.id.app.AppController;
import demoapp.co.id.model.Data;
import demoapp.co.id.util.Server;

public class CRUDActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Toolbar toolbar;
    FloatingActionButton fab;
    ListView list;
    SwipeRefreshLayout swipe;
    List<Data> itemList = new ArrayList<>();
    CRUDAdapter adapter;
    int success;
    AlertDialog.Builder dialog;
    LayoutInflater inflater;
    View dialogView;
    EditText txt_id, txt_nama, txt_alamat;
    String id, nama, alamat;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static String url_select = Server.URL + "select.php";
    private static String url_insert = Server.URL + "insert.php";
    private static String url_edit = Server.URL + "edit.php";
    private static String url_update = Server.URL + "update.php";
    private static String url_delete = Server.URL + "delete.php";
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_ALAMAT = "alamat";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String tag_json_obj = "json_obj_req";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_r_u_d);

        init();
    }

    private void init() {

        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // menghubungkan variablel pada layout dan pada java
        fab = findViewById(R.id.fab_add);
        swipe = findViewById(R.id.swipe_refresh_layout);
        list = findViewById(R.id.list);
        // untuk mengisi data dari JSON ke dalam adapter
        adapter = new CRUDAdapter(CRUDActivity.this, itemList);
        list.setAdapter(adapter);
        // menamilkan widget refresh
        swipe.setOnRefreshListener(this);
        swipe.post(new Runnable() {
                       @Override
                       public void run() {
                           swipe.setRefreshing(true);
                           itemList.clear();
                           adapter.notifyDataSetChanged();
                           callVolley();
                       }
                   }
        );
        // fungsi floating action button memanggil form biodata
        fab.setOnClickListener(view -> DialogForm("", "", "", "SIMPAN"));

        list.setOnItemClickListener((adapterView, view, i, l) -> {
            final String idx = itemList.get(i).getId();
            final CharSequence[] dialogitem = {"Edit", "Delete"};
            dialog = new AlertDialog.Builder(CRUDActivity.this);
            dialog.setCancelable(true);
            dialog.setItems(dialogitem, (dialog, which) -> {
                // TODO Auto-generated method stub
                switch (which) {
                    case 0:
                        edit(idx);
                        break;
                    case 1:
                        delete(idx);
                        break;
                }
            }).show();
        });

    }

    @Override
    public void onRefresh() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        callVolley();
    }

    // untuk mengosongi edittext pada form
    private void kosong() {
        txt_id.setText(null);
        txt_nama.setText(null);
        txt_alamat.setText(null);
    }

    // untuk menampilkan dialog from biodata
    private void DialogForm(String idx, String namax, String alamatx, String button) {
        dialog = new AlertDialog.Builder(CRUDActivity.this);
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.form_biodata, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Form Biodata");

        txt_id = dialogView.findViewById(R.id.txt_id);
        txt_nama = dialogView.findViewById(R.id.txt_nama);
        txt_alamat = dialogView.findViewById(R.id.txt_alamat);
        if (!idx.isEmpty()) {
            txt_id.setText(idx);
            txt_nama.setText(namax);
            txt_alamat.setText(alamatx);
        } else {
            kosong();
        }
        dialog.setPositiveButton(button, (dialog, which) -> {
            id = txt_id.getText().toString();
            nama = txt_nama.getText().toString();
            alamat = txt_alamat.getText().toString();
            simpan_update();
            dialog.dismiss();
        });
        dialog.setNegativeButton("BATAL", (dialog, which) -> {
            dialog.dismiss();
            kosong();
        });
        dialog.show();
    }

    // untuk menampilkan semua data pada listview
    private void callVolley() {
        itemList.clear();
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(true);

        // membuat request JSON
        JsonArrayRequest jArr = new JsonArrayRequest(url_select, response -> {
            Log.d(TAG, response.toString());
            // Parsing json
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject obj = response.getJSONObject(i);
                    Data item = new Data();
                    item.setId(obj.getString(TAG_ID));
                    item.setNama(obj.getString(TAG_NAMA));
                    item.setAlamat(obj.getString(TAG_ALAMAT));
                    // menambah item ke array
                    itemList.add(item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            // notifikasi adanya perubahan data pada adapter
            adapter.notifyDataSetChanged();
            swipe.setRefreshing(false);
        }, error -> {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
            swipe.setRefreshing(false);
        });
        // menambah request ke request queue
        AppController.getInstance().addToRequestQueue(jArr);
    }

    // fungsi untuk menyimpan atau update
    private void simpan_update() {

        String url;
        // jika id kosong maka simpan, jika id ada nilainya maka update
        if (id.isEmpty()) {
            url = url_insert;
        } else {
            url = url_update;
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("Add/update", jObj.toString());
                        callVolley();
                        kosong();
                        Toast.makeText(CRUDActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(CRUDActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.e(TAG, "Error: " + error.getMessage());
            Toast.makeText(CRUDActivity.this, error.getMessage(),
                    Toast.LENGTH_LONG).show();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // jika id kosong maka simpan, jika id ada nilainya maka update
                if (!id.isEmpty()) {
                    params.put("id", id);
                }
                params.put("nama", nama);
                params.put("alamat", alamat);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    // fungsi untuk get edit data
    private void edit(final String idx){

        StringRequest strReq = new StringRequest(Request.Method.POST, url_edit, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response);
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("get edit data", jObj.toString());
                        String idx = jObj.getString(TAG_ID);
                        String namax = jObj.getString(TAG_NAMA);
                        String alamatx = jObj.getString(TAG_ALAMAT);
                        DialogForm(idx, namax, alamatx, "UPDATE");
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(CRUDActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.e(TAG, "Error: " + error.getMessage());
            Toast.makeText(CRUDActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

    }

    // fungsi untuk menghapus
    private void delete(final String idx){

        StringRequest strReq = new StringRequest(Request.Method.POST, url_delete, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Response: " + response.toString());
                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);
                    // Cek error node pada json
                    if (success == 1) {
                        Log.d("delete", jObj.toString());
                        callVolley();
                        Toast.makeText(CRUDActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(CRUDActivity.this, jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.e(TAG, "Error: " + error.getMessage());
            Toast.makeText(CRUDActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Posting parameters ke post url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", idx);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);

    }

}