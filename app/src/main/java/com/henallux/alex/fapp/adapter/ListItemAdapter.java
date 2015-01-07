package com.henallux.alex.fapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henallux.alex.fapp.R;
import com.henallux.alex.fapp.model.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Alexandre on 1/5/2015.
 */
public class ListItemAdapter extends BaseAdapter {
    private ArrayList<Item> items;
    private LayoutInflater mInflater;

    private TextView nameItem;
    private TextView typeItem;
    private TextView expiredDateItem;

    public ListItemAdapter(Context context, ArrayList<Item> items)
    {
        this.items = items;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutListItem;

        if(convertView == null){
            layoutListItem = (LinearLayout) mInflater.inflate(R.layout.list_item, parent, false);
        } else{
            layoutListItem = (LinearLayout) convertView;
        }

        nameItem = (TextView)layoutListItem.findViewById(R.id.nameItem);
        nameItem.setText(items.get(position).getName());

        typeItem = (TextView)layoutListItem.findViewById(R.id.typeItem);
        typeItem.setText(items.get(position).getType().getName());

        expiredDateItem = (TextView)layoutListItem.findViewById(R.id.expiredDateItem);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        expiredDateItem.setText(sdf.format(items.get(position).getExpiryDate().getTime()));
        return layoutListItem;
    }
}
