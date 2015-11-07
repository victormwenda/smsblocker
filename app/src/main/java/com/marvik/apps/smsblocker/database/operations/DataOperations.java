package com.marvik.apps.smsblocker.database.operations;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by victor on 11/7/2015.
 */
public interface DataOperations {
    Uri getUri();

    String getType();

    void setType(Uri uri);

    Uri insert(ContentValues values);

    int delete(String selection, String[] selectionArgs);

    int update(ContentValues values, String selection, String[] selectionArgs);

    Cursor query(String[] projection, String selection, String[] selectionArgs, String sortOrder);
}
