package com.marvik.apps.smsblocker.database.transactions;

import android.content.Context;

/**
 * Created by victor on 11/7/2015.
 */
public class TransactionsManager {

    private Context context;

    public TransactionsManager(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
