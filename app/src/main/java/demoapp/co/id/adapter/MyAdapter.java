package demoapp.co.id.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import demoapp.co.id.R;
import demoapp.co.id.model.MyList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<MyList> myListList;
    private Context ct;
    public MyAdapter(List<MyList> myListList, Context ct) {
        this.myListList = myListList;
        this.ct = ct;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int
            viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyList myList=myListList.get(position);

        holder.imageView.setImageDrawable(ct.getResources().getDrawable(myList.getImage
                ()));
    }

    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.myimage);
        }
    }


}

