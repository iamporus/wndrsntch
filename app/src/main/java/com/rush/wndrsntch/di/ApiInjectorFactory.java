package com.rush.wndrsntch.di;


import android.content.res.Resources;

import com.rush.wndrsntch.data.network.APIClient;
import com.rush.wndrsntch.data.network.IAPIClientHelper;

import androidx.annotation.NonNull;

public class ApiInjectorFactory
{
    @NonNull
    public static IAPIClientHelper getInstance( Resources resources )
    {
        return APIClient.getInstance(resources);
    }
}
