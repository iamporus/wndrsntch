package com.rush.wndrsntch.base;

public class BasePresenter< V extends IView > implements IPresenter< V >
{
    private V mView;

    public BasePresenter()
    {
        //Inject global dependencies through this constructor
    }

    @Override
    public void onAttach( V view )
    {
        mView = view;
    }

    @Override
    public void onDetach()
    {
        mView = null;
    }

    @Override
    public V getBaseView()
    {
        return mView;
    }

    @Override
    public boolean isViewAttached()
    {
        return mView != null;
    }
}
