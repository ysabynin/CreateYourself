package com.kozsabynin.createyourself.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeni Developer on 03.04.2016.
 */
public class CategoryDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CreateYourself.db";
    public static final String CATEGORY_TABLE_NAME = "category";
    public static final String CATEGORY_COLUMN_ID = "category_id";
    public static final String CATEGORY_COLUMN_NAME = "category_title";
    public static final String CATEGORY_COLUMN_TYPE = "category_type";

    public static final String CREATE_CATEGORY_TABLE = "create table category if not exists(" +
            "category_id integer primary key," +
            " category_title text," +
            " category_type text )";

    public CategoryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CATEGORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists category");
    }

    public boolean removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CATEGORY_TABLE_NAME, null, null);
        return true;
    }

    public boolean insertCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_title", category.getTitle());
        contentValues.put("category_type", category.getCashType().getText());

        db.insert(CATEGORY_TABLE_NAME, null, contentValues);

        return true;
    }

    public boolean updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("category_title", category.getTitle());
        contentValues.put("category_type", category.getCashType().getText());

        db.update(CATEGORY_TABLE_NAME, contentValues, "category_id = ?", new String[]{category.getId().toString()});
        return true;
    }

    public Integer deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CATEGORY_TABLE_NAME,
                "category_id = ?",
                new String[]{category.getId().toString()});
    }

    public List<Category> getCategories(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.query(CATEGORY_TABLE_NAME, null, null, null, null, null, "category_title ASC");

        return executeQuery(res);
    }

    private List<Category> executeQuery(Cursor cursor){
        List<Category> category = new ArrayList<>();

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            Integer id = cursor.getInt(cursor.getColumnIndex(CATEGORY_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_NAME));

            String bdType = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_TYPE));
            CashType type = ("I".equals(bdType)) ? CashType.INCOME : CashType.EXPENSE;

            category.add(new Category(String.valueOf(id), name, type));

            cursor.moveToNext();
        }

        return category;
    }

    public void deleteCategoryById(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CATEGORY_TABLE_NAME, "category_id=?", new String[]{category.getId()});
    }

}
