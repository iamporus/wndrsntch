package com.rush.wndrsntch.base;

import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements IView
{
    @Override
    public void showToast( String message, int length )
    {
        if( getActivity() != null )
        {
            Toast.makeText( getActivity().getApplicationContext(), message, length ).show();
        }
    }

    @Override
    public void showToast( int resId, int length )
    {
        showToast( getString( resId ), length );
    }

    @Override
    public void showSnackBar( String message, int length )
    {
        if( getActivity() != null )
        {
            Snackbar snackbar = Snackbar.make( getActivity().findViewById( android.R.id.content ), message, length );
            snackbar.show();
        }
    }

    @Override
    public void showSnackBar( int resId, int length )
    {
        showSnackBar( getString( resId ), length );
    }
}
