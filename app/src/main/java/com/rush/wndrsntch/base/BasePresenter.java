package com.rush.wndrsntch.base;

import com.rush.wndrsntch.data.network.IAPIClientHelper;
import com.rush.wndrsntch.data.preference.IPreferenceHelper;

public class BasePresenter< V extends IView > implements IPresenter< V >
{
    private final IAPIClientHelper mIApiClientHelper;
    private final IPreferenceHelper mIPreferenceHelper;
    private V mView;

    public BasePresenter( IAPIClientHelper iapiClientHelper, IPreferenceHelper iPreferenceHelper )
    {
        mIApiClientHelper = iapiClientHelper;
        mIPreferenceHelper = iPreferenceHelper;
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

    public IAPIClientHelper getIApiClientHelper()
    {
        return mIApiClientHelper;
    }

    public IPreferenceHelper getIPreferenceHelper()
    {
        return mIPreferenceHelper;
    }
}
