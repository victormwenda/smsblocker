package com.marvik.apps.smsblocker.intents;

import android.provider.Telephony;

/**
 * Created by victor on 11/7/2015.
 */
public class Intents {
    public static final String INTENT_SMS_RECEIVED = Telephony.Sms.Intents.SMS_RECEIVED_ACTION;
    public static final String INTENT_SMS_DELIVERED = Telephony.Sms.Intents.SMS_DELIVER_ACTION;
    public static final String ACTION_BLOCKED_MESSAGE_SAVED = "com.marvik.apps.smsblocker.ACTION_BLOCKED_MESSAGE_SAVED";
}
