package com.marvik.apps.smsblocker.database.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.marvik.apps.smsblocker.database.actions.DataAction;
import com.marvik.apps.smsblocker.database.queries.Queries;
import com.marvik.apps.smsblocker.database.schemas.Database;
import com.marvik.apps.smsblocker.database.schemas.Tables;
import com.marvik.apps.smsblocker.intents.Intents;
import com.marvik.apps.smsblocker.utils.Utils;

import java.util.Locale;

/**
 * Created by victor on 11/7/2015.
 */
public class DataProvider extends ContentProvider {

    private static final String AUTHORITY;

    private static final int MATCHER_BLOCKED_SMS;
    private static final int MATCHER_BLOCKED_SMS_SENDERS;

    private static UriMatcher uriMatcher;

    static {

        AUTHORITY = "com.marvik.apps.smsblocker.database.provider.DataProvider";

        MATCHER_BLOCKED_SMS = 1;
        MATCHER_BLOCKED_SMS_SENDERS = 2;

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


        uriMatcher.addURI(AUTHORITY, Tables.BlockedSms.TABLE_NAME, MATCHER_BLOCKED_SMS);
        uriMatcher.addURI(AUTHORITY, Tables.SMSSenders.TABLE_NAME, MATCHER_BLOCKED_SMS_SENDERS);
    }

    private Database database;
    private SQLiteDatabase sqLiteDatabase;

    private Utils utils;

    public static String getAuthority() {
        return AUTHORITY;
    }

    @Override
    public boolean onCreate() {
        initAll();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        initSqliteDatabase(DataAction.DATA_ACTION_QUERY);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {
            String groupBy = null;
            if (selectionArgs != null) {
                if (selectionArgs.length > 0) {
                    groupBy = selectionArgs[0];//Tables.BlockedSms.COL_SENDER_PHONENUMBER;
                }
            }
            selectionArgs = null; //RESET TO NULL BECAUSE selectionArgs WAS JUST USED TO TRANSPORT PARAMS
            sortOrder = Queries.BlockedSms.DEFAULT_SORT_ORDER;
            return getSqLiteDatabase().query(false, Tables.BlockedSms.TABLE_NAME, projection, selection, selectionArgs, groupBy, null, sortOrder, null);
        }
        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS_SENDERS) {
            sortOrder = Queries.SmsSenders.DEFAULT_SORT_ORDER;
            return getSqLiteDatabase().query(false, Tables.SMSSenders.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder, null);
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {

        initSqliteDatabase(DataAction.DATA_ACTION_GET_TYPE);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {

        }
        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS_SENDERS) {

        }
        return uri.getLastPathSegment();
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        initSqliteDatabase(DataAction.DATA_ACTION_INSERT);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {
            long _id = getSqLiteDatabase().insert(Tables.BlockedSms.TABLE_NAME, null, values);
            getUtils().getUtilities().sendBroadcast(Intents.ACTION_BLOCKED_MESSAGE_SAVED);
            return uri.buildUpon().appendPath(Tables.BlockedSms.COL_ID + "/" + String.format(Locale.getDefault(), "%d", _id)).build();
        }
        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS_SENDERS) {
            long _id = getSqLiteDatabase().insert(Tables.SMSSenders.TABLE_NAME, null, values);
            return uri.buildUpon().appendPath(Tables.BlockedSms.COL_ID + "/" + String.format(Locale.getDefault(), "%d", _id)).build();
        }


        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        initSqliteDatabase(DataAction.DATA_ACTION_DELETE);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {
            return getSqLiteDatabase().delete(Tables.BlockedSms.TABLE_NAME, selection, selectionArgs);
        }
        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS_SENDERS) {
            return getSqLiteDatabase().delete(Tables.SMSSenders.TABLE_NAME, selection, selectionArgs);
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        initSqliteDatabase(DataAction.DATA_ACTION_UPDATE);

        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS) {
            return getSqLiteDatabase().update(Tables.BlockedSms.TABLE_NAME, values, selection, selectionArgs);
        }
        if (uriMatcher.match(uri) == MATCHER_BLOCKED_SMS_SENDERS) {
            return getSqLiteDatabase().update(Tables.SMSSenders.TABLE_NAME, values, selection, selectionArgs);
        }
        return 0;
    }

    private void initSqliteDatabase(DataAction dataAction) {
        switch (dataAction) {
            case DATA_ACTION_DELETE:
            case DATA_ACTION_GET_TYPE:
            case DATA_ACTION_INSERT:
            case DATA_ACTION_UPDATE:
                sqLiteDatabase = database.getWritableDatabase();
                break;
            case DATA_ACTION_QUERY:
                sqLiteDatabase = database.getWritableDatabase();
                break;
        }
    }

    public Database getDatabase() {
        return database;
    }

    public SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }

    public Utils getUtils() {
        return utils;
    }

    private void initAll() {
        database = new Database(getContext());
        utils = new Utils(getContext());
    }
}
