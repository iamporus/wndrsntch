package com.rush.wndrsntch.ui.views;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;

import com.google.common.base.Preconditions;

public class AudioPlayer implements MediaAdapter
{
    private MediaPlayer mMediaPlayer;
    private boolean mbInit;

    AudioPlayer( @NonNull Context context, @RawRes int audioResId )
    {
        Preconditions.checkNotNull( context );
        mMediaPlayer = MediaPlayer.create( context, audioResId );
        mbInit = true;
    }

    @Override
    public void play()
    {
        if( mbInit )
        {
            mMediaPlayer.start();
        }
    }

    @Override
    public void pause()
    {
        if( mbInit )
        {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void resume()
    {
        if( mbInit )
        {
            mMediaPlayer.start();
        }
    }

    @Override
    public void stop()
    {
        if( mbInit )
        {
            mMediaPlayer.stop();
        }
    }
}
