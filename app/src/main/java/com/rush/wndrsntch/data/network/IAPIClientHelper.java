package com.rush.wndrsntch.data.network;

import android.support.annotation.Nullable;

import com.rush.wndrsntch.data.network.model.Stage;

public interface IAPIClientHelper
{
    @Nullable
    Stage getStageById( int stageId );
}
