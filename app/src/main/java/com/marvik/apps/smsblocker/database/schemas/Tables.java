package com.marvik.apps.smsblocker.database.schemas;

/**
 * Created by victor on 11/7/2015.
 */
public class Tables {

    public static class BlockedSms {

        public static final String COL_ID = "_id";
        public static final String COL_SENDER_PHONENUMBER = "sender_phonenumber";
        public static final String COL_MESSAGE_TEXT = "message_text";
        public static final String COL_MESSAGE_SEND_TIME = "send_time";
        public static final String COL_MESSAGE_RECEIVE_TIME = "receive_time";
        public static final String COL_MESSAGE_SYSTEM_TIME = "system_time";

    }
}
