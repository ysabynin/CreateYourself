package com.kozsabynin.createyourself.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;
import com.kozsabynin.createyourself.util.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Created by Evgeni Developer on 03.03.2016.
 */
public class CashflowListViewAdapter extends ArrayAdapter<Cashflow> {
    Context context;
    LayoutInflater inflater;
    List<Cashflow> baseItemsList;
    private SparseBooleanArray mSelectedItemsIds;

    public CashflowListViewAdapter(Context context, int resourceId,
                                   Set<Cashflow> baseItemsList) {
        super(context, resourceId, new ArrayList<>(baseItemsList));
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.baseItemsList = new ArrayList<>(baseItemsList);
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView categoryIcon;
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
            TextView categoryIcon = (TextView) v.findViewById(R.id.category_icon);

            holder.title = tv;
            holder.price = distView;
            holder.date = dateView;
            holder.categoryIcon = categoryIcon;

            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();


        Cashflow p = baseItemsList.get(position);
        GradientDrawable bgShape = (GradientDrawable) holder.categoryIcon.getBackground();
        bgShape.setStroke(40, Color.BLUE);
        bgShape.setColor(Color.BLUE);
        //TODO: add check for npe
        String iconTitle = p.getCategory().getTitle().substring(0, 1);
        holder.categoryIcon.setText(iconTitle);

        holder.title.setText(p.getTitle());

        int color = p.getType()== CashType.INCOME.getText()?Color.GREEN:Color.RED;
        holder.price.setTextColor(color);
        String sign = CashType.INCOME.getText() == p.getType() ?"+":"-";

        holder.price.setText(sign + " " + p.getCost() + " руб.");

        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTimeInMillis(p.getDate());
        String dateText = DateUtils.getDateTextByCalendar(tempCalendar);
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
