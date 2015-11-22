package com.marvik.apps.smsblocker.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Telephony;

import com.marvik.apis.core.utilities.Utilities;
import com.marvik.apps.smsblocker.database.schemas.Tables;
import com.marvik.apps.smsblocker.database.transactions.TransactionsManager;
import com.marvik.apps.smsblocker.infos.blocked.senders.SmsSendersInfo;
import com.marvik.apps.smsblocker.preferences.manager.PrefsManager;

import java.util.List;

/**
 * Created by victor on 11/7/2015.
 */
public class Utils {

    private Context context;

    private PrefsManager prefsManager;
    private Utilities utilities;
    private TransactionsManager transactionsManager;

    public Utils(Context context) {
        initAll(context);
    }

    private void initAll(Context context) {
        this.context = context;

        utilities = new Utilities(getContext());
        transactionsManager = new TransactionsManager(getContext());

        prefsManager = new PrefsManager(getContext());

        if (getPrefsManager().isFirstRun()) {
            indexMessageSendersAll();
            getPrefsManager().setFirstRun(true);
        }
    }

    public Context getContext() {
        return context;
    }

    public Utilities getUtilities() {
        return utilities;
    }

    public TransactionsManager getTransactionsManager() {
        return transactionsManager;
    }

    public PrefsManager getPrefsManager() {
        return prefsManager;
    }

    public String getHumanFriendlySenderName(String phonenumber) {
        return getUtilities().getContactDisplayName(phonenumber);
    }

    public Bitmap getHumanFriendlySenderAvatar(String phonenumber) {
        return getUtilities().getContactAvatar(phonenumber);
    }

    public boolean isSenderBlocked(String messageSender) {

        boolean blocked = false;

        List<SmsSendersInfo> smsSendersInfos = getTransactionsManager().getSmsSendersInfo(messageSender);

        for (SmsSendersInfo smsSendersInfo : smsSendersInfos) {
            if (messageSender.equals(smsSendersInfo.getMessageSenderAddress())) {
                blocked = smsSendersInfo.isBlocked();
            }
        }
        return blocked;
    }

    private void indexMessageSendersAll() {

        Uri conversationsUri = Uri.parse("content://sms/conversations");
        String[] projection = {Telephony.Sms.Conversations.ADDRESS};
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;

        Cursor cursor = getContext().getContentResolver().query(conversationsUri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndex(projection[0]));


                Uri uri = Tables.SMSSenders.CONTENT_URI;
                String[] columns = {Tables.SMSSenders.COL_SENDER_ADDRESS};
                String[] columnValues = {address};
                if (!getTransactionsManager().isExists(uri, columns, columnValues)) {
                    getTransactionsManager().saveMessageSender(address);
                }
            }
        }


        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

    }

    public String getPersonContactPhoneNumber(Uri contactUri) {
        return getUtilities().getPersonContactsDataItem(contactUri, ContactsContract.CommonDataKinds.Phone.NUMBER);
    }
}
