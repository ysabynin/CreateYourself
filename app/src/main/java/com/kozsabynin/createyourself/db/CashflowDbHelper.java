package com.kozsabynin.createyourself.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Cashflow;

import java.util.ArrayList;
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

    private final String CREATE_CASHFLOW_TABLE = "create table cashflow(" +
            "id integer primary key," +
            " title text," +
            " type text ," +
            " cost real)";

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

    public boolean removeAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CASHFLOW_TABLE_NAME, null, null);
        return true;
    }

    public boolean insertCashflow(Cashflow cashflow) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", cashflow.getTitle());
        contentValues.put("type", cashflow.getType().getText());
        contentValues.put("cost", cashflow.getCost());

        db.insert("cashflow", null, contentValues);

        return true;
    }

    public boolean updateCashflow(Cashflow cashflow) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", cashflow.getTitle());
        contentValues.put("type", cashflow.getType().getText());
        contentValues.put("cost", cashflow.getCost());
        db.update("cashflow", contentValues, "id = ?", new String[]{cashflow.getId().toString()});
        return true;
    }

    public Integer deleteCashflow(Cashflow cashflow) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("cashflow",
                "id = ?",
                new String[]{cashflow.getId().toString()});
    }

    public List<Cashflow> getCashflowByType(CashType cashType) {
        List<Cashflow> cashflow = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.query("cashflow", null, "type = ?", new String[]{cashType.getText()}, null, null, "cost DESC");

        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Integer id = res.getInt(res.getColumnIndex(CASHFLOW_COLUMN_ID));
            String name = res.getString(res.getColumnIndex(CASHFLOW_COLUMN_NAME));

            String bdType = res.getString(res.getColumnIndex(CASHFLOW_COLUMN_TYPE));
            CashType type = ("I".equals(bdType)) ? CashType.INCOME : CashType.EXPENSE;

            Double cost = res.getDouble(res.getColumnIndex(CASHFLOW_COLUMN_COST));

            cashflow.add(new Cashflow(id, name, type, cost));

            res.moveToNext();
        }

        return cashflow;
    }

    public void deleteCashflowById(Cashflow cashflow){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CASHFLOW_TABLE_NAME, "id=?", new String[]{Integer.toString(cashflow.getId())});
    }

}
