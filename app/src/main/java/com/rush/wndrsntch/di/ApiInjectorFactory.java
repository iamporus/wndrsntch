package com.rush.wndrsntch.di;


import com.rush.wndrsntch.data.network.APIClient;
import com.rush.wndrsntch.data.network.IAPIClientHelper;

import androidx.annotation.NonNull;

public class ApiInjectorFactory
{
    @NonNull
    public static IAPIClientHelper getInstance()
    {
        return new APIClient();
    }
}
