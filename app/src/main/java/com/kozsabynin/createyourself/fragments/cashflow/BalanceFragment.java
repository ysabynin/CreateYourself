package com.kozsabynin.createyourself.fragments.cashflow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kozsabynin.createyourself.R;
import com.kozsabynin.createyourself.db.CashflowDbHelper;
import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.CashflowLineChartElement;
import com.kozsabynin.createyourself.domain.CashflowPieChartElement;

import java.util.ArrayList;
import java.util.List;

public class BalanceFragment extends Fragment {

    private RelativeLayout mainLayout;
    private PieChart assetsPieChart;
    private PieChart incomePieChart;
    private LineChart balanceChart;
    // we're going to display pie chart for smartphones martket shares
    private float[] yData = { 5, 10, 15, 30, 40 };
    private String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung" };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.balance_layout, null);
        assetsPieChart = (PieChart) view.findViewById(R.id.category_assets_pie_chart);
        incomePieChart = (PieChart) view.findViewById(R.id.category_income_pie_chart);
        balanceChart = (LineChart) view.findViewById(R.id.balance_line_chart);

        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getActivity());
        List<CashflowPieChartElement> incomeItems = cashflowDbHelper.getPieChartCashflow(CashType.INCOME);
        List<CashflowPieChartElement> expenseItems = cashflowDbHelper.getPieChartCashflow(CashType.EXPENSE);

        // configure pie chart
        assetsPieChart.setUsePercentValues(true);

        // enable hole and configure
        assetsPieChart.setDrawHoleEnabled(true);
        assetsPieChart.setHoleRadius(7);
        assetsPieChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        assetsPieChart.setRotationAngle(0);
        assetsPieChart.setRotationEnabled(true);

        incomePieChart.setUsePercentValues(true);

        // enable hole and configure
        incomePieChart.setDrawHoleEnabled(true);
        incomePieChart.setHoleRadius(7);
        incomePieChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        incomePieChart.setRotationAngle(0);
        incomePieChart.setRotationEnabled(true);
        // add data
        addDataToPieChart(incomePieChart,incomeItems);
        addDataToPieChart(assetsPieChart,expenseItems);

        // creating list of entry
        List<CashflowLineChartElement> totalAmountByMonthes = cashflowDbHelper.getLineChartCashflow();
        ArrayList<Entry> entries = new ArrayList<>();

        for (CashflowLineChartElement lineChartElement : totalAmountByMonthes) {
            entries.add(new Entry((float)lineChartElement.getMoneyAmount(), lineChartElement.getMonthNumber()));
        }

        LineDataSet dataset = new LineDataSet(entries, "");

        ArrayList<String> labels = new ArrayList<>();
        labels.add("Января");
        labels.add("Февраль");
        labels.add("Март");
        labels.add("Апрель");
        labels.add("Май");
        labels.add("Июнь");
        labels.add("Июль");
        labels.add("Август");
        labels.add("Сентябрь");
        labels.add("Октябрь");
        labels.add("Ноябрь");
        labels.add("Декабрь");

        LineData data = new LineData(labels, dataset);
        balanceChart.setData(data); // set the data and list of lables into chart
        balanceChart.getLegend().setEnabled(false);
        balanceChart.setDescription("");
        dataset.setDrawFilled(true);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        balanceChart.animateY(5000);

        return view;
    }

    private void addDataToPieChart(PieChart pieChart, List<CashflowPieChartElement> items) {
        ArrayList<Entry> yVals1 = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            xVals.add(items.get(i).getTitle()+" ("+items.get(i).getTotalCost()+" р)");
            yVals1.add(new Entry(items.get(i).getPercentage(), i));
        }

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "оплопо олпр");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        pieChart.setData(data);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription("");

        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();
    }
}
