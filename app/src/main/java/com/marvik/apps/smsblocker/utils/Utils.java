package com.marvik.apps.smsblocker.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.marvik.apis.core.utilities.Utilities;
import com.marvik.apps.smsblocker.database.transactions.TransactionsManager;
import com.marvik.apps.smsblocker.infos.blocked.senders.BlockedMessageSendersInfo;
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

        if(prefsManager.isFirstRun()){
            indexMessageSendersAll();
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

        List<BlockedMessageSendersInfo> blockedMessageSendersInfos = getTransactionsManager().getBlockedMessageSendersInfo();

        for (BlockedMessageSendersInfo blockedMessageSendersInfo : blockedMessageSendersInfos) {
            if (messageSender.equals(blockedMessageSendersInfo.getMessageSenderAddress())) {
                blocked = blockedMessageSendersInfo.isBlocked();
            }
        }
        return blocked;
    }

    private void indexMessageSendersAll(){


    }

}
