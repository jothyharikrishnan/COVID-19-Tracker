package com.example.covid19.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.covid19.R;
import com.example.covid19.model.ItemModel;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.viewHolder> {

    Context context;
    ArrayList<ItemModel> arrayList;

    public CustomAdapter(Context context, ArrayList<ItemModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public  CustomAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemlist, viewGroup, false);
        return new viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(CustomAdapter.viewHolder viewHolder,int position) {
        viewHolder.iconName.setText(arrayList.get(position).getName());
        viewHolder.icon.setText(arrayList.get(position).getField());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView icon;
        TextView iconName;

        public viewHolder(View itemView) {
            super(itemView);
            icon = (TextView) itemView.findViewById(R.id.icon);
            iconName = (TextView) itemView.findViewById(R.id.icon_name);

        }
    }

}