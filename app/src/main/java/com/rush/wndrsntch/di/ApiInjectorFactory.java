package com.rush.wndrsntch.di;

import android.support.annotation.NonNull;

import com.rush.wndrsntch.data.network.APIClient;
import com.rush.wndrsntch.data.network.IAPIClientHelper;

public class ApiInjectorFactory
{
    @NonNull
    public static IAPIClientHelper getInstance()
    {
        return new APIClient();
    }
}
