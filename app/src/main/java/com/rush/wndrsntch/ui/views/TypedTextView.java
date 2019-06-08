package com.rush.wndrsntch.ui.views;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.google.common.base.Preconditions;

import java.util.Random;

public class TypedTextView extends AppCompatTextView
{
    public static final String TAG = "TypedTextView";
    private long mSentenceDelayMillis = 1500;
    private long mCursorBlinkDelayMillis = 530;
    private static final int TYPING_SEED = 100;
    private long mTypingDelayMillis = 175;
    private int mIndex;
    private CharSequence mText;
    private TypingUpdateListener mTypingUpdateListener;
    private boolean mbShowCursor = false;
    private boolean mbSplitSentences = false;
    private boolean mbEmitSound = false;
    private AudioPlayer mAudioPlayer;
    private int mAudioResId;
    private boolean mbRandomizeTypeDelay;

    public interface TypingUpdateListener
    {
        void onTypingUpdate( int index );
    }

    public TypedTextView( Context context )
    {
        super( context );
    }

    public TypedTextView( Context context, AttributeSet attrs )
    {
        super( context, attrs );
    }

    private Handler mHandler = new Handler();
    private Runnable mTypeWriter = new Runnable()
    {
        @Override
        public void run()
        {
            //extract characters by index
            CharSequence charSequence = mText.subSequence( 0, mIndex );

            //append cursor
            if( mbShowCursor && mIndex < mText.length() )
            {
                charSequence = charSequence + "|";
            }

            //play typing sound
            if( mbEmitSound && mbRandomizeTypeDelay )
            {
                mAudioPlayer.play();
                if( mTypingDelayMillis == 0 )
                {
                    mTypingDelayMillis = TYPING_SEED;
                }
                mTypingDelayMillis = TYPING_SEED + new Random().nextInt( ( int ) ( mTypingDelayMillis ) );
            }

            //set character by character
            setText( charSequence );

            if( mTypingUpdateListener != null )
            {
                mTypingUpdateListener.onTypingUpdate( mIndex );
            }

            if( mIndex < mText.length() )
            {
                mHandler.postDelayed( mTypeWriter, mTypingDelayMillis );

                if( mIndex != 0 && mText.charAt( mIndex - 1 ) == '.' )
                {
                    mHandler.removeCallbacks( mTypeWriter );
                    mHandler.postDelayed( mTypeWriter, mSentenceDelayMillis );
                    if( mbEmitSound )
                    {
                        mAudioPlayer.pause();
                    }
                }
                mIndex++;
            }
            else
            {
                mHandler.removeCallbacks( mTypeWriter );
                if( mbShowCursor )
                {
                    mHandler.postDelayed( mCursorProxyRunnable, mCursorBlinkDelayMillis );
                }
                if( mbEmitSound )
                {
                    mAudioPlayer.stop();
                }
            }
        }
    };

    private Runnable mCursorProxyRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            /*

            If TextView gravity is set to center, appending and removing pipe in each execution,
            re-aligns the text in order to keep it centered.

            To overcome this, an empty space is added which replaces pipe | in order to keep the text in same position.

            if cursor is not shown and empty space is not shown, append cursor.
            else Replace empty space with cursor/pipe.
            else Replace cursor/pipe with empty space.

            */
            CharSequence charSequence = mText;

            if( charSequence.charAt( charSequence.length() - 1 ) != '|' && charSequence.charAt( charSequence.length() - 1 ) != ' ' )
            {
                charSequence = charSequence + "|";
            }
            else if( charSequence.charAt( charSequence.length() - 1 ) == ' ' )
            {
                charSequence = charSequence.subSequence( 0, charSequence.length() - 1 );
                charSequence = charSequence + "|";
            }
            else
            {
                charSequence = charSequence.subSequence( 0, charSequence.length() - 1 );
                charSequence = charSequence + " ";
            }
            mText = charSequence;
            setText( charSequence );
            mHandler.postDelayed( mCursorProxyRunnable, mCursorBlinkDelayMillis );
        }
    };

    /**
     * Set text to be typed by the TypeWriter
     *
     * @param text                 String text to be typed
     * @param typingUpdateListener {@link TypingUpdateListener} Listener to listen to receive update on typed word
     */
    public void setTypedText( final @NonNull String text, final TypingUpdateListener typingUpdateListener )
    {
        Preconditions.checkNotNull( text );

        mText = mbSplitSentences ? splitSentences( text ) : text;

        mIndex = 0;
        setText( "" );
        mHandler.removeCallbacks( mTypeWriter );
        if( mbShowCursor )
        {
            mHandler.removeCallbacks( mCursorProxyRunnable );
        }
        mHandler.postDelayed( mTypeWriter, mTypingDelayMillis );
        if( mbEmitSound )
        {
            mAudioPlayer = new AudioPlayer( getContext(), mAudioResId );
        }
        mTypingUpdateListener = typingUpdateListener;
    }

    public void emitSound( boolean bEmitSound, @RawRes int resId )
    {
        mbEmitSound = mbRandomizeTypeDelay = bEmitSound;
        mAudioResId = resId;
    }

    private String splitSentences( @NonNull String text )
    {
        Preconditions.checkNotNull( text );
        int index = text.indexOf( '.' );
        int lastIndex = text.lastIndexOf( '.' );
        if( index != lastIndex )
        {
            //multiple sentences found.
            //introduce new lines for every full stop except the last one terminating string.
            do
            {
                text = text.replaceFirst( "\\. ", ".\n" );

                index = text.indexOf( '.', index + 1 );
                lastIndex = text.lastIndexOf( '.' );

            } while( index != -1 && index != lastIndex );
        }

        return text;
    }

    /**
     * Show cursor while typing
     *
     * @param bShowCursor boolean display blinking cursor while typing.
     */
    public void showCursor( final boolean bShowCursor )
    {
        this.mbShowCursor = bShowCursor;
    }

    /**
     * Split sentences on a new line.
     *
     * @param bSplitSentences boolean Type Writer splits sentences onto new line based on fullstops
     *                        found in the passed string
     */
    public void splitSentences( final boolean bSplitSentences )
    {
        this.mbSplitSentences = bSplitSentences;
    }

    /**
     * Set duration to wait after every sentence
     *
     * @param sentenceDelayMillis long duration in milliseconds to wait after every sentence
     */
    public void setSentenceDelay( final long sentenceDelayMillis )
    {
        mSentenceDelayMillis = sentenceDelayMillis;
    }

    /**
     * Set duration to wait after every cursor blink
     *
     * @param cursorBlinkDelayMillis long duration in milliseconds between every cursor blink
     */
    public void setCursorBlinkDelay( final long cursorBlinkDelayMillis )
    {
        mCursorBlinkDelayMillis = cursorBlinkDelayMillis;
    }

    /**
     * Set duration to wait after every character typed
     *
     * @param typingDelayMillis long duration in milliseconds to wait after every character typed
     */
    public void setTypingDelay( final long typingDelayMillis )
    {
        mTypingDelayMillis = typingDelayMillis;
    }
}