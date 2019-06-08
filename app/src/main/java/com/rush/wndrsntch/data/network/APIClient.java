package com.rush.wndrsntch.data.network;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.rush.wndrsntch.data.network.model.Stage;
import com.rush.wndrsntch.data.network.model.Stages;

import java.util.ArrayList;

public class APIClient implements IAPIClientHelper
{
    private static final String TAG = "APIClient";

    //TODO: fetch from server.
    private String json = "{\n" +
            "  \"stages\": [\n" +
            "    {\n" +
            "      \"id\": 0,\n" +
            "      \"value\": \"You are in a jungle. Tap here.\",\n" +
            "      \"nextStageId\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"value\": \"You see two roads in front of you.\",\n" +
            "      \"nextStageId\": 2\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"value\": \"Which one would you choose?\",\n" +
            "      \"choices\": [\n" +
            "        {\n" +
            "          \"choiceId\": 0,\n" +
            "          \"stageId\": 3,\n" +
            "          \"choice\": \"Less travelled.\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"choiceId\": 1,\n" +
            "          \"stageId\": 6,\n" +
            "          \"choice\": \"More travelled.\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"value\": \"Look around. See, there's a rare footprint on the ground.\",\n" +
            "      \"nextStageId\": 4\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"value\": \"Here comes a tiger from the bush. He attacks on you. And you die.\",\n" +
            "      \"nextStageId\": 5\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"value\": \"The End.\",\n" +
            "      \"nextStageId\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 6,\n" +
            "      \"value\": \"You seem like a reserved person to me. Anyway. Keep walking.\",\n" +
            "      \"nextStageId\": 7\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 7,\n" +
            "      \"value\": \"There is a nicely made pavement in front of you.\",\n" +
            "      \"nextStageId\": 8\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 8,\n" +
            "      \"value\": \"You keep walking. You saw the exit and you walk out of the jungle. You live to live another day.\",\n" +
            "      \"nextStageId\": 5\n" +
            "    }\n" +
            "  ]\n" +
            "}";

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
}
