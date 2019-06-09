package com.rush.wndrsntch.base;

import android.content.Context;

import androidx.annotation.StringRes;

public interface IView
{
    void showToast( String message, int length );

    void showToast( @StringRes int resId, int length );

    void showSnackBar( String message, int length );

    void showSnackBar( @StringRes int message, int length );

    Context getContext();
}
