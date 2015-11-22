package com.marvik.apps.smsblocker.database.transactions;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.marvik.apps.smsblocker.database.operations.DataOperations;
import com.marvik.apps.smsblocker.database.queries.Queries;
import com.marvik.apps.smsblocker.database.schemas.Tables;
import com.marvik.apps.smsblocker.infos.blocked.senders.SmsSendersInfo;
import com.marvik.apps.smsblocker.infos.blocked.sms.BlockedSmsInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by victor on 11/7/2015.
 */
public class TransactionsManager {

    private Context context;
    private TblBlockedSms blockedSms;
    private TblSmsSenders smsSenders;

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

    public TblSmsSenders getSmsSenders() {
        return smsSenders;
    }

    private void initAll(Context context) {
        this.context = context;
        blockedSms = new TblBlockedSms();
        smsSenders = new TblSmsSenders();
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

        if (searchKey != null && !searchKey.equals("")) {

            selection = Tables.BlockedSms.COL_SENDER_PHONENUMBER + " LIKE '%" + searchKey + "%' OR "
                    + Tables.BlockedSms.COL_MESSAGE_TEXT + " LIKE '%" + searchKey + "%'";

            /* TO BE ADDED IN FUTURE VERSIONS
            +Tables.BlockedSms.COL_MESSAGE_SEND_TIME +" LIKE '" +parseTimeinMillis(searchKey) +"'"
            +Tables.BlockedSms.COL_MESSAGE_RECEIVE_TIME +" LIKE '" +parseTimeinMillis(searchKey) +"'"
            +Tables.BlockedSms.COL_MESSAGE_SYSTEM_TIME +" LIKE '" +parseTimeinMillis(searchKey) +"'";*/

        }

        String[] projection = null;

        Cursor cursor = getBlockedSms().query(projection, selection, null, Queries.BlockedSms.DEFAULT_SORT_ORDER);

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

    private long parseTimeinMillis(String searchKey) {
        return System.currentTimeMillis();
    }

    private String mysqlRealEscape(String searchKey) {
        return searchKey.replace("'", "'\\");
    }


    public int getBlockedSmsSenderBlockedMessagesCount(String senderPhonenumber) {

        String[] projection = {Tables.BlockedSms.COL_SENDER_PHONENUMBER};

        String selection = Tables.BlockedSms.COL_SENDER_PHONENUMBER + "='" + senderPhonenumber + "'";

        Cursor cursor = getBlockedSms().query(projection, selection, null, Queries.BlockedSms.DEFAULT_SORT_ORDER);

        int smsCount = 0;

        if (cursor != null) {
            smsCount = cursor.getCount();
        }
        if (cursor != null) if (!cursor.isClosed()) cursor.close();

        return smsCount;
    }

    public List<SmsSendersInfo> getSmsSendersInfo(String searchKey) {

        List<SmsSendersInfo> smsSendersInfos = new ArrayList<SmsSendersInfo>();

        searchKey = mysqlRealEscape(searchKey);

        String selection = null;
        String[] projection = null;

        if (searchKey != null && !searchKey.equals("")) {

            selection = Tables.SMSSenders.COL_SENDER_ADDRESS + " LIKE '%" + searchKey + "%'";
        }


        Cursor cursor = getSmsSenders().query(projection, selection, null, null);

        if (cursor != null) {

            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

                int messageSenderId = cursor.getInt(cursor.getColumnIndex(Tables.SMSSenders.COL_ID));
                String messageSenderAddress = cursor.getString(cursor.getColumnIndex(Tables.SMSSenders.COL_SENDER_ADDRESS));
                int blocked = cursor.getInt(cursor.getColumnIndex(Tables.SMSSenders.COL_BLOCKED));
                long blockedTime = cursor.getLong(cursor.getColumnIndex(Tables.SMSSenders.COL_BLOCK_TIME));

                smsSendersInfos.add(new SmsSendersInfo(messageSenderId, messageSenderAddress, blocked, blockedTime));
            }
        }


        if (cursor != null)
            if (!cursor.isClosed()) cursor.close();


        return smsSendersInfos;
    }

    public void saveMessageSender(String address) {
        saveMessageSender(address, false, System.currentTimeMillis());
    }

    public void saveMessageSender(String address, boolean blocked, long blockTime) {

        if (address == null) {
            return;
        }

        int iBlocked = blocked == true ? 1 : 0;

        String[] columns = {Tables.SMSSenders.COL_SENDER_ADDRESS};
        String[] columnValues = {address};

        ContentValues values = new ContentValues();

        values.put(Tables.SMSSenders.COL_SENDER_ADDRESS, address);
        values.put(Tables.SMSSenders.COL_BLOCKED, iBlocked);

        if (isExists(getSmsSenders().getUri(), columns, columnValues)) {
            int smsSenderId = getSmsSenders().getBlockedSmsSenderId(address);
            getSmsSenders().update(values, Tables.SMSSenders.COL_ID + "='" + smsSenderId + "'", null);
        } else {
            values.put(Tables.SMSSenders.COL_BLOCK_TIME, System.currentTimeMillis());
            getSmsSenders().insert(values);
        }
    }

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

        //Log.i("WHERE_CLAUSE", "isExists(" + where + ")");

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

        //Log.i("WHERE_CLAUSE", "getColumnsValues(" + where + ")");

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

    public int getSendersCount() {
        int senders = -1;
        Cursor cursor = getSmsSenders().query(null, null, null, null);
        if (cursor != null) {
            senders = cursor.getCount();
        }
        if (cursor != null) {
            cursor.close();
        }
        return senders;
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

    private class TblSmsSenders implements DataOperations {

        @Override
        public Uri getUri() {
            return Tables.SMSSenders.CONTENT_URI;
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

        public int getBlockedSmsSenderId(String senderAddress) {

            String _id = getColumnValue(new String[]{senderAddress},
                    new String[]{Tables.SMSSenders.COL_SENDER_ADDRESS},
                    Tables.SMSSenders.COL_ID);
            if (_id == null) {
                return -1;
            }
            return Integer.parseInt(_id);
        }

        public String getBlockedMessageSenderAddress(int blockedSmsSenderId) {

            return getColumnValue(new String[]{"" + blockedSmsSenderId},
                    new String[]{Tables.SMSSenders.COL_ID},
                    Tables.SMSSenders.COL_SENDER_ADDRESS);

        }

        public long getBlockedTime(int blockedSmsSenderId) {

            String blockTime = getColumnValue(new String[]{"" + blockedSmsSenderId},
                    new String[]{Tables.SMSSenders.COL_ID},
                    Tables.SMSSenders.COL_BLOCK_TIME);
            if (blockTime == null) {
                return System.currentTimeMillis();
            }
            return Long.parseLong(blockTime);
        }

        public int getBlocked(int blockedSmsSenderId) {

            String blocked = getColumnValue(new String[]{"" + blockedSmsSenderId},
                    new String[]{Tables.SMSSenders.COL_ID},
                    Tables.SMSSenders.COL_BLOCKED);
            if (blocked == null) {
                return 0;
            }
            return Integer.parseInt(blocked);
        }

        public boolean isBlocked(int blockedSmsSenderId) {
            return getBlocked(blockedSmsSenderId) == 1;
        }

    }
}
