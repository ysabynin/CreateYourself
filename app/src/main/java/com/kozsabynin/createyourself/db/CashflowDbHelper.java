package com.kozsabynin.createyourself.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;
import com.kozsabynin.createyourself.domain.CashflowLineChartElement;
import com.kozsabynin.createyourself.domain.CashflowPieChartElement;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Evgeni Developer on 26.03.2016.
 */
public class CashflowDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CreateYourself.db";
    public static final String CASHFLOW_TABLE_NAME = "cashflow";
    public static final String CASHFLOW_COLUMN_ID = "id";
    public static final String CASHFLOW_COLUMN_NAME = "title";
    public static final String CASHFLOW_COLUMN_TYPE = "type";
    public static final String CASHFLOW_COLUMN_COST = "cost";
    public static final String CASHFLOW_COLUMN_DATE = "date";
    public static final String CASHFLOW_COLUMN_CATEGORY_ID = "c_id";
    public static final String CATEGORY_COLUMN_ID = "category_id";
    public static final String CATEGORY_COLUMN_NAME = "category_title";
    public static final String CATEGORY_COLUMN_TYPE = "category_type";

    private final String CREATE_CASHFLOW_TABLE = "create table if not exists cashflow(" +
            "id integer primary key," +
            " title text," +
            " type text ," +
            " cost real," +
            " date integer," +
            " template_ind integer," +
            " c_id integer," +
            " FOREIGN KEY(c_id) REFERENCES category(category_id));";

    public static final String CREATE_CATEGORY_TABLE = "create table if not exists category(" +
            "category_id integer primary key," +
            " category_title text," +
            " category_type text )";

    public static final String CREATE_TEMPLATE_TABLE = "create table if not exists template(" +
            "id integer primary key," +
            " title text," +
            " type text ," +
            " cost real," + " " +
            " c_id integer," +
            " FOREIGN KEY(c_id) REFERENCES category(category_id));";


    public CashflowDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CASHFLOW_TABLE);
        db.execSQL(CREATE_CATEGORY_TABLE);
        db.execSQL(CREATE_TEMPLATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists cashflow");
    }

    public boolean removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CASHFLOW_TABLE_NAME, null, null);
        return true;
    }

    public boolean insertCashflow(Cashflow cashflow) {
        SQLiteDatabase db = this.getWritableDatabase();

        long millis = cashflow.getDate();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", cashflow.getTitle());
        contentValues.put("type", cashflow.getType());
        contentValues.put("cost", cashflow.getCost());
        contentValues.put("date", millis);
        contentValues.put("c_id", cashflow.getCategory() != null ? cashflow.getCategory().getId() : null);

        db.insert("cashflow", null, contentValues);

        return true;
    }

    public boolean updateCashflow(Cashflow cashflow) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", cashflow.getTitle());
        contentValues.put("type", cashflow.getType());
        contentValues.put("cost", cashflow.getCost());
        contentValues.put("date", cashflow.getDate());
        contentValues.put("c_id", cashflow.getCategory() != null ? cashflow.getCategory().getId() : null);

        db.update("cashflow", contentValues, "id = ?", new String[]{cashflow.getId().toString()});
        return true;
    }

    public Integer deleteCashflow(Cashflow cashflow) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cashflow",
                "id = ?",
                new String[]{cashflow.getId().toString()});
    }

    public List<Cashflow> getCashflow(CashType cashType) {
        List<Cashflow> cashflow = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;

        if (cashType != null)
            res = db.rawQuery("SELECT * FROM cashflow INNER JOIN category ON cashflow.c_id=category.category_id  WHERE TYPE = ? ORDER BY date DESC, cost DESC", new String[]{cashType.getText()});
        else
            res = db.rawQuery("SELECT * FROM cashflow INNER JOIN category ON cashflow.c_id=category.category_id  ORDER BY cost DESC", null);


        return executeQuery(res);
    }

    public List<Cashflow> getAllCashflow() {
        List<Cashflow> cashflow = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res;

        res = db.rawQuery("SELECT * FROM cashflow INNER JOIN category ON cashflow.c_id=category.category_id  ORDER BY date DESC, cost DESC", null);


        return executeQuery(res);
    }

    public List<Cashflow> getCashflowTemplates() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM cashflow INNER JOIN category ON cashflow.c_id=category.category_id  ORDER BY cost", null);

        return executeQuery(res);
    }

    private List<Cashflow> executeQuery(Cursor cursor) {
        List<Cashflow> cashflow = new ArrayList<>();

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            Integer id = cursor.getInt(cursor.getColumnIndex(CASHFLOW_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(CASHFLOW_COLUMN_NAME));

            String bdType = cursor.getString(cursor.getColumnIndex(CASHFLOW_COLUMN_TYPE));
            CashType type = ("I".equals(bdType)) ? CashType.INCOME : CashType.EXPENSE;

            Double cost = cursor.getDouble(cursor.getColumnIndex(CASHFLOW_COLUMN_COST));

            long millis = cursor.getLong(cursor.getColumnIndex(CASHFLOW_COLUMN_DATE));
            Calendar date = Calendar.getInstance();
            date.setTimeInMillis(millis);

            //Category creation
            int categoryId = cursor.getInt(cursor.getColumnIndex(CASHFLOW_COLUMN_CATEGORY_ID));
            String categoryTitle = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_NAME)).toUpperCase();
            int color = Color.RED;
            String cType = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_TYPE));
            CashType categoryType = ("I".equals(cType)) ? CashType.INCOME : CashType.EXPENSE;

