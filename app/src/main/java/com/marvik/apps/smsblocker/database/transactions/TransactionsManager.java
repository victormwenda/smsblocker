package com.marvik.apps.smsblocker.database.transactions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.marvik.apps.smsblocker.database.operations.DataOperations;
import com.marvik.apps.smsblocker.database.queries.Queries;
import com.marvik.apps.smsblocker.database.schemas.Tables;
import com.marvik.apps.smsblocker.infos.BlockedSmsInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public TblBlockedSms getBlockedSms() {
        return blockedSms;
    }

    private void initAll(Context context) {
        this.context = context;
        blockedSms = new TblBlockedSms();
    }

    public void saveBlockedSms(String senderPhonenumber, String messageText, long messageSendTime, long messageReceiveTime) {

        long systemTime = System.currentTimeMillis();

        ContentValues values = new ContentValues();
        values.put(Tables.BlockedSms.COL_SENDER_PHONENUMBER, senderPhonenumber);
        values.put(Tables.BlockedSms.COL_MESSAGE_TEXT, messageText);
        values.put(Tables.BlockedSms.COL_MESSAGE_SEND_TIME, messageSendTime);
        values.put(Tables.BlockedSms.COL_MESSAGE_RECEIVE_TIME, messageReceiveTime);
        values.put(Tables.BlockedSms.COL_MESSAGE_SYSTEM_TIME, systemTime);

        String[] columns = {
                Tables.BlockedSms.COL_SENDER_PHONENUMBER,
                Tables.BlockedSms.COL_MESSAGE_TEXT,
                Tables.BlockedSms.COL_MESSAGE_SEND_TIME,
                Tables.BlockedSms.COL_MESSAGE_RECEIVE_TIME,
                Tables.BlockedSms.COL_MESSAGE_SYSTEM_TIME};

        String[] columnValues = {senderPhonenumber, messageText, "" + messageSendTime, "" + messageReceiveTime, "" + systemTime};

        if (!isExists(getBlockedSms().getUri(), columns, columnValues)) {
            getBlockedSms().insert(values);
        }
    }

    public int deleteBlockedSms(int blockedSmsId) {
        String[] selectionArgs = null;
        String selection = Tables.BlockedSms.COL_ID + "='" + blockedSmsId + "'";
        return getBlockedSms().delete(selection, selectionArgs);
    }

    public List<BlockedSmsInfo> getAllBlockedSmsInfo(String searchKey) {
        return getBlockedSmsInfo(null);
    }

    public List<BlockedSmsInfo> getBlockedSmsInfo(String searchKey) {
        return searchBlockedSms(searchKey);
    }

    public List<BlockedSmsInfo> searchBlockedSms(String searchKey) {

        searchKey = mysqlRealEscape(searchKey);

        String selection = null;

        if (searchKey != null || !searchKey.equals("")) {

            selection = Tables.BlockedSms.COL_SENDER_PHONENUMBER + " LIKE '" + searchKey + "'"
                    + Tables.BlockedSms.COL_MESSAGE_TEXT + " LIKE '" + searchKey + "'";

            /* TO BE ADDED IN FUTURE VERSIONS
            +Tables.BlockedSms.COL_MESSAGE_SEND_TIME +" LIKE '" +parseTimeinMillis(searchKey) +"'"
            +Tables.BlockedSms.COL_MESSAGE_RECEIVE_TIME +" LIKE '" +parseTimeinMillis(searchKey) +"'"
            +Tables.BlockedSms.COL_MESSAGE_SYSTEM_TIME +" LIKE '" +parseTimeinMillis(searchKey) +"'";*/

        }

        String[] projection = {Tables.BlockedSms.COL_SENDER_PHONENUMBER, Tables.BlockedSms.COL_MESSAGE_TEXT};

        Cursor cursor = getBlockedSms().query(projection, selection, null, Queries.BlockedSms.SORT_ORDER_DEFAULT);

        List<BlockedSmsInfo> blockedSmsInfos = new ArrayList<>();

        if (cursor != null) {

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                int blockedSmsId = cursor.getInt(cursor.getColumnIndex(Tables.BlockedSms.COL_ID));
                String senderPhonenumber = cursor.getString(cursor.getColumnIndex(Tables.BlockedSms.COL_SENDER_PHONENUMBER));
                String messageText = cursor.getString(cursor.getColumnIndex(Tables.BlockedSms.COL_MESSAGE_TEXT));
                long messageSendTime = cursor.getLong(cursor.getColumnIndex(Tables.BlockedSms.COL_MESSAGE_SEND_TIME));
                long messageReceiveTime = cursor.getLong(cursor.getColumnIndex(Tables.BlockedSms.COL_MESSAGE_RECEIVE_TIME));
                long systemTime = cursor.getLong(cursor.getColumnIndex(Tables.BlockedSms.COL_MESSAGE_SYSTEM_TIME));

                blockedSmsInfos.add(new BlockedSmsInfo(blockedSmsId, senderPhonenumber, messageText,
                        messageSendTime, messageReceiveTime, systemTime));
            }
        }


        if (cursor != null)
            if (!cursor.isClosed()) cursor.close();

        return blockedSmsInfos;
    }

    private long parseTimeinMillis(String searchKey) { return System.currentTimeMillis();}

    private String mysqlRealEscape(String searchKey) { return searchKey.replace("'", "'\\");}

    public boolean isExists(@NonNull Uri uri, @NonNull String[] columns, @NonNull String[] columnValues) {

        String where = null;

        if (columns.length != columnValues.length) {
            throw new IllegalArgumentException("Missing values for Columns " + Arrays.deepToString(columns) + ", You provided " + Arrays.deepToString(columnValues));
        }

        if (columns.length == columnValues.length) {

            for (int i = 0; i < columns.length; i++) {
                if (i == 0) {
                    where = "";
                }
                where += columns[i] + "='" + columnValues[i] + "' ";
                if (i < columns.length - 1) {
                    where += " AND ";
                }
            }
        }

        Log.i("WHERE_CLAUSE", "isExists(" + where + ")");

        Cursor cursor = getContext().getContentResolver().query(uri, null, where, null, null);

        boolean isExists = false;

        if (cursor != null) {
            isExists = cursor.getCount() > 0;
            cursor.close();
        }

        return isExists;
    }

    private String getColumnsValues(@NonNull Uri uri, @NonNull String[] index, @NonNull String[] indexColumn, String targetColumn) {
        String where = null;
        if (index.length == 0 && indexColumn.length == 0) {
            where = null;
        } else {
            where = "";
            if (index.length != indexColumn.length) {
                throw new IllegalArgumentException("Missing params for Columns " + Arrays.deepToString(indexColumn) + ", You provided " + Arrays.deepToString(index));
            }

            if (index.length == indexColumn.length) {
                for (int i = 0; i < indexColumn.length; i++) {
                    where += indexColumn[i] + "='" + index[i] + "' ";
                    if (i < (index.length - 1)) {
                        where += " AND ";
                    }
                }
            }
        }

        Log.i("WHERE_CLAUSE", "getColumnsValues(" + where + ")");

        Cursor cursor = getContext().getContentResolver().query(uri, null, where, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            String foundValue = null;
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                foundValue = cursor.getString(cursor.getColumnIndex(targetColumn));
            }
            if (cursor != null) cursor.close();
            return foundValue;
        }
        if (cursor != null) cursor.close();
        return null;
    }

    public int getBlockedSmsSenderBlockedMessagesCount(String senderPhonenumber) {

        String[] projection = {Tables.BlockedSms.COL_SENDER_PHONENUMBER};

        String selection = Tables.BlockedSms.COL_SENDER_PHONENUMBER + "='" + senderPhonenumber + "'";

        Cursor cursor = getBlockedSms().query(projection, selection, null, Queries.BlockedSms.SORT_ORDER_DEFAULT);

        int smsCount = 0;

        if (cursor != null) {
            smsCount = cursor.getCount();
        }
        if (cursor != null) if (!cursor.isClosed()) cursor.close();

        return smsCount;
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

        private String getColumnValue(@NonNull String[] index, @NonNull String[] indexColumn, String targetColumn) {
            return getColumnsValues(getUri(), index, indexColumn, targetColumn);
        }

        public int getBlockedSmsId(String senderPhonenumber, String messageText) {

            String _id = getColumnValue(new String[]{senderPhonenumber, messageText},
                    new String[]{Tables.BlockedSms.COL_SENDER_PHONENUMBER, Tables.BlockedSms.COL_MESSAGE_TEXT},
                    Tables.BlockedSms.COL_ID);
            if (_id == null) {
                return -1;
            }
            return Integer.parseInt(_id);
        }

        public String getBlockedMessageText(int blockedSmsId) {

            return getColumnValue(new String[]{"" + blockedSmsId},
                    new String[]{Tables.BlockedSms.COL_ID},
                    Tables.BlockedSms.COL_MESSAGE_TEXT);

        }

        public long getBlockedSendTime(int blockedSmsId) {

            String sendTime = getColumnValue(new String[]{"" + blockedSmsId},
                    new String[]{Tables.BlockedSms.COL_ID},
                    Tables.BlockedSms.COL_MESSAGE_SEND_TIME);
            if (sendTime == null) {
                return System.currentTimeMillis();
            }
            return Long.parseLong(sendTime);
        }

        public long getBlockedReceiveTime(int blockedSmsId) {

            String receiveTime = getColumnValue(new String[]{"" + blockedSmsId},
                    new String[]{Tables.BlockedSms.COL_ID},
                    Tables.BlockedSms.COL_MESSAGE_RECEIVE_TIME);
            if (receiveTime == null) {
                return System.currentTimeMillis();
            }
            return Long.parseLong(receiveTime);
        }

        public long getBlockedSystemTime(int blockedSmsId) {

            String systemTime = getColumnValue(new String[]{"" + blockedSmsId},
                    new String[]{Tables.BlockedSms.COL_ID},
                    Tables.BlockedSms.COL_MESSAGE_SYSTEM_TIME);
            if (systemTime == null) {
                return System.currentTimeMillis();
            }
            return Long.parseLong(systemTime);
        }

    }

}
