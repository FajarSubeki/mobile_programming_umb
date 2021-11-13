package demoapp.co.id.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import demoapp.co.id.R;
import demoapp.co.id.util.ContactsInfo;

public class MyCustomAdapter extends ArrayAdapter {

    private final List contactsInfoList;
    private final Context context;

    public MyCustomAdapter(@NonNull Context context, int resource, @NonNull
            List objects) {
        super(context, resource, objects);
        this.contactsInfoList = objects;
        this.context = context;
    }

    private static class ViewHolder {
        TextView displayName;
        TextView phoneNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.contact_info, null);
            holder = new ViewHolder();
            holder.displayName = convertView.findViewById(R.id.displayName);
            holder.phoneNumber = convertView.findViewById(R.id.phoneNumber);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ContactsInfo contactsInfo = (ContactsInfo)
                contactsInfoList.get(position);
        holder.displayName.setText(contactsInfo.getDisplayName());
        holder.phoneNumber.setText(contactsInfo.getPhoneNumber());
        return convertView;
    }

}
