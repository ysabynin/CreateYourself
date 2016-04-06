package com.kozsabynin.createyourself.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

import com.kozsabynin.createyourself.domain.CashType;
import com.kozsabynin.createyourself.domain.Category;
import com.kozsabynin.createyourself.domain.Template;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeni Developer on 03.04.2016.
 */
public class TemplateDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CreateYourself.db";
    public static final String TEMPLATE_TABLE_NAME = "template";
    public static final String TEMPLATE_COLUMN_ID = "id";
    public static final String TEMPLATE_COLUMN_NAME = "title";
    public static final String TEMPLATE_COLUMN_TYPE = "type";
    public static final String TEMPLATE_COLUMN_COST = "cost";
    public static final String TEMPLATE_COLUMN_CATEGORY_ID = "c_id";

    public static final String CATEGORY_COLUMN_NAME = "category_title";
    public static final String CATEGORY_COLUMN_TYPE = "category_type";

    public static final String CREATE_TEMPLATE_TABLE = "create table template(" +
            "id integer primary key," +
            " title text," +
            " type text ," +
            " cost real,"+" " +
            " c_id integer," +
            " FOREIGN KEY(c_id) REFERENCES category(category_id));";

    public TemplateDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TEMPLATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists template");
    }

    public boolean removeAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TEMPLATE_TABLE_NAME, null, null);
        return true;
    }

    public boolean insertTemplate(Template template) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", template.getTitle());
        contentValues.put("type", template.getType().getText());
        contentValues.put("cost", template.getCost());
        contentValues.put("c_id", template.getCategory().getId());

        db.insert(TEMPLATE_TABLE_NAME, null, contentValues);

        return true;
    }

    public boolean updateTemplate(Template template) {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", template.getTitle());
        contentValues.put("type", template.getType().getText());
        contentValues.put("cost", template.getCost());
        contentValues.put("c_id", template.getCategory().getId());

        db.update(TEMPLATE_TABLE_NAME, contentValues, "id = ?", new String[]{template.getId().toString()});
        return true;
    }

    public Integer deleteTemplate(Template template) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TEMPLATE_TABLE_NAME,
                "id = ?",
                new String[]{template.getId().toString()});
    }
    
    public List<Template> getTemplates(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM template INNER JOIN category ON template.c_id=category.category_id ORDER BY cost DESC", null);

        return executeQuery(res);
    }

    private List<Template> executeQuery(Cursor cursor){
        List<Template> templates = new ArrayList<>();

        cursor.moveToFirst();

        while (cursor.isAfterLast() == false) {
            Integer id = cursor.getInt(cursor.getColumnIndex(TEMPLATE_COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(TEMPLATE_COLUMN_NAME));

            String bdType = cursor.getString(cursor.getColumnIndex(TEMPLATE_COLUMN_TYPE));
            CashType type = ("I".equals(bdType)) ? CashType.INCOME : CashType.EXPENSE;

            Double cost = cursor.getDouble(cursor.getColumnIndex(TEMPLATE_COLUMN_COST));

            //Category creation
            int categoryId = cursor.getInt(cursor.getColumnIndex(TEMPLATE_COLUMN_CATEGORY_ID));
            String categoryTitle = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_NAME)).toUpperCase();
            String cType = cursor.getString(cursor.getColumnIndex(CATEGORY_COLUMN_TYPE));
            CashType categoryType = ("I".equals(cType)) ? CashType.INCOME : CashType.EXPENSE;
            Category category = new Category(categoryId,categoryTitle,categoryType);

            templates.add(new Template(id, name, type, category,cost));

            cursor.moveToNext();
        }

        return templates;
    }

    public void deleteTemplateById(Template template) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TEMPLATE_TABLE_NAME, "id=?", new String[]{Integer.toString(template.getId())});
    }

}



