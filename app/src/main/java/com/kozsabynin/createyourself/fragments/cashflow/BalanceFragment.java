package com.kozsabynin.createyourself.fragments.cashflow;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kozsabynin.createyourself.R;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.balance_layout, null);
        assetsPieChart = (PieChart) view.findViewById(R.id.category_assets_pie_chart);
        incomePieChart = (PieChart) view.findViewById(R.id.category_income_pie_chart);
        balanceChart = (LineChart) view.findViewById(R.id.balance_line_chart);

/*        Set<CashflowPieChartElement> incomeItems = cashflowDbHelper.getPieChartCashflow(CashType.INCOME);
        Set<CashflowPieChartElement> expenseItems = cashflowDbHelper.getPieChartCashflow(CashType.EXPENSE);
        List<CashflowLineChartElement> totalAmountByMonthes = cashflowDbHelper.getLineChartCashflow();

        // add data
        addDataToPieChart(incomePieChart,new ArrayList<>(incomeItems),CashType.INCOME);
        addDataToPieChart(assetsPieChart,new ArrayList<>(expenseItems),CashType.EXPENSE);
        addDataToLineChart(totalAmountByMonthes);*/

        return view;
    }

    private void addDataToPieChart(PieChart pieChart, List<CashflowPieChartElement> items, CashType cashType) {
        // configure pie chart
        pieChart.setUsePercentValues(true);
        // enable hole and configure
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleRadius(7);
        pieChart.setTransparentCircleRadius(10);
        // enable rotation of the chart by touch
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(true);

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
        List<Integer> colors = new ArrayList<>();

        if(cashType == CashType.INCOME){
            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);
        } else if(cashType == CashType.EXPENSE){
            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);
        }

/*


        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription("");

        // undo all highlights
        pieChart.highlightValues(null);

        // update pie chart
        pieChart.invalidate();
    }

    private void addDataToLineChart(List<CashflowLineChartElement> totalAmountByMonthes){
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
    }

    @Override
    public void onResume() {
/*        CashflowDbHelper cashflowDbHelper = new CashflowDbHelper(getActivity());
        List<CashflowPieChartElement> incomeItems = new ArrayList<>(cashflowDbHelper.getPieChartCashflow(CashType.INCOME));
        List<CashflowPieChartElement> expenseItems = new ArrayList<>(cashflowDbHelper.getPieChartCashflow(CashType.EXPENSE));
        List<CashflowLineChartElement> totalAmountByMonthes = cashflowDbHelper.getLineChartCashflow();

        // add data
        addDataToPieChart(incomePieChart,incomeItems,CashType.INCOME);
        addDataToPieChart(assetsPieChart, expenseItems, CashType.EXPENSE);
        addDataToLineChart(totalAmountByMonthes);*/

        super.onResume();
    }
}
