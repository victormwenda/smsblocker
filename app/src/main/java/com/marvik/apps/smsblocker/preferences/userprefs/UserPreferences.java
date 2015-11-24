package com.marvik.apps.smsblocker.preferences.userprefs;

import com.marvik.apps.smsblocker.preferences.declarations.system.ISystemPreferences;
import com.marvik.apps.smsblocker.preferences.declarations.user.IUserPreferences;

/**
 * Created by victor on 11/7/2015.
 */
public interface UserPreferences extends IUserPreferences, ISystemPreferences {


    @Override
    public boolean isEnabled();

    @Override
    public void setEnabled(boolean enabled);

    @Override
    boolean isFirstRun();

    @Override
    void setFirstRun(boolean firstRun);

    @Override
    String getLastKnownSenderAddress();

    @Override
    void setLastKnownSenderAddress(String senderPhone);
}
