package com.rush.wndrsntch.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Choice implements Serializable
{
    @SerializedName( "choiceId" )
    @Expose
    private int mChoiceId;
    @SerializedName( "stageId" )
    @Expose
    private int mStageId;
    @SerializedName( "choice" )
    @Expose
    private String mChoice;

    public int getChoiceId()
    {
        return mChoiceId;
    }

    public void setChoiceId( int choiceId )
    {
        this.mChoiceId = choiceId;
    }

    public int getStageId()
    {
        return mStageId;
    }

    public void setStageId( int stageId )
    {
        this.mStageId = stageId;
    }

    public String getChoice()
    {
        return mChoice;
    }

    public void setChoice( String choice )
    {
        this.mChoice = choice;
    }

    @Override
    public String toString()
    {
        return "Choice{" +
                "mChoiceId=" + mChoiceId +
                ", mStageId=" + mStageId +
                ", mChoice='" + mChoice + '\'' +
                '}';
    }
}
