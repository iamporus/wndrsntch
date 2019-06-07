package com.rush.wndrsntch.ui.views;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;

public class TypedTextView extends AppCompatTextView
{
    public static final String TAG = "TypedTextView";
    public static final long SENTENCE_DELAY_MILLIS = 1500;
    public static final long CURSOR_BLINK_DELAY_MILLIS = 530;
    private static final long TYPING_DELAY_MILLIS = 175;
    private int mIndex;
    private CharSequence mText;
    private TypingUpdateListener mTypingUpdateListener;

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
            Log.d( TAG, "run: " + mText.subSequence( 0, mIndex ) );
            CharSequence charSequence = mText.subSequence( 0, mIndex );
            if( mIndex < mText.length() )
            {
                charSequence = charSequence + "|";
            }
            setText( charSequence );
            mTypingUpdateListener.onTypingUpdate( mIndex );

            if( mIndex < mText.length() )
            {
                mHandler.postDelayed( mTypeWriter, TYPING_DELAY_MILLIS );
                if( mIndex != 0 && mText.charAt( mIndex - 1 ) == '.' )
                {
                    mHandler.removeCallbacks( mTypeWriter );
                    mHandler.postDelayed( mTypeWriter, SENTENCE_DELAY_MILLIS );
                }
                mIndex++;
            }
            else
            {
                mHandler.removeCallbacks( mTypeWriter );
                mHandler.postDelayed( mCursorProxyRunnable, CURSOR_BLINK_DELAY_MILLIS );
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
            mHandler.postDelayed( mCursorProxyRunnable, CURSOR_BLINK_DELAY_MILLIS );
        }
    };

    public void setTypedText( String text, TypingUpdateListener typingUpdateListener )
    {
        int index = text.indexOf( '.' );
        int lastIndex = text.lastIndexOf( '.' );
        if( index != lastIndex )
        {
            //multiple sentences found.
            //introduce new lines for every full stop except the last one terminating string.
            do
            {
                mText = text.replaceFirst( "\\.", ".\n" );
                index = text.indexOf( '.', index + 1 );
            } while( index != -1 && index != lastIndex );
        }
        else
        {
            //single sentence found.
            mText = text;
        }
        mIndex = 0;
        setText( "" );
        mHandler.removeCallbacks( mTypeWriter );
        mHandler.removeCallbacks( mCursorProxyRunnable );
        mHandler.postDelayed( mTypeWriter, TYPING_DELAY_MILLIS );
        mTypingUpdateListener = typingUpdateListener;
    }
}