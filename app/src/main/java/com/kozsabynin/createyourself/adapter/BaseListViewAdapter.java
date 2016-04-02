package com.kozsabynin.createyourself.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.domain.Cashflow;
import com.kozsabynin.createyourself.util.DateUtils;

import java.util.List;

/**
 * Created by Evgeni Developer on 03.03.2016.
 */
public class BaseListViewAdapter extends ArrayAdapter<Cashflow> {
    Context context;
    LayoutInflater inflater;
    List<Cashflow> baseItemsList;
    private SparseBooleanArray mSelectedItemsIds;

    public BaseListViewAdapter(Context context, int resourceId,
                               List<Cashflow> baseItemsList) {
        super(context, resourceId, baseItemsList);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.baseItemsList = baseItemsList;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView title;
        TextView price;
        TextView date;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        ViewHolder holder = new ViewHolder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.cashflow_list_item, null);
            // Now we can fill the layout with the right values
            TextView tv = (TextView) v.findViewById(R.id.title);
            TextView distView = (TextView) v.findViewById(R.id.price);
            TextView dateView = (TextView) v.findViewById(R.id.date);

            holder.title = tv;
            holder.price = distView;
            holder.date = dateView;

            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();

        System.out.println("Position [" + position + "]");
        Cashflow p = baseItemsList.get(position);
        holder.title.setText(p.getTitle());
        holder.price.setText("" + p.getCost());

        String dateText = DateUtils.getDateTextByCalendar(p.getDate());
        holder.date.setText(dateText);

        return v;
    }

    @Override
    public void remove(Cashflow object) {
        baseItemsList.remove(object);
        notifyDataSetChanged();
    }

    public List<Cashflow> getWorldPopulation() {
        return baseItemsList;
    }

    public void toggleSelection(int position) {
        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }

    public void selectView(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, value);
        else
            mSelectedItemsIds.delete(position);
        notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
}
