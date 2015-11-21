package com.marvik.apps.smsblocker.database.transactions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.marvik.apps.smsblocker.database.operations.DataOperations;
import com.marvik.apps.smsblocker.database.schemas.Tables;

/**
 * Created by victor on 11/7/2015.
 */
public class TransactionsManager {

    private Context context;
    private TblBlockedSms blockedSms;

    public TransactionsManager(Context context) {
        initAll(context);
    }

    /**
     * public Context getContext()
     *
     * @return context
     */
    public Context getContext() {
        return context;
    }

    private void initAll(Context context) {
        this.context = context;
    }

    private class TblBlockedSms implements DataOperations {

        @Override
        public Uri getUri() {
            return Tables.BlockedSms.CONTENT_URI;
        }

        @Override
        public String getType() {
            return getUri().getLastPathSegment();
        }

        @Override
        public void setType(Uri uri) {

        }

        @Override
        public Uri insert(ContentValues values) {
            return getContext().getContentResolver().insert(getUri(), values);
        }

        @Override
        public int delete(String selection, String[] selectionArgs) {
            return getContext().getContentResolver().delete(getUri(), selection, selectionArgs);
        }

        @Override
        public int update(ContentValues values, String selection, String[] selectionArgs) {
            return getContext().getContentResolver().update(getUri(), values, selection, selectionArgs);
        }

        @Override
        public Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            return getContext().getContentResolver().query(getUri(), projection, selection, selectionArgs, sortOrder);
        }
    }
}
