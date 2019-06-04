package com.rush.wndrsntch.ui.stage;

import com.rush.wndrsntch.base.IPresenter;
import com.rush.wndrsntch.data.network.model.Choice;

public interface IStagePresenter< V extends IStageView > extends IPresenter< V >
{
    void onChoiceSelected( Choice choice );

    void gotoStage( int currentStage );
}
