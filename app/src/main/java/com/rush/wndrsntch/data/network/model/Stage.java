package com.rush.wndrsntch.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Stage implements Serializable
{
    @SerializedName( "id" )
    @Expose
    private int mId;
    @SerializedName( "value" )
    @Expose
    private String mValue;
    @SerializedName( "nextStageId" )
    @Expose
    private int mNextStageId;
    @SerializedName( "choices" )
    @Expose
    private List< Choice > mChoices = null;

    public int getId()
    {
        return mId;
    }

    public void setId( int id )
    {
        this.mId = id;
    }

    public String getValue()
    {
        return mValue;
    }

    public void setValue( String value )
    {
        this.mValue = value;
    }

    public int getNextStageId()
    {
        return mNextStageId;
    }

    public void setNextStageId( int parent )
    {
        this.mNextStageId = parent;
    }

    public List< Choice > getChoices()
    {
        return mChoices;
    }

    public void setChoices( List< Choice > choices )
    {
        this.mChoices = choices;
    }

    @Override
    public String toString()
    {
        return mValue;
    }
}
