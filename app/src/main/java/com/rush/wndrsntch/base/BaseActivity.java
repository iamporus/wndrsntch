package com.rush.wndrsntch.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

@SuppressLint( "Registered" )
public class BaseActivity extends AppCompatActivity implements IView
{
    @Override
    public void showToast( String message, int length )
    {
        Toast.makeText( getApplicationContext(), message, length ).show();
    }

    @Override
    public void showToast( int resId, int length )
    {
        showToast( getString( resId ), length );
    }

    @Override
    public void showSnackBar( String message, int length )
    {
        Snackbar snackbar = Snackbar.make( findViewById( android.R.id.content ), message, length );
        snackbar.show();
    }

    @Override
    public void showSnackBar( int resId, int length )
    {
        showSnackBar( getString( resId ), length );
    }

    @Override
    public Context getContext()
    {
        return BaseActivity.this;
    }
}
