package com.rush.wndrsntch.ui.splash;

import android.content.Intent;
import android.os.Bundle;

import com.rush.wndrsntch.R;
import com.rush.wndrsntch.base.BaseActivity;
import com.rush.wndrsntch.ui.MainActivity;

public class SplashActivity extends BaseActivity implements ISplashView
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
    }

    @Override
    public void navigateToLaunchScreen()
    {
        Intent intent = new Intent( SplashActivity.this, MainActivity.class );
        startActivity( intent );
    }

    @Override
    public void startIntro()
    {

    }
}
