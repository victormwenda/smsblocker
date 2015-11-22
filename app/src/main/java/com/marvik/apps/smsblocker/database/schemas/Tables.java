package com.marvik.apps.smsblocker.database.schemas;

import android.net.Uri;

/**
 * Created by victor on 11/7/2015.
 */
public class Tables {

    public static class TablesSQL {
        public static final String[] SQL = {BlockedSms.SQL, SMSSenders.SQL};
    }

    public static class BlockedSms {

        public static final String COL_ID = "_id";
        public static final String COL_SENDER_PHONENUMBER = "sender_phonenumber";
        public static final String COL_MESSAGE_TEXT = "message_text";
        public static final String COL_MESSAGE_SEND_TIME = "send_time";
        public static final String COL_MESSAGE_RECEIVE_TIME = "receive_time";
        public static final String COL_MESSAGE_SYSTEM_TIME = "system_time";

        public static final String TABLE_NAME = "blockedsms";

        public static final Uri CONTENT_URI = Uri.parse("content://com.marvik.apps.smsblocker.database.provider.DataProvider/" + TABLE_NAME);

        public static final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SENDER_PHONENUMBER + " TEXT NOT NULL, "
                + COL_MESSAGE_TEXT + " TEXT, "
                + COL_MESSAGE_SEND_TIME + " LONG NOT NULL, "
                + COL_MESSAGE_RECEIVE_TIME + " LONG NOT NULL, "
                + COL_MESSAGE_SYSTEM_TIME + " LONG NOT NULL "
                + ");";

    }

    public static class SMSSenders {

        public static final String COL_ID = "_id";
        public static final String COL_SENDER_ADDRESS = "sender_address";
        public static final String COL_BLOCKED = "blocked";
        public static final String COL_BLOCK_TIME = "block_time";


        public static final String TABLE_NAME = "blockedsmssenders";

        public static final Uri CONTENT_URI = Uri.parse("content://com.marvik.apps.smsblocker.database.provider.DataProvider/" + TABLE_NAME);

        public static final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SENDER_ADDRESS + " TEXT NOT NULL, "
                + COL_BLOCKED + " INTEGER NOT NULL, "
                + COL_BLOCK_TIME + " LONG NOT NULL "
                + ");";

    }

}
