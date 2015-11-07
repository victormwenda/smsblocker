package com.marvik.apps.smsblocker.database.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.marvik.apps.smsblocker.database.actions.Action;
import com.marvik.apps.smsblocker.database.schemas.Database;
import com.marvik.apps.smsblocker.database.schemas.Tables;

import java.util.Locale;

/**
 * Created by victor on 11/7/2015.
 */
public class DataProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        database = new Database(getContext());
        return database != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        initSqliteDatabase(Action.ACTION_QUERY);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {
            return getSqLiteDatabase().query(false, Tables.BlockedSms.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder, null);
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {

        initSqliteDatabase(Action.ACTION_TYPE);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {

        }
        return uri.getLastPathSegment();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        initSqliteDatabase(Action.ACTION_INSERT);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {
            long _id = getSqLiteDatabase().insert(Tables.BlockedSms.TABLE_NAME, null, values);
            return uri.buildUpon().appendPath(Tables.BlockedSms.COL_ID + "/" + String.format(Locale.getDefault(), "%d", _id)).build();
        }


        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        initSqliteDatabase(Action.ACTION_DELETE);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {
            return getSqLiteDatabase().delete(Tables.BlockedSms.TABLE_NAME, selection, selectionArgs);
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        initSqliteDatabase(Action.ACTION_UPDATE);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {
            return getSqLiteDatabase().update(Tables.BlockedSms.TABLE_NAME, values, selection, selectionArgs);
        }
        return 0;
    }

    private void initSqliteDatabase(Action action) {
        switch (action) {
            case ACTION_DELETE:
            case ACTION_INSERT:
            case ACTION_TYPE:
            case ACTION_UPDATE:
                sqLiteDatabase = database.getWritableDatabase();
                break;
            case ACTION_QUERY:
                sqLiteDatabase = database.getWritableDatabase();
                break;
        }
    }

    private Database database;
    private SQLiteDatabase sqLiteDatabase;

    private static final String AUTHORITY;

    private static UriMatcher uriMatcher;

    private static final int MATCHER_BLOCKED_SMS;

    static {

        AUTHORITY = "com.marvik.apps.smsblocker.database.provider.DataProvider";

        MATCHER_BLOCKED_SMS = 1;

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


        uriMatcher.addURI(AUTHORITY, Tables.BlockedSms.TABLE_NAME, MATCHER_BLOCKED_SMS);
    }

    public Database getDatabase() {
        return database;
    }

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public static String getAuthority() {
        return AUTHORITY;
    }
}
