package com.henallux.alex.fapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.henallux.alex.fapp.R;
import com.henallux.alex.fapp.model.Container;

import java.util.ArrayList;

/**
 * Created by Alexandre on 11/01/2015.
 */
public class ListContainerAdapter extends BaseAdapter{
    private ArrayList<Container> containers;
    private Context context;
    private LayoutInflater mInflater;

    private TextView nameContainer;

    public ListContainerAdapter(Context context, ArrayList<Container> containers) {
        this.containers = containers;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return containers.size();
    }

    @Override
    public Object getItem(int position) {
        return containers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layoutListContainer;

        if(convertView == null){
            layoutListContainer = (LinearLayout) mInflater.inflate(R.layout.list_container,
                    parent, false);
        } else {
            layoutListContainer = (LinearLayout) convertView;
        }

        nameContainer = (TextView) layoutListContainer.findViewById(R.id.nameContainer);
        nameContainer.setText(containers.get(position).getName());

        if(containers.get(position).getType() == Container.TYPE_FREEZER) {
            layoutListContainer.setBackgroundColor(context.getResources().getColor(R.color.color_freezer));
        } else {
            layoutListContainer.setBackgroundColor(context.getResources().getColor(R.color.color_congel));
        }
        return layoutListContainer;
    }

    public ArrayList<Container> getContainers() {
        return containers;
    }

    public void setContainers(ArrayList<Container> containers) {
        this.containers = containers;
    }
}
