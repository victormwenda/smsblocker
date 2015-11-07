package com.marvik.apps.smsblocker.preferences.userprefs;

import com.marvik.apps.smsblocker.preferences.declarations.user.IUserPreferences;

/**
 * Created by victor on 11/7/2015.
 */
public interface UserPreferences extends IUserPreferences {


    @Override
    public boolean isEnabled() ;

    @Override
    public void setEnabled(boolean enabled);
}
