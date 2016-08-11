package com.example.jumping.databasetest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper = new MyDatabaseHelper(this, "BookStore.db", null, 5);
        Button button = (Button) findViewById(R.id.create_database);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhelper.getWritableDatabase();
            }
        });

        Button button2 = (Button) findViewById(R.id.add_data);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "The DaVinci code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.99);
                db.insert("Book", null, values);
                values.clear();
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                long rowid = db.insert("Book", null, values);
                Toast.makeText(MainActivity.this,"Insert succeess" + rowid,Toast.LENGTH_SHORT).show();
            }
        });

        Button button3 = (Button) findViewById(R.id.update_data);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 10.99);
                db.update("Book", values, "name=?", new String[]{"The DaVinci code"});
                Toast.makeText(MainActivity.this,"Update success",Toast.LENGTH_SHORT).show();
            }
        });

        Button button4 = (Button) findViewById(R.id.delete_data);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[]{"500"});
                Toast.makeText(MainActivity.this,"Delete success",Toast.LENGTH_SHORT).show();
            }
        });

        Button button5 = (Button) findViewById(R.id.query_data);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                Cursor cursor = db.query("Book", null, "name = ?", new String[]{"The DaVinci code"}, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "Book name is " + name);
                        Log.d("MainActivity", "Book author is " + author);
                        Log.d("MainActivity", "Book pages is " + pages);
                        Log.d("MainActivity", "Book price is " + price);

                    } while (cursor.moveToNext());
                }
                cursor.close();
                Toast.makeText(MainActivity.this,"Query success",Toast.LENGTH_SHORT).show();
            }
        });

        Button button6 = (Button) findViewById(R.id.replace_data);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    db.delete("Book", null, null);
//                    if (true) {
//                        throw new NullPointerException();
//                    }
                    ContentValues values = new ContentValues();
                    values.put("name", "Game of Thrones");
                    values.put("author", "George Martin");
                    values.put("pages", 720);
                    values.put("price", 20.85);
                    db.insert("Book", null, values);
                    db.setTransactionSuccessful();
                    Toast.makeText(MainActivity.this,"Replace data success",Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    db.endTransaction();
                }
            }
        });
    }
}
