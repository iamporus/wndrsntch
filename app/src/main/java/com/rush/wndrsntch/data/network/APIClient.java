package com.rush.wndrsntch.data.network;

import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.rush.wndrsntch.data.network.model.Stage;
import com.rush.wndrsntch.data.network.model.Stages;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class APIClient implements IAPIClientHelper
{
    private static final String TAG = "APIClient";

    private static String json;

    private APIClient()
    {
    }

    //TODO: create lazy singleton
    public static APIClient getInstance( Resources resources )
    {
        json = loadJSONFromAsset( resources );
        return new APIClient();
    }


    @Nullable
    @Override
    public Stage getStageById( int stageId )
    {
        Log.d( TAG, "getStageById() called with: stageId = [" + stageId + "]" );
        Gson gson = new Gson();

        Stages level = gson.fromJson( json, Stages.class );
        ArrayList< Stage > stages = ( ArrayList< Stage > ) level.getStages();
        for( Stage stage : stages )
        {
            if( stage.getId() == stageId )
            {
                return stage;
            }
        }
        return null;
    }

    @Nullable
    private static String loadJSONFromAsset( @NonNull Resources resources )
    {
        String json;
        try
        {
            InputStream is = resources.getAssets().open( "bndrsntch.json" );
            int size = is.available();
            byte[] buffer = new byte[ size ];
            is.read( buffer );
            is.close();
            json = new String( buffer, "UTF-8" );
        }
        catch ( IOException ex )
        {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
