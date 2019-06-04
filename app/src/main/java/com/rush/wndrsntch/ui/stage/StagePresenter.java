package com.rush.wndrsntch.ui.stage;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.rush.wndrsntch.base.BasePresenter;
import com.rush.wndrsntch.data.network.APIClient;
import com.rush.wndrsntch.data.network.model.Choice;
import com.rush.wndrsntch.data.network.model.Stage;
import com.rush.wndrsntch.ui.MainActivity;

public class StagePresenter< V extends IStageView > extends BasePresenter< V > implements
                                                                               IStagePresenter< V >
{
    private static final String TAG = "StagePresenter";

    @Override
    public void onChoiceSelected( Choice choice )
    {
        Log.d( TAG, "onChoiceSelected() called with: choice = [" + choice + "]" );
        int nextStageId = choice.getStageId();
        gotoStage( nextStageId );
    }

    @Override
    public void gotoStage( int stageId )
    {
        Log.d( TAG, "gotoStage() called with: stageId = [" + stageId + "]" );
        Stage stage = new APIClient().getStageById( stageId );
        if( stage != null )
        {
            Intent intent = new Intent();
            intent.setAction( MainActivity.SETUP_STAGE_ACTION );
            intent.putExtra( MainActivity.STAGE, stage );
            LocalBroadcastManager.getInstance( getBaseView().getContext() ).sendBroadcast( intent );
        }
        else
        {
            Intent intent = new Intent();
            intent.setAction( MainActivity.END_OF_LEVEL_ACTION );
            LocalBroadcastManager.getInstance( getBaseView().getContext() ).sendBroadcast( intent );
        }
    }
}
