package com.rush.wndrsntch.base;

public interface IPresenter< V extends IView >
{
    void onAttach( V view );

    void onDetach();

    V getBaseView();

    boolean isViewAttached();

}
