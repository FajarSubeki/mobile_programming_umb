package demoapp.co.id.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import demoapp.co.id.MenuActivity;
import demoapp.co.id.NotesActivity;
import demoapp.co.id.R;
import demoapp.co.id.helper.DBHelper;
import demoapp.co.id.model.SetterGetterData;

public class AdapterDaftarCatatan extends BaseAdapter {

    private List<SetterGetterData> sgd;
    private Context context;
    private int id;
    private AlertDialog daftarCatatan;

    public AdapterDaftarCatatan(List<SetterGetterData> sgd, Context context, AlertDialog daftarCatatan) {
        this.sgd = sgd;
        this.context = context;
        this.daftarCatatan = daftarCatatan;
    }

    @Override
    public int getCount() {
        return sgd.size();
    }

    @Override
    public Object getItem(int i) {
        return sgd.size();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.item_catatan, null);
        TextView itemJudul = view.findViewById(R.id.itemJudul);
        ImageView editCatatan = view.findViewById(R.id.editCatatan);
        ImageView hapusCatatan = view.findViewById(R.id.hapusCatatan);
        final SetterGetterData setterGetterData = sgd.get(i);
        itemJudul.setText(setterGetterData.getJudul());
        //Metode klik untuk melihat catatan
        itemJudul.setOnClickListener(v -> {
            Intent i1 = new Intent(context, NotesActivity.class);
            i1.putExtra("judul", setterGetterData.getJudul());
            i1.putExtra("catatan", setterGetterData.getCatatan());
            i1.putExtra("lihat", true);
            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i1);
            daftarCatatan.dismiss();
        });
        //metode klik untuk menyunting catatan, dengan mengirimkan intent keaktivitas yang sama
        //setelah aktivitas di refresh, mode edit catatan aktif.
        editCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = setterGetterData.getId();
                Intent i = new Intent(context, NotesActivity.class);
                i.putExtra("id", id);
                i.putExtra("judul", setterGetterData.getJudul());
                i.putExtra("catatan", setterGetterData.getCatatan());
                i.putExtra("edit", true);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                daftarCatatan.dismiss();
            }
        });
        //menghapus catatan satu-persatu
        hapusCatatan.setOnClickListener(v -> {
            id = setterGetterData.getId();
            DBHelper dbHelper = new DBHelper(context);
            dbHelper.hapusCatatan(id);
            notifyDataSetChanged();
            dbHelper.close();
            Toast.makeText(context, "Catatan dihapus", Toast.LENGTH_SHORT).show();
            daftarCatatan.dismiss();
        });
        return view;
    }
}
