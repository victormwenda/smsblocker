package com.marvik.apps.smsblocker.database.schemas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by victor on 11/7/2015.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smsblocker";
    private static final int DATABASE_VERSION = 1;
    private static final SQLiteDatabase.CursorFactory CURSOR_FACTORY = null;

    public Database(Context context) {
        super(context, DATABASE_NAME, CURSOR_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        createTables(db);
    }

    private void createTables(SQLiteDatabase db) {
        String[] tables_sql = Tables.TablesSQL.SQL;
        for (String sql : tables_sql) {
            createTable(db, sql);
        }
    }

    private void createTable(SQLiteDatabase db, String sql) {
        db.execSQL(sql);
    }
}
