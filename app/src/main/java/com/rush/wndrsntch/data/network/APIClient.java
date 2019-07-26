package com.rush.wndrsntch.data.network;

import android.util.Log;

import com.google.gson.Gson;
import com.rush.wndrsntch.data.network.model.Stage;
import com.rush.wndrsntch.data.network.model.Stages;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class APIClient implements IAPIClientHelper
{
    private static final String TAG = "APIClient";

    //TODO: fetch from server.

    private String json = "{\n" +
            "  \"stages\": [" + "\n" +
            "    {\n" +
            "      \"id\": 0,\n" +
            "      \"value\": \"This game is a choose your own adventure story.\nSo, the story will have multiple endings.\nAs you progress, you\'ll find choices at certain stages.\nEvery choice will lead you to a different ending.\n\nSo...\nAre you ready to play the game?\",\n" +
            "      \"choices\": [\n" +
            "        {\n" +
            "          \"choiceId\": 0,\n" +
            "          \"stageId\": 5,\n" +
            "          \"choice\": \"Yeah.\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"choiceId\": 1,\n" +
            "          \"stageId\": 5,\n" +
            "          \"choice\": \"Hell yeah.\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"value\": \"You are in a jungle.\nThere are two roads in front of you.\nWhich one would you choose?\",\n" +
            "      \"choices\": [\n" +
            "        {\n" +
            "          \"choiceId\": 0,\n" +
            "          \"stageId\": 8,\n" +
            "          \"choice\": \"Less travelled\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"choiceId\": 1,\n" +
            "          \"stageId\": 11,\n" +
            "          \"choice\": \"More travelled\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 8,\n" +
            "      \"value\": \"Look around. See there's a rare footprint on the ground.\nHere comes a tiger from the bush. He attacks on you. And you die.\nThe End.\",\n" +
            "      \"choices\": [\n" +
            "        {\n" +
            "          \"choiceId\": 0,\n" +
            "          \"stageId\": 5,\n" +
            "          \"choice\": \"Play again.\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"choiceId\": 1,\n" +
            "          \"stageId\": 0,\n" +
            "          \"choice\": \"Go to Next Level.\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 11,\n" +
            "      \"value\": \"You seem like a reserved person to me. Anyway. Keep walking.\nThere is a nicely made pavement in front of you.\nYou keep walking. You saw the exit and you walk out of the jungle. You live to live another day.\nThe End.\",\n" +
            "      \"choices\": [\n" +
            "        {\n" +
            "          \"choiceId\": 0,\n" +
            "          \"stageId\": 5,\n" +
            "          \"choice\": \"Play again.\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"choiceId\": 1,\n" +
            "          \"stageId\": 0,\n" +
            "          \"choice\": \"Go to Next Level.\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
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
