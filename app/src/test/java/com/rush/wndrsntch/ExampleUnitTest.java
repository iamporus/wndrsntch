package com.rush.wndrsntch;

import com.google.gson.Gson;
import com.rush.wndrsntch.data.network.model.Choice;
import com.rush.wndrsntch.data.network.model.Stage;
import com.rush.wndrsntch.data.network.model.Stages;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest
{

    private String json = "{\n" +
            "  \"stages\": [\n" +
            "    {\n" +
            "      \"id\": 1,\n" +
            "      \"value\": \"You are in a jungle.\",\n" +
            "      \"parent\": 0,\n" +
            "      \"choices\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 2,\n" +
            "      \"value\": \"You see two roads in front of you.\",\n" +
            "      \"parent\": 1,\n" +
            "      \"choices\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3,\n" +
            "      \"value\": \"Which one would you choose?\",\n" +
            "      \"parent\": 2,\n" +
            "      \"choices\": [\n" +
            "        {\n" +
            "          \"choiceId\": 0,\n" +
            "          \"stageId\": 4,\n" +
            "          \"choice\": \"Take the less travelled.\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"choiceId\": 1,\n" +
            "          \"stageId\": 7,\n" +
            "          \"choice\": \"Take the more travelled.\"\n" +
            "        }\n" +
            "      ]\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 4,\n" +
            "      \"parent\": 3,\n" +
            "      \"value\": \"Look around. See there's a rare footprint on the ground.\",\n" +
            "      \"choices\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 5,\n" +
            "      \"parent\": 4,\n" +
            "      \"value\": \"Here comes an animal from the bush. And you die.\",\n" +
            "      \"choices\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 6,\n" +
            "      \"parent\": 5,\n" +
            "      \"value\": \"The End.\",\n" +
            "      \"choices\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 7,\n" +
            "      \"parent\": 3,\n" +
            "      \"value\": \"You seem like a reserved person to me. Anyway. Keep walking.\",\n" +
            "      \"choices\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 8,\n" +
            "      \"parent\": 7,\n" +
            "      \"value\": \"There is a nicely made pavement in front of you.\",\n" +
            "      \"choices\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 9,\n" +
            "      \"parent\": 8,\n" +
            "      \"value\": \"You keep walking. You saw find the exit. You win.\",\n" +
            "      \"choices\": []\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 10,\n" +
            "      \"parent\": 9,\n" +
            "      \"value\": \"The End.\",\n" +
            "      \"choices\": []\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Test
    public void addition_isCorrect()
    {
        assertEquals( 4, 2 + 2 );
    }


    @Test
    public void create_sampleTree()
    {
        // create a json
        // traverse it and create tree accordingly
        // pop root and show it
        // pop next and show it
        // if next has choices, spawn buttons
        // get choice from user and pop node for choice from json
        // pop next and show it

        Gson gson = new Gson();

        Stages level = gson.fromJson( json, Stages.class );
        int totalLevels = level.getStages().size();

        for( int currentLevel = 0; currentLevel < totalLevels; currentLevel++ )
        {
            Stage stage = getStageById( currentLevel );
            if( stage != null )
            {
                if( currentLevel == 0 )
                {
                    System.out.println( "Root - " + stage.getValue() );
                }
                else if( stage.getChoices() != null && stage.getChoices().size() > 0 )
                {
                    System.out.println( stage.getId() + " : " + stage.getValue() );
                    for( Choice choice : stage.getChoices() )
                        System.out.println( " Choice - " + choice.getChoice() );
                }
                else
                {
                    System.out.println( stage.getId() + " : " + stage.getValue() );
                }
            }
        }
    }

    private Stage getStageById( int parentId )
    {
        Gson gson = new Gson();

        Stages level = gson.fromJson( json, Stages.class );
        ArrayList< Stage > stages = ( ArrayList< Stage > ) level.getStages();
        for( Stage stage : stages )
        {
            if( stage.getNextStageId() == parentId )
            {
                return stage;
            }
        }
        return null;
    }
}