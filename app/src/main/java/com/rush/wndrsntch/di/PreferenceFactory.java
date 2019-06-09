package com.rush.wndrsntch.di;

import android.content.Context;

import com.rush.wndrsntch.data.preference.IPreferenceHelper;
import com.rush.wndrsntch.data.preference.PreferenceHelper;

import androidx.annotation.NonNull;

public class PreferenceFactory
{
    @NonNull
    public static IPreferenceHelper getInstance( @NonNull Context context,
                                                 @NonNull String fileName )
    {
        return new PreferenceHelper( context, fileName );
    }
}
