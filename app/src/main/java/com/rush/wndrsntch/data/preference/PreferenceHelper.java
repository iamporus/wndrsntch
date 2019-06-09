package com.rush.wndrsntch.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.rush.wndrsntch.AppConstants;

import androidx.annotation.NonNull;

public class PreferenceHelper implements IPreferenceHelper
{
    private final SharedPreferences mSharedPrefs;
    private final static String PREF_USER_STATE = "PREF_USER_STATE";

    public PreferenceHelper( @NonNull Context context, @NonNull String fileName )
    {
        mSharedPrefs = context.getSharedPreferences( fileName, Context.MODE_PRIVATE );
    }

    @Override
    public AppConstants.UserState getUserState()
    {
        return AppConstants.UserState.getUserStateByVal( mSharedPrefs.getInt( PREF_USER_STATE, 0 ) );

    }

    @Override
    public void setUserState( AppConstants.UserState state )
    {
        mSharedPrefs.edit().putInt( PREF_USER_STATE, state.getVal() ).apply();
    }
}
