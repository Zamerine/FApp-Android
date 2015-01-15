package com.henallux.alex.fapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henallux.alex.fapp.R;
import com.henallux.alex.fapp.model.Container;
import com.henallux.alex.fapp.model.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Created by Alexandre on 1/5/2015.
 */
public class ListItemAdapter extends BaseAdapter {
    private final long MILISECOND_PER_DAY = 24 * 60 * 60 * 1000;

    private ArrayList<Item> items;
    private Context context;
    private LayoutInflater mInflater;

    private TextView nameItem;
    private TextView quantityItem;
    private TextView expiredDateItem;

    public ListItemAdapter(Context context, ArrayList<Item> items)
    {
        this.items = items;
        this.context = context;
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

        quantityItem = (TextView)layoutListItem.findViewById(R.id.quantityItem);
        quantityItem.setText(String.valueOf(items.get(position).getQuantity()));

        expiredDateItem = (TextView)layoutListItem.findViewById(R.id.expiredDateItem);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        expiredDateItem.setText(sdf.format(items.get(position).getExpiryDate().getTime()));

        setDefaultTextColor();
        if(NbDayBeforeExpiredDate(items.get(position)) > 14) {
            layoutListItem.setBackgroundColor(context.getResources().getColor(R.color.color_normal_item));
        } else if( NbDayBeforeExpiredDate(items.get(position)) > 5) {
            layoutListItem.setBackgroundColor(context.getResources().getColor(R.color.color_unless2Weeks));
        } else if(NbDayBeforeExpiredDate(items.get(position)) > 0) {
            layoutListItem.setBackgroundColor(context.getResources().getColor(R.color.color_unless5days));
        } else {
            layoutListItem.setBackgroundColor(context.getResources().getColor(R.color.color_inedible));
            setLightTextColor();
        }
        return layoutListItem;
    }

    private double NbDayBeforeExpiredDate(Item item) {
        GregorianCalendar now = new GregorianCalendar();
        return (item.getExpiryDate().getTimeInMillis() - now.getTimeInMillis())/MILISECOND_PER_DAY;
    }

    private void setDefaultTextColor() {
        nameItem.setTextColor(context.getResources().getColor(R.color.secondary_text_default_material_light));
        quantityItem.setTextColor(context.getResources().getColor(R.color.secondary_text_default_material_light));
        expiredDateItem.setTextColor(context.getResources().getColor(R.color.secondary_text_default_material_light));
    }

    private void setLightTextColor() {
        nameItem.setTextColor(context.getResources().getColor(R.color.secondary_text_default_material_dark));
        quantityItem.setTextColor(context.getResources().getColor(R.color.secondary_text_default_material_dark));
        expiredDateItem.setTextColor(context.getResources().getColor(R.color.secondary_text_default_material_dark));
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
