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
import com.kozsabynin.createyourself.domain.Template;
import com.kozsabynin.createyourself.util.DateUtils;

import java.util.List;

/**
 * Created by Evgeni Developer on 03.04.2016.
 */
public class TemplateListViewAdapter extends ArrayAdapter<Template> {
    Context context;
    LayoutInflater inflater;
    List<Template> baseItemsList;
    private SparseBooleanArray mSelectedItemsIds;

    public TemplateListViewAdapter(Context context, int resourceId,
                                   List<Template> baseItemsList) {
        super(context, resourceId, baseItemsList);
        mSelectedItemsIds = new SparseBooleanArray();
        this.context = context;
        this.baseItemsList = baseItemsList;
        inflater = LayoutInflater.from(context);
    }

    private class ViewHolder {
        TextView title;
        TextView price;
        TextView categoryIcon;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        ViewHolder holder = new ViewHolder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.template_list_item, null);
            // Now we can fill the layout with the right values
            TextView tv = (TextView) v.findViewById(R.id.title);
            TextView distView = (TextView) v.findViewById(R.id.price);
            TextView categoryIcon = (TextView) v.findViewById(R.id.category_icon);

            holder.title = tv;
            holder.price = distView;
            holder.categoryIcon = categoryIcon;

            v.setTag(holder);
        } else
            holder = (ViewHolder) v.getTag();

        Template template = baseItemsList.get(position);

        GradientDrawable bgShape = (GradientDrawable)holder.categoryIcon.getBackground();
        bgShape.setStroke(40, Color.BLUE);
        String iconTitle = template.getTitle().substring(0,1);
        holder.categoryIcon.setText(iconTitle);

        holder.title.setText(template.getTitle());

        int color = template.getType()== CashType.INCOME?Color.GREEN:Color.RED;
        holder.price.setTextColor(color);
        String sign = CashType.INCOME == template.getType() ?"+":"-";

        holder.price.setText(sign + " "+template.getCost()+" руб.");

        return v;
    }

    @Override
    public void remove(Template object) {
        baseItemsList.remove(object);
        notifyDataSetChanged();
    }

    public List<Template> getWorldPopulation() {
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
