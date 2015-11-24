package com.marvik.apps.smsblocker.constants;

/**
 * Created by victor on 11/22/2015.
 */
public final class Constants {
    public static final class Preferences {
        public static final String FIRSTRUN = "firstrun";
        public static final String APP_ENABLED = "app_enabled";
        public static final String LAST_KNOWN_SENDER = "last_known_sender";
    }

    public class Intents {
        public static final int INTENT_SELECT_CONTACT = 0x00001;
        public static final String EXTRA_MESSAGE_SENDER_ADDRESS = "message_sender_address";
    }
}
