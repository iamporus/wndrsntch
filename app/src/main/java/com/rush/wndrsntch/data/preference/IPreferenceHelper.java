package com.rush.wndrsntch.data.preference;

import com.rush.wndrsntch.AppConstants;

public interface IPreferenceHelper
{
    AppConstants.UserState getUserState();

    void setUserState( AppConstants.UserState state );
}
