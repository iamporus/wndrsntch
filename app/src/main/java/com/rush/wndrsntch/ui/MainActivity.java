package com.rush.wndrsntch.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;

import com.rush.wndrsntch.R;
import com.rush.wndrsntch.base.BaseActivity;
import com.rush.wndrsntch.data.network.APIClient;
import com.rush.wndrsntch.data.network.model.Stage;
import com.rush.wndrsntch.ui.stage.StageFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity
{

    public static final String STAGE_FRAGMENT = "StageFragment";
    private FragmentManager mFragmentManager;

    public static final String SETUP_STAGE_ACTION = "com.rush.bandersnatch.actions.SETUP_STAGE_ACTION";
    public static final String END_OF_LEVEL_ACTION = "com.rush.bandersnatch.actions.END_OF_LEVEL_ACTION";
    public static final String STAGE = "com.rush.bandersnatch.extras.STAGE";
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void attachBaseContext( Context newBase )
    {
        super.attachBaseContext( CalligraphyContextWrapper.wrap( newBase ) );
    }

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        mFragmentManager = getSupportFragmentManager();

        //TODO: inject APIClient
        APIClient apiClient = new APIClient();

        if( savedInstanceState == null )
        {
            Stage currentStage = apiClient.getStageById( 0 );
            StageFragment stageFragment = StageFragment.newInstance( currentStage );
            setFragment( stageFragment, STAGE_FRAGMENT, false );
        }

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction( SETUP_STAGE_ACTION );
        intentFilter.addAction( END_OF_LEVEL_ACTION );

        mBroadcastReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive( Context context, Intent intent )
            {
                if( intent.getAction() != null )
                {
                    switch( intent.getAction() )
                    {
                        case SETUP_STAGE_ACTION:
                        {
                            Stage nextStage = ( Stage ) intent.getSerializableExtra( STAGE );
                            StageFragment stageFragment = ( StageFragment ) mFragmentManager.findFragmentByTag( STAGE_FRAGMENT );
                            if( stageFragment != null && stageFragment.isVisible() )
                            {
                                stageFragment.setupStage( nextStage );
                            }
                            break;
                        }
                        case END_OF_LEVEL_ACTION:
                        {
                            showSnackBar( "You have reached the end.", Snackbar.LENGTH_SHORT );
                            break;
                        }
                    }
                }
            }
        };

        LocalBroadcastManager.getInstance( getApplicationContext() )
                .registerReceiver( mBroadcastReceiver, intentFilter );

    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        LocalBroadcastManager.getInstance( getApplicationContext() ).unregisterReceiver( mBroadcastReceiver );
    }

    private void setFragment( Fragment fragment, String fragmentTag, boolean bAddToBackstack )
    {
        if( fragment != null )
        {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.replace( R.id.fragmentContainer, fragment, fragmentTag );
            if( bAddToBackstack )
            {
                fragmentTransaction.addToBackStack( fragmentTag );
            }
            fragmentTransaction.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_FADE );
            fragmentTransaction.commit();
        }
    }
}
