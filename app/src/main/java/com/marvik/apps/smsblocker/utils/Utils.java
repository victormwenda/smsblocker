package com.marvik.apps.smsblocker.utils;

import android.content.Context;

import com.marvik.apis.core.utilities.Utilities;
import com.marvik.apps.smsblocker.database.transactions.TransactionsManager;
import com.marvik.apps.smsblocker.preferences.manager.PrefsManager;

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
}
