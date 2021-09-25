package demoapp.co.id.util;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import demoapp.co.id.R;

public class ListviewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //Data-Data yang Akan dimasukan Pada ListView
    private final String[] provinceList = {"Aceh","Sumatera Utara","Jawa Barat","Jakarta","Jawa Tengah",
            "DIY Yogyakarta","Kalimantan Tenggara","Kalimantant Timur","Nusa Tenggara Timur","Bali"};
    //ArrayList digunakan Untuk menampung Data mahasiswa
    private ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        ListView listView = findViewById(R.id.provinceList);
        data = new ArrayList<>();
        getData();
        ArrayAdapter adapter = new ArrayAdapter<>(this,
                R.layout.support_simple_spinner_dropdown_item, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    private void getData() {
        Collections.addAll(data, provinceList);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String getProvince = data.get(i);
        Toast.makeText(this, getProvince + "is clicked",
                Toast.LENGTH_SHORT).show();
    }
}