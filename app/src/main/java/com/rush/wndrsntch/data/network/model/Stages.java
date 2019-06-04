package com.rush.wndrsntch.data.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stages
{
    @SerializedName( "stages" )
    @Expose
    private List< Stage > stages = null;

    public List< Stage > getStages()
    {
        return stages;
    }

    public void setStages( List< Stage > stages )
    {
        this.stages = stages;
    }

}