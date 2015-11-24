package com.marvik.apps.smsblocker.preferences.declarations.system;

/**
 * Created by victor on 11/22/2015.
 */
public interface ISystemPreferences {

    void setFirstRun(boolean firstRun);

    boolean isFirstRun();

    void setLastKnownSenderAddress(String senderPhone);

    String getLastKnownSenderAddress();
}
