package com.rush.wndrsntch.data.network;


import com.rush.wndrsntch.data.network.model.Stage;

import androidx.annotation.Nullable;

public interface IAPIClientHelper
{
    @Nullable
    Stage getStageById( int stageId );
}
