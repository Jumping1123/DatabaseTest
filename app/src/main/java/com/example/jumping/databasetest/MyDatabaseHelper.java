package com.example.jumping.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Jumping on 2016/8/10.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_CATEGRY = "create table Category (" + "id integer primary key autoincrement," + "category_name text," + "category_code integer)";
    public static final String CREATE_BOOK = "create table Book (" + "id integer primary key autoincrement," + "author text," + "price real," + "pages integer," + "name text," + "category_id integer)";
    private Context mcontext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                db.execSQL(CREATE_CATEGRY);
            case 2:
                db.execSQL("alter table Book add column category_id integer");
            default:

        }
    }
}