package com.kozsabynin.createyourself.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;

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
    public static final String CASHFLOW_COLUMN_TEMPLATE_IND = "template_ind";

    private final String CREATE_CASHFLOW_TABLE = "create table cashflow(" +
            "id integer primary key," +
            " title text," +
            " type text ," +
            " cost real," +
            " date integer," +
            " template_ind integer)";

    public CashflowDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CASHFLOW_TABLE);
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

        long millis = cashflow.getDate().getTimeInMillis();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", cashflow.getTitle());
        contentValues.put("type", cashflow.getType().getText());
        contentValues.put("cost", cashflow.getCost());
        contentValues.put("date", millis);
        contentValues.put("template_ind", cashflow.isTemplate() ? 1 : 0);

        db.insert("cashflow", null, contentValues);

        return true;
    }

    public boolean updateCashflow(Cashflow cashflow) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", cashflow.getTitle());
        contentValues.put("type", cashflow.getType().getText());
        contentValues.put("cost", cashflow.getCost());
        contentValues.put("date", cashflow.getDate().getTimeInMillis());
        contentValues.put("template_ind", cashflow.isTemplate() ? 1 : 0);

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
            res = db.query("cashflow", null, "type = ?", new String[]{cashType.getText()}, null, null, "cost DESC");
        else
            res = db.query("cashflow", null, null, null, null, null, "cost DESC");

        return executeQuery(res);
    }

    public List<Cashflow> getCashflowTemplates(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.query("cashflow", null, "template_ind = 1", null, null, null, "cost DESC");

        return executeQuery(res);
    }

    private List<Cashflow> executeQuery(Cursor cursor){
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

            int templateInd = cursor.getInt(cursor.getColumnIndex(CASHFLOW_COLUMN_TEMPLATE_IND));
            boolean isTemplate = templateInd == 1 ? true : false;

            cashflow.add(new Cashflow(id, name, type, cost, date,isTemplate));

            cursor.moveToNext();
        }

        return cashflow;
    }

    public void deleteCashflowById(Cashflow cashflow) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CASHFLOW_TABLE_NAME, "id=?", new String[]{Integer.toString(cashflow.getId())});
    }

}
