package com.marvik.apps.smsblocker.receivers.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.marvik.apps.smsblocker.intents.Intents;
import com.marvik.apps.smsblocker.utils.Utils;

/**
 * Created by victor on 11/7/2015.
 */
public class SmsReceiver extends BroadcastReceiver {
    private Context context;
    private Utils utils;

    @Override
    public void onReceive(Context context, Intent intent) {

        initAll(context);

        if (intent.getAction().equals(Intents.INTENT_SMS_RECEIVED)) {
            if (getUtils().getPrefsManager().isEnabled()) {
                interceptReceivedMessage(intent);
            }
        }
    }

    private Context getContext() {return context;}

    public Utils getUtils() {
        return utils;
    }

    private void initAll(Context context) {
        this.context = context;
        utils = new Utils(context);
    }

    private void interceptReceivedMessage(Intent intent) {
        Bundle extras = intent.getExtras();
        Object[] pdus = (Object[]) extras.get("pdus");
        SmsMessage[] messagePdus = new SmsMessage[pdus.length];

        for (int i = 0; i < messagePdus.length; i++) {
            SmsMessage messagePdu = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String senderPhone = null;
            String messageText = null;
            long sendTime = messagePdu.getTimestampMillis();

            if (messagePdu.isEmail()) {
                senderPhone = messagePdu.getDisplayOriginatingAddress();
                messageText = messagePdu.getDisplayMessageBody();
            } else {
                senderPhone = messagePdu.getOriginatingAddress();
                messageText = messagePdu.getMessageBody();
            }
        }
    }
}