/*            Category category = new Category(categoryId, categoryTitle, categoryType);

            cashflow.add(new Cashflow(id, name, type, cost, date, category));*/

            cursor.moveToNext();
        }

        return cashflow;
    }

    public void deleteCashflowById(Cashflow cashflow) {
/*        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CASHFLOW_TABLE_NAME, "id=?", new String[]{Integer.toString(cashflow.getId())});*/
    }

    private String query = "SELECT category_title,\n" +
            "       total_sum,\n" +
            "\t   ROUND(total_sum * 1.0/(SELECT sum(cost) from cashflow where type=?)*100) percentage\n" +
            "FROM category JOIN (SELECT\n" +
            "      c_id,\n" +
            "\t  cost,\n" +
            "      SUM(cost) as total_sum\n" +
            "      FROM cashflow where type=?\n" +
            "      GROUP BY c_id) sum_table ON category.category_id=sum_table.c_id\n" +
            "GROUP BY category_id";

    public List<CashflowPieChartElement> getPieChartCashflow(CashType cashType) {
        SQLiteDatabase db = this.getWritableDatabase();
        String cashTypeText = cashType.getText();
        Cursor cursor = db.rawQuery(query, new String[]{cashTypeText, cashTypeText});

        List<CashflowPieChartElement> cashflow = new ArrayList<>();

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            String title = cursor.getString(cursor.getColumnIndex("category_title"));
            Integer sum = cursor.getInt(cursor.getColumnIndex("total_sum"));
            Integer percentage = cursor.getInt(cursor.getColumnIndex("percentage"));

            CashflowPieChartElement pieChartElement = new CashflowPieChartElement(title, sum, percentage);
            cashflow.add(pieChartElement);

            cursor.moveToNext();
        }

        return cashflow;
    }

    private static final String getCashflowByMonth = "SELECT * FROM cashflow WHERE strftime('%m',date,'unixepoch') = ?";

    public List<CashflowLineChartElement> getLineChartCashflow() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<CashflowLineChartElement> cashflow = new ArrayList<>();

        for (int i = 1; i < 13; i++) {
            String monthNumberText = i < 10 ? "0" + i : String.valueOf(i);
            Cursor cursor = db.rawQuery(getCashflowByMonth, new String[]{monthNumberText});

            double sum = 0;
            cursor.moveToFirst();

            while (cursor.isAfterLast() == false) {
                Double cost = cursor.getDouble(cursor.getColumnIndex(CASHFLOW_COLUMN_COST));

                String cType = cursor.getString(cursor.getColumnIndex("type"));
                if ("I".equals(cType)) {
                    sum += cost;
                } else sum -= cost;

                cursor.moveToNext();
            }

            CashflowLineChartElement lineChartElement = new CashflowLineChartElement(sum, i);
            cashflow.add(lineChartElement);
        }

        return cashflow;
    }

}
